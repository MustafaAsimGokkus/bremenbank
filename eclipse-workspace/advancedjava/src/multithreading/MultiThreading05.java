package multithreading;

public class MultiThreading05 {

	public static void main(String[] args) throws InterruptedException {
		
		Long startingTime00 = System.currentTimeMillis();
		
		Brackets2 brackets = new Brackets2(); 
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
		thread1.join();
		thread2.join();
		Long endingTime00 = System.currentTimeMillis();
		//System.out.println("Duration synchronized: "+(-startingTime00+endingTime00));//3108
		System.out.println("Duration synchronized block: "+(-startingTime00+endingTime00));//1565
	}
  }
  class Brackets2{
  public void generateBrackets() {
	   //1st part
	  
	  synchronized (this) {
	  for(int i=1; i<=10; i++) {
		if(i<=5) {
			System.out.print("[");
		}else {
			System.out.print("]");
		}
		}

		System.out.println();
	  }
		
		//2nd part 
		for(int i=1; i<=5; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}