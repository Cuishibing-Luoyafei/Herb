package com.herb.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 上传文件工具类
 * @author Diamond
 *
 */
public class UploadFileUtil {

	/**
	 * 这里的上传图片的工具类，只提供把struts2接收的文件，传递给自己创建的文件
	 * @param picture struts2接收的文件
	 * @param file 自己根据需求地址创建的文件
	 * @return
	 */
	public static boolean justDoIt(File picture, File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		/**
		 * 标识，创建文件是否成功
		 */
		boolean create = false;
		
		try {
			bis = new BufferedInputStream(new FileInputStream(picture));
			bos = new BufferedOutputStream(new FileOutputStream(file));  
			
			byte[] bts=new byte[1024];
			int length = -1;
			
			while((length = bis.read(bts)) != -1) {
				bos.write(bts, 0, length);
			}
			
			bis.close();
			bos.flush();
			bos.close();
			
			create = true;
			
		} catch (IOException e) {
			create = false;
			e.printStackTrace();
		} finally {
			try {
				if(bis != null) {
					bis.close();
					bis = null;
				}
				if(bos != null) {
					bos.flush();
					bos.close();
					bos = null;
				}
			} catch(final IOException e) {
				e.printStackTrace();
			}
		}
		return create;
	}
	
}
