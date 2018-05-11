package com.desktop.utils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import lombok.extern.slf4j.Slf4j;

/** 
 *    
 * @ClassName: VideoCaptureUtil  
 * @Description: 视频截图工具
 * @author wangxiulong  
 * @date 2018年5月7日  
 *
 */
@Slf4j
public class VideoCaptureUtil {
	
	/**
	 * 
	 * @Title: fetchFrameByJavacv  
	 * @Description: 将视频截图
	 * @param @param videofile 视频文件
	 * @return byte[]    返回截图字节数组
	 * @throws
	 */
	public static byte[] fetchFrameByJavacv(String videofile) {

		byte[] data = null;
				
		Frame f = null;
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
		
		try {
			ff.start();
			int lenght = ff.getLengthInFrames();
			
			int i = 0;
			while (i < lenght) {
				
				// 过滤前5帧，避免出现全黑的图片，依自己情况而定
				f = ff.grabFrame();
				if ((i > 5) && (f.image != null)) {
					break;
				}
				i++;
			}
			
			IplImage img = f.image;
			int owidth = img.width();
			int oheight = img.height();
			
			// 对截取的帧进行等比例缩放
			int width = 800;
			int height = (int) (((double) width / owidth) * oheight);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
					0, null);
			
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", os);
			data = os.toByteArray();
			// ff.flush();
			ff.stop();
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
		return data;
		
	}

	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * 
	 * @param videofile
	 *            源视频文件路径
	 * @param framefile
	 *            截取帧的图片存放路径
	 * @throws Exception
	 */
	public static void fetchFrameByJavacv(String videofile, String framefile) throws Exception {
		long start = System.currentTimeMillis();
		File targetFile = new File(framefile);
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
		ff.start();
		int lenght = ff.getLengthInFrames();
		int i = 0;
		Frame f = null;
		while (i < lenght) {
			// 过滤前5帧，避免出现全黑的图片，依自己情况而定
			f = ff.grabFrame();
			if ((i > 5) && (f.image != null)) {
				break;
			}
			i++;
		}
		IplImage img = f.image;
		int owidth = img.width();
		int oheight = img.height();
		// 对截取的帧进行等比例缩放
		int width = 800;
		int height = (int) (((double) width / owidth) * oheight);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
				0, null);
		ImageIO.write(bi, "jpg", targetFile);
		// ff.flush();
		ff.stop();
		System.out.println(System.currentTimeMillis() - start);
	}

	
	public static void main(String[] args) {
		try {
//			VideoCaptureUtil.fetchFrameByJavacv("http://49.4.8.123:8888/files/罪恶黑名单.The.Blacklist.S05E02.中英字幕.HDTVrip.720P-人人影视.mp4", "D:/test111.jpg");
			System.out.println(fetchFrameByJavacv("D:/test.mp4"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
