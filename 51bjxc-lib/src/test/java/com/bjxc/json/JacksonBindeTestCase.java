package com.bjxc.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JacksonBindeTestCase {
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	/**
	 * 序列化对象/集合到Json字符串.
	 */
	@Test
	public void toJson() throws Exception {
		// Bean
		TestBean bean = new TestBean("A");
		bean.setDay(new Date());
		String beanString = binder.toJson(bean);
		System.out.println("Bean:" + beanString);
		/**
		assertEquals("{\"name\":\"A\"}", beanString);

		// Map
		Map<String, Object> map = new LinkedHashMap();
		map.put("name", "A");
		map.put("age", 2);
		String mapString = binder.toJson(map);
		System.out.println("Map:" + mapString);
		assertEquals("{\"name\":\"A\",\"age\":2}", mapString);

		// List<String>
		List<String> stringList = new ArrayList<String>();
		stringList.add("A");
		stringList.add("B");
		stringList.add("C");
		String listString = binder.toJson(stringList);
		System.out.println("String List:" + listString);
		assertEquals("[\"A\",\"B\",\"C\"]", listString);

		// List<Bean>
		List<TestBean> beanList = new ArrayList<TestBean>();
		beanList.add(new TestBean("A"));
		beanList.add(new TestBean("B"));
		String beanListString = binder.toJson(beanList);
		System.out.println("Bean List:" + beanListString);
		assertEquals("[{\"name\":\"A\"},{\"name\":\"B\"}]", beanListString);

		// Bean[]
		TestBean[] beanArray = new TestBean[] { new TestBean("A"),
				new TestBean("B") };
		String beanArrayString = binder.toJson(beanArray);
		System.out.println("Array List:" + beanArrayString);
		assertEquals("[{\"name\":\"A\"},{\"name\":\"B\"}]", beanArrayString);
		**/
	}

	/**
	 * 从Json字符串反序列化对象/集合.
	 */
	@SuppressWarnings("unchecked")
	public void fromJson() throws Exception {
		// Bean
		String beanString = "{\"name\":\"A\"}";
		TestBean bean = binder.fromJson(beanString, TestBean.class);
		System.out.println("Bean:" + bean);

		// Map
		String mapString = "{\"name\":\"A\",\"age\":2}";
		Map<String, Object> map = binder.fromJson(mapString, HashMap.class);
		System.out.println("Map:");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		// List<String>
		String listString = "[\"A\",\"B\",\"C\"]";
		List<String> stringList = binder.getMapper().readValue(listString,
				List.class);
		System.out.println("String List:");
		for (String element : stringList) {
			System.out.println(element);
		}

		// List<Bean>
		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = binder.getMapper().readValue(beanListString,
				new TypeReference<List<TestBean>>() {
				});
		System.out.println("Bean List:");
		for (TestBean element : beanList) {
			System.out.println(element);
		}
	}
	
	public void parseDate() throws JsonParseException, JsonMappingException, IOException{

		String beanString = "{\"name\":\"A\",\"day\":\"20160825\"}";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String beanString = "{\"name\":\"A\"}";
		TestBean bean = binder.fromJson(beanString, TestBean.class);
		//Map bean = binder.fromJson(beanString, Map.class);
		System.out.println(bean);
		System.out.println(df.format(bean.getDay()));
	}
	
	

}

