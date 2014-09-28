package com.mingda.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageWithByte {

	public static byte[] image2Bytes(String imagePath) throws Exception {
		BufferedImage bu = ImageIO.read(new File(imagePath));
		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
		try {
			boolean resultWrite = ImageIO.write(bu, "jpg", imageStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageStream.flush();
		byte[] tagInfo = imageStream.toByteArray();

		return tagInfo;
	}

	public static void main(String[] args) throws Exception {
		byte[] info = image2Bytes("C:\\c\\00001.jpg");
		for(int i=0;i<info.length;i++){
			System.out.print(info[i]);
		}
		System.out.println( );
		ByteArrayInputStream imageStream = new ByteArrayInputStream(info);
		BufferedImage image = ImageIO.read(imageStream);
		String newFilePath = "C:\\c\\00002.jpg";
		// BufferedImage image=ImageIO.read(new File("D:/我的文档/许女子.jpg"));
		ImageIO.write(image, "jpg", new File(newFilePath));
	}

}