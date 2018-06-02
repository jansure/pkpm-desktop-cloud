package com.messageserver.messageserver.service.message;

import com.desktop.constant.MessageTypeEnum;
import com.desktop.utils.exception.Exceptions;
import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * EmailMessageSenderImpl
 *
 * author .zhang
 * @since 5/5/2018
 */
@Data
public class Message implements Serializable{
    private String from;
    private String to;
    private String subject;
    private String content;
    private String messageType;
    private Boolean withAttachment;
    private Map<String,String> attachments;
    
    //发送状态
    private String status;

    public void eval() {
        
        switch (MessageTypeEnum.eval(messageType)) {
            case email:
            	Preconditions.checkArgument(!StringUtils.isEmpty(from), "消息发送人不能为空");
                Preconditions.checkArgument(!StringUtils.isEmpty(to), "消息接收人不能为空");
                Preconditions.checkArgument(!StringUtils.isEmpty(subject), "消息主题不能为空");
                
                Preconditions.checkArgument(null != withAttachment, "是否有附件不能为空");
                Preconditions.checkArgument(Boolean.FALSE == withAttachment ||
                        (Boolean.TRUE == withAttachment &&
                                MapUtils.isNotEmpty(attachments)),
                        "发送附件不能为空");
                Preconditions.checkArgument(!StringUtils.isEmpty(content), "消息内容不能为空");
                break;
            case sms:
            	Preconditions.checkArgument(!StringUtils.isEmpty(to), "短信接收人不能为空");
            	Preconditions.checkArgument(!StringUtils.isEmpty(content), "短信内容不能为空");
                break;
            case message:
                break;
            default:
                throw Exceptions.newIllegalArgumentException("暂时不支持的消息类型" + messageType);
        }
    }
}
