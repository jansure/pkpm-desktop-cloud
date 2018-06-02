package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PkpmCloudComponentDef implements Serializable {

	/** 子产品ID */
	private Integer componentId;

	/**
	 * 产品子类类型 待定义 1、结构-钢结构 2、建筑-渲染 3、BIM-协同 4、BIM-轻量化 5、主机配置
	 */
	private Integer componentType;

	/**
	 * 子产品名称 其他
	 */
	private String componentName;

	/** 资产描述 */
	private String componentDesc;

	/** */
	private String componentTypeName;

	/** 是否是默认选项：1是 0否 */
	private String isDefault;

	/** 主机配置时对应华为的产品套餐id */
	private String hwProductId;

}
