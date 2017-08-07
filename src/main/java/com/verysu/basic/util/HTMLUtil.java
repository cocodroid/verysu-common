package com.verysu.basic.util;

import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;

import freemarker.template.utility.StringUtil;

public class HTMLUtil {
	
	private static HTMLUtil util = null;

	public static HTMLUtil getInstance() {
		if (util == null) {
			util = new HTMLUtil();
		}
		return util;
	}
	
	/*
	 * 删除Html标签
	 *
	 * @param inputString
	 * @return
	 */
	public String htmlRemoveTag(String inputString) {
	    if (inputString == null)
	        return null;
	    String htmlStr = inputString; // 含html标签的字符串
	    String textStr = "";
	    java.util.regex.Pattern p_script;
	    java.util.regex.Matcher m_script;
	    java.util.regex.Pattern p_style;
	    java.util.regex.Matcher m_style;
	    java.util.regex.Pattern p_html;
	    java.util.regex.Matcher m_html;
	    try {
	        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
	        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); // 过滤script标签
	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); // 过滤style标签
	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); // 过滤html标签
	        
	        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	        java.util.regex.Matcher m = p.matcher(htmlStr);
	        htmlStr = m.replaceAll("").replaceAll("&nbsp;", "");// 过滤空格，换行
			
	        textStr = htmlStr;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return textStr;// 返回文本字符串
	}
	
	/**
	 * 判断文本最后是否有'<'或'&lt;' html标签
	 * 并且截取去除
	 * @param text
	 * @return
	 */
	public String lastHasLeftAndCut(String text){
		if(text != null && !"".equals(text)){
			int lastLeftIndex = text.lastIndexOf("<");
			int lastRightIndex = text.lastIndexOf(">");
			if(lastLeftIndex > lastRightIndex){//文本末尾包含<则截取
				return text.substring(0, lastLeftIndex);
			}
			int lastLeftIndex1 = text.lastIndexOf("&lt;");
			int lastRightIndex1 = text.lastIndexOf("&gt;");
			if(lastLeftIndex1 > lastRightIndex1){//文本末尾包含&lt;则截取
				return text.substring(0, lastLeftIndex1);
			}
		}
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(HTMLUtil.getInstance().htmlRemoveTag("a<iframe>13</iframe>"));
		
		String str = "aaf<a href=\"hhh\"/a> <a fvsb";
		int last = str.lastIndexOf("<");
		int last1 = str.lastIndexOf(">");
		System.out.println(last);
		System.out.println(last1);
		
		String str1 = "aaf<a href=\"hhh\"/a> <a fvsb /a>";
		int last2 = str1.lastIndexOf("<");
		int last3 = str1.lastIndexOf(">");
		System.out.println(last2);
		System.out.println(last3);
		
		 
		String str2 = "aaf&lt;a href=\"hhh\"/a&gt; &lt;a fvsb /a&gt;";
		int last4 = str2.lastIndexOf("&lt;");
		int last5 = str2.lastIndexOf("&gt;");
		System.out.println(last4);
		System.out.println(last5);
		
		System.out.println(HTMLUtil.getInstance().lastHasLeftAndCut("aaf&lt;a href=\"hhh\"/a&gt; &lt;a fvsb"));
		System.out.println(HTMLUtil.getInstance().lastHasLeftAndCut("什么是<span class=\"highlight\" color=\"red\">大数据</span>内容？在<span class=\"highlight\" color=\"red\">数据</span>库中，有一条一条的记录，记录中很多字段都是几个字符就够的，假如现在要把一部小说存入<span class=\"highlight\" color=\"red\">数据</span>库，这本小说当然不是几个字符组成，而是由几万字组成，这本小说的<span class=\"highlight\" color=\"red\">数据</span>我们就可以说是<span class=\"highlight\" color=\"re<"));
	}
}
