package com.zxsoft.toolkit.common.utils;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.util.*;

/**
 * 动态数据模型类
 */

public class QueryDomain implements Map<String, Object>, Iterable<Map.Entry<String, Object>>, Serializable {

    private static final long         serialVersionUID = -2083654392208323215L;

    private final Map<String, Object> _fields;

    public QueryDomain() {
        _fields = new LinkedHashMap<String, Object>();
    }

    /**
     * @return 数据模型的Field名称列表 - 直接通过QueryDomain后台获取的集合.
     * @see #keySet
     */
    public Collection<String> getFieldNames() {
        return this.keySet();
    }

    /**
     * 从Domain中删除所有的fileds
     */
    public void clear() {
        _fields.clear();
    }

    /**
     * 通过指定name删除所有fields
     */
    public boolean removeFields(String name) {
        return this.remove(name) != null;
    }

    /**
     * 通过给定的对象和name设置一个filed
     * 如果对象是数据，则设置多个filed;设置时会替换与name相同的所有fields。
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setField(String name, Object value) {
        if (value instanceof Object[]) {
            value = new ArrayList(Arrays.asList((Object[]) value));
        } else if (value instanceof Collection) {
            // nothing
        } else if (value instanceof Iterable) {
            ArrayList<Object> lst = new ArrayList<Object>();
            for (Object o : (Iterable) value) {
                lst.add(o);
            }
            value = lst;
        }
        _fields.put(name, value);
    }

    /**
     * 增加一个field到Domain中
     * 如果存在一个名为name的field，则往集合中添加该field；
     * 如果value是一个集合类，那么每个值都会被添加进来。
     */
    @SuppressWarnings("unchecked")
    public void addField(String name, Object value) {
        Object existing = _fields.get(name);
        if (existing == null) {
            if (value instanceof Collection) {
                Collection<Object> c = new ArrayList<Object>(3);
                for (Object o : (Collection<Object>) value) {
                    c.add(o);
                }
                this.setField(name, c);
            } else {
                this.setField(name, value);
            }
            return;
        }

        Collection<Object> vals = null;
        if (existing instanceof Collection) {
            vals = (Collection<Object>) existing;
        } else {
            vals = new ArrayList<Object>(3);
            vals.add(existing);
        }

        // 添加所有的值到集合类中
        if (value instanceof Iterable) {
            for (Object o : (Iterable<Object>) value) {
                vals.add(o);
            }
        } else if (value instanceof Object[]) {
            for (Object o : (Object[]) value) {
                vals.add(o);
            }
        } else {
            vals.add(value);
        }
        _fields.put(name, vals);
    }

    /**
     * 返回一个field的第一个值
     */
    @SuppressWarnings("rawtypes")
    public Object getFirstValue(String name) {
        Object v = _fields.get(name);
        if (v == null || !(v instanceof Collection))
            return v;
        Collection c = (Collection) v;
        if (c.size() > 0) {
            return c.iterator().next();
        }
        return null;
    }

    /**
     * 返回一个field的值或者值的集合
     */
    public Object getFieldValue(String name) {
        return _fields.get(name);
    }

    /**
     * 返回一个filed的值的集合
     */
    @SuppressWarnings("unchecked")
    public Collection<Object> getFieldValues(String name) {
        Object v = _fields.get(name);
        if (v instanceof Collection) {
            return (Collection<Object>) v;
        }
        if (v != null) {
            ArrayList<Object> arr = new ArrayList<Object>(1);
            arr.add(v);
            return arr;
        }
        return null;
    }

    @Override
    public String toString() {
        return ""+ _fields;
    }

    /**
     * 返回fields的集合迭代器
     */
    public Iterator<Entry<String, Object>> iterator() {
        return _fields.entrySet().iterator();
    }

    /**
     * 获取QueryDomain的filed-value集合的Map接口/形式
     */
    public Map<String, Collection<Object>> getFieldValuesMap() {
        return new Map<String, Collection<Object>>() {
            /** 获取field-Value */
            public Collection<Object> get(Object key) {
                return getFieldValues((String) key);
            }

            // 支持的方法
            public boolean containsKey(Object key) {
                return _fields.containsKey(key);
            }

            public Set<String> keySet() {
                return _fields.keySet();
            }

            public int size() {
                return _fields.size();
            }

            public boolean isEmpty() {
                return _fields.isEmpty();
            }

            public void clear() {
                throw new UnsupportedOperationException();
            }

            public boolean containsValue(Object value) {
                throw new UnsupportedOperationException();
            }

            public Set<Entry<String, Collection<Object>>> entrySet() {
                throw new UnsupportedOperationException();
            }

            public void putAll(Map<? extends String, ? extends Collection<Object>> t) {
                throw new UnsupportedOperationException();
            }

            public Collection<Collection<Object>> values() {
                throw new UnsupportedOperationException();
            }

            public Collection<Object> put(String key, Collection<Object> value) {
                throw new UnsupportedOperationException();
            }

            public Collection<Object> remove(Object key) {
                throw new UnsupportedOperationException();
            }

            public String toString() {
                return _fields.toString();
            }
        };
    }

