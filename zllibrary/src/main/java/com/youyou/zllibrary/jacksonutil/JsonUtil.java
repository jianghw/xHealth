package com.youyou.zllibrary.jacksonutil;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 创建时间: 2015/12/17
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 取得劲松字符串的根节点
     *
     * @param message json字符串
     * @return
     * @throws IOException
     */
    public static JsonNode getRoot(String message) throws IOException {
        JsonNode root = objectMapper.readTree(message);
        return root;
    }

    /**
     * 将json节点转化成相应的类
     *
     * @param jsonNode
     * @param className
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValue(JsonNode jsonNode, Class<T> className) throws IOException {
        return objectMapper.readValue(jsonNode, className);
    }

    /**
     * 将json节点转化成相应的类
     *
     * @param jsonNode
     * @param className
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValue(String jsonNode, Class<T> className) throws IOException {
        return objectMapper.readValue(jsonNode, className);
    }

    /**
     *
     */
    public static <T> String getJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
