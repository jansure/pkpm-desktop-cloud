package com.messageserver.messageserver.service.impl;

import com.messageserver.messageserver.service.Message;
import com.messageserver.messageserver.service.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * EmailMessageSenderImpl
 *
 * author jansure.zhang
 * @since 5/5/2018
 */

@Slf4j
@Service("smsMessageSender")
public class SmsMessageSenderImpl implements MessageSender{
    @Override
    public Boolean sendMessage(Message message) {
        return null;
    }
}
