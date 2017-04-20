package com.verysu.basic.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import cz.jiripinkas.jsitemapgenerator.Image;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;

public class MySitemapGenerator extends SitemapGenerator {

	private StringBuilder additionalNamespacesStringBuilder = new StringBuilder();
	private boolean isNew;
	public MySitemapGenerator(String baseUrl,boolean isNew) {
		super(baseUrl);
		this.isNew = isNew;
	}

	@Override
	public void constructAndSaveSitemap(File file) throws IOException {
		saveSitemap(file);// 去除sitemap获取配置数据的方式
	}

	public void saveSitemap(File file) throws IOException {
		StringBuilder xmlHead = new StringBuilder();
		if (isNew || !file.exists()) {
			// 生成xml文件
			additionalNamespacesStringBuilder.append("xmlns:image=\"image\"");
			xmlHead.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xmlHead.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" "
					+ additionalNamespacesStringBuilder.toString() + ">\n");
			xmlHead.append("</urlset>");
			DomUtil.string2XmlFile(xmlHead.toString(), file);
		}
		// 增加配置节点
		try {
			addConfig(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addConfig(File file) throws Exception {
		Document document = DomUtil.load(file);
		Element root = document.getRootElement();
		
		ArrayList<WebPage> values = new ArrayList<WebPage>(urls.values());
		for (WebPage webPage : values) {
			Element urlElement = root.addElement("url");
			if (webPage.getName() != null) {
				urlElement.addElement("loc").setText(
						new URL(baseUrl + webPage.getName()).toString());
			} else {
				urlElement.addElement("loc").setText(
						new URL(baseUrl).toString());
			}
			if (webPage.getLastMod() != null) {
				urlElement.addElement("lastmod").setText(
						dateFormat.format(webPage.getLastMod()));
			}
			if (webPage.getPriority() != null) {
				urlElement.addElement("priority").setText(
						webPage.getPriority().toString());
			}
			if (webPage.getLastMod() != null) {
				urlElement.addElement("changefreq").setText(
						webPage.getChangeFreq().toString());
			}

			// 图片
			if (webPage.getImages() != null) {
				if(root.getNamespaceForPrefix("image") == null){
					root.addNamespace("image", "image");
				}
				for (Image image : webPage.getImages()) {
					Element imgElement = urlElement.addElement("image:image");// .addNamespace("image",
																				// "");
					// QName qName = new QName("image",new
					// Namespace("image",Namespace.NO_NAMESPACE.getURI()),
					// "image");
					// System.out.println(qName.getNamespacePrefix());
					// imgElement.setQName(qName);
					if (image.getLoc() != null) {
						imgElement.addElement("image:loc").setText(
								image.getLoc());
					}
					if (image.getCaption() != null) {
						imgElement.addElement("image:caption").setText(
								image.getCaption());
					}
					if (image.getGeoLocation() != null) {
						imgElement.addElement("image:geo_location").setText(
								image.getGeoLocation());
					}
					if (image.getTitle() != null) {
						imgElement.addElement("image:title").setText(
								image.getTitle());
					}
					if (image.getLicense() != null) {
						imgElement.addElement("image:license").setText(
								image.getLicense());
					}
				}
			}

			String docStr = DomUtil.doc2String(document);
			DomUtil.string2XmlFile(docStr, file);
		}
	}

}
