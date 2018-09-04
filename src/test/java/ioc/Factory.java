package ioc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Factory {
	/**
	 * 简单的一个工厂模式
	 * 没有用反射实现,如果有很多子类需要添加,都要修改这个类,麻烦
	 * @param classname
	 * @return
	 */
	/*public static Animal getInstance(String classname){
		Animal animal = null;
		if(classname.equals("Cat")){
			animal = new Cat();
		}
		if(classname.equals("Pig")){
			animal = new Pig();
		}
		if(classname.equals("Snake")){
			animal = new Snake();
		}
		return animal;
	}*/
	
	
	/**
	 *  反射的工厂模式, 之后只需要添加子类,但是提供类名需要完整的路径名
	 *  ico容器:解析xml反射实现工厂模式
	 *  下面的classname 就要通过解析xml 来获取了
	 * @param classname
	 * @return
	 */
	public static Animal getInstance(String classname){
		Animal animal = null;
		try {
			
			animal = (Animal) Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return animal;
	}
}
