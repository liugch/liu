package io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class test2 {
	public static void main(String[] args) throws IOException {
		
		//Paths.get("F:\\平时笔记", "Git 基本使用.md");
		
		
		 //1.Paths
       // Path path = Paths.get("/Java/新建文件夹/java相关资料/java各种资料");
        //Path path1 = Paths.get(URI.create("/Java/新建文件夹/java相关资料/java各种资料"));
        //2.FileSystems
        //Path path2 = FileSystems.getDefault().getPath("/Java/新建文件夹/java相关资料/java各种资料");
        //3.File
        //Path path3 = new File("/Java/新建文件夹/java相关资料/java各种资料").toPath();
        
		
		
		// 只会创建文件夹，文件夹里面的东西不会创建
		/*Path source = Paths.get("f:", "平时笔记","Eclipse常用的快捷键.md");
		Path target = Paths.get("d:","a","Eclipse常用的快捷键.md");
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);*/
		
		
		
		//Path source = Paths.get("f:", "平时笔记","Java 模式之代理模式.md");
		//Path target = Paths.get("d:","a","Java 模式之代理模式.md");
       // Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
		
		// 不用的盘符不能够移动
		//Path source = Paths.get("f:", "平时笔记","Java 模式之代理模式.md");
		//Path target = Paths.get("f:","a","Java 模式之代理模式.md");
		//Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
		
		/*Path source = Paths.get("f:", "平时笔记");
		System.out.println(source.getNameCount());
		System.out.println(source.getRoot());
		System.out.println(source.getFileName());
		System.out.println(source.getFileSystem());
		System.out.println(source.getParent());
		System.out.println(source.toAbsolutePath().getName(0));*/
		
		//Path dir = Paths.get("f:", "平时笔记","a","aa.txt");
		
		//Files.createDirectory(dir); 创建文件夹
		//Files.lines(dir);
		
		//List<String> list = Files.readAllLines(dir, Charset.forName("UTF-8"));
		//System.out.println(list);
		
		/*List<String> wenz = new ArrayList<String>();
		wenz.add("1");
		wenz.add("4");
		Files.write(dir, wenz, Charset.forName("UTF-8"));*/
		
		
		Path dir = Paths.get("f:/平时笔记");
		Path tag = Paths.get("d:/a/b");
		list(dir,tag);
		
	}
	
	// Path Files 遍历所有的文件
	public static void list(Path dir,Path tag) throws IOException{
		Stream<Path> stream = Files.list(dir);
		stream.forEach(p -> {
			try {
				if(Files.isDirectory(p)){
						System.out.println(p.toAbsolutePath());
						if(!Files.exists(tag)){
							System.out.println(tag+"该目录不存在");
							Files.createDirectories(tag);
							System.out.println(tag+"该目录创建成功");
						}
						// 创建目标的path
						Path target = Paths.get(tag.toString()+"/"+p.getFileName().toString()); 
						if(!Files.exists(target)){
							Files.createDirectory(target);
							System.out.println(target+"目标目录创建成功!");
						}
						list(p,target);
				}else{
					//System.out.println(p.toAbsolutePath()+":"+p.getFileName());
					String fileName = null;
					Path to = null;
					if(p.getFileName().toString().endsWith(".md")){
						// 从哪里读取
						//List<String> a = Files.readAllLines(dir,StandardCharsets.UTF_8);
						fileName = tag.toString()+"/"+p.getFileName().toString().replaceAll("\\.md$", ".txt");
						System.out.println(fileName);
						to = Paths.get(fileName);
						System.out.println("开始复制");
						Files.copy(p, to);
						System.out.println("复制完成!");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
