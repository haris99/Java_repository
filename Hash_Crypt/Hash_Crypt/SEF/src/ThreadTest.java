import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ThreadTest implements Runnable{
	private String fileName;
	
	public ThreadTest(String fileName){
		this.fileName = fileName;
	}
	
	public void start(){
		run();
	}
	
	@Override
	public void run() {
		int timesToRead = 300;
		long t= System.currentTimeMillis();
		//System.out.println(t);
		// TODO Auto-generated method stub
		for (int i =1;i<=timesToRead;i++){
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            //sb.append(line);
	            //sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        //String everything = sb.toString();
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("File: "+ fileName +" read "+timesToRead+" times"+ " in "+ (System.currentTimeMillis()-t)+ " miliseconds");
		
	}
	
	
	public static void main(String args[]){
		//multipleThreads();
		singleThreads();
		
	}
	
	public static void multipleThreads(){
		ThreadTest t1 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file1.txt");
		ThreadTest t2 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file2.txt");
		ThreadTest t3 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file3.txt");
		ThreadTest t4 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file4.txt");
		Thread thread1 = new Thread(t1);
		Thread thread2 = new Thread(t2);
		Thread thread3 = new Thread(t3);
		Thread thread4 = new Thread(t4);
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
	
	public static void singleThreads(){
		ThreadTest thread = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file1.txt");
		thread.run();
		ThreadTest thread2 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file2.txt");
		thread2.run();
		ThreadTest thread3 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file3.txt");
		thread3.run();
		ThreadTest thread4 = new ThreadTest("C:\\SEF - Participants Workspace\\Hash_Crypt\\Hash_Crypt\\SEF\\src\\file4.txt");
		thread4.run();
	}
	
}
