package test;

public class ThreadLocalDemo implements Runnable {
	private final static ThreadLocal<Integer> threadLocal  = new ThreadLocal<Integer>();
	
	@Override
	public void run() {
		 threadLocal.set( (int) (Math.random() * 100D) );
		    
         try {
             Thread.sleep(2000);
         } catch (InterruptedException e) {
         }
 
         System.out.println(threadLocal.get());
	}
	
	public static void main(String[] args) {
		ThreadLocalDemo demo = new ThreadLocalDemo();
		Thread thread1 = new Thread(demo, "A");
		Thread thread2 = new Thread(demo, "B");
		thread1.start();
		thread2.start();
	}

}
