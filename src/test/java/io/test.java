package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 编写一个程序，将F:\平时笔记目录下的所有.md文件复制到d:\jad目录下，并将原来文件的扩展名从.md改为.txt。
 * 
 * 
 * 过滤
 * 
 * 替换
 * 
 * 复制
 * 
 * 
 * @author Administrator
 *
 */
public class test {
	File[] fileall = null;
	
	public static void main(String[] args) throws Exception {
		doAll("F:\\平时笔记");
		
		
		
		
		
		
		
		
		
		
		
		
	}
	public static void doAll(String fileName) throws Exception{
		
		if(!fileName.equals("")&&fileName!=null){
			File srcfile = new File(fileName);
			
			if(!(srcfile.exists()&&srcfile.isDirectory()))
				throw new Exception("该文件价不存在，或者不是一个文件夹！");
			
			//获取所有的.md 文件组
			File[] files = filerFiles(srcfile);
			System.out.println("wendjiandaxiaowei:"+files.length);
			
			// 创建目标文件夹
			File target = new File("d:\\jad");
			if(!target.exists())
				target.mkdir();
			
			//遍历所有的过滤之后的文件 逐个写入到目标文件夹中，并把后缀名 改成.txt
			for (File file : files) {
				//FileInputStream inputStream = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				// 替换后缀名
				String targetFileName = file.getName().replaceAll("\\.md$", ".txt");
				//FileOutputStream outputStream = new FileOutputStream(new File(target,targetFileName));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(target,targetFileName)));
				
				copy(bis,bos);
				//System.out.println("开始复制"+file.getName());
			}
			System.out.println("复制完成");
		}
	}
	
	public static void copy(InputStream inputStream,OutputStream outputStream) throws IOException{
		int len = 0;//读取的长度
		byte[] buff = new byte[1024];
		//len = inputStream.read(buff);
		if((len= inputStream.read(buff))>0){
			outputStream.write(buff, 0, len);
		}
		inputStream.close();
		outputStream.close();
	}
	
	//过滤文件夹文件
	public static File[] filerFiles(File srcfile){
		
		File[] files = srcfile.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				//递归调用
				System.out.println("遍历的文件："+name);
				
				return name.endsWith(".md");
			}
		});
		return files;
	}
	

}
