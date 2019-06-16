package com.known.common.utils;

import com.known.common.config.MailConfig;
import com.known.common.config.UrlConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.ArrayUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageUtil {

	private static final String[] static_ext = {"jpg", "png", "gif", "bmp", "JPG", "PNG", "GIF", "BMP"};


	public static String createThumbnail(String topicImage) {

		StringBuilder topicImageSmall = new StringBuilder();
		if (!StringUtil.isEmpty(topicImage)) {

			String[] topicImaes = topicImage.split("\\|");
			int smallCount = topicImaes.length;
			if (smallCount > Constants.MAX_FILE_NUM) {
				smallCount = Constants.MAX_FILE_NUM;
			}
			for (int i = 0; i < smallCount; i++) {
				String img = topicImaes[i];
				String sourcePath =  AbsolutePathUtil.getAbsoluteStaticPath()+ img;
				String smallSavePath = sourcePath + "_s.jpg";
				String smallPath = img + "_s.jpg";
				try {
					BufferedImage src = ImageIO.read(new File(sourcePath));
					if (src.getWidth() <= Constants.SMALL_IMAGE_WIDTH) {
						continue;
					}
					Thumbnails
							.of(sourcePath)
							.size(Constants.SMALL_IMAGE_WIDTH,
									Constants.SMALL_IMAGE_HEIGHT)
							.toFile(smallSavePath);

				} catch (Exception e) {
					
				}
				topicImageSmall.append(smallPath).append("|");
			}

		}
		return topicImageSmall.toString();
	}
	public static String getImages(String content) {
		StringBuilder sbf = new StringBuilder();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		List<TagNode> nodeList = (List<TagNode>) allNode.getElementListByName("img", true);
		String image = "";
		if (nodeList != null) {
			for (TagNode node : nodeList) {
				image = String.valueOf(node.getAttributeByName("src")).trim();
				if (!image.contains("emotion")
						&& !image.contains("grey.gif")
						&& image.contains(".")
						&& ArrayUtils.contains(static_ext,
								image.substring(image.lastIndexOf(".") + 1))) {
					sbf.append(image + "|");
				}
			}
		}
		if (sbf.length() > 0) {
			sbf.substring(sbf.lastIndexOf("|"));
		}
		return sbf.toString();
	}
}
