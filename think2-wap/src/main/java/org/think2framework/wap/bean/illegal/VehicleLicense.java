package org.think2framework.wap.bean.illegal;

import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/7/21. 机动车行驶证
 */
@Table(name = "vehicle_license", pk = "vehicle_license_id", uniques = {"vehicle_plate_no"}, comment = "机动车行驶证")
public class VehicleLicense {

	@Column(name = "vehicle_license_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "vehicle_plate_no", length = 20, comment = "车牌号码")
	private String plateNo;

	@Column(name = "vehicle_type", length = 20, comment = "车辆类型")
	private String vehicleType;

	@Column(name = "vehicle_owner", length = 10, comment = "所有人")
	private String owner;

	@Column(name = "vehicle_address", length = 255, comment = "住址")
	private String address;

	@Column(name = "vehicle_use_character", length = 20, comment = "使用性质")
	private String useCharacter;

	@Column(name = "vehicle_model", comment = "品牌型号")
	private String model;

	@Column(name = "vehicle_vin", length = 20, comment = "车辆识别代码(车架号)")
	private String vin;

	@Column(name = "vehicle_engine_no", length = 20, comment = "发动机号码")
	private String engineNo;

	@Column(name = "vehicle_register_date", length = 20, comment = "注册日期")
	private String registerDate;

	@Column(name = "vehicle_issue_date", length = 20, comment = "发证日期")
	private String issueDate;

	@Column(name = "vehicle_file_no", length = 20, comment = "档案编号")
	private String fileNo;

	@Column(name = "modify_time", type =ClassUtils.TYPE_INTEGER, length = 20, comment = "最后修改时间")
	private Integer modifyTime;

	@Column(length = 255, comment = "备注")
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUseCharacter() {
		return useCharacter;
	}

	public void setUseCharacter(String useCharacter) {
		this.useCharacter = useCharacter;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
