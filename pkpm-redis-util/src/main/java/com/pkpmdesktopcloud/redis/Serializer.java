package com.pkpmdesktopcloud.redis;

/**
 * Serialize method for redis object
 * @return serialize bytes
 * @author zhangshuai
 * @since 04.19.18
 */
public interface Serializer {
    byte[] serialize(Object object);

/**
 * unSerialize method for redis object
 * @return deserialize Object
 * @author zhangshuai
 * @since 04.19.18
 */
    Object deserialize(byte[] bytes);
}