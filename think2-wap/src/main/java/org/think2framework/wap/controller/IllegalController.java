package org.think2framework.wap.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.think2framework.common.*;
import org.think2framework.common.exception.SimpleException;
import org.think2framework.context.ConstantFactory;
import org.think2framework.context.ModelFactory;
import org.think2framework.orm.Query;
import org.think2framework.orm.Writer;
import org.think2framework.wap.bean.illegal.IllegalBinding;
import org.think2framework.wap.bean.illegal.IllegalVehicleInfo;
import org.think2framework.wap.bean.illegal.VehicleLicense;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhoubin on 16/7/18. 违章控制器
 */
@Controller
@RequestMapping(value = "/illegal")
public class IllegalController extends BaseController {

	private static Logger logger = LogManager.getLogger(IllegalController.class); // log4j日志对象

	private static Integer TYPE_VEHICLE; // 车辆违章

	private static Integer TYPE_DRIVING; // 驾驶证违章

	private static String URL_VEHICLE; // 车辆违章查询接口地址

	private static String URL_PIC; // 车辆违章图片接口地址

	public IllegalController() {
		TYPE_VEHICLE = NumberUtils.toInt(ConstantFactory.get("illegalType", "vehicle"));
		TYPE_DRIVING = NumberUtils.toInt(ConstantFactory.get("illegalType", "driving"));
		URL_VEHICLE = ConstantFactory.get("illegalUrl", "vehicle");
		URL_PIC = ConstantFactory.get("illegalUrl", "pic");
	}

	/**
	 * 获取用户绑定的违章信息(车辆和驾驶证)
	 * 
	 * @param request
	 *            页面请求
	 * @param response
	 *            页面相应
	 */
	@RequestMapping(value = {"/binding"})
	public void getUserBindingInfo(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("u");
		String type = request.getParameter("t");
		// 如果用户id为null或者空则返回空
		if (StringUtils.isBlank(userId)) {
			throw new SimpleException("20001");
		}
		Query query = ModelFactory.createQuery(IllegalBinding.class);
		query.eq("user_id", userId); // 过滤用户id
		// 如果有类型过滤则过滤类型,如果没有则取全部的数据
		if (StringUtils.isNotBlank(type)) {
			query.eq("illegal_binding_type", type);
		}
		List<IllegalBinding> illegalBindings = query.queryForList(IllegalBinding.class);
		writeData(response, illegalBindings);
	}

