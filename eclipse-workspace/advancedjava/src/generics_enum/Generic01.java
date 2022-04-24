package generics_enum;

import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.PrinterInfo;

/*
 Type parameters and naming convention,it is commonly used.Dont use different naming convention
 T->Type
 E->Element
 K->Key
 V->Value
 N->Number
 */

public class Generic01 {
   public static void main(String[] args) {
	
	 Generic02 <String,Integer> obj1 = new Generic02<>("Ahmet",22); 
//	 obj1.display(true,'C'); 
//	 obj1.display(12,"Ali");
	 obj1.print("Xyz");
	 obj1.print(123);
	 obj1.print('A');
   }

}

class Generic02<K,V>{
	private K key;
	private V value;
	
	public Generic02(K key, V value) {
		this.key = key;
		this.value = value;
	}

		public K getKey() {
		return key;
	}

		
	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Generic02 [key=" + key + ", value=" + value + "]";
	}

	public <E,N extends Set&List> void display(E element, N number) {
		System.out.println("Element: "+element + ", Number: "+number);
	}
	
	public <E> void print(E element) {
		System.out.println(element);
	}
	
	
}














