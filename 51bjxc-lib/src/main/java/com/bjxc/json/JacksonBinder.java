package com.bjxc.json;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class JacksonBinder {
    private static Logger logger = LoggerFactory.getLogger(JacksonBinder.class);     
    private ObjectMapper mapper;   
  
    public JacksonBinder(Include  include ) {   
        mapper = new ObjectMapper();
        //设置输出包含的属性   
       mapper.setSerializationInclusion(include);   
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性   
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false); 
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //日期格式处理
        mapper.setAnnotationIntrospectors(new JacksonAnnotationIntrospector() {
            @Override  
            public Object findSerializer(Annotated a) {  
                    if(a instanceof AnnotatedMethod) {  
                            AnnotatedElement m=a.getAnnotated();  
                            DateTimeFormat an=m.getAnnotation(DateTimeFormat.class);  
                            if(an!=null) {  
                                   return new JsonDateSerializer(an.pattern());  
                            }  
                    }  
                    return super.findSerializer(a);  
            }  
    },new JacksonAnnotationIntrospector() {
        @Override  
        public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated a) {  
                if(a instanceof AnnotatedMethod) {  
                        AnnotatedElement m=a.getAnnotated();  
                        DateTimeFormat an=m.getAnnotation(DateTimeFormat.class);  
                        if(an!=null) {
                        	if("yyyy-MM-dd".equals(an.pattern()) || "yyyyMMdd".equals(an.pattern())|| "yyyy/MM/dd".equals(an.pattern())){
                        		return JsonDateYMDDeserializer.class;  
                        	}
                        }  
                }  
                return super.findDeserializer(a);
        }  
});  
    }   
  
    /**  
     * 创建输出全部属性到Json字符串的Binder.  
    */  
    public static JacksonBinder buildNormalBinder() {   
        return new JacksonBinder(Include.ALWAYS);   
    }   
  
    /**  
     * 创建只输出非空属性到Json字符串的Binder.  
     */  
    public static JacksonBinder buildNonNullBinder() {   
        return new JacksonBinder(Include.NON_NULL);   
    }   
  
    /**  
     * 创建只输出初始值被改变的属性到Json字符串的Binder.  
     */  
    public static JacksonBinder buildNonDefaultBinder() {   
        return new JacksonBinder(Include.NON_DEFAULT);   
    }   
  
    /**  
     * 如果JSON字符串为Null或"null"字符串,返回Null.  
     * 如果JSON字符串为"[]",返回空集合.  
     *   
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:  
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});  
     */  
    public <T> T fromJson(String jsonString, Class<T> clazz) {   
        if (StringUtils.isEmpty(jsonString)) {   
            return null;   
        }   
  
        try {   
            return mapper.readValue(jsonString, clazz);   
        } catch (IOException e) {   
            logger.warn("parse json string error:" + jsonString, e);   
            return null;   
        }   
    }   
  
    /**  
     * 如果对象为Null,返回"null".  
     * 如果集合为空集合,返回"[]".  
     */  
    public String toJson(Object object) {   
  
        try {   
            return mapper.writeValueAsString(object);   
        } catch (IOException e) {   
            logger.warn("write to json string error:" + object, e);   
            return null;   
        }   
    }   
  
    /**  
     * 取出Mapper做进一步的设置或使用其他序列化API.  
     */  
    public ObjectMapper getMapper() {   
        return mapper;   
    }   

}
