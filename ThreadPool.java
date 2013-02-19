package edu.upenn.cis455.hw1;

import java.util.*;

// This singleton class creates a pool of threads when instantiated. 

public class ThreadPool{

	private static int portNum;
	private static int thread_pool_size;
	private String rootDir;
	private String pathToServletXml;
	private ProcessThread runnable;
	private static ThreadPool threadPool;
	private static ArrayList<Thread> myThread;
	public static boolean interrupt_flag;
	private static int lastThreadIndex = 0;
	private static HashMap<Integer, String> uriReg = new HashMap<Integer, String>();
	
	// Constructor takes as arg, the thread pool size and queue size
	private ThreadPool(int in_thread_pool_size, String rootDir, int in_portNum, String pathToServletXml) {
		this.rootDir = rootDir;
		this.pathToServletXml = pathToServletXml;
		interrupt_flag = false;
		portNum = in_portNum;
		myThread = new ArrayList<Thread>();
		runnable = new ProcessThread(this.rootDir, this.pathToServletXml);
		thread_pool_size = in_thread_pool_size;
		for(int i=0; i<thread_pool_size; i++){
			myThread.add(i, new Thread(runnable));
			myThread.get(i).start();
			uriReg.put((int)myThread.get(i).getId(), "waiting");
		}
	}

	public static void GetSingleton(int in_thread_pool_size, String rootDir, int portNum, String pathToServletXml){
		if(threadPool == null){
			threadPool = new ThreadPool(in_thread_pool_size, rootDir, portNum, pathToServletXml);
		}
		return;
	}

	public static void interruptAllThreads(long callingThreadId) throws AllThreadsTerminatedException{
		if(threadPool != null){
			for(int i=0; i<thread_pool_size; i++){
				Thread thisThread = myThread.get(i);
				if(thisThread.getId() != callingThreadId){
					thisThread.interrupt();
					while(thisThread.getState() != Thread.State.valueOf("TERMINATED"));
				}
				else{
					System.out.println("last thread ind is set here to: "+i);
					lastThreadIndex = i;
				}
			}
		}
		throw new AllThreadsTerminatedException();
		
	}

	public static void isLastThreadTerminated(){
		System.out.println("Last thread index: " + lastThreadIndex);
		while(myThread.get(lastThreadIndex).getState() != Thread.State.valueOf("TERMINATED"));
		return;
	}
	
	// handle GET /control 
	public static String getControlDisplay(){
		String html;
		Thread thread;
		html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
		html += "<h1> Rohan Shah (shahroh) </h1></br>";
		
		for(int i=0; i<thread_pool_size; i++){
			thread = myThread.get(i);
			html += "<h2> thread name: " + thread.getName() + "     status: " + thread.getState() + "     URI: " + uriReg.get((int)thread.getId())+ "</h2></br>";
		}
		
		return html;
	}
	
	// get port number
	public static int getPortNumber(){
		return portNum;
	}
	
	// hash the thread ID to its URI 
	public static void registerURI(long threadID, String uri){
		uriReg.put((int)threadID, uri);
	}
	
	public static void removeURI(long threadID){
		uriReg.remove((int)threadID);
	}
}
