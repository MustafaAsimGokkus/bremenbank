package multithreading;

public class MultiThreading02 {
public static void main(String[] args) throws InterruptedException {
	Long startingTime00 = System.currentTimeMillis();
	Thread thread1 = new Thread(new CounterMultiThread(1));
	Thread thread2 = new Thread(new CounterMultiThread(2));
	
	thread1.start();
    thread2.start();
    
    thread1.join();
    thread2.join();
    Long endingTime00 = System.currentTimeMillis();
    System.out.println("Duration of thread1 and thread2: "+(-startingTime00+endingTime00));
    
    
    Long startingTime01 = System.currentTimeMillis();
    Thread thread3 = new Thread(new Runnable() {
    
		@Override
		public void run() {
			for(int i=1; i<=9; i++) {
				System.out.println("i="+i+ " Thread number:3");
			   }
			
		}});
    
      thread3.start();
    
    
    
      Thread thread4 = new Thread(new Runnable() {
    	    
  		@Override
  		public void run() {
  			for(int i=1; i<=9; i++) {
  				System.out.println("i="+i+ " Thread number:4");
  			   }
  			
  		}});
      
       thread4.start();
        Long endingTime01 = System.currentTimeMillis();
    
        
        thread3.join();
        thread4.join();
        System.out.println("Duration of thread3 and thread4: "+(-startingTime01+endingTime01));
    
    
    
    }
}









class CounterMultiThread implements Runnable{

	private int threadNumber;
	
	public CounterMultiThread(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	
	
	@Override
	public void run() {
	for(int i=1; i<=9; i++) {
		System.out.println("i="+i+ " Thread number:"+threadNumber);
	   }
	}
	
}


/*
    Thread thread  = new Thread(new Runnable(){
    
    @Override
    public void run(){
     
     //here come own method for example a for loop
     
     }});



*/
