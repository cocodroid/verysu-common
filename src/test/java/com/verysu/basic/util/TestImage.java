package com.verysu.basic.util;

import java.io.File;

import org.junit.Test;

public class TestImage {

	@Test
	public void testCompress() {
		ImageUtil iu = ImageUtil.getInstance();
		iu.compressImg(new File("f:/Images/01_thumb.jpg"), new File("f:/Images/IMG_20131205_172658.jpg"),
				200, 0, true);
		iu.cropImg("f:/Images/01_thumb.jpg", 20, 40, 150, 100);
	}
}
