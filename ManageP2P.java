import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ManageP2P {
	
	public static ArrayList<NodeInfo> Node;
	public static DatagramSocket manageSocket;
	
	public static void main( String [] args) throws Exception
	{
		System.out.println("Enter the port for Manage Server: ");
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int port = scanner.nextInt();
		manageSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[1024];
		Node = new ArrayList<NodeInfo>();
		NodeInfo nodeinfo;
		
		StartSearch request =new StartSearch(manageSocket); 
        Thread thread = new Thread(request);
        thread.start();
        File file = new File("cacheinfo.txt");
		file.createNewFile();
		
		
		while(true){
			System.out.println("Manage Server is listening on port number: " + port);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			manageSocket.receive(receivePacket);
		
			String Msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println("Server:  "+Msg);
          
			Msg = Msg.trim();
			String[] token = Msg.split(":");
			String cacheIP = null;
			int cachePort = 0;
			String cahcheFileName = null;
			String line = "";
			int fileCount = 0;
			FileWriter writer = new FileWriter(file, true);
			if(token[0].equals("CACHEINFO"))
			{
				System.out.println("Inside cache info");
				cahcheFileName = token[3];
				cahcheFileName.trim();
				System.out.println(cahcheFileName);
				cachePort = Integer.parseInt(token[2]);
				cacheIP = token[1];
				BufferedReader br = new BufferedReader(new FileReader("zipfprob.txt"));
				while((line = br.readLine()) != null)
				{
					String[] words = line.split(":");
					//System.out.println(words[1]);
					if(words[1].equals(cahcheFileName))
					{
						System.out.println("Inside checking");
						fileCount = Integer.parseInt(words[3]);
						writer.write(cahcheFileName + ":" + cacheIP + ":" + cachePort + ":" + fileCount + "\n");
					}
				}
				writer.flush();
			    writer.close();
			    startCache();
			}
			else{
				String IPnode = token[0];
				int portnode = Integer.parseInt(token[1]);
        
				nodeinfo = new NodeInfo(portnode, IPnode);
				Node.add(nodeinfo);
        
				for(int i = 0;i< Node.size(); i++)
				{
					System.out.println("Node Info: " +Node.get(i).IPaddr + " "+Node.get(i).port);
				}
			}
			
		}
		
	}
	public static void startCache() throws NumberFormatException, IOException
	{
		FileReader freader = new FileReader("cacheinfo.txt");
    	BufferedReader bufferedReader = new BufferedReader(freader);
    	String line = null;
    	String filetoCache = null;
    	int numQueriesCache = 0;
    	int oneEntry1 = 0, oneEntry2 = 0, oneEntry3 = 0, oneEntry4 = 0, oneEntry5 = 0; 
    	Random randCache = new Random();
    	int numNodes = Node.size();
    	
    	
    	while((line = bufferedReader.readLine()) != null){
    		String[] token = line.split(":");
    		filetoCache = token[0];
    		System.out.println(filetoCache);
    		numQueriesCache = Integer.parseInt(token[3]);
    		String IPofFile = token[1];
    		int portOfFile = Integer.parseInt(token[2]);
    		int cachesndIPCount = 0;
    				
    		if((numQueriesCache == 15) || (numQueriesCache == 18) || (numQueriesCache == 22))
    		{
    			
    			while(cachesndIPCount < 8){
    				int index = randCache.nextInt(numNodes -1) + 2;
    				if(index == numNodes){
    					index = numNodes - 1;
    				}
    				InetAddress sendIP = InetAddress.getByName(Node.get(index).IPaddr);
    				int sendPort = Node.get(index).port;
    				
    				byte[] sendSearchData = new byte[1024];
		    		String searchStart = "0011" + " " + "CACHE" +" " + IPofFile + " " + portOfFile + " " + "\"" + filetoCache + "\"";
		    		sendSearchData = searchStart.getBytes();        
		 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
		 	        manageSocket.send(sendSearch);
		 	        System.out.println("hello1: " + searchStart);
		 	        cachesndIPCount++;
    			}
    		}
    		else if((numQueriesCache == 14) || (numQueriesCache == 13) || (numQueriesCache == 12))
    		{
    			
    			while(cachesndIPCount < 5){
    				int index = randCache.nextInt(numNodes -1) + 2;
    				if(index == numNodes){
    					index = numNodes - 1;
    				}
    				InetAddress sendIP = InetAddress.getByName(Node.get(index).IPaddr);
    				int sendPort = Node.get(index).port;
    				
    				byte[] sendSearchData = new byte[1024];
		    		String searchStart = "0011" + " " + "CACHE" +" " + IPofFile + " " + portOfFile + " " + "\"" + filetoCache + "\"";
		    		sendSearchData = searchStart.getBytes();        
		 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
		 	        manageSocket.send(sendSearch);
		 	        System.out.println("hello2: " + searchStart);
		 	        cachesndIPCount++;
    			}
    		}
    		else if((numQueriesCache == 11) || (numQueriesCache == 10) || (numQueriesCache == 9))
    		{
    			
    			while(cachesndIPCount < 3){
    				int index = randCache.nextInt(numNodes -1) + 2;
    				if(index == numNodes){
    					index = numNodes - 1;
    				}
    				InetAddress sendIP = InetAddress.getByName(Node.get(index).IPaddr);
    				int sendPort = Node.get(index).port;
    				
    				byte[] sendSearchData = new byte[1024];
		    		String searchStart = "0011" + " " + "CACHE" +" " + IPofFile + " " + portOfFile + " " + "\"" + filetoCache + "\"";
		    		sendSearchData = searchStart.getBytes();        
		 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
		 	        manageSocket.send(sendSearch);
		 	        System.out.println("hello3: " + searchStart);
		 	        cachesndIPCount++;
    			}
    		}
    		else if((numQueriesCache == 8) || (numQueriesCache == 7) || (numQueriesCache == 6))
    		{
    			
    			while(cachesndIPCount < 2){
    				int index = randCache.nextInt(numNodes -1) + 2;
    				if(index == numNodes){
    					index = numNodes - 1;
    				}
    				InetAddress sendIP = InetAddress.getByName(Node.get(index).IPaddr);
    				int sendPort = Node.get(index).port;
    				
    				byte[] sendSearchData = new byte[1024];
		    		String searchStart = "0011" + " " + "CACHE" +" " + IPofFile + " " + portOfFile + " " + "\"" + filetoCache + "\"";
		    		sendSearchData = searchStart.getBytes();        
		 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
		 	        manageSocket.send(sendSearch);
		 	       System.out.println("hello4: " + searchStart);
		 	        cachesndIPCount++;
    			}
    		}
    	}
	}
}

