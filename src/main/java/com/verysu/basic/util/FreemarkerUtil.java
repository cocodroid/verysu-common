package com.verysu.basic.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreeMarker工具类
 * @author Sjg
 * 2014-10-2
 * 下午07:00:07
 */
public class FreemarkerUtil {
	private static FreemarkerUtil util;
	private static Configuration cfg;
	private FreemarkerUtil() {
	}
	
	public static FreemarkerUtil getInstance(String pname) {
		if(util==null) {
			cfg = new Configuration();
			cfg.setClassForTemplateLoading(FreemarkerUtil.class, pname);
			util = new FreemarkerUtil();
		}
		return util;
	}
	
	private Template getTemplate(String fname) {
		try {
			return cfg.getTemplate(fname,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过标准输出流输出模板的结果
	 * @param root 数据对象
	 * @param fname 模板文件
	 */
	public void sprint(Map<String,Object> root,String fname) {
		try {
			getTemplate(fname).process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 基于文件的输出
	 * @param root
	 * @param fname
	 * @param outpath
	 */
	public void fprint(Map<String,Object> root,String fname,String outpath) {
		try {
			getTemplate(fname).process(root, new FileWriter(outpath));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
