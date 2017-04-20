package com.verysu.basic.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DomUtil {
	private static String ECONDING = "UTF-8";

	/**
	 * doc2String 将xml文档内容转为String
	 * 
	 * @return 字符串
	 * @param document
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, ECONDING);
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString(ECONDING);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * string2Document 将字符串转为Document
	 * 
	 * @return
	 * @param s
	 *            xml格式的字符串
	 */
	public static Document string2Document(String s) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static boolean doc2XmlFile(Document document, Object fileObj) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			// 默认为UTF-8格式，指定为"GB2312"
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(ECONDING);
			File file = null;
			if(fileObj instanceof String){
				file = new File((String)fileObj);
			}else if(fileObj instanceof File){
				file = (File)fileObj;
			}
			XMLWriter writer = new XMLWriter(
					new FileWriter(file), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * string2XmlFile 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param str
	 *            需要保存的字符串
	 */
	public static boolean string2XmlFile(String str, Object fileObj) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = doc2XmlFile(doc, fileObj);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * load 载入一个xml文档
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param uri
	 *            文件路径
	 */
	public static Document load(Object fileObj) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			File file = null;
			if(fileObj instanceof String){
				file = new File((String)fileObj);
			}else if(fileObj instanceof File){
				file = (File)fileObj;
			}
			document = saxReader.read(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	public static void main(String[] args) {
		Document  document = load("sitemap.xml");
		System.out.println(document);
		Element element = document.getRootElement();
//		System.out.println(element);
		
		Element urlElement = element.addElement("url");
		urlElement.addElement("loc").setText("aaaa");
		urlElement.addElement("lastmod").setText("bbbb");
		urlElement.addElement("changefreq").setText("always");
		urlElement.addElement("priority").setText("ccccc");
		
//		element.add(urlElement);
//		Element element = DocumentHelper.createElement("url");
//		Attribute attribute = DocumentHelper.createAttribute(element, "loc", "aaa");
//		boolean b = document.remove(element);
//		System.out.println(b);
//		System.out.println(attribute);
//		document.add(element);
		System.out.println(doc2String(document));
	}
}