class StartSearch implements Runnable{

	    DatagramPacket packet;
	    DatagramSocket socket;
	    byte[] sendData = new byte[1024];
	    ManageP2P mp = new ManageP2P();

	    public StartSearch(DatagramSocket socket) throws Exception 
	    {
	        this.socket = socket;
	    }

	    @Override
	    public void run() 
	    {
	        try {
	        	while(true)
	        	{
	        		System.out.println("Type 1 to start searching");
	        		Scanner scanner = new Scanner(new InputStreamReader(System.in));
	        		int sr = scanner.nextInt();
	        		if(sr == 1)
	        		{
	        			sendRequest();
	        		}
	        	}
	        }catch (Exception e){
	            System.out.println(e);
	        }

	    }  
	    
	    void sendRequest() throws IOException
	    {
	    	System.out.println("Type SEARCH to start the searching: ");
	    	@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new InputStreamReader(System.in));
	    	scanner.nextLine();
	    	System.out.println("Enter 1 for Normal Search or 2 for zipfs search");
	    	@SuppressWarnings("resource")
			//Scanner scanner1 = new Scanner(new InputStreamReader(System.in));
	    	String scan = scanner.nextLine();
	    	ArrayList<RandomFileList> randomfile = new ArrayList<RandomFileList>();
	    	RandomFileList rfl;
	    	int i = 0;
	    	@SuppressWarnings("resource")
			BufferedReader br2 = new BufferedReader(new FileReader("resources.txt")); //opening the file for reading
			String readf = null;
	    	while ((readf = br2.readLine()) != null) 
			{
				if(readf.startsWith("#"))
				{
					continue;
				}
				else
				{
					i = i+1;
					rfl = new RandomFileList(i,readf);
					randomfile.add(rfl);   //adding to an arraylist
				}
				
			}
	     	if(scan.equals("1"))
	     	{
	     		int count = 0;
		    	int index1 = 1;
		    	System.out.println("Enter the value of Ns: ");
		    	int NS = scanner.nextInt();
		    	
		    	//Random rand1 = new Random();
		    	//int value1 = rand1.nextInt(160) + 1;
				//String filetoSearch = (randomfile.get(value1).filename);
				
		    	while(count != NS)
		    	{
		    		
		    		Random rand = new Random();
			    	int value = rand.nextInt(5) + 2;
			    		
			    	InetAddress sendIP = InetAddress.getByName(mp.Node.get(index1).IPaddr);
			    	int sendPort = mp.Node.get(index1).port;
//		    		
			    	//System.out.println("HI");
			    	//Random rand1 = new Random();
			    	//int value1 = rand1.nextInt(160) + 1;
			    	int value1 = 0;
			    	while(value1 < 160){
					String filetoSearch = (randomfile.get(value1).filename);

		    		//System.out.println(filetoSearch);
		    		
		    		
		    		byte[] sendSearchData = new byte[1024];
		    		String searchStart = "0011" + " " + "SEARCH" +" "+ "\"" +filetoSearch + "\"";
		    		sendSearchData = searchStart.getBytes();        
		 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
		 	        socket.send(sendSearch);
		 	        value1++;
			    	}
		 	        
		 	        index1++;
		 	        count++;
		    	}
		    	}
		    	else if (scan.equals("2"))
		    	{
		    		System.out.println("Enter the value of Ns");
		    		int valueNs = scanner.nextInt();
		    		int numNodes = mp.Node.size();
		    		int count = 0;
		    		int count1 = 0;
		    		while(count != valueNs){
		    			Random randNs = new Random();
		    			int index = randNs.nextInt(numNodes - 1) + 2;
		    			if(index == numNodes)
		    			{
		    				index = numNodes - 1;
		    			}
		    			
		    			InetAddress sendIP = InetAddress.getByName(mp.Node.get(index).IPaddr);
				    	int sendPort = mp.Node.get(index).port;
				    	
				    	FileReader freader = new FileReader("zipfprob.txt");
				    	BufferedReader bufferedReader = new BufferedReader(freader);
				    	String line = null;
				    	int numQueries = 0;
				    	String filetoSearch = null;
				    	while((line = bufferedReader.readLine()) != null){
				    		String[] token = line.split(":");
				    		numQueries = Integer.parseInt(token[3]);
				    		filetoSearch = token[1];
				    		//System.out.println(filetoSearch);
				    		
				    		while(count1 < numQueries){
				    			byte[] sendSearchData = new byte[1024];
					    		String searchStart = "0011" + " " + "SEARCH" +" "+ "\"" +filetoSearch + "\"";
					    		//System.out.println(searchStart);
					    		sendSearchData = searchStart.getBytes();        
					 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
					 	        socket.send(sendSearch);
					 	        count1++;
				    		}
				    		count1 = 0;
				    	}
				    	bufferedReader.close();
				    	freader.close();		    	
				    	count++;
		    		}

		    		
		    		
		    		
//		    		System.out.println("Enter 1 to search for most popular file and 2 for least popular file: ");
//			    	int typeSearch = scanner.nextInt();
//			    	if(typeSearch == 1)
//			    	{
////				    	Random rand1 = new Random();
////				    	int value1 = rand1.nextInt(5) + 1;
////						String filetoSearch = (randomfile.get(value1).filename);
//						int numNodes = mp.Node.size();
//						Random randNs = new Random();
//						int valueNs = randNs.nextInt(numNodes-2) + (numNodes-6);
//						int count = 0;
//						int index1 = 2;
//						System.out.println("Value of Ns: " + valueNs);
//						while(count != valueNs)
//						{	
//					    	InetAddress sendIP = InetAddress.getByName(mp.Node.get(index1).IPaddr);
//					    	int sendPort = mp.Node.get(index1).port;
////				    		
//					    	//System.out.println("HI");
//
//					    	Random rand1 = new Random();
//					    	int value1 = rand1.nextInt(5) + 1;
//							String filetoSearch = (randomfile.get(value1).filename);
//
//				    		System.out.println(filetoSearch);
//				    		
//				    		
//				    		byte[] sendSearchData = new byte[1024];
//				    		String searchStart = "0011" + " " + "SEARCH" +" "+ "\"" +filetoSearch + "\"";
//				    		sendSearchData = searchStart.getBytes();        
//				 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
//				 	        socket.send(sendSearch);
//				 	        
//				 	        index1++;
//				 	        count++;
//				    	}
//			    	}
//			    	else if(typeSearch == 2)
//			    	{
//			    		Random rand1 = new Random();
//				    	int value1 = rand1.nextInt(160) + 155;
//						String filetoSearch = (randomfile.get(value1).filename);
//						int numNodes = mp.Node.size();
//						Random randNs = new Random();
//						int valueNs = randNs.nextInt(numNodes-6) + (numNodes-2);
//						int count = 0;
//						int index1 = 2;
//						while(count != valueNs)
//						{	
//					    	InetAddress sendIP = InetAddress.getByName(mp.Node.get(index1).IPaddr);
//					    	int sendPort = mp.Node.get(index1).port;
////				    		
//					    	//System.out.println("HI");
//				    		
//				    		System.out.println(filetoSearch);
//				    		
//				    		byte[] sendSearchData = new byte[1024];
//				    		String searchStart = "0011" + " " + "SEARCH" +" "+ "\"" +filetoSearch + "\"";
//				    		sendSearchData = searchStart.getBytes();        
//				 	        DatagramPacket sendSearch = new DatagramPacket(sendSearchData, sendSearchData.length, sendIP, sendPort); 
//				 	        socket.send(sendSearch);
//				 	        
//				 	        index1++;
//				 	        count++;
//				    	}
//			    	}
		    	}
	     	}
	    	
	    }

class NodeInfo
{
	int port;
	String IPaddr;
	
	NodeInfo(int pt, String ip)
	{
		this.port = pt;
		this.IPaddr = ip;
	}
}

class RandomFileList
{
	int index;
	String filename;
	
	RandomFileList(int index, String filename)
	{
		this.index = index;
		this.filename = filename;
	}
}
