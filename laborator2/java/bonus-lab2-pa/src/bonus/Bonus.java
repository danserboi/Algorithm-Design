package bonus;

import java.util.*;

public class Bonus {
	static class Item implements Comparable<Item>{
		int firstWeek;
		int secondWeek;
		public Item() {
			super();
			firstWeek = 0;
			secondWeek = 0;
		}
		public Item(int firstWeek, int secondWeek) {
			super();
			this.firstWeek = firstWeek;
			this.secondWeek = secondWeek;
		}
		//vrem sa sortam produsele dupa diferenta preturilor dintre prima saptamana si a doua saptamana
		//astfel ordonam produsele dupa cat de avantajos e sa le luam in prima saptamana
		@Override
		public int compareTo(Item item) {
			if(this.firstWeek - this.secondWeek < item.firstWeek - item.secondWeek)
				return -1;
			else if(this.firstWeek - this.secondWeek == item.firstWeek - item.secondWeek)
					return 0;
			else
				return 1;
		}
		@Override
		public String toString() {
			return "Item [firstWeek=" + firstWeek + ", secondWeek=" + secondWeek + "]";
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		Item[] items = new Item[n];
		for(int i = 0; i < n; i++) {
			items[i] = new Item();
			items[i].firstWeek = sc.nextInt();
		}
		for(int i = 0; i < n; i++) {
			items[i].secondWeek = sc.nextInt();
		}
		Arrays.sort(items);
		//vom cumpara neaparat primele k produse din prima saptamana
		int result = 0;
		int i = 0;
		for(; i < k; i++) {
			result += items[i].firstWeek;
		}
		//dupa cumparam tot din prima saptamana cat timp
		//diferenta dintre prima saptamana si a doua saptamana se mentine negativa
		//altfel luam din a doua saptamana
		for(; i < n && items[i].firstWeek - items[i].secondWeek <= 0; i++) {
			result += items[i].firstWeek;
		}
		for(; i < n; i++) {	
			result += items[i].secondWeek;
		}
		System.out.println(result);
	}
}