package thread3;


/**
 * 这里是一个 售票站, 假设还有20 张去珠海的票, 三个窗口abc在卖, 一个窗口一张一张票来卖
 * @author Administrator
 *
 */
public class Station implements Runnable{
	
	private static int num = 20;
	
	private String a = "a";
	
	private String name;
	
	public Station(String name) {
		 this.name= name;
	}
	
	
	@Override
	public void run() {
		while(true){
			if(num>0){
				synchronized (a) {
					num--;
					System.out.println(name+"窗口卖出一张票还剩"+num);
				}
			}else{
				System.out.println(name+"窗口在买的时票已经卖完了");
			}
		}
		
	}
	
}
	

