package multithreading;

public class MultiThreading04 {
  public static void main(String[] args) {
	//i will create object because method is not static
	Brackets brackets = new Brackets(); 
	Thread thread1 = new Thread(new Runnable() {

		@Override
		public void run() {
		
		for(int i=1; i<=5; i++) {
	    brackets.generateBrackets();	
			}	
		  }
		});
	thread1.start();
	
	Thread thread2 = new Thread(new Runnable() {

		@Override
		public void run() {
		for(int i=1; i<=5; i++) {
			brackets.generateBrackets();	
		}}
		});
	thread2.start();
   }
}

class Brackets{
synchronized	public void generateBrackets() {
		for(int i=1; i<=10; i++) {
		if(i<=5) {
			System.out.print("[");
		}else {
			System.out.print("]");
		}
		}
		System.out.println();
	}
}