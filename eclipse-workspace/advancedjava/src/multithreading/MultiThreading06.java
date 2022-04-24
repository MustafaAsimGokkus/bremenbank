package multithreading;

public class MultiThreading06 {
 public static void main(String[] args) {
	
	 Brackets3 bracket1 = new Brackets3();
	 Brackets3 bracket2 = new Brackets3();
	 
	 Thread thread1 = new Thread(new Runnable() {
		
		@Override
		public void run() {
		for(int i=1; i<=10; i++) {
			bracket1.generateBrackets("thread1");
		}
		System.out.println("thread1 is completed");
			
		}});
	    thread1.start();
	    
	    Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
			for(int i=1; i<=10; i++) {
				bracket2.generateBrackets("thread2");
			}
			System.out.println("thread2 is completed");
				
			}});
		    thread2.start();
	    
  }
}


class Brackets3{
 synchronized static public void generateBrackets(String threadName) {
		for(int i=1; i<=10; i++) {
		
			try {
				Thread.sleep(20);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
			if(i<=5) {
			System.out.print("[");
		}else {
			System.out.print("]");
		}
		}
		System.out.println("Thread executed: "+threadName);
	}
}