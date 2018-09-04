package ioc;

import org.springframework.web.servlet.HandlerMapping;

public class Pig implements Animal{

	@Override
	public void eat() {
		// TODO Auto-generated method stub
		System.out.println("pig eat");
	}
	
}
