//@code contributor: Mohammad J. Nourbakhsh
import java.util.concurrent.*;


public class Scraper_Test {
	private static URLfinder urlfinder = new URLfinder();
	
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new FindUrlHREFsTask());
		executor.execute(new WriterTask());
		executor.shutdown();
		
	}
	
	public static class FindUrlHREFsTask implements Runnable{
	@Override
	public void run() {
		try {
		while(true){
		urlfinder.FindUrlHREFs();
			Thread.sleep(1000);
		}
		}
		 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		 }
		}
	}
	
	public static class WriterTask implements Runnable{
		@Override
		public void run() {
			while(true){
			urlfinder.Writer();	
			}
		}
		
		
	}

}
