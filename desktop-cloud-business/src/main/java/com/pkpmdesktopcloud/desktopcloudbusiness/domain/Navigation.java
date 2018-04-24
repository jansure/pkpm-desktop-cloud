package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * @author yangpengfei
 * @date 2018/01/24
 *
 */
@Slf4j  
@Data 
public class Navigation implements Serializable {
	private Integer navId;
    private String navName;
    private Integer parentNavId;
    private String parentNavName;
    private String valid;
    @Transient
    private List<Navigation> children;
    

	@Override
    public String toString() {
        return "Navigation [navId=" + navId + ", navName=" + navName + ", parentNavId=" + parentNavId
                + ", parentNavName=" + parentNavName + "]";
    }

}
