import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

/**    
 * @Title: Test.java  
 * @Package   
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年5月3日  
 * @version V1.0    
 */
public class Test {

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

	
	public static void fetchFrameByFfmpeg(String ffmpegPath, String videofile, String framefile) {
		long start = System.currentTimeMillis();

		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath); // 视频提取工具的位置
		cutpic.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		cutpic.add(videofile); // 视频文件路径
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("1"); // 添加起始时间为第1秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("800*600"); // 添加截取的图片大小为800*600
		cutpic.add(framefile); // 添加截取的图片的保存路径

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {

			builder.command(cutpic);
			builder.redirectErrorStream(true);
			// 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
			// 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
			builder.start();
			
			System.out.println(System.currentTimeMillis() - start);

		} catch (Exception e) {
			mark = false;
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Test.fetchFrameByJavacv("D:/test.mp4", "D:/test.jpg");
			
			//linux下ffmpegPath： /usr/local/ffmpeg2/bin/./ffmpeg
//			Test.fetchFrameByFfmpeg("D:/ffmpeg-20180102-41e51fb-win64-static/bin/ffmpeg.exe", "d:/test.mp4", "D:/test1.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
