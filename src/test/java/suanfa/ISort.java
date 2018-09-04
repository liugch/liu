package suanfa;

import java.util.Comparator;

public interface ISort{

	
	public <T extends Comparable<T>> T[] sort(T[] list);
	
	public <T> T[] sort2(T[] list, Comparator<T> com);
}
