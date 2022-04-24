package multithreading;

public class Volatile01 {
 volatile public static int age=0;;
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
				if(age == 0) {
					System.out.println("Thread1 is running");
				}else {
					break;
				}}}});
		thread1.start();
		
		
	
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
			   try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				age=1;
				System.out.println("age is updated");
			}});
		thread2.start();
		

	}

}
