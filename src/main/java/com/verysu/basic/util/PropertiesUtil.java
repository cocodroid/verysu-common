package com.verysu.basic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 配置文件操作类
 * @author Sjg
 * 2014-10-2
 * 下午07:04:28
 */
public class PropertiesUtil {
	private static PropertiesUtil util = null;
	private static Map<String,Properties> props = null;
	private PropertiesUtil(){
		
	}
	
	public static PropertiesUtil getInstance() {
		if(util==null) {
			props = new HashMap<String, Properties>();
			util = new PropertiesUtil();
		}
		return util;
	}
	
	public Properties load(String name) {
		if(props.get(name)!=null) {
			return props.get(name);
		} else {
			try{
				Properties prop = new Properties();
				prop.load(PropertiesUtil.class.getResourceAsStream("/"+name+".properties"));
				props.put(name, prop);
				return prop;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
