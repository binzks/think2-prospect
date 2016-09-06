package org.think2framework.wap.bean.illegal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/7/21. 用户与违章绑定关系
 */
@Table(name = "illegal_binding", pk = "illegal_binding_id", comment = "用户违章绑定关系")
public class IllegalBinding {

	@Column(name = "illegal_binding_id", type = ClassUtils.TYPE_INTEGER, comment = "主键")
	@JsonIgnore
	private Integer id;

	@Column(name = "user_id", length = 20, comment = "用户id")
	@JsonIgnore
	private String userId;

	@Column(name = "illegal_binding_info", comment = "绑定标识,车牌号或者驾驶证号")
	private String binding;

	@Column(name = "illegal_binding_detail", comment = "绑定的详情,车辆的车架号或者驾驶证档案编号")
	private String detail;

	@Column(name = "illegal_binding_type", type = ClassUtils.TYPE_INTEGER, length = 2, comment = "绑定类型 1:车辆 2:驾驶证")
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
