package com.desktop.utils;

import com.desktop.utils.exception.Exceptions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * jackson序列化工具
 *
 * @author zhangshuai
 * @since 13/03/2018
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper mapper;

    /* static{
         //mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
         mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
     }*/
    public static String serialize(Object object) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return StringUtils.EMPTY;
        }
    }

    public static String serializeEx(Object object) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return StringUtils.EMPTY;
        }
    }

    public static <T> T deserialize(String jsonStr, Class<T> valueType) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            return mapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return null;
        }
    }

    public static <T> T deserializeEx(String jsonStr, Class<T> valueType) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return null;
        }
    }

    public static <T> T deserialize(String jsonStr, TypeReference<T> valueTypeRef) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return null;
        }
    }

    public static <T> T deserializeEx(String jsonStr, TypeReference<T> valueTypeRef) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            log.error(Exceptions.getStackTrace(e));
            return null;
        }
    }

    public static <T> List<T> decodeList(@NonNull String array) {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
        };
        List<T> list = deserialize(array, typeReference);
        return Optional.ofNullable(list).orElse(Lists.newArrayList());
    }

    public static <T> List<T> decodeListEx(@NonNull String array) {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
        };
        List<T> list = deserializeEx(array, typeReference);
        return Optional.ofNullable(list).orElse(Lists.newArrayList());
    }
}