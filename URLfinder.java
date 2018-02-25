//@code contributor: Mohammad J. Nourbakhsh

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class URLfinder  {
	
	private static Lock lock = new ReentrantLock(); 
	private static Condition newHref = lock.newCondition();
	private static final String FILENAME = "D:\\workspace\\Scraperurls.txt";
	private static final File file = new File("D:\\workspace\\Scraperurls.txt");
	private static final String FILENAME1 = "D:\\workspace\\Scraper.txt";
	private PrintWriter pw ;
	private Scanner inp = new Scanner(System.in);
	private String URL;
	private String ahref;
	private Document doc1 = null;
	private Elements els = null;
	private ArrayList<String> tmp = new ArrayList<>();
	private PrintWriter pw2 ;
	private Document doc2;
	private Elements image;
	private String head;
	private String prgph;
	private String tmpURLstr;
	private int i = -1;
	private int q = 0;

	
	
	
    public URLfinder() {
    	
    	System.out.println(i);
    	System.out.println(q);
    	
    	System.out.println("Enter The URL");
    	this.URL = inp.next();
    	System.out.println("Enter thr HREFs model");
    	this.ahref = inp.next();
    	
    	try {
			pw = new PrintWriter(new FileOutputStream(new File(FILENAME),true));
		} catch (FileNotFoundException e) {
			System.out.println("File for writing in not found(URLfinde)");
		}
    	
    	try {
			pw2 = new PrintWriter(new FileOutputStream(new File(FILENAME1),true));
		} catch (FileNotFoundException e) {
			System.out.println("File for writing in not found");
		}
	}
	
	
	public void FindUrlHREFs() {
//		System.out.println(i);
		for (String element : tmp) {
			System.out.println(element);
		}
		
		try {
			this.doc1 = Jsoup.connect(URL).get();
		} catch (IOException e) {
			System.out.println("Somting went wrong in doc1 URLfinder jsoup connect");
		}
		els = doc1.select(ahref);
		
		if(checkIfNew(this.els, this.tmp)){
			tmp.clear();
		for (int k = 0; k < 3; k++) {
			lock.lock();
			pw.println(URL+els.get(k).attr("href"));
			pw.flush(); 
//			System.out.println(tmp.get(i));
			this.tmp.add(k, els.get(k).attr("href").toString());
//			System.out.println(els.get(i).toString());
			newHref.signalAll();
			lock.unlock();
			this.i +=1;
		}
		}
		
	}
	
	public void Writer(){
			
		
		
			try {
				while(i<q){
					lock.lock();
				newHref.await();
				lock.unlock();
				}
				
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				Scanner inf = new Scanner(file);
				for (int e = 0; e < q; e++) {
					if (inf.hasNextLine()) {
						inf.nextLine();
					}	
				}

				this.tmpURLstr = inf.nextLine();
				inf.close();
			} catch (FileNotFoundException e1) {
				System.out.println("File for reading from not found (Writer)");
			}
			
			pw2.println(q);
			pw2.println(tmpURLstr);
			
			try {
				doc2 = Jsoup.connect(tmpURLstr).get();
			} catch (IOException e) {
				System.out.print("Jsoup connect is wrong! (Writer)");
			}
			this.head = doc2.select("a#source_news_link").text();
	        pw2.println(head);
	  	    this.prgph = doc2.select("p#news-teaser").text();
	  	    pw2.println(prgph);
	  	    image = doc2.select("img[data-src$=/thumb]");
	  	    pw2.println("\npic.src : " + image.attr("data-src"));
	        pw2.println("-------------------------------------------");
	        pw2.println();
	        pw2.flush();
	        this.q +=1;
//	        System.out.println(i);
	        
		
			}
	
	public boolean checkIfNew(Elements els, ArrayList<String> tmp){
		boolean flag = true ;
		
		if(!tmp.isEmpty()){
		if(els.get(0).attr("href").toString().equals(tmp.get(0))&&
				els.get(1).attr("href").toString().equals(tmp.get(1))&&
				els.get(1).attr("href").toString().equals(tmp.get(1))
				){
			flag = false;
		}}
								
		return flag;
	}
	
	

}
