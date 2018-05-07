package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.util.List;

import com.desktop.utils.mybatis.Invisible;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei
 * @date 2018/01/24
 *
 */
@Data
public class PkpmCloudNavigation implements Serializable {

	/** 导航目录ID */
	private Integer navId;

	/** 导航目录名称 */
	private String navName;

	/** 上级导航目录ID */
	private Integer parentNavId;

	/** 上级导航目录名称 */
	private String parentNavName;

	/** 有效性 */
	private String valid;

	@Override
	public String toString() {
		return "Navigation [navId=" + navId + ", navName=" + navName + ", parentNavId=" + parentNavId
				+ ", parentNavName=" + parentNavName + "]";
	}

}