	/**
	 * 校验车辆或者驾驶证是否正确,如果车辆或者驾驶证已经缓存到数据库则表示正确,如果数据库没有则调用接口获取数据,如果接口返回正确数据,
	 * 则将数据缓存到数据库返回正确,否则返回错误
	 * 
	 * @param request
	 *            页面请求
	 * @param response
	 *            页面相应
	 */
	@RequestMapping(value = {"/check"})
	public void checkIllegal(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("k"); // 车牌号或驾驶证号
		String value = request.getParameter("v"); // 车架号或档案编号
		String userId = request.getParameter("u"); // 用户id
		String type = request.getParameter("t"); // 类型-车辆违章或者驾驶证违章
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			throw new SimpleException("20002");
		}
		if (TYPE_VEHICLE.toString().equals(type)) {
			if (!checkVehicleFromDb(key, value, userId)) {
				checkVehicleFromInterface(key, value, userId);
			}
			writeData(response, "success");
		} else if (TYPE_DRIVING.toString().equals(type)) {

		} else {
			throw new SimpleException("20003");
		}
	}

	/**
	 * 将车辆信息保存到数据库
	 * 
	 * @param plateNo
	 *            车牌号
	 * @param vin
	 *            车架号
	 */
	private void saveVehicleLicense(String plateNo, String vin) {
		// 将车辆信息写入数据库,如果有绑定人员则绑定
		Writer writer = ModelFactory.createWriter(VehicleLicense.class);
		VehicleLicense vehicleLicense = new VehicleLicense();
		vehicleLicense.setPlateNo(plateNo);
		vehicleLicense.setVin(vin);
		writer.save(vehicleLicense, "vehicle_plate_no");
	}

	/**
	 * 校验车辆在数据库是否存在,如果已经存在则更新车辆信息和绑定信息
	 * 
	 * @param plateNo
	 *            车牌号
	 * @param vin
	 *            车架号
	 * @param userId
	 *            用户id
	 * @return 是否存在车辆
	 */
	private boolean checkVehicleFromDb(String plateNo, String vin, String userId) {
		Query query = ModelFactory.createQuery(VehicleLicense.class);
		query.eq("vehicle_plate_no", plateNo);
		query.leftLike("vehicle_vin", vin);
		VehicleLicense vehicleLicense = query.queryForObject(VehicleLicense.class);
		// 如果车辆已经存在则认为校验正确
		if (null == vehicleLicense) {
			return false;
		} else {
			// 更新车辆信息,主要是兼容原来接口车架号是6位,新接口是7位
			if (!vin.equals(vehicleLicense.getVin())) {
				saveVehicleLicense(plateNo, vin);
			}
			// 如果有绑定用户则将车辆和用户绑定
			if (StringUtils.isNotBlank(userId)) {
				bindingUser(plateNo, vin, userId, TYPE_VEHICLE);
			}
			return true;
		}
	}

	/**
	 * 校验车辆接口是否能返回违章数据,如果能正确返回则保存违章、车辆和绑定信息到数据库
	 * 
	 * @param key
	 *            车牌号
	 * @param value
	 *            车架号
	 * @param userId
	 *            用户id
	 */
	private void checkVehicleFromInterface(String key, String value, String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cph", key);
		params.put("cjh", value);
		params.put("from", DatetimeUtils.toLong((Calendar.getInstance().get(Calendar.YEAR) - 1) + "-01-01 00:00:00"));
		params.put("to", DatetimeUtils.now());
		Map<String, Object> result;
		try {
			String illegal = HttpClientUtils.post(URL_VEHICLE, params);
			result = JsonUtils.readString(illegal, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			throw new SimpleException("20005");
		}
		String errCode = result.get("code").toString();
		if ("10006".equals(errCode) || !"0".equals(errCode)) {
			throw new SimpleException("20004");
		}
		// 解析接口返回的违章信息并存入数据库
		Map<String, Object> data = (Map<String, Object>) result.get("data");
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("items");
		List<IllegalVehicleInfo> infoList = new ArrayList<>();
		for (Map<String, Object> map : list) {
			IllegalVehicleInfo illegalVehicleInfo = new IllegalVehicleInfo();
			illegalVehicleInfo.setModifyTime(DatetimeUtils.now().intValue());
			illegalVehicleInfo.setVin(value);
			illegalVehicleInfo.setFileNo(StringUtils.toString(map.get("surveil_file_no")));
			illegalVehicleInfo.setVehicle(StringUtils.toString(map.get("vehicle_type")));
			illegalVehicleInfo.setPlateNo(StringUtils.toString(map.get("vehicle_plate_no")));
			Long time = Long.valueOf(map.get("violation_time").toString()) / 1000;
			illegalVehicleInfo.setTime(time.intValue());
			illegalVehicleInfo.setAct(StringUtils.toString(map.get("violation_act")));
			illegalVehicleInfo.setAddress(StringUtils.toString(map.get("violation_address")));
			illegalVehicleInfo.setScore(NumberUtils.toInt(map.get("violation_score")));
			illegalVehicleInfo.setAmount(NumberUtils.toInt(map.get("violation_amount")));
			illegalVehicleInfo.setType(StringUtils.toString(map.get("violation_type")));
			illegalVehicleInfo.setStatus(NumberUtils.toInt(map.get("violation_status")));
			illegalVehicleInfo.setProcessedBy(StringUtils.toString(map.get("violation_processed_by")));
			infoList.add(illegalVehicleInfo);
		}
		if (infoList.size() > 0) {
			Writer writer = ModelFactory.createWriter(IllegalVehicleInfo.class);
			writer.batchSave(infoList, "surveil_file_no");
		}
		// 将车辆信息写入数据库,如果有绑定人员则绑定
		saveVehicleLicense(key, value);
		if (StringUtils.isNotBlank(userId)) {
			bindingUser(key, value, userId, TYPE_VEHICLE);
		}
	}

	/**
	 * 绑定用户,如果用户已经绑定过了则不再重复绑定
	 * 
	 * @param key
	 *            车牌号或者驾驶证号
	 * @param value
	 *            车架号或者档案编号
	 * @param userId
	 *            用户id
	 * @param type
	 *            1-车辆 2-驾驶证
	 */
	private void bindingUser(String key, String value, String userId, Integer type) {
		if (StringUtils.isBlank(userId)) {
			return;
		}
		Query query = ModelFactory.createQuery(IllegalBinding.class);
		query.eq("user_id", userId);
		query.eq("illegal_binding_info", key);
		query.eq("illegal_binding_detail", value);
		query.eq("illegal_binding_type", type);
		if (query.queryForCount() == 0) {
			Writer bindingWriter = ModelFactory.createWriter(IllegalBinding.class);
			IllegalBinding illegalBinding = new IllegalBinding();
			illegalBinding.setBinding(key);
			illegalBinding.setDetail(value);
			illegalBinding.setType(type);
			illegalBinding.setUserId(userId);
			bindingWriter.insert(illegalBinding);
		}
	}

	@RequestMapping(value = {"/query"})
	public void queryIllegal(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("k");
		String value = request.getParameter("v");
		String type = request.getParameter("t");
		String status = request.getParameter("s");
		if (TYPE_VEHICLE.toString().equals(type)) {
			List<IllegalVehicleInfo> illegalVehicles = queryVehicleIllegal(key, value, status);
			writeData(response, illegalVehicles);
		} else if (TYPE_DRIVING.toString().equals(type)) {
			queryDrivingIllegal(response, key, value, status);
		} else {
			throw new SimpleException("20003");
		}
	}

	/**
	 * 查询车辆违章信息
	 * 
	 * @param key
	 *            车牌号
	 * @param value
	 *            车架号
	 * @param status
	 *            状态
	 */
	private List<IllegalVehicleInfo> queryVehicleIllegal(String key, String value, String status) {
		Query query = ModelFactory.createQuery(IllegalVehicleInfo.class);
		query.eq("vehicle_plate_no", "苏" + key);
		query.leftLike("vehicle_vin", value);
		if (StringUtils.isNotBlank(status)) {
			query.eq("violation_status", status);
		}
		query.between("violation_time",
				DatetimeUtils.toLong((Calendar.getInstance().get(Calendar.YEAR) - 1) + "-01-01 00:00:00"),
				DatetimeUtils.now());
		List<IllegalVehicleInfo> illegalVehicles = query.queryForList(IllegalVehicleInfo.class);
		return illegalVehicles;
	}

	/**
	 * 查询驾驶证违章信息
	 * 
	 * @param response
	 *            页面响应
	 * @param key
	 *            驾驶证号
	 * @param value
	 *            档案编号
	 * @param status
	 *            状态
	 */
	private void queryDrivingIllegal(HttpServletResponse response, String key, String value, String status) {

	}

	@RequestMapping(value = {"/deleteBinding"})
	public void deleteBinding(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("u");
		String key = request.getParameter("k");
		String value = request.getParameter("v");
		String type = request.getParameter("t");
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(key) || StringUtils.isBlank(value)
				|| StringUtils.isBlank(type)) {
			throw new SimpleException("20002");
		}
		Writer writer = ModelFactory.createWriter(IllegalBinding.class);
		List<Object> values = new ArrayList<>();
		values.add(userId);
		values.add(key);
		values.add(value);
		values.add(type);
		writer.delete(values, "user_id", "illegal_binding_info", "illegal_binding_detail", "illegal_binding_type");
		writeData(response, "success");
	}

	@RequestMapping(value = {"/queryDetail"})
	public void queryDetail(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String type = request.getParameter("t");
		if (TYPE_VEHICLE.toString().equals(type)) {
			Query query = ModelFactory.createQuery(IllegalVehicleInfo.class);
			query.eq(id);
			IllegalVehicleInfo illegalVehicleInfo = query.queryForObject(IllegalVehicleInfo.class);
			// 如果图片为null去获取图片,如果为空表示违章没有图片
			if (null == illegalVehicleInfo.getPic()) {
				byte[] bytes = HttpClientUtils.getByteArray(URL_PIC + illegalVehicleInfo.getFileNo());
				// 如果没有图片返回{"status": "404","code": "10006","exception":
				// "NotFoundException","message":
				// "3117007900463743","more_info":
				// ""}字符串长度107,当返回107的时候判断,如果是这个错误则图片为空
				boolean isPic = true;
				if (bytes.length == 107) {
					try {
						Map<String, Object> map = JsonUtils.readString(new String(bytes, "UTF-8"),
								new TypeReference<Map<String, Object>>() {
								});
						if ("10006".equals(map.get("code").toString())) {
							isPic = false;
						}
					} catch (Exception e) {
						logger.error("获取违章图片,长度107,解析错误{}", e);
					}
				}
				String picUrl = "";
				if (isPic) {
					picUrl = FastDfsUtils.upload(bytes, "png");
				}
				illegalVehicleInfo.setPic(picUrl);
				Writer writer = ModelFactory.createWriter(IllegalVehicleInfo.class);
				writer.update(illegalVehicleInfo);
			}
			writeData(response, illegalVehicleInfo);
		} else if (TYPE_DRIVING.toString().equals(type)) {

		} else {
			throw new SimpleException("20003");
		}
	}

}
