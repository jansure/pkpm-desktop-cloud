package com.messageserver.messageserver.service.impl;

import com.desktop.constant.MessageTypeEnum;
import com.desktop.utils.exception.Exceptions;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageSender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * EmailMessageSenderImpl
 *
 * author jansure.zhang
 * @since 5/5/2018
 */
@Slf4j
//@Service("mailMessageSender")
public class MailMessageSenderImpl implements MessageSender {
    @Resource
    private JavaMailSender mailSender;
    @Override
    public Boolean sendMessage(Message message) {
        Preconditions.checkArgument(null != message, "消息不能为空");
        message.setMessageType(MessageTypeEnum.email.name());
        message.eval();
        if (message.getWithAttachment()) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(message.getFrom());
                helper.setTo(message.getTo());
                helper.setReplyTo(message.getFrom());
                helper.setSubject(message.getSubject());
                helper.setText(message.getContent());
                helper.setSentDate(new Date());
                message.getAttachments().forEach((k, v) -> {
                    try {
                        helper.addAttachment(k, new FileSystemResource(v));
                    } catch (MessagingException e) {
                        log.error(e.getMessage(), e);
                        throw Exceptions.newBusinessException("添加附件失败");
                    }
                });
                mailSender.send(mimeMessage);
            } catch (MessagingException | MailException e) {
                log.error(e.getMessage(), e);
                throw Exceptions.newBusinessException("邮件发送失败, 请检查邮件配置");
            }
        } else {
            try {
                SimpleMailMessage helper = new SimpleMailMessage();
                helper.setFrom(message.getFrom());
                helper.setTo(message.getTo());
                helper.setReplyTo(message.getFrom());
                helper.setSubject(message.getSubject());
                helper.setText(message.getContent());
                mailSender.send(helper);
            } catch (MailException e) {
                log.error(e.getMessage(), e);
                throw Exceptions.newBusinessException("邮件发送失败, 请检查邮件配置");
            }
        }
        return Boolean.TRUE;
    }
}
