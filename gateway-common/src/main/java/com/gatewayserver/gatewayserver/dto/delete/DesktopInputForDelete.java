package com.gatewayserver.gatewayserver.dto.delete;

import com.gatewayserver.gatewayserver.dto.DesktopForDel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Title: DesktopInputForDelete
 * Description:
 *
 * @author lijinliang
 * 2018年3月30日 下午4:56:21
 */

@Data
public class DesktopInputForDelete implements Serializable {
    private String projectId;
    private Integer adId;
    private Integer userId;
    private String userName;
    private List<DesktopForDel> desktops;
}
