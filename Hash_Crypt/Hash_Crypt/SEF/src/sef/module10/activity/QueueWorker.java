package sef.module10.activity;

import java.util.Timer;
import java.util.TimerTask;

public class QueueWorker implements Runnable {

	private String task;
	private String name;
	private int length;
	private TaskQueue queue;
	public boolean done = false;

	public QueueWorker(String name, TaskQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	public void run() {

		while (true) {

			synchronized (this.queue) {
				if (this.queue.isEmpty()) {
					try {
						System.out.println(name + " waiting for task");
						queue.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

			this.task = queue.getNextTask();
			this.length = task.length();

			doTask();
			
		}
	}
	
	private void doTask(){

		while (true){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t= System.currentTimeMillis();
		long end = t+5000;
		double x=5;
		double y=7;
		while(System.currentTimeMillis() < end) {
			x=y*x*y;
			y=x/5*y;
		  // do something
		  // pause to avoid churning
		  //Thread.sleep( xxx );
		}
		/*System.out.println(name + " GIVEN : " + task);
		for (int i = 0; i < length; i++) {
			System.out.println(name + " DOING " + task + " at cycle " + i);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	}
}
