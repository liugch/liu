package suanfa;

import java.util.Comparator;

public  class MainTest implements ISort {

	// 继承Comparable 类 使用 compareTo 方式进行排序
	@Override
	public <T extends Comparable<T>> T[] sort(T[] list) {
		boolean b = true;

		for (int i = 1; i < list.length && b; i++) {
			b = false;
			for (int j = 0; j < list.length - i; j++) {
				if (list[j].compareTo(list[j + 1]) > 0) {
					T temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
					b = true;
				}
			}
		}
		return list;
	}

	// 可以自定义比较器进行排序
	@Override
	public <T> T[] sort2(T[] list, Comparator<T> com) {
		boolean b = true;

		for (int i = 1; i < list.length && b; i++) {
			b = false;
			for (int j = 0; j < list.length - i; j++) {
				if (com.compare(list[j], list[j+1])>0) {
					T temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
					b = true;
				}
			}
		}
		return list; 
	}


	public static void main(String[] args) {
		MainTest t = new MainTest();
		Integer[] list = { 3, 5, 6, 2, 6, 9 };
		list = t.sort2(list, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) { 
				return o1>o2?1:-1;
			}
		});
		for (Integer integer : list) {
			System.out.println(integer);

		}

	}

}
