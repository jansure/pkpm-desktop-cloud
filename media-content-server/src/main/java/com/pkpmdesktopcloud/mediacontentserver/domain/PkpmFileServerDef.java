package com.pkpmdesktopcloud.mediacontentserver.domain;

import lombok.Data;

@Data
public class PkpmFileServerDef {
	
    /** */
    private Integer id;

    /**服务器IP */
    private String serverIp;

    /**服务器名称 */
    private String serverName;

    /**服务器端口 */
    private String serverPort;

}