package org.think2framework.context.bean;

/**
 * Created by zhoubin on 16/7/28. 缓存对象
 */
public class Cache {

	private String name; // 缓存名称

	private String type; // 缓存类型,model-模型缓存,json-json文件,xml-xml文件

	private String entity; // 缓存实体,model类型对应的model名称,json,xml为对应文件的路径和名称,静态值为缓存值

	private String clazz; // 缓存对应的java bean类名

	private Integer valid; // 有效时间,单位秒,0表示永不失效

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
}
