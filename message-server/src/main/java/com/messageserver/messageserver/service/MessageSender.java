package com.messageserver.messageserver.service;


/**
 * EmailMessageSenderImpl
 *
 * author jansure.zhang
 * @since 5/5/2018
 */
public interface MessageSender {
    Boolean sendMessage(Message message);
}
