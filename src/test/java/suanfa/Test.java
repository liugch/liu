package suanfa;

public class Test {
	public static void main(String[] args) {
		digui(1234);
	}
	//一个整数，大于0，不用循环和本地变量，按照n，2n，4n，8n的顺序递增，当值大于5000时，把值按照指定顺序输出来。
	public static void digui(int n){
		System.out.println(n);
		if(n<5000){
			digui(n*2);
			System.out.println(n);
		}
			
	}
}
