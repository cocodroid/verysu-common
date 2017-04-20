package com.verysu.basic.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.jiripinkas.jsitemapgenerator.ChangeFreq;
import cz.jiripinkas.jsitemapgenerator.Image;
import cz.jiripinkas.jsitemapgenerator.WebPage;

public class SitemapGeneratorUtil {
	
	private static SitemapGeneratorUtil util;
	
	private SitemapGeneratorUtil() {
	}
	
	public static SitemapGeneratorUtil getInstance(){
		if(util == null){
			util = new SitemapGeneratorUtil();
		}
		return util;
	}
	
	/**
	 * @param baseUrl 一般为域名
	 * @param webPages 
	 * @param isRegenerate 是否重新生成新的
	 * @throws IOException 
	 */
	public void generateSiteMap(String baseUrl, File file, List<WebPage> webPages, boolean isRegenerate) throws IOException{
		MySitemapGenerator generator = new MySitemapGenerator(baseUrl,isRegenerate);
		generator.addPages(webPages);
		generator.constructAndSaveSitemap(file);
	}
	
	public static void main(String[] args){
	  
		MySitemapGenerator generator = new MySitemapGenerator("http://www.javatips123.net",false);
//		SitemapGenerator generator = new SitemapGenerator("http://www.javatips123.net");
//		SitemapIndexGenerator indexGenerator = new SitemapIndexGenerator("123");
		
		WebPage page = new WebPage();
		page.setLastMod(new Date());
		page.setName("ccc");
		page.setPriority(1.0);
		page.setChangeFreq(ChangeFreq.HOURLY);
		List<Image> images = new ArrayList<Image>();
		Image img = new Image();
		img.setLoc("iiii1");
		images.add(img);
		img.setLoc("iiii2");
		images.add(img);
		page.setImages(images);
		generator.addPage(page);
//		generator.pingBing();
		File file = new File("sitemap.xml");
		try {
			generator.constructAndSaveSitemap(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	  }
}
