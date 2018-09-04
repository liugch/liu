package suanfa;

import java.util.Comparator;

public class Find {
	
	/**
	 * 二分查找， 返回下标
	 * @param list
	 * @param a
	 * @param com
	 * @return
	 */
	public <T> int find(T[] list,T a, Comparator<T> com){
		int low = 0;
		int hight = list.length;
		while(low<hight){
			
			int mid = (low+hight)>>>1;
			
			int b = com.compare(list[mid], a);
			
			if(b<0){
				low = mid+1;
			}else if(b>0){
				hight = mid -1;
			}else{
				return mid;
			}
		}
		return -1;
	}
}
