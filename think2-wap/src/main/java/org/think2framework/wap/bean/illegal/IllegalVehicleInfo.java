package org.think2framework.wap.bean.illegal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "illegal_vehicle_info", pk = "illegal_vehicle_info_id", indexes = {"vehicle_plate_no", "violation_time"})
public class IllegalVehicleInfo {

	@Column(name = "illegal_vehicle_info_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	private Integer id;

	@Column(name = "surveil_file_no", comment = "档案编号")
	@JsonProperty("surveil_file_no")
	private String fileNo;

	@Column(name = "vehicle_type", length = 20, comment = "事件类型编号")
	@JsonProperty("vehicle_type")
	private String vehicle;

	@Column(name = "vehicle_plate_no", length = 20, comment = "车牌号")
	@JsonProperty("vehicle_plate_no")
	private String plateNo;

	@Column(name = "vehicle_vin", length = 20, comment = "车辆识别代码(车架号)")
	private String vin;

	@Column(name = "violation_time", type = ClassUtils.TYPE_INTEGER, length = 20, comment = "违法时间")
	@JsonProperty("violation_time")
	private Integer time;

	@Column(name = "violation_act", length = 255, comment = "事件描述")
	@JsonProperty("violation_act")
	private String act;

	@Column(name = "violation_address", length = 255, comment = "地址")
	@JsonProperty("violation_address")
	private String address;

	@Column(name = "violation_score", type = ClassUtils.TYPE_INTEGER, length = 5, comment = "扣分分数")
	@JsonProperty("violation_score")
	private Integer score;

	@Column(name = "violation_amount", type = ClassUtils.TYPE_INTEGER, length = 10, comment = "罚款")
	@JsonProperty("violation_amount")
	private Integer amount;

	@Column(name = "violation_type", comment = "事件类型描述")
	@JsonProperty("violation_type")
	private String type;

	@Column(name = "violation_status", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "缴纳罚款状态 1：已缴纳；0未缴纳")
	@JsonProperty("violation_status")
	private Integer status;

	@Column(name = "violation_processed_by", length = 255, comment = "发行机关描述")
	@JsonProperty("violation_processed_by")
	private String processedBy;

	@Column(name = "violation_pic_url", comment = "图片地址", length = 255)
	@JsonProperty("violation_pic_url")
	private String pic;

	@Column(name = "modify_time", type = ClassUtils.TYPE_INTEGER, length = 20, comment = "最后修改时间")
	@JsonIgnore
	private Integer modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
}