    /**
     * 获取QueryDomain的fileds的Map接口/形式
     */
    public Map<String, Object> getFieldValueMap() {

        return new Map<String, Object>() {
            /** 获取field-Value */
            public Object get(Object key) {
                return getFirstValue((String) key);
            }

            public boolean containsKey(Object key) {
                return _fields.containsKey(key);
            }

            public Set<String> keySet() {
                return _fields.keySet();
            }

            public int size() {
                return _fields.size();
            }

            public boolean isEmpty() {
                return _fields.isEmpty();
            }

            public void clear() {
                throw new UnsupportedOperationException();
            }

            public boolean containsValue(Object value) {
                throw new UnsupportedOperationException();
            }

            public Set<Entry<String, Object>> entrySet() {
                throw new UnsupportedOperationException();
            }

            public void putAll(Map<? extends String, ? extends Object> t) {
                throw new UnsupportedOperationException();
            }

            public Collection<Object> values() {
                throw new UnsupportedOperationException();
            }

            public Collection<Object> put(String key, Object value) {
                throw new UnsupportedOperationException();
            }

            public Collection<Object> remove(Object key) {
                throw new UnsupportedOperationException();
            }

            public String toString() {
                return _fields.toString();
            }
        };
    }

    public boolean containsKey(Object key) {
        return _fields.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return _fields.containsValue(value);
    }

    public Set<Entry<String, Object>> entrySet() {
        return _fields.entrySet();
    }

    public Object get(Object key) {
        return _fields.get(key);
    }

    public boolean isEmpty() {
        return _fields.isEmpty();
    }

    public Set<String> keySet() {
        return _fields.keySet();
    }

    public Object put(String key, Object value) {
        return _fields.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends Object> t) {
        _fields.putAll(t);
    }

    public Object remove(Object key) {
        return _fields.remove(key);
    }

    public int size() {
        return _fields.size();
    }

    public Collection<Object> values() {
        return _fields.values();
    }

    /**
     * JSON树解析方法
     * @param jsonStr：json字符串
     * @return 解析好的JSON对象
     */
    public static  QueryDomain parseJsonTree(String jsonStr) {
        QueryDomain result = new QueryDomain();
        JsonNode node = JsonUtils.getJsonNode(jsonStr);
        Iterator<String> fieldNames = node.getFieldNames();
        String field = "", value = "";
        JsonNode tnode = null;
        while (fieldNames.hasNext()) {
            field = fieldNames.next();
            if (JsonUtils.getJsonNode(node, field).size() != 0) {
                tnode = JsonUtils.getJsonNode(node, field);
                if (tnode.isArray()) {
                    List<Object> arr = new ArrayList<Object>();
                    for (JsonNode nodet : tnode) {
                        arr.add(parseJsonTree(nodet.toString()));
                    }
                    result.put(field, arr);
                } else {
                    result.put(field, parseJsonTree(tnode.toString()));
                }
                continue;
            }
            value = node.get(field).toString().replaceAll("\"", "");
            if (value.length() > 0 && value.length() < 15 && JsonUtils.isAllNum(value) && !value.contains("-")) {
                if (value.contains(".") | value.contains("+")) {
                    result.addField(field, Double.parseDouble(value));
                } else {
                    result.addField(field, Long.parseLong(value));
                }
            } else {
                result.addField(field, value);
            }
        }

        return result;
    }



    static  class CaseQuery1{

        Map<String,Object> eqMap;
        Map<String,Object> upMap;

        public  CaseQuery1(){

        }

        public CaseQuery1(Map<String, Object> eqMap, Map<String, Object> upMap) {
            this.eqMap = eqMap;
            this.upMap = upMap;
        }

        public Map<String, Object> getEqMap() {
            return eqMap;
        }

        public void setEqMap(Map<String, Object> eqMap) {
            this.eqMap = eqMap;
        }

        public Map<String, Object> getUpMap() {
            return upMap;
        }

        public void setUpMap(Map<String, Object> upMap) {
            this.upMap = upMap;
        }
    }

    public static void main(String[] args) {
        CaseQuery1 domain = new CaseQuery1();
        Map <String ,Object> eqMap=new HashedMap();
        eqMap.put("case_id","haha");
        eqMap.put("case1_id","800");
        domain.setEqMap(eqMap);
        Map <String ,Object> upMap=new HashedMap();
        upMap.put("case_id","9800");
        upMap.put("case1_id","80");
        domain.setUpMap(upMap);
        QueryDomain domain1=QueryDomain.parseJsonTree(JsonUtils.toJson(domain));
        System.out.println(domain1.getFieldNames());
        System.out.println(domain1.get("eqMap"));
        System.out.println(JsonUtils.toJsonWithoutPretty(domain1.get("eqMap")));
        System.out.print(domain1.containsKey("eqMap"));

    }
}
