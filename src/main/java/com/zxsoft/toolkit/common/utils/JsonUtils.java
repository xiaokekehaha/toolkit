package com.zxsoft.toolkit.common.utils;

/**
 * Created by xuwenjuan on 16/11/18.
 */

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * JSON工具类
 *
 */
public class JsonUtils {

    private static Logger logger     = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper mapper     = new ObjectMapper();

    //	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    private static DateFormat         dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    static {
        mapper.setDateFormat(dateFormat);
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static String toJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Exception:{}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String toJsonWithoutPretty(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Exception:{}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 从Json字符串中，
     * 获取Jackson格式的JsonNode
     * JsonNode:把json映射为树形结构，此结构由各个节点组成
     *
     * @param jsonStr	Json字符串
     * @return			Jackson格式的JsonNode
     */
    public static JsonNode getJsonNode(String jsonStr) {
        JsonNode jsonNode = null;
        if (StringUtils.isEmpty(jsonStr)) {
            return jsonNode;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (JsonParseException e) {
            logger.error("When JsonUtils Process getJsonNode JsonParseException", e);
        } catch (JsonMappingException e) {
            logger.error("When JsonUtils Process getJsonNode JsonMappingException", e);
        } catch (IOException e) {
            logger.error("When JsonUtils Process getJsonNode IOException", e);
        } catch (Exception e) {
            logger.error("When JsonUtils Process getJsonNode Exception", e);
        }
        return jsonNode;
    }

    /**
     * 从Json字符串中，
     * 获取指定字段的Jackson格式的JsonNode
     * JsonNode:把json映射为树形结构中的一个节点
     *
     * @param jsonStr	Json字符串
     * @return			Jackson格式的JsonNode
     */
    public static JsonNode getJsonNode(String jsonStr, String fieldName) {
        JsonNode node = null;
        JsonNode jsonNode = getJsonNode(jsonStr);
        if (null != jsonNode) {
            node = jsonNode.get(fieldName);
        }
        return node;
    }

    /**
     * 从指定的JsonNode中，获取指定字段的子JsonNode
     *
     * @param parentNode	父JsonNode
     * @return			    父JsonNode的指定字段的子JsonNode
     */
    public static JsonNode getJsonNode(JsonNode parentNode, String fieldName) {
        JsonNode node = null;
        if (StringUtils.isNotEmpty(fieldName) && null != parentNode) {
            node = parentNode.get(fieldName);
        }
        return node;
    }

    public static boolean isAllNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

}
