
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class P2PConnection {
	

	    public static DatagramSocket clientSocket1 = null;
	    public static DatagramSocket clientSocket2 = null;
	    public static DatagramSocket clientSocket3 = null;
	    public static Thread thread1;
	    public static Thread thread2;
	    public static int stopConn = 0;
	    ClientServer CSP = new ClientServer();
	    //public static int serAckCheck = 0;
	    public static int filePresent = 0;
	    public static int alreadyAddressed = 0;
	    public static int srcNode = 0;
	    int keyIndex = 0;
	    String joinMsg;
	    public ArrayList<RoutingTableInfo> routingTableNode = new ArrayList<RoutingTableInfo>();
	    public RoutingTableInfo rti;
	    public ArrayList<RoutingTableInfo> routingTableNode1 = new ArrayList<RoutingTableInfo>();
	    public RoutingTableInfo rti1;
	    public int recvQueryMsg = 0;
	    public int frwdQueryMsg = 0;
	    public int answQueryMsg = 0;
	    List<String> list = new ArrayList<String>();
	    public static int uid = 1;
	    public ArrayList<CacheList> cacheMemory = new ArrayList<CacheList>(2);
	    public CacheList chl;
	    //public static MyLogger p2plog1 = new MyLogger();
	    
	    
	    

	    public void openConnection(String ownIp, int ownPort, int numConnection, String host1, int port1, String host2, int port2, String host3, int port3) throws Exception
	    {
	    	if(numConnection == 1)
	    	{
	    		    		
	    		clientSocket1 = new DatagramSocket (port1);
	    		InetAddress IPAddress1 = InetAddress.getByName(host1);
	    		byte[] sendData1 = new byte[1024];
	    		String joinMsg = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength = joinMsg.length() + 4;
	    		String testmsg1 = "00" + joinMsgLength + " " + joinMsg;
	    		System.out.println("joinMsg and testmsg1 in 1" + joinMsg + " " + testmsg1);
	    		sendData1 = testmsg1.getBytes();
	    		DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress1, port1);
	    		clientSocket1.send(sendPacket1);
	    		
	    		byte[] rcv1= new byte[1024];
	    		DatagramPacket rcvMsg1 = new DatagramPacket(rcv1, rcv1.length);
	    		clientSocket1.receive(rcvMsg1);
		        System.out.println(new String(rcvMsg1.getData(), 0, rcvMsg1.getLength()));
		        
		        /*Creating thread to handle the query and leave message*/
		        InetAddress IPforQL = InetAddress.getByName(ownIp);
		        MessageHandler request =new MessageHandler(clientSocket1, null, null, numConnection, IPforQL, ownPort, IPAddress1, port1, null, 0, null, 0); 
		        thread1 = new Thread(request);
		        thread1.start();
	    	}
	    	else if(numConnection == 2)
	    	{
	    	    		
	    		clientSocket1 = new DatagramSocket (port1);
	    		InetAddress IPAddress2 = InetAddress.getByName(host1);
	    		byte[] sendData2 = new byte[1024];
	    		String joinMsg1 = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength1 = joinMsg1.length() + 4;
	    		String testmsg2 = "00" + joinMsgLength1 + " " + joinMsg1;
	    		System.out.println("joinMsg and testmsg1 in 2" + joinMsg1 + " " + testmsg2);
	    		sendData2 = testmsg2.getBytes();
	    		DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPAddress2, port1);
	    		clientSocket1.send(sendPacket2);
	    		
	    		clientSocket2 = new DatagramSocket (port2);
	    		InetAddress IPAddress3 = InetAddress.getByName(host2);
	    		byte[] sendData3 = new byte[1024];
	    		String joinMsg2 = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength2 = joinMsg2.length() + 4;
	    		String testmsg3 = "00" + joinMsgLength2 + " " + joinMsg1;
	    		System.out.println("joinMsg and testmsg1 in 3" + joinMsg2 + " " + testmsg3);
	    		sendData3 = testmsg3.getBytes();
	    		DatagramPacket sendPacket3 = new DatagramPacket(sendData3, sendData3.length, IPAddress3, port2);
	    		clientSocket2.send(sendPacket3);
	    		
	    		byte[] rcv2= new byte[1024];
	    		DatagramPacket rcvMsg2 = new DatagramPacket(rcv2, rcv2.length);
	    		clientSocket1.receive(rcvMsg2);
		        System.out.println(new String(rcvMsg2.getData(), 0, rcvMsg2.getLength()));
		        
		        byte[] rcv3= new byte[1024];
	    		DatagramPacket rcvMsg3 = new DatagramPacket(rcv3, rcv3.length);
	    		clientSocket2.receive(rcvMsg3);
		        System.out.println(new String(rcvMsg3.getData(), 0, rcvMsg3.getLength()));
		        
		        /*Creating thread to handle the query and leave message*/
		        InetAddress IPforQL = InetAddress.getByName(ownIp);
		        MessageHandler request =new MessageHandler(clientSocket1, clientSocket2, null ,numConnection, IPforQL, ownPort, IPAddress2, port1, IPAddress3, port2, null, 0); 
		        thread1 = new Thread(request);
		        thread1.start();
	    	}
	    	else if(numConnection == 3)
	    	{
	    		  		
	    		clientSocket1 = new DatagramSocket (port1);
	    		InetAddress IPAddress4 = InetAddress.getByName(host1);
	    		byte[] sendData4 = new byte[1024];
	    		String joinMsg3 = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength3 = joinMsg3.length() + 4;
	    		String testmsg4 = "00" + joinMsgLength3 + " " + joinMsg3;
	    		System.out.println("joinMsg and testmsg1 in 4" + joinMsg3 + " " + testmsg4);
	    		sendData4 = testmsg4.getBytes();
	    		DatagramPacket sendPacket4 = new DatagramPacket(sendData4, sendData4.length, IPAddress4, port1);
	    		clientSocket1.send(sendPacket4);
	    		
	    		clientSocket2 = new DatagramSocket (port2);
	    		InetAddress IPAddress5 = InetAddress.getByName(host2);
	    		byte[] sendData5 = new byte[1024];
	    		String joinMsg4 = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength4 = joinMsg4.length() + 4;
	    		String testmsg5 = "00" + joinMsgLength4 + " " + joinMsg4;
	    		System.out.println("joinMsg and testmsg1 in 5" + joinMsg4 + " " + testmsg5);
	    		sendData5 = testmsg5.getBytes();
	    		DatagramPacket sendPacket5 = new DatagramPacket(sendData5, sendData5.length, IPAddress5, port2);
	    		clientSocket2.send(sendPacket5);
	    		
	    		clientSocket3 = new DatagramSocket (port3);
	    		InetAddress IPAddress6 = InetAddress.getByName(host3);
	    		byte[] sendData6 = new byte[1024];
	    		String joinMsg5 = "JOIN" + " " + ownIp + " "+ownPort;
	    		int joinMsgLength5 = joinMsg5.length() + 4;
	    		String testmsg6 = "00" + joinMsgLength5 + " " + joinMsg5;
	    		System.out.println("joinMsg and testmsg1 in 6" + joinMsg5 + " " + testmsg6);
	    		sendData6 = testmsg6.getBytes();
	    		DatagramPacket sendPacket6 = new DatagramPacket(sendData6, sendData6.length, IPAddress6, port3);
	    		clientSocket3.send(sendPacket6);
	    		
	    		byte[] rcv4= new byte[1024];
	    		DatagramPacket rcvMsg4 = new DatagramPacket(rcv4, rcv4.length);
	    		clientSocket1.receive(rcvMsg4);
		        System.out.println(new String(rcvMsg4.getData(), 0, rcvMsg4.getLength()));
		        
		        byte[] rcv5= new byte[1024];
	    		DatagramPacket rcvMsg5 = new DatagramPacket(rcv5, rcv5.length);
	    		clientSocket2.receive(rcvMsg5);
		        System.out.println(new String(rcvMsg5.getData(), 0, rcvMsg5.getLength()));
		        
		        byte[] rcv6= new byte[1024];
	    		DatagramPacket rcvMsg6 = new DatagramPacket(rcv6, rcv6.length);
	    		clientSocket3.receive(rcvMsg6);
		        System.out.println(new String(rcvMsg6.getData(), 0, rcvMsg6.getLength()));
		        
		        /*Creating thread to handle the query and leave message*/
		        InetAddress IPforQL = InetAddress.getByName(ownIp);
		        MessageHandler request =new MessageHandler(clientSocket1, clientSocket2, clientSocket3 ,numConnection, IPforQL, ownPort, IPAddress4, port1, IPAddress5, port2, IPAddress6, port3); 
		        thread1 = new Thread(request);
		        thread1.start();
	    	}
	    	else
	    	{
	    		System.out.println("Check for error");
	    	}
	    	
	    	
//	        clientSocket = new DatagramSocket (port);
//	        InetAddress IPAddress = InetAddress.getByName(host);   
//	        byte[] sendData = new byte[1024];       
//	        //System.out.println(sentence);
//	        sendData = sentence.getBytes();        
//	        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 
//	        clientSocket.send(sendPacket);
	    }

	    void listenConnection(String ownIP, int serport, int numConnection, String host1, int port1, String host2, int port2, String host3, int port3, InetAddress manageIp, int managePort) throws IOException{
	        try {
	        	//System.out.println("server Port:" +serport);
	            // Establish the listen socket.
	            DatagramSocket serverSocket = new DatagramSocket(serport); 
	            //routingTableNode = new ArrayList<RoutingTableInfo>();
	            ArrayList<KeyValue> kvList = new ArrayList<KeyValue>();
	            
	            KeyValue kv;
	            byte[] receiveData = new byte[1024]; 
	            byte[] buf = new byte[1024];
	            InetAddress srcIP = null;
	            int srcPort = 0;
	            String uniMatch = "####";
	            long start = 0;
	            ArrayList<String> uniIdStore = new ArrayList<String>();
	            ArrayList<String> fileFoundNodeId = new ArrayList<String>();
	            
	            if(numConnection == 1){
	            	rti1 = new RoutingTableInfo(port1, host1, "JOINED");
	            	routingTableNode1.add(rti1);
	            }
	            else if(numConnection == 2){
	            	rti1 = new RoutingTableInfo(port1, host1, "JOINED");
	            	routingTableNode1.add(rti1);
	            	rti1 = new RoutingTableInfo(port2, host2, "JOINED");
	            	routingTableNode1.add(rti1);
	            }
	            else if(numConnection == 3)
		    	{
	            	rti1 = new RoutingTableInfo(port1, host1, "JOINED");
	            	routingTableNode1.add(rti1);
	            	rti1 = new RoutingTableInfo(port2, host2, "JOINED");
	            	routingTableNode1.add(rti1);
	            	rti1 = new RoutingTableInfo(port3, host3, "JOINED");
	            	routingTableNode1.add(rti1);
		    	}
	            
	           
	            while (true) {
	            	System.out.println("listening on port " + serverSocket.getLocalPort());
	                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	                serverSocket.receive(receivePacket);        
	                joinMsg = new String( receivePacket.getData(), 0, receivePacket.getLength());
	                System.out.println("Server:  "+joinMsg);
	                CSP.logger.info(joinMsg);
	                joinMsg = joinMsg.trim();
	                String[] token = joinMsg.split(" ");
	                String MsgToken = token[1];
	                String uniqueQueryId = null;
	                int serAckCheck = 0;
	                if(MsgToken.equals("JOIN"))
	                {
	                	String sendIp = token[2];
//	                	InetAddress sendIPAddress = InetAddress.getByName(sendIp);
	                	int sendPort = Integer.parseInt(token[3]);
	                	System.out.println("IP and Port:"+sendIp+ " "+ sendPort);
	                	String joinOkMsg = "0014" + " "+ "JOINOK" + " " + "0" ;
	                	byte[] sendToConnNode = new byte[1024];
	                	sendToConnNode = joinOkMsg.getBytes(); 
	                	DatagramPacket sendJoinOkMsg = new DatagramPacket(buf, 1024);
	                	serverSocket.send (new DatagramPacket(sendToConnNode, sendToConnNode.length, receivePacket.getAddress(), receivePacket.getPort()));
	                	rti = new RoutingTableInfo(sendPort, sendIp, "JOINED");
	                	routingTableNode.add(rti);
	                	String routinglog;
	                	for(int i = 0;i< routingTableNode.size(); i++)
	                	{
	                		System.out.println("Routing Table Info:" +routingTableNode.get(i).IPaddr + " "+routingTableNode.get(i).port + " "+ routingTableNode.get(i).Status);
	                		routinglog = "Routing Table Info:" +routingTableNode.get(i).IPaddr + " "+routingTableNode.get(i).port + " "+ routingTableNode.get(i).Status;
	                		CSP.logger.info(routinglog);
	                	}
	                }
	                else if(MsgToken.equals("SER"))
	                {
	                	srcIP = receivePacket.getAddress();
	                	srcPort = receivePacket.getPort();
	                	String srcIP1 = token[2];
	                	recvQueryMsg ++;
	                	String[] srcIP2 = srcIP1.split("/");
	                	System.out.println("srcIP: " + srcIP2[1]);
	                	int srcPort1 = Integer.parseInt(token[3]);
	                	InetAddress  directIP= InetAddress.getByName(srcIP2[1]);
	                	int searchMsgMatch = 0;
	                	int fileFoundCheck = 0;
	                	
	                	String flName1 = token[4];
	                	int t = 4;
	                	String flname = " ";
	                	if(token[t].endsWith("\""))
                		{
                			flname = token[t];
                		}
	                	else{
	                		while(true)
	                		{
	                			flname = flname + " " +token[t];
	                			t++;
	                			if(token[t].endsWith("\""))
	                			{
	                				flname = flname + " " +token[t];
	                				break;
	                			}
	                			
	                		}
	                	}
	                	int numHop = Integer.parseInt(token[t+1]);
	                	//String uniqueQueryId = token[t+2];
	                	
	                	int i = t + 2;
	                	while(i < token.length)
	                	{
	                		if(i == (t+2))
	                		{
	                			uniqueQueryId = token[i];
	                			i++;
	                		}
	                		else{
	                			uniqueQueryId = uniqueQueryId + " " + token[i];
	                			i++;
	                		}
	                	}
	                	
	                	//System.out.println("token[t+1]:" + token[t+1]);
	                	flname = flname.replaceAll("\"", "");
	                	flname = flname.trim();
	                	System.out.println("filename and number of hop " + " " +flname + " "+ numHop);
	                	InetAddress  IPforSQ= InetAddress.getByName(ownIP);
	                	System.out.println("value of already addressed: " + alreadyAddressed);
	                	for(int s = 0; s < uniIdStore.size(); s++)
                		{
	                		if(uniIdStore.get(s).equals(uniqueQueryId))
	                		{
	                			searchMsgMatch = 1;
	                		}
                		}
	                	
	                	if(uniMatch.equals(uniqueQueryId))
	                	{
	                		searchMsgMatch = 1;
	                	}
	                	
	                	//if(uniMatch.equals(uniqueQueryId))
	                	if(searchMsgMatch == 1)
	                	{
	                		System.out.println("Packet Dropped!!!");
	                	}
	                	else
	                	{
	                		System.out.println("Inside file checking");
	                		for(int k = 0; k < CSP.FileList.size(); k++)
	                		{
	                			if(CSP.FileList.get(k).equals(flname))
	                			{
	                				System.out.println("Inside file checking1");
	                				String searchOkMsg = "SEROK" + " " +  IPforSQ + " "+ serport + " " + numHop + " " + flname;
	                				int srLength = searchOkMsg.length() + 4;
	                				String srOkMsg = "00" + srLength + " " + searchOkMsg;
	                				byte[] sendToQueryNode = new byte[1024];
	                				sendToQueryNode = srOkMsg.getBytes(); 
	                				if(filePresent == 0 || searchMsgMatch != 1){
	                				DatagramPacket sendSearchOkMsg = new DatagramPacket(buf, 1024);
	                				serverSocket.send (new DatagramPacket(sendToQueryNode, sendToQueryNode.length, directIP, srcPort1));
	                				fileFoundNodeId.add(uniqueQueryId);
	                				answQueryMsg++;
	                				CSP.logger.info(srOkMsg);}
	                				kv = new KeyValue(keyIndex + 1, flname);
	                				kvList.add(kv);
	                				serAckCheck = 1;
	                				filePresent = 1;
	                				alreadyAddressed = 1;
	                				//uniMatch = uniqueQueryId;
	                			}
	                		}
	                	}
	                	if((searchMsgMatch != 1) && (serAckCheck != 1))
	                	{
	                		for(int f = 0; f < cacheMemory.size(); f++)
	                		{
	                			if(cacheMemory.get(f).file.equals(flname))
	                			{
	                				String chIP = cacheMemory.get(f).containIP;
	                				InetAddress cacheIP= InetAddress.getByName(chIP);
	                				int cachePort = cacheMemory.get(f).containPort;
	                				numHop++;
	                				String chMsg = "0014" + " " + "CACHEMSG" + " " +  srcIP2[1] + " "+ srcPort1 + " " + numHop + " " + flname;
	                				
	                				byte[] sendCacheMsg = new byte[1024];
	                				sendCacheMsg = chMsg.getBytes(); 
	                				
	                				if(filePresent == 0 || searchMsgMatch != 1){
		                				DatagramPacket sendChMsg = new DatagramPacket(buf, 1024);
		                				serverSocket.send (new DatagramPacket(sendCacheMsg, sendCacheMsg.length, cacheIP, cachePort));
		                				fileFoundNodeId.add(uniqueQueryId);
		                				answQueryMsg++;
		                				CSP.logger.info(chMsg);}
	                					kv = new KeyValue(keyIndex + 1, flname);
	                					kvList.add(kv);
	                					serAckCheck = 1;
	                					filePresent = 1;
	                					alreadyAddressed = 1;
	                					//uniMatch = uniqueQueryId;
	                			}
	                		}
	                	}
	                	System.out.println(uniMatch + " "+ uniqueQueryId);
	                	//if(filePresent == 0)
	                	//{
	                	for(int a = 0; a < fileFoundNodeId.size(); a++)
                		{
	                		if(fileFoundNodeId.get(a).equals(uniqueQueryId))
	                		{
	                			fileFoundCheck = 1;
	                		}
                		}
	                		//if(!(uniMatch.equals(uniqueQueryId)))
	                		if(searchMsgMatch != 1)
	                		{	
	                			if(fileFoundCheck != 1){
	                			list.add(flname);
	                			sendQueryForward(numHop, srcIP1, srcPort1, flname, uniqueQueryId);
	                			alreadyAddressed = 1;
	                			//uniMatch = uniqueQueryId;
	                			frwdQueryMsg++;}
	                		}
	                		//alreadyAddressed = 1;
	                	//}
	                	else
	                	{
	                		System.out.println("Message dropped!!!");
	                	}  
	                	uniIdStore.add(uniqueQueryId);
	                	System.out.println("Number of Query received: " + recvQueryMsg);
		                System.out.println("Number of Queries forwarded: " + frwdQueryMsg);
		                System.out.println("Number of Queries Answered: " + answQueryMsg);
		                int rtsize = (routingTableNode.size() + routingTableNode1.size());
		                System.out.println("Size of Routing Table: " + rtsize);
		                int overheadMsg = recvQueryMsg - (frwdQueryMsg + answQueryMsg);
		                System.out.println("Number of overhead message: " + overheadMsg);
		                String stat = "Number of Query received: " + recvQueryMsg;
		                CSP.logger.info(stat);
		                stat = "Number of Queries forwarded: " + frwdQueryMsg;
		                CSP.logger.info(stat);
		                stat = "Number of Queries Answered: " + answQueryMsg;
		                CSP.logger.info(stat);
		                stat = "Size of Routing Table: " + rtsize;
		                CSP.logger.info(stat);
		                stat = "Node degree: " + rtsize;
		                CSP.logger.info(stat);
		                stat = "Number of overhead message: " + overheadMsg;
		                CSP.logger.info(stat);
//		                Set<String> uniqueSet = new HashSet<String>(list);
//		            	for (String temp : uniqueSet) {
//		            		System.out.println(temp + ": " + Collections.frequency(list, temp));
//		            	}
	                }
	                else if(MsgToken.equals("SEROK"))
	                {
//	                	if(srcNode == 1)
//	                	{
//	                		System.out.println("File Found" + joinMsg);
//	                	}
//	                	else
//	                	{	
//	                		byte[] reverseSending = new byte[1024];
//	                		reverseSending = joinMsg.getBytes(); 
//	                		DatagramPacket sendrevSearchOkMsg = new DatagramPacket(buf, 1024);
//	                		serverSocket.send (new DatagramPacket(reverseSending, reverseSending.length, srcIP, srcPort));
//	                	}
	                	long end = System.currentTimeMillis();
	                	long latency = end-start;
	        	        System.out.println("latency: "+latency+" milliseconds");
	                	System.out.println("Yupiee, my file found!!!");
	                	System.out.println("The number of hops are: " + token[4]);
	                	String searchFound = "The number of hops are: " + token[4];
	                	CSP.logger.info(searchFound);
	                	searchFound = "latency: "+latency+" milliseconds";
	                	CSP.logger.info(searchFound);
	                	
	                	String cacheFileIP = token[2];
	                	String[] cache = cacheFileIP.split("/");
	                	int cachePort = Integer.parseInt(token[3]);
	                	InetAddress cacheIP= InetAddress.getByName(cache[0]);
	                	int i = 5;
	                	String cacheFileName = "";
	                	System.out.println("Hello");
	                	
	                	while(i < token.length)
	                	{
	                		if(i == 5)
	                		{
	                			cacheFileName = token[i];
	                			i++;
	                		}
	                		else{
	                			cacheFileName = cacheFileName + " " + token[i];
	                			i++;
	                		}
	                	}
	                	
	                	String cacheMsg = "CACHEINFO" + ":" +  cacheIP + ":"+ cachePort + ":" + cacheFileName;
	                	System.out.println(cacheMsg);
	                	byte[] sendCacheInfo = new byte[1024];
	                	sendCacheInfo = cacheMsg.getBytes();
	                	DatagramPacket sendCahceMsg = new DatagramPacket(buf, 1024);
        				serverSocket.send (new DatagramPacket(sendCacheInfo, sendCacheInfo.length, manageIp, managePort));
        				System.out.println("hello");
	                }
	                else if(MsgToken.equals("LEAVE"))
	                {
	                	int lvPort = Integer.parseInt(token[3]);
	                	for(int i = 0; i < routingTableNode.size(); i++)
	                	{
	                		if(lvPort == routingTableNode.get(i).port)
	                		{
	                			routingTableNode.remove(i);
	                		}
	                	}
	                	for(int i = 0;i< routingTableNode.size(); i++)
	                	{
	                		System.out.println("Routing Table Info after leaving:" +routingTableNode.get(i).IPaddr + " "+routingTableNode.get(i).port + " "+ routingTableNode.get(i).Status);
	                	}
	                	String leaveOkMsg = "0015" + " "+ "LEAVEOK" + " " + "0" ;
	                	byte[] replyLeaving = new byte[1024];
	                	replyLeaving = leaveOkMsg.getBytes(); 
	                	DatagramPacket sendLeaveOkMsg = new DatagramPacket(buf, 1024);
	                	serverSocket.send (new DatagramPacket(replyLeaving, replyLeaving.length, receivePacket.getAddress(), receivePacket.getPort()));
	                }
	                else if(MsgToken.equals("CLOSE"))
	                {
	                	serverSocket.close();
	                	break;
	                }
	                else if(MsgToken.equals("SEARCH"))
	                {
	                	//System.out.println("Sitakant");
	                	int p = 2;
	                	String filetosearch = "";
	                	while(!(token[p].endsWith("\"")))
	                	{
	                		filetosearch = filetosearch + token[p];
	                		filetosearch = filetosearch + " ";
	                		p++;
	                	}
	                	filetosearch = filetosearch + token[p];
	                	filetosearch.trim();
	                	
	                	list.add(filetosearch);
	                	
	                	start = System.currentTimeMillis();
	                	if(numConnection == 1)
	                	{
	                		//System.out.println("Sitakant1");
	                		/*Creating thread to start the query message*/
	        		        InetAddress IPforQS = InetAddress.getByName(ownIP);
	        		        InetAddress IPAddress1 = InetAddress.getByName(host1);
	        		        QueryNsHandler request =new QueryNsHandler(clientSocket1, null, null, numConnection, IPforQS, serport, IPAddress1, port1, null, 0, null, 0, filetosearch); 
	        		        thread2 = new Thread(request);
	        		        thread2.start();
	                	}
	                	else if(numConnection == 2)
	                	{
	                		//System.out.println("Sitakant2");
	                		InetAddress IPforQS = InetAddress.getByName(ownIP);
	                		InetAddress IPAddress2 = InetAddress.getByName(host1);
	                		InetAddress IPAddress3 = InetAddress.getByName(host2);
	                		QueryNsHandler request =new QueryNsHandler(clientSocket1, clientSocket2, null ,numConnection, IPforQS, serport, IPAddress2, port1, IPAddress3, port2, null, 0, filetosearch); 
	        		        thread2 = new Thread(request);
	        		        thread2.start();
	                	}
	                	else if(numConnection == 3)
	                	{
	                		//System.out.println("Sitakant3");
	                		InetAddress IPforQS = InetAddress.getByName(ownIP);
	                		InetAddress IPAddress4 = InetAddress.getByName(host1);
	                		InetAddress IPAddress5 = InetAddress.getByName(host2);
	                		InetAddress IPAddress6 = InetAddress.getByName(host3);
	                		QueryNsHandler request =new QueryNsHandler(clientSocket1, clientSocket2, clientSocket3 ,numConnection, IPforQS, serport, IPAddress4, port1, IPAddress5, port2, IPAddress6, port3, filetosearch); 
	        		        thread2 = new Thread(request);
	        		        thread2.start();
	                	}
	                }
	                else if(MsgToken.equals("CACHE"))
	                {
	                	String chIP = token[2];
	                	int cachePort = Integer.parseInt(token[3]);
	                	String[] cache = chIP.split("/");
	                	String cacheIP = cache[0];
	                	System.out.println("Inside Cache block!!!");
	                	int p = 4;
	                	String filetosearch = "";
	                	while(!(token[p].endsWith("\"")))
	                	{
	                		filetosearch = filetosearch + token[p];
	                		filetosearch = filetosearch + " ";
	                		p++;
	                	}
	                	filetosearch = filetosearch + token[p];
	                	filetosearch.trim();
	                	int j = 0;
	                	chl = new CacheList(filetosearch, cacheIP, cachePort);
	                	if(cacheMemory.size() == 0)
	                	{
	                		cacheMemory.add(chl);
	                	}
	                	else if(cacheMemory.size() < 2){
	                		if(!(cacheMemory.get(j).file.equals(filetosearch)))
	                			cacheMemory.add(chl);
	                	}
	                	
	                	for(int i = 0; i < cacheMemory.size(); i++)
	                	{
	                		System.out.println("Cache Memory: " + cacheMemory.get(i).file + " " + cacheMemory.get(i).containIP + " " + cacheMemory.get(i).containPort);
	                	}
	                }
	                else if(MsgToken.equals("CACHEMSG"))
	                {
	                	String cacheSrcIP = token[2];
	                	String[] cache = cacheSrcIP.split("/");
	                	int cachesrcPort = Integer.parseInt(token[3]);
	                	InetAddress cachesrcIP= InetAddress.getByName(cache[0]);
	                	int numHop = Integer.parseInt(token[4]);
	                	InetAddress  IPforSQ= InetAddress.getByName(ownIP);
	                	int i = 5;
	                	String cacheFileName = "";
	                	
	                	while(i < token.length)
	                	{
	                		if(i == 5)
	                		{
	                			cacheFileName = token[i];
	                			i++;
	                		}
	                		else{
	                			cacheFileName = cacheFileName + " " + token[i];
	                			i++;
	                		}
	                	}  	
	                	String searchOkMsg = "0014"+ " " + "SEROK" + " " +  IPforSQ + " "+ serport + " " + numHop + " " + cacheFileName;
	                	byte[] sendCacheMsg = new byte[1024];
        				sendCacheMsg = searchOkMsg.getBytes(); 
        				
        				
            			DatagramPacket sendChMsg = new DatagramPacket(buf, 1024);
            			serverSocket.send (new DatagramPacket(sendCacheMsg, sendCacheMsg.length, cachesrcIP, cachesrcPort));
            			fileFoundNodeId.add(uniqueQueryId);
            			answQueryMsg++;
            			CSP.logger.info(searchOkMsg);
        				kv = new KeyValue(keyIndex + 1, cacheFileName);
        				kvList.add(kv);
        				serAckCheck = 1;
        				filePresent = 1;
        				alreadyAddressed = 1;
        				uniMatch = uniqueQueryId;
	                }
	                else
	                {
	                	System.out.println("Do nothing for now...");
	                }
	                
	                //RequestHandler request =new RequestHandler(receivePacket, serverSocket);     

	                // Create a new thread to process the request.
	                //Thread thread = new Thread(request);

	                // Start the thread.
	                //thread.start(); 
	                //count++;
	            }     
	        }catch (Exception e) {  // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	    }
	    
	    void sendQueryForward(int numHop, String srcIP1, int srcPort1, String flName, String uniqueQueryId) throws IOException
	    {
	    	System.out.println("Forwarding the query...");
	    	InetAddress sqFrwdIP1 = null;
	    	int sqFrwdPort1 = 0;
	    	InetAddress sqFrwdIP2 = null;
	    	int sqFrwdPort2 = 0;
	    	InetAddress sqFrwdIP3 = null;
	    	int sqFrwdPort3 = 0;
	    	Iterator<RoutingTableInfo> itr = routingTableNode.iterator();
	    	
	    	numHop = numHop + 1;
	    	String flName1 = "\"" + flName + "\"";
	    	String newUpdatedHopSrMsg1 = "SER" + " " + srcIP1 + " " + srcPort1 + " " + flName1 + " " + numHop + " "+uniqueQueryId; 
    		int newUpdatedHopSrMsgLength = newUpdatedHopSrMsg1.length() + 4;
    		String newUpdatedHopSrMsg = "00" + newUpdatedHopSrMsgLength + " " + newUpdatedHopSrMsg1;
	    	
	    	if(CSP.numberNode == 0)
	    	{
	    		if(itr.hasNext()){
	    		sqFrwdIP1 = InetAddress.getByName(routingTableNode.get(0).IPaddr);
	    		sqFrwdPort1 = routingTableNode.get(0).port;
	    		itr.next();}
	    		if(itr.hasNext()){
	    		sqFrwdIP2 = InetAddress.getByName(routingTableNode.get(1).IPaddr);
	    		sqFrwdPort2 = routingTableNode.get(1).port;
	    		itr.next();}
	    		if(itr.hasNext()){
	    		sqFrwdIP3 = InetAddress.getByName(routingTableNode.get(2).IPaddr);
	    		sqFrwdPort3 = routingTableNode.get(2).port;}
	    		
	    		if(sqFrwdIP1 != null){
	    		if(clientSocket1 == null){
	    		clientSocket1 = new DatagramSocket (sqFrwdPort1);}
	    		byte[] sendQfrwdata1 = new byte[1024];
	    		sendQfrwdata1 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort1){
	    		DatagramPacket sendQfrwdPacket1 = new DatagramPacket(sendQfrwdata1, sendQfrwdata1.length, sqFrwdIP1, sqFrwdPort1);
	    		clientSocket1.send(sendQfrwdPacket1);}}
	    		
	    		if(sqFrwdIP2 != null){
	    		if(clientSocket2 == null){
	    		clientSocket2 = new DatagramSocket (sqFrwdPort2);}
	    		byte[] sendQfrwdata2 = new byte[1024];
	    		sendQfrwdata2 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort2){
	    		DatagramPacket sendQfrwdPacket2 = new DatagramPacket(sendQfrwdata2, sendQfrwdata2.length, sqFrwdIP2, sqFrwdPort2);
	    		clientSocket2.send(sendQfrwdPacket2);}}
	    		
	    		if(sqFrwdIP3 != null){
	    		if(clientSocket3 == null){
	    		clientSocket3 = new DatagramSocket (sqFrwdPort3);}
	    		byte[] sendQfrwdata3 = new byte[1024];
	    		sendQfrwdata3 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort3){
	    		DatagramPacket sendQfrwdPacket3 = new DatagramPacket(sendQfrwdata3, sendQfrwdata3.length, sqFrwdIP3, sqFrwdPort3);
	    		clientSocket3.send(sendQfrwdPacket3);}}
	    	}
	    	else if(CSP.numberNode == 1)
	    	{
	    		InetAddress  frwdIP1 = InetAddress.getByName(CSP.tokens[4]);
	    		int frwdPort1 = Integer.parseInt(CSP.tokens[5]);
	    		byte[] sendQfrwdData1 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData1 = newUpdatedHopSrMsg.getBytes();
	    		System.out.println("the IP: " +frwdIP1);
	    		if(srcPort1 != frwdPort1){
	    		DatagramPacket sendQfrwdMsg1 = new DatagramPacket(sendQfrwdData1, sendQfrwdData1.length, frwdIP1, frwdPort1);	
	    		clientSocket1.send(sendQfrwdMsg1);}	
	    		
	    		if(routingTableNode.get(0).IPaddr != null){
	    		sqFrwdIP2 = InetAddress.getByName(routingTableNode.get(0).IPaddr);
	    		sqFrwdPort2 = routingTableNode.get(0).port;}
	    		if(routingTableNode.get(1).IPaddr != null){
	    		sqFrwdIP3 = InetAddress.getByName(routingTableNode.get(1).IPaddr);
	    		sqFrwdPort3 = routingTableNode.get(1).port;}
	    		
	    		if(sqFrwdIP2 != null){
	    			System.out.println("Inside send1");
	    		if(clientSocket2 == null){
	    		clientSocket2 = new DatagramSocket (sqFrwdPort2);}
	    		byte[] sendQfrwdata2 = new byte[1024];
	    		sendQfrwdata2 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort2){
	    		DatagramPacket sendQfrwdPacket2 = new DatagramPacket(sendQfrwdata2, sendQfrwdata2.length, sqFrwdIP2, sqFrwdPort2);
	    		clientSocket2.send(sendQfrwdPacket2);}}
	    		
	    		if(sqFrwdIP3 != null){
	    			System.out.println("Inside send2");
	    		if(clientSocket3 == null){
	    		clientSocket3 = new DatagramSocket (sqFrwdPort3);}
	    		byte[] sendQfrwdata3 = new byte[1024];
	    		sendQfrwdata3 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort2){
	    		DatagramPacket sendQfrwdPacket3 = new DatagramPacket(sendQfrwdata3, sendQfrwdata3.length, sqFrwdIP3, sqFrwdPort3);
	    		clientSocket3.send(sendQfrwdPacket3);}}
	    		
	    	}
	    	else if(CSP.numberNode == 2)
	    	{
	    		InetAddress  frwdIP1 = InetAddress.getByName(CSP.tokens[4]);
	    		int frwdPort1 = Integer.parseInt(CSP.tokens[5]);
	    		byte[] sendQfrwdData1 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData1 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort2){
	    		DatagramPacket sendQfrwdMsg1 = new DatagramPacket(sendQfrwdData1, sendQfrwdData1.length, frwdIP1, frwdPort1);	
	    		clientSocket1.send(sendQfrwdMsg1);}
	    		
	    		InetAddress  frwdIP2 = InetAddress.getByName(CSP.tokens[6]);
	    		int frwdPort2 = Integer.parseInt(CSP.tokens[7]);
	    		byte[] sendQfrwdData2 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData2 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort2){
	    		DatagramPacket sendQfrwdMsg2 = new DatagramPacket(sendQfrwdData2, sendQfrwdData2.length, frwdIP2, frwdPort2);	
	    		clientSocket2.send(sendQfrwdMsg2);}
	    		
	    		if(routingTableNode.get(0).IPaddr != null){
	    		sqFrwdIP3 = InetAddress.getByName(routingTableNode.get(0).IPaddr);
	    		sqFrwdPort3 = routingTableNode.get(0).port;}
	    		
	    		if(sqFrwdIP3 != null){
	    		if(clientSocket3 == null){
	    		clientSocket3 = new DatagramSocket (sqFrwdPort3);}
	    		byte[] sendQfrwdata3 = new byte[1024];
	    		sendQfrwdata3 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != sqFrwdPort3){
	    		DatagramPacket sendQfrwdPacket3 = new DatagramPacket(sendQfrwdata3, sendQfrwdata3.length, sqFrwdIP3, sqFrwdPort3);
	    		clientSocket3.send(sendQfrwdPacket3);}}
	    		
	    	}
	    	else if(CSP.numberNode == 3)
	    	{
	    		InetAddress  frwdIP1 = InetAddress.getByName(CSP.tokens[4]);
	    		int frwdPort1 = Integer.parseInt(CSP.tokens[5]);
	    		byte[] sendQfrwdData1 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData1 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != frwdPort1){
	    		DatagramPacket sendQfrwdMsg1 = new DatagramPacket(sendQfrwdData1, sendQfrwdData1.length, frwdIP1, frwdPort1);	
	    		clientSocket1.send(sendQfrwdMsg1);}
	    		
	    		InetAddress  frwdIP2 = InetAddress.getByName(CSP.tokens[6]);
	    		int frwdPort2 = Integer.parseInt(CSP.tokens[7]);
	    		byte[] sendQfrwdData2 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData2 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != frwdPort2){
	    		DatagramPacket sendQfrwdMsg2 = new DatagramPacket(sendQfrwdData2, sendQfrwdData2.length, frwdIP2, frwdPort2);	
	    		clientSocket2.send(sendQfrwdMsg2);}
	    		
	    		InetAddress  frwdIP3 = InetAddress.getByName(CSP.tokens[8]);
	    		int frwdPort3 = Integer.parseInt(CSP.tokens[9]);
	    		byte[] sendQfrwdData3 = new byte[1024];
	    		System.out.println(newUpdatedHopSrMsg);
	    		sendQfrwdData3 = newUpdatedHopSrMsg.getBytes();
	    		if(srcPort1 != frwdPort3){
	    		DatagramPacket sendQfrwdMsg3 = new DatagramPacket(sendQfrwdData3, sendQfrwdData3.length, frwdIP3, frwdPort3);	
	    		clientSocket3.send(sendQfrwdMsg3);}
	    	}
	    }	
}


class QueryNsHandler implements Runnable
{
	DatagramPacket sendPacket;
	DatagramPacket sendPacket1;
	DatagramPacket sendPacket2;
    DatagramSocket nodeSocket;
    DatagramSocket nodeSocket1;
    DatagramSocket nodeSocket2;
    InetAddress ownIP1;
    int ownclipt1;
    InetAddress IP1;
    int clipt1;
    InetAddress IP2;
    int clipt2;
    InetAddress IP3;
    int clipt3;
    int numConn;
    int hopCount;
    byte[] sendNodeMsg = new byte[1024];
    public static P2PConnection pc = new P2PConnection();
    ClientServer CS = new ClientServer();
    String fileSearch;
    //int uid;
    //public static MyLogger p2plog2 = new MyLogger();
    
    
    public QueryNsHandler(DatagramSocket nodeSocket, DatagramSocket nodeSocket1, DatagramSocket nodeSocket2, int numConn, InetAddress ownIP1, int ownclipt1, InetAddress IP1, int clipt1, InetAddress IP2, int clipt2, InetAddress IP3, int clipt3, String filetosearch) throws Exception 
    {
        this.nodeSocket = nodeSocket;
        this.nodeSocket1 = nodeSocket1;
        this.nodeSocket2 = nodeSocket2;
        this.numConn = numConn;
        this.ownIP1 = ownIP1;
        this.ownclipt1 = ownclipt1;
        this.IP1 = IP1;
        this.clipt1 = clipt1;
        this.IP2 = IP2;
        this.clipt2 = clipt2;
        this.IP3 = IP3;
        this.clipt3 = clipt3;
        this.fileSearch = filetosearch;
        //this.uid = uid;
    }
    @Override
    public void run() 
    {
        try {
        	handleQuery();
        }catch (Exception e){
            System.out.println(e);
        }
    } 
    
    void handleQuery() throws Exception
    {
    	pc.srcNode = 1;
    	
    	//BufferedReader br1 = new BufferedReader(new FileReader("queries.txt")); //opening the file for reading
		//String readf = null;
		
		String uniQid = null;
		//System.out.println("Sitakant4");
		String generation; 
		
		
		
		//while ((readf = br1.readLine()) != null) 
		//{
			//fileSearch = readf;
			uniQid = ownIP1 + ":" + pc.uid + ":" + fileSearch;
			//fileSearch = "\"" + fileSearch + "\"";
			System.out.println(fileSearch);
			
    	if(numConn == 1)
    	{
    		hopCount = 1;
    		if(!(nodeSocket.isClosed()))
    		{
    		byte[] sendQueryData1 = new byte[1024];
    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount + " "+ uniQid; 
    		int QueryMsgLength = QueryMsg.length() + 4;
    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
    		System.out.println(Qrmsg1);
    		generation = "Search Generated: " + Qrmsg1;
    		CS.logger.info(generation);
    		sendQueryData1 = Qrmsg1.getBytes();
    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
    		nodeSocket.send(sendQueryMsg1);
    		
    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
	        //thread2 = new Thread(request);
	        //thread2.start();
	        }
    		
    		InetAddress stQueryIP2 = InetAddress.getByName(pc.routingTableNode.get(0).IPaddr);
    		int stQueryPort2 = pc.routingTableNode.get(0).port;
    		InetAddress stQueryIP3 = InetAddress.getByName(pc.routingTableNode.get(1).IPaddr);
    		int stQueryPort3 = pc.routingTableNode.get(1).port;
    		
    		nodeSocket1 = new DatagramSocket (stQueryPort2);
    		byte[] sendQueryData2 = new byte[1024];
    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount + " "+ uniQid; 
    		int QueryMsgLength1 = QueryMsg1.length() + 4;
    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
    		System.out.println(Qrmsg2);
    		generation = "Search Generated: " + Qrmsg2;
    		CS.logger.info(generation);
    		sendQueryData2 = Qrmsg2.getBytes();
    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, stQueryIP2, stQueryPort2);
    		nodeSocket1.send(sendQueryMsg2);
    		
    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
	        //thread3 = new Thread(request1);
	        //thread3.start();
    		
    		nodeSocket2 = new DatagramSocket (stQueryPort3);
    		byte[] sendQueryData3 = new byte[1024];
    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength2 = QueryMsg2.length() + 4;
    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
    		System.out.println(Qrmsg3);
    		generation = "Search Generated: " + Qrmsg3;
    		CS.logger.info(generation);
    		sendQueryData3 = Qrmsg3.getBytes();
    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, stQueryIP3, stQueryPort3);
    		nodeSocket2.send(sendQueryMsg3);
    		
    		//QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
	        //thread4 = new Thread(request2);
	        //thread4.start();
    		
    	}
    	else if(numConn == 2)
    	{
    		//System.out.println("Sitakant5");
    		hopCount = 1;
    		if(!(nodeSocket.isClosed()))
    		{
    			//System.out.println("Sitakant6");
    		byte[] sendQueryData1 = new byte[1024];
    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength = QueryMsg.length() + 4;
    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
    		System.out.println(Qrmsg1);
    		generation = "Search Generated: " + Qrmsg1;
    		CS.logger.info(generation);
    		sendQueryData1 = Qrmsg1.getBytes();
    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
    		nodeSocket.send(sendQueryMsg1);
    		
    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
	        //thread2 = new Thread(request);
	        //thread2.start();
	        }
    		
    		if(!(nodeSocket1.isClosed())){
    			//System.out.println("Sitakant7");
    		byte[] sendQueryData2 = new byte[1024];
    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength1 = QueryMsg1.length() + 4;
    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
    		//System.out.println(lvmsg1);
    		generation = "Search Generated: " + Qrmsg2;
    		CS.logger.info(generation);
    		sendQueryData2 = Qrmsg2.getBytes();
    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, IP2, clipt2);	
    		nodeSocket1.send(sendQueryMsg2);
    		
    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
	        //thread3 = new Thread(request1);
	        //thread3.start();
	        }
    		//System.out.println("Sitakant8");
    		InetAddress stQueryIP3 = InetAddress.getByName(pc.routingTableNode.get(0).IPaddr);
    		int stQueryPort3 = pc.routingTableNode.get(0).port;
    		//System.out.println("Sitakant9");
    		nodeSocket2 = new DatagramSocket (stQueryPort3);
    		byte[] sendQueryData3 = new byte[1024];
    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength2 = QueryMsg2.length() + 4;
    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
    		System.out.println(Qrmsg3);
    		generation = "Search Generated: " + Qrmsg3;
    		CS.logger.info(generation);
    		sendQueryData3 = Qrmsg3.getBytes();
    		//System.out.println("Sitakant10");
    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, stQueryIP3, stQueryPort3);
    		nodeSocket2.send(sendQueryMsg3);
    		//System.out.println("Sitakant11");
    		//QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
	        //thread4 = new Thread(request2);
	        //thread4.start();
    	}
    	else if(numConn == 3)
    	{
    		hopCount = 1;
    		//System.out.println("Sitakant12");
    		if(!(nodeSocket.isClosed()))
    		{
    			//System.out.println("Sitakant13");
    		byte[] sendQueryData1 = new byte[1024];
    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength = QueryMsg.length() + 4;
    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
    		System.out.println(Qrmsg1);
    		generation = "Search Generated: " + Qrmsg1;
    		CS.logger.info(generation);
    		sendQueryData1 = Qrmsg1.getBytes();
    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
    		nodeSocket.send(sendQueryMsg1);
    		
    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
	        //thread2 = new Thread(request);
	        //thread2.start();
	        }
    		
    		if(!(nodeSocket1.isClosed())){
    			//System.out.println("Sitakant13");
    		byte[] sendQueryData2 = new byte[1024];
    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength1 = QueryMsg1.length() + 4;
    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
    		//System.out.println(lvmsg1);
    		generation = "Search Generated: " + Qrmsg2;
    		CS.logger.info(generation);
    		sendQueryData2 = Qrmsg2.getBytes();
    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, IP2, clipt2);	
    		nodeSocket1.send(sendQueryMsg2);
    		
    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
	        //thread3 = new Thread(request1);
	        //thread3.start();
	        }
    		
    		if(!(nodeSocket2.isClosed())){
    			//System.out.println("Sitakant14");
    		byte[] sendQueryData3 = new byte[1024];
    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
    		int QueryMsgLength2 = QueryMsg2.length() + 4;
    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
    		//System.out.println(lvmsg1);
    		generation = "Search Generated: " + Qrmsg3;
    		CS.logger.info(generation);
    		sendQueryData3 = Qrmsg3.getBytes();
    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, IP3, clipt3);	
    		nodeSocket2.send(sendQueryMsg3);

	        //QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
	        //thread4 = new Thread(request2);
	        //thread4.start();
	        }
    	}
    	pc.uid++;
    }
    }
//}


//	 class RequestHandler implements Runnable{
//
//	    DatagramPacket packet;
//	    DatagramSocket socket;
//	    byte[] sendData = new byte[1024];
//
//	    public RequestHandler(DatagramPacket packet,DatagramSocket socket) throws Exception 
//	    {
//	        this.packet = packet;
//	        this.socket = socket;
//	        String sentence = new String(packet.getData()); 
//	        System.out.println("handler : "+sentence);
//	    }
//
//	    @Override
//	    public void run() 
//	    {
//	        try {
//	            System.out.println("Test");
//	            processRequest();
//	        }catch (Exception e){
//	            System.out.println(e);
//	        }
//
//	    }   
//
//	    /*
//	     * Gets a request from a node. 
//	     * 
//	     */
//	    private void processRequest() throws Exception 
//	    {       
//
//	        String sentence = new String( packet.getData());                   
//	        System.out.println("RECEIVED: " + sentence);                   
//	        InetAddress IPAddress =packet.getAddress();                   
//	        int port = packet.getPort();                   
//	        String capitalizedSentence = sentence.toUpperCase();   
//	        System.out.println(capitalizedSentence + port + IPAddress);
////	        sendData = capitalizedSentence.getBytes();                   
////	        DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, port);
////	        socket.send(sendPacket);
////	        socket.close();
//
//	    }
//
//	}
	
	class MessageHandler implements Runnable
	{
		DatagramPacket sendPacket;
		DatagramPacket sendPacket1;
		DatagramPacket sendPacket2;
	    DatagramSocket nodeSocket;
	    DatagramSocket nodeSocket1;
	    DatagramSocket nodeSocket2;
	    InetAddress ownIP1;
	    int ownclipt1;
	    InetAddress IP1;
	    int clipt1;
	    InetAddress IP2;
	    int clipt2;
	    InetAddress IP3;
	    int clipt3;
	    int numConn;
	    int hopCount;
	    byte[] sendNodeMsg = new byte[1024];
	    public static P2PConnection pc = new P2PConnection();
	    ClientServer CS = new ClientServer();
	    public static Thread thread2;
	    public static Thread thread3;
	    public static Thread thread4;
	    String fileSearch;
	    //public static MyLogger p2plog3 = new MyLogger();
	    String leavelog; 
	    String leaveoklog;
	    
	    
	    public MessageHandler(DatagramSocket nodeSocket, DatagramSocket nodeSocket1, DatagramSocket nodeSocket2, int numConn, InetAddress ownIP1, int ownclipt1, InetAddress IP1, int clipt1, InetAddress IP2, int clipt2, InetAddress IP3, int clipt3) throws Exception 
	    {
	        this.nodeSocket = nodeSocket;
	        this.nodeSocket1 = nodeSocket1;
	        this.nodeSocket2 = nodeSocket2;
	        this.numConn = numConn;
	        this.ownIP1 = ownIP1;
	        this.ownclipt1 = ownclipt1;
	        this.IP1 = IP1;
	        this.clipt1 = clipt1;
	        this.IP2 = IP2;
	        this.clipt2 = clipt2;
	        this.IP3 = IP3;
	        this.clipt3 = clipt3;
	    }
	    @Override
	    public void run() 
	    {
	        try {
	            processMessage();
	        }catch (Exception e){
	            System.out.println(e);
	        }

	    }     
	    public void processMessage() throws Exception
	    {
	    	int choice = 0;
	    	
	    	
	    	
	    	while(true)
	    	{
	    		if(P2PConnection.stopConn == 1)
	    		{
	    			break;
	    		}
	    		System.out.println("Enter 1 to generate a query");
	    		System.out.println("Enter 2 to leave the distributed network");
	    		Scanner scnChoice = new Scanner(new InputStreamReader(System.in));
	    		choice = scnChoice.nextInt();
	    		switch(choice)
	    		{
	    			case 1:
	    				//System.out.println("Enter the file to be searched:");
	    				//Scanner fs = new Scanner(new InputStreamReader(System.in));
	    				//fileSearch = fs.nextLine();
	    				handleQuery();
	    				break;
	    			case 2:
	    				System.out.println("Wait, working on leaving the distributed network...");
	    				handleLeaving();
	    				P2PConnection.stopConn = 1;
	    				System.out.println("value of stopconn in handle: " + pc.stopConn);
	    				closeSocket();				
	    				break;
	    			default:
	    				System.out.println("Invalid entry");
	    				break;
	    		}
	    	}
	    }
	    private void handleLeaving() throws IOException
	    {
	    	System.out.println("Entering handleLeaving");
	    	int check = 0;
	    	if(numConn == 1)
	    	{
	    		//System.out.println(nodeSocket.getRemoteSocketAddress());
	    		//if(nodeSocket.getRemoteSocketAddress() != null)
	    		//{
	    			//System.out.println("Entering leave message");
	    		byte[] sendLeaveData1 = new byte[1024];
	    		String leaveMsg = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength = leaveMsg.length() + 4;
	    		String lvmsg1 = "00" + leaveMsgLength + " " + leaveMsg;
	    		//System.out.println(lvmsg1);
	    		leavelog = "leave Generated: " + lvmsg1;
	    		CS.logger.info(lvmsg1);
	    		sendLeaveData1 = lvmsg1.getBytes();
	    		try{
	    		DatagramPacket sendLeaveMsg1 = new DatagramPacket(sendLeaveData1, sendLeaveData1.length, IP1, clipt1);	
	    		//System.out.println("before sending");
	    		nodeSocket.send(sendLeaveMsg1);
	    		//System.out.println("after sending");
	    		}
	    		catch(Exception e){
	    			System.out.println("unknownhostception");
	    			check = 1;
	    		}
//	    		catch(IOException e)
//	    		{
//	    			System.out.println("IO exception");
//	    			check = 1;
//	    		}
	    		byte[] rcvLeaveOk= new byte[1024];
	    		nodeSocket.setSoTimeout(1000);
	    		try{
	    		DatagramPacket rcvLeaveMsg = new DatagramPacket(rcvLeaveOk, rcvLeaveOk.length);
	    		nodeSocket.receive(rcvLeaveMsg);
		        System.out.println(new String(rcvLeaveMsg.getData(), 0, rcvLeaveMsg.getLength()));
		        leaveoklog = new String(rcvLeaveMsg.getData());
		        CS.logger.info(leaveoklog);
		        System.out.println("Bye bye!!!");
	    		}
	    		catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			//check = 1;
	    		
		        
		        byte[] closeData1 = new byte[1024];
	    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
	    		//System.out.println(lvmsg1);
	    		closeData1 = closeMsg.getBytes();
	    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
	    		nodeSocket.send(sendCloseMsg1);	
	    		nodeSocket.close();
	    		}
	    		
	    		byte[] closeData1 = new byte[1024];
		    	String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    	closeData1 = closeMsg.getBytes();
		    	DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    	nodeSocket.send(sendCloseMsg1);	
		    	nodeSocket.close();
	    		
	    		byte[] sendLeaveBS = new byte[1024];
	    		String cmdLeave = "DEL IPADDRESS" + " " + CS.regIp + " " + ownclipt1 + " " + CS.regUserName;
	    		int cmdLeaveLength = cmdLeave.length() + 4;
	    		String cmdLeave1 = "00" + cmdLeaveLength + " " + cmdLeave;
	    		System.out.println(cmdLeave1);
	    		leavelog = "leave Generated: " + cmdLeave1;
	    		CS.logger.info(leavelog);
		        sendLeaveBS = cmdLeave1.getBytes();        
		        DatagramPacket sendPackLeaveBS = new DatagramPacket(sendLeaveBS, sendLeaveBS.length, CS.BSIP, CS.port); 
		        CS.clBs.send(sendPackLeaveBS);
		        byte[] receiveLeaveBS = new byte[1024];
		        DatagramPacket rcvLeavePacket = new DatagramPacket(receiveLeaveBS, receiveLeaveBS.length);
		        CS.clBs.receive(rcvLeavePacket);
	            String LeaveOkBS = new String( rcvLeavePacket.getData());
	            System.out.println(LeaveOkBS);
	            String[] tk = LeaveOkBS.split(" ");
	            if(tk[4].equals("9998"))
	            {
	            	System.out.println("error while unregistering");
	            }
	    	}
	    	else if(numConn == 2)
	    	{
//	    		if(nodeSocket.getRemoteSocketAddress() != null) 
//	    		{
	    		byte[] sendLeaveData1 = new byte[1024];
	    		String leaveMsg = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength = leaveMsg.length() + 4;
	    		String lvmsg1 = "00" + leaveMsgLength + " " + leaveMsg;
	    		//System.out.println(lvmsg1);
	    		leavelog = "leave Generated: " + lvmsg1;
	    		CS.logger.info(leavelog);
	    		sendLeaveData1 = lvmsg1.getBytes();
	    		DatagramPacket sendLeaveMsg1 = new DatagramPacket(sendLeaveData1, sendLeaveData1.length, IP1, clipt1);	
	    		nodeSocket.send(sendLeaveMsg1);
	    		
	    		byte[] rcvLeaveOk= new byte[1024];
	    		nodeSocket.setSoTimeout(1000);
	    		try{
	    		DatagramPacket rcvLeaveMsg = new DatagramPacket(rcvLeaveOk, rcvLeaveOk.length);
	    		nodeSocket.receive(rcvLeaveMsg);
		        System.out.println(new String(rcvLeaveMsg.getData(), 0, rcvLeaveMsg.getLength()));
		        System.out.println("Bye bye!!!");}
	    		catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			byte[] closeData1 = new byte[1024];
		    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    		closeData1 = closeMsg.getBytes();
		    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    		nodeSocket.send(sendCloseMsg1); 
		    		nodeSocket.close();
	    		}
	    		
	    		//if (nodeSocket1.getRemoteSocketAddress() != null){
	    		byte[] sendLeaveData2 = new byte[1024];
	    		String leaveMsg1 = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength1 = leaveMsg1.length() + 4;
	    		String lvmsg2 = "00" + leaveMsgLength1 + " " + leaveMsg1;
	    		//System.out.println(lvmsg1);
	    		leavelog = "leave Generated: " + lvmsg2;
	    		CS.logger.info(leavelog);
	    		sendLeaveData2 = lvmsg2.getBytes();
	    		DatagramPacket sendLeaveMsg2 = new DatagramPacket(sendLeaveData2, sendLeaveData2.length, IP2, clipt2);	
	    		nodeSocket1.send(sendLeaveMsg2);
	    		
	    		byte[] rcvLeaveOk1= new byte[1024];
	    		nodeSocket1.setSoTimeout(1000);
	    		try{
	    		DatagramPacket rcvLeaveMsg1 = new DatagramPacket(rcvLeaveOk1, rcvLeaveOk1.length);
	    		nodeSocket1.receive(rcvLeaveMsg1);
		        System.out.println(new String(rcvLeaveMsg1.getData(), 0, rcvLeaveMsg1.getLength()));
		        leaveoklog = new String(rcvLeaveMsg1.getData());
		        CS.logger.info(leaveoklog);
		        System.out.println("Bye bye!!!");}
	    		
	    		catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			byte[] closeData1 = new byte[1024];
		    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    		closeData1 = closeMsg.getBytes();
		    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    		nodeSocket.send(sendCloseMsg1); 
		    		nodeSocket1.close();
	    		}
	    		
		        byte[] closeData1 = new byte[1024];
	    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
	    		//System.out.println(lvmsg1);
	    		closeData1 = closeMsg.getBytes();
	    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
	    		nodeSocket.send(sendCloseMsg1); 
	    		
	    		byte[] sendLeaveBS = new byte[1024];
	    		String cmdLeave = "DEL IPADDRESS" + " " + ownIP1 + " " + ownclipt1 + " " + CS.regUserName;
	    		int cmdLeaveLength = cmdLeave.length() + 4;
	    		String cmdLeave1 = "00" + cmdLeaveLength + " " + cmdLeave;
	    		leavelog = "leave Generated: " + cmdLeave1;
	    		CS.logger.info(leavelog);
		        sendLeaveBS = cmdLeave1.getBytes();        
		        DatagramPacket sendPackLeaveBS = new DatagramPacket(sendLeaveBS, sendLeaveBS.length, CS.BSIP, CS.port); 
		        CS.clBs.send(sendPackLeaveBS);
		        byte[] receiveLeaveBS = new byte[1024];
		        DatagramPacket rcvLeavePacket = new DatagramPacket(receiveLeaveBS, receiveLeaveBS.length);
		        CS.clBs.receive(rcvLeavePacket);
	            String LeaveOkBS = new String( rcvLeavePacket.getData());
	            CS.logger.info(LeaveOkBS);
	            System.out.println(LeaveOkBS);
	            String[] tk = LeaveOkBS.split(" ");
	            if(tk[4].equals("9998"))
	            {
	            	System.out.println("error while unregistering");
	            }
	    	}
	    	else if(numConn == 3)
	    	{
	    		//if(nodeSocket.getRemoteSocketAddress() != null){  
	    		byte[] sendLeaveData1 = new byte[1024];
	    		String leaveMsg = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength = leaveMsg.length() + 4;
	    		String lvmsg1 = "00" + leaveMsgLength + " " + leaveMsg;
	    		leavelog = "leave Generated: " + lvmsg1;
	    		CS.logger.info(leavelog);
	    		//System.out.println(lvmsg1);
	    		sendLeaveData1 = lvmsg1.getBytes();
	    		DatagramPacket sendLeaveMsg1 = new DatagramPacket(sendLeaveData1, sendLeaveData1.length, IP1, clipt1);	
	    		nodeSocket.send(sendLeaveMsg1);
	    		
	    		byte[] rcvLeaveOk= new byte[1024];
	    		nodeSocket.setSoTimeout(1000);
	    		try{
	    		DatagramPacket rcvLeaveMsg = new DatagramPacket(rcvLeaveOk, rcvLeaveOk.length);
	    		nodeSocket.receive(rcvLeaveMsg);
		        System.out.println(new String(rcvLeaveMsg.getData(), 0, rcvLeaveMsg.getLength()));
		        leaveoklog = new String(rcvLeaveMsg.getData());
		        CS.logger.info(leaveoklog);
		        System.out.println("Bye bye!!!");}
		        
		        catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			byte[] closeData1 = new byte[1024];
		    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    		closeData1 = closeMsg.getBytes();
		    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    		nodeSocket.send(sendCloseMsg1); 
		    		nodeSocket.close();
	    		}
	    		
	    		//if (nodeSocket1.getRemoteSocketAddress() != null){0028 DEL UNAME testsk3
	    		byte[] sendLeaveData2 = new byte[1024];
	    		String leaveMsg1 = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength1 = leaveMsg1.length() + 4;
	    		String lvmsg2 = "00" + leaveMsgLength1 + " " + leaveMsg1;
	    		//System.out.println(lvmsg1);
	    		leavelog = "leave Generated: " + lvmsg2;
	    		CS.logger.info(leavelog);
	    		sendLeaveData2 = lvmsg2.getBytes();
	    		DatagramPacket sendLeaveMsg2 = new DatagramPacket(sendLeaveData2, sendLeaveData2.length, IP2, clipt2);	
	    		nodeSocket1.send(sendLeaveMsg2);
	    		
	    		byte[] rcvLeaveOk1= new byte[1024];
	    		nodeSocket1.setSoTimeout(1000);
	    		try{
	    		DatagramPacket rcvLeaveMsg1 = new DatagramPacket(rcvLeaveOk1, rcvLeaveOk1.length);
	    		nodeSocket1.receive(rcvLeaveMsg1);
		        System.out.println(new String(rcvLeaveMsg1.getData(), 0, rcvLeaveMsg1.getLength()));
		        leaveoklog = new String(rcvLeaveMsg1.getData());
		        CS.logger.info(leaveoklog);
		        System.out.println("Bye bye!!!");}
		        
		        catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			byte[] closeData1 = new byte[1024];
		    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    		closeData1 = closeMsg.getBytes();
		    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    		nodeSocket.send(sendCloseMsg1); 
		    		nodeSocket1.close();
	    		}
	    		
	    		//if (nodeSocket2.getRemoteSocketAddress() != null){
	    		byte[] sendLeaveData3 = new byte[1024];
	    		String leaveMsg2 = "LEAVE" + " " + ownIP1 + " " + ownclipt1; 
	    		int leaveMsgLength2 = leaveMsg2.length() + 4;
	    		String lvmsg3 = "00" + leaveMsgLength2 + " " + leaveMsg2;
	    		//System.out.println(lvmsg1);
	    		leavelog = "leave Generated: " + lvmsg3;
	    		CS.logger.info(leavelog);
	    		sendLeaveData3 = lvmsg3.getBytes();
	    		DatagramPacket sendLeaveMsg3 = new DatagramPacket(sendLeaveData3, sendLeaveData3.length, IP3, clipt3);	
	    		nodeSocket2.send(sendLeaveMsg3);
	    		
	    		byte[] rcvLeaveOk2= new byte[1024];
	    		nodeSocket2.setSoTimeout(1000);
	    		try{
		    	DatagramPacket rcvLeaveMsg2 = new DatagramPacket(rcvLeaveOk2, rcvLeaveOk2.length);
		    	nodeSocket2.receive(rcvLeaveMsg2);
			    System.out.println(new String(rcvLeaveMsg2.getData(), 0, rcvLeaveMsg2.getLength()));
			    leaveoklog = new String(rcvLeaveMsg2.getData());
		        CS.logger.info(leaveoklog);
			    System.out.println("Bye bye!!!");}
			    
			    catch(SocketTimeoutException e){
	    			System.out.println("Socket Time out exception");
	    			byte[] closeData1 = new byte[1024];
		    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
		    		//System.out.println(lvmsg1);
		    		closeData1 = closeMsg.getBytes();
		    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
		    		nodeSocket.send(sendCloseMsg1); 
		    		nodeSocket2.close();
	    		}
		        
		        byte[] closeData1 = new byte[1024];
	    		String closeMsg = "0010" + " " + "CLOSE" + " "; 
	    		//System.out.println(lvmsg1);
	    		closeData1 = closeMsg.getBytes();
	    		DatagramPacket sendCloseMsg1 = new DatagramPacket(closeData1, closeData1.length, ownIP1, ownclipt1);	
	    		nodeSocket.send(sendCloseMsg1); 
	    		
	    		byte[] sendLeaveBS = new byte[1024];
	    		String cmdLeave = "DEL IPADDRESS" + " " + ownIP1 + " " + ownclipt1 + " " + CS.regUserName;
	    		int cmdLeaveLength = cmdLeave.length() + 4;
	    		String cmdLeave1 = "00" + cmdLeaveLength + " " + cmdLeave;
		        sendLeaveBS = cmdLeave1.getBytes();   
		        leavelog = "leave Generated: " + cmdLeave1;
	    		CS.logger.info(leavelog);
		        DatagramPacket sendPackLeaveBS = new DatagramPacket(sendLeaveBS, sendLeaveBS.length, CS.BSIP, CS.port); 
		        CS.clBs.send(sendPackLeaveBS);
		        byte[] receiveLeaveBS = new byte[1024];
		        DatagramPacket rcvLeavePacket = new DatagramPacket(receiveLeaveBS, receiveLeaveBS.length);
		        CS.clBs.receive(rcvLeavePacket);
	            String LeaveOkBS = new String( rcvLeavePacket.getData());
	    		CS.logger.info(LeaveOkBS);
	            System.out.println(LeaveOkBS);
	            String[] tk = LeaveOkBS.split(" ");
	            if(tk[4].equals("9998"))
	            {
	            	System.out.println("error while unregistering");
	            }
	    	}
	    	else
	    	{
	    		System.out.println("Debug the problem!!!");
	    	}
	    }
	    
	    void closeSocket()
	    {
	    	if(numConn == 1)
	    	{
	    		if(!(nodeSocket.isClosed())){
	    		nodeSocket.close();}
				pc.thread1.stop();
	    	}
	    	else if(numConn == 2)
	    	{
	    		if(!(nodeSocket.isClosed())){
	    		nodeSocket.close();}
	    		if(!(nodeSocket1.isClosed())){
	    		nodeSocket1.close();}
				pc.thread1.stop();
	    	}
	    	else if(numConn == 3)
	    	{
	    		if(!(nodeSocket.isClosed())){
	    		nodeSocket.close();}
	    		if(!(nodeSocket1.isClosed())){
	    		nodeSocket1.close();}
	    		if(!(nodeSocket2.isClosed())){
	    		nodeSocket2.close();}
				pc.thread1.stop();
	    	}
	    }
	    void handleQuery() throws Exception
	    {
	    	pc.srcNode = 1;
	    	
	    	BufferedReader br1 = new BufferedReader(new FileReader("queries.txt")); //opening the file for reading
			String readf = null;
			int uid = 1;
			String uniQid = null;
			
			while ((readf = br1.readLine()) != null) 
			{
				fileSearch = readf;
				uniQid = ownIP1 + ":" + uid;
				fileSearch = "\"" + fileSearch + "\"";
				System.out.println(fileSearch);
	    	
	    	if(numConn == 1)
	    	{
	    		hopCount = 1;
	    		if(!(nodeSocket.isClosed()))
	    		{
	    		byte[] sendQueryData1 = new byte[1024];
	    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount + " "+ uniQid; 
	    		int QueryMsgLength = QueryMsg.length() + 4;
	    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
	    		System.out.println(Qrmsg1);
	    		sendQueryData1 = Qrmsg1.getBytes();
	    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
	    		nodeSocket.send(sendQueryMsg1);
	    		
	    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
		        //thread2 = new Thread(request);
		        //thread2.start();
		        }
	    		
	    		InetAddress stQueryIP2 = InetAddress.getByName(pc.routingTableNode.get(0).IPaddr);
	    		int stQueryPort2 = pc.routingTableNode.get(0).port;
	    		InetAddress stQueryIP3 = InetAddress.getByName(pc.routingTableNode.get(1).IPaddr);
	    		int stQueryPort3 = pc.routingTableNode.get(1).port;
	    		
	    		nodeSocket1 = new DatagramSocket (stQueryPort2);
	    		byte[] sendQueryData2 = new byte[1024];
	    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount + " "+ uniQid; 
	    		int QueryMsgLength1 = QueryMsg1.length() + 4;
	    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
	    		System.out.println(Qrmsg2);
	    		sendQueryData2 = Qrmsg2.getBytes();
	    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, stQueryIP2, stQueryPort2);
	    		nodeSocket1.send(sendQueryMsg2);
	    		
	    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
		        //thread3 = new Thread(request1);
		        //thread3.start();
	    		
	    		nodeSocket2 = new DatagramSocket (stQueryPort3);
	    		byte[] sendQueryData3 = new byte[1024];
	    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength2 = QueryMsg2.length() + 4;
	    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
	    		System.out.println(Qrmsg3);
	    		sendQueryData3 = Qrmsg3.getBytes();
	    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, stQueryIP3, stQueryPort3);
	    		nodeSocket2.send(sendQueryMsg3);
	    		
	    		//QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
		        //thread4 = new Thread(request2);
		        //thread4.start();
	    		
	    	}
	    	else if(numConn == 2)
	    	{
	    		hopCount = 1;
	    		if(!(nodeSocket.isClosed()))
	    		{
	    		byte[] sendQueryData1 = new byte[1024];
	    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength = QueryMsg.length() + 4;
	    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
	    		System.out.println(Qrmsg1);
	    		sendQueryData1 = Qrmsg1.getBytes();
	    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
	    		nodeSocket.send(sendQueryMsg1);
	    		
	    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
		        //thread2 = new Thread(request);
		        //thread2.start();
		        }
	    		
	    		if(!(nodeSocket1.isClosed())){
	    		byte[] sendQueryData2 = new byte[1024];
	    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength1 = QueryMsg1.length() + 4;
	    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
	    		//System.out.println(lvmsg1);
	    		sendQueryData2 = Qrmsg2.getBytes();
	    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, IP2, clipt2);	
	    		nodeSocket1.send(sendQueryMsg2);
	    		
	    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
		        //thread3 = new Thread(request1);
		        //thread3.start();
		        }
	    		
	    		InetAddress stQueryIP3 = InetAddress.getByName(pc.routingTableNode.get(0).IPaddr);
	    		int stQueryPort3 = pc.routingTableNode.get(0).port;
	    		
	    		nodeSocket2 = new DatagramSocket (stQueryPort3);
	    		byte[] sendQueryData3 = new byte[1024];
	    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength2 = QueryMsg2.length() + 4;
	    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
	    		System.out.println(Qrmsg3);
	    		sendQueryData3 = Qrmsg3.getBytes();
	    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, stQueryIP3, stQueryPort3);
	    		nodeSocket2.send(sendQueryMsg3);
	    		
	    		//QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
		        //thread4 = new Thread(request2);
		        //thread4.start();
	    	}
	    	else if(numConn == 3)
	    	{
	    		hopCount = 1;
	    		if(!(nodeSocket.isClosed()))
	    		{
	    		byte[] sendQueryData1 = new byte[1024];
	    		String QueryMsg = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength = QueryMsg.length() + 4;
	    		String Qrmsg1 = "00" + QueryMsgLength + " " + QueryMsg;
	    		System.out.println(Qrmsg1);
	    		sendQueryData1 = Qrmsg1.getBytes();
	    		DatagramPacket sendQueryMsg1 = new DatagramPacket(sendQueryData1, sendQueryData1.length, IP1, clipt1);	
	    		nodeSocket.send(sendQueryMsg1);
	    		
	    		//QueryReceiver request =new QueryReceiver(nodeSocket, numConn, thread2); 
		        //thread2 = new Thread(request);
		        //thread2.start();
		        }
	    		
	    		if(!(nodeSocket1.isClosed())){
	    		byte[] sendQueryData2 = new byte[1024];
	    		String QueryMsg1 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength1 = QueryMsg1.length() + 4;
	    		String Qrmsg2 = "00" + QueryMsgLength1 + " " + QueryMsg1;
	    		//System.out.println(lvmsg1);
	    		sendQueryData2 = Qrmsg2.getBytes();
	    		DatagramPacket sendQueryMsg2 = new DatagramPacket(sendQueryData2, sendQueryData2.length, IP2, clipt2);	
	    		nodeSocket1.send(sendQueryMsg2);
	    		
	    		//QueryReceiver request1 =new QueryReceiver(nodeSocket1, numConn, thread3); 
		        //thread3 = new Thread(request1);
		        //thread3.start();
		        }
	    		
	    		if(!(nodeSocket2.isClosed())){
	    		byte[] sendQueryData3 = new byte[1024];
	    		String QueryMsg2 = "SER" + " " + ownIP1 + " " + ownclipt1 + " " + fileSearch + " " + hopCount+ " "+ uniQid; 
	    		int QueryMsgLength2 = QueryMsg2.length() + 4;
	    		String Qrmsg3 = "00" + QueryMsgLength2 + " " + QueryMsg2;
	    		//System.out.println(lvmsg1);
	    		sendQueryData3 = Qrmsg3.getBytes();
	    		DatagramPacket sendQueryMsg3 = new DatagramPacket(sendQueryData3, sendQueryData3.length, IP3, clipt3);	
	    		nodeSocket2.send(sendQueryMsg3);
	
		        //QueryReceiver request2 =new QueryReceiver(nodeSocket2, numConn, thread4); 
		        //thread4 = new Thread(request2);
		        //thread4.start();
		        }
	    	}
	    	uid++;
	    }
	    }
	}
	
	class QueryReceiver implements Runnable
	{
		
	    DatagramSocket Socket;
	    int numConn; 
	    Thread thread;
	    
	    public QueryReceiver(DatagramSocket nodeSocket, int numConn, Thread thread) throws Exception 
	    {
	        this.Socket = nodeSocket;
	        this.numConn = numConn;
	        this.thread = thread;
	    }
	    @Override
	    public void run() 
	    {
	        try {
	        	byte[] rcvSearchOk= new byte[1024];
	        	Socket.setSoTimeout(10000);
	    		try{
		    	DatagramPacket rcvQreply = new DatagramPacket(rcvSearchOk, rcvSearchOk.length);
		    	Socket.receive(rcvQreply);
			    System.out.println(new String(rcvQreply.getData(), 0, rcvQreply.getLength()));
	    		}
	    		catch(SocketTimeoutException e){
	    			System.out.println("File not found!!!");
	    			thread.stop();
	    		}
			    
	        }catch (Exception e){
	            System.out.println(e);
	        }

	    }
	}
	
	
	class RoutingTableInfo
	{
		int port;
		String IPaddr;
		String Status;
		
		RoutingTableInfo(int pt, String ip, String st)
		{
			this.port = pt;
			this.IPaddr = ip;
			this.Status = st;
		}
	}
	
	class ResourceList
	{
		int index;
		String filename;
		
		ResourceList(int index, String filename)
		{
			this.index = index;
			this.filename = filename;
		}
	}
	
	class KeyValue
	{
		int key;
		String Value;
		
		KeyValue(int key, String Value)
		{
			this.key = key;
			this.Value = Value;
		}
	}
	
	class CacheList{
		String file;
		String containIP;
		int containPort;
		
		CacheList(String file, String containIP, int containPort)
		{
			this.file = file;
			this.containIP = containIP;
			this.containPort = containPort;
		}
	}
	
	class MyLogger {
		public static Logger logger;

		public static void main(String[] args) {
	        logger = Logger.getLogger("MyLog");
	        FileHandler fh = null;
	        try {
	        	fh = new FileHandler("hostname.log");
	            logger.addHandler(fh);
	            SimpleFormatter formatter = new SimpleFormatter();
	            fh.setFormatter(formatter);
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        //logger.info("Hi How r u?");
	    }
	}
	
	 class ClientServer {

		 public static DatagramSocket clBs;
		 public static int port;
		 public static InetAddress BSIP;
		 public static String regUserName;
		 public static int numberNode;
		 public static ArrayList<String> FileList;
		 public static String[] tokens;
		 public static String regIp;
		 //public static MyLogger p2plog = new MyLogger();
		 public static Logger logger;
		 
		 
	    public static void main( String [] args ) throws NumberFormatException, Exception{

	    	P2PConnection node1=new P2PConnection();
	    	P2PConnection node2=new P2PConnection();
	    	int regPort = 0;
	    	
	    	if(args[0].equals("-p"))
	    	{
	    		regPort = Integer.parseInt(args[1]);
	    	}
	    	else if(args[0].equals("-h"))
	    	{
	    		System.out.println("<unstructpp> ‐p <portnum> ‐b <bootstrap ip> ‐n <bootstrap port>");
	    		System.out.println("<unstructpp> ‐h <help>");
	    	}

	    	if(args[2].equals("-b"))
	    	{
	    		BSIP = InetAddress.getByName(args[3]);
	    	}
	    	if(args[4].equals("-n"))
	    	{
	    		port = Integer.parseInt(args[5]);
	    	}
	    	
	    	logger = Logger.getLogger("MyLog");
	        FileHandler fh = null;
	        try {
	        	fh = new FileHandler("hostname.log");
	            logger.addHandler(fh);
	            SimpleFormatter formatter = new SimpleFormatter();
	            fh.setFormatter(formatter);
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	
//	    	int whicservport = Integer.parseInt(args[0]);
//	    	int serport = Integer.parseInt(args[1]);
//	    	String serip = args[2];
	    	   	
	    	Scanner scanner = new Scanner(new InputStreamReader(System.in));
	    	System.out.println("Enter the IP of node to register: ");
	    	regIp = scanner.nextLine();
	    	System.out.println("Enter the username to register: ");
	    	regUserName = scanner.nextLine();
	    	System.out.println("Enter the IP of the manage server: ");
	    	String manageIp1 = scanner.nextLine();
	    	InetAddress manageIp = InetAddress.getByName(manageIp1);
	    	System.out.println("Enter the port of the manage server: ");
	    	int managePort = scanner.nextInt();
	    	
	    	//System.out.println("Enter the port of node to register: ");
	    	//int regPort = scanner.nextInt();
	    	System.out.println("Port entered in main:" + regPort);
	    	System.out.println("Enter the number of nodes to add in the network: ");
	    	int numberNode = scanner.nextInt();
	    	logger.info(regIp);
	    	/*Distributing the resources in the node*/
	    	resourceDistribution(numberNode);
	    	
	    	/*Zipf distribution */
	    	//zipfDistribution();
	    	
	    	String cmd1 = "REG" + " "+ regIp + " "+regPort+ " " +regUserName;
	    	int cmdLength = cmd1.length() + 5;
	    	cmd1 = "00" + cmdLength + " " +cmd1;
	    	//System.out.println(cmd1);
	    	
	    	
	    	
	    	//port = Integer.parseInt(args[0]);
			clBs = new DatagramSocket ();
	        //BSIP = InetAddress.getByName(args[1]);         
	        byte[] sendDataBS = new byte[1024];
	        sendDataBS = cmd1.getBytes();        
	        DatagramPacket sendPackBS = new DatagramPacket(sendDataBS, sendDataBS.length, BSIP, port); 
	        clBs.send(sendPackBS);
	        byte[] receiveDataBS = new byte[1024];
	        DatagramPacket receivePacket = new DatagramPacket(receiveDataBS, receiveDataBS.length);
	        clBs.receive(receivePacket);
            String regOk = new String(receivePacket.getData());
            logger.info(regOk);
//            StringTokenizer regOkHandle = new StringTokenizer(regOk, " ");
//            ArrayList<String> regOkList = new ArrayList<String>();
//            while (regOkHandle.hasMoreTokens())
//            {
//            	regOkList.add(regOkHandle.nextToken());
//            	
//            }
            String sendManageServer = regIp + ":" +regPort;
            byte[] sendDataMS = new byte[1024];
            sendDataMS = sendManageServer.getBytes();
            DatagramPacket sendManagerServerPacket = new DatagramPacket(sendDataMS, sendDataMS.length, manageIp, managePort); 
	        clBs.send(sendManagerServerPacket);
            
            regOk = regOk.trim();
            tokens = regOk.split(" ");
            System.out.println(regOk + regOk.length());
            System.out.println("Value from BS:"+ tokens[3] +"hello");
            String test = (String)tokens[3];
            numberNode = Integer.parseInt(tokens[3]);
            
            //System.out.println("length now:" + regOk.length());
            //System.out.print(test);
            //int numNode = Integer.parseInt(tokens[3]);
            //System.out.println("numnode:" + numNode);
	    	//String numNode = regOkList.get(3);
            if(test.equals("1"))
            {
            	System.out.println("There is only one node!!!");
            	node1.openConnection(regIp, regPort, Integer.parseInt(test),tokens[4], Integer.parseInt(tokens[5]), null, 0, null, 0);
            }
            else if(test.equals("2"))
            {
            	node1.openConnection(regIp, regPort, Integer.parseInt(test),tokens[4], Integer.parseInt(tokens[5]), tokens[6], Integer.parseInt(tokens[7]), null, 0);
            }
            else if(test.equals("3"))
            {
            	node1.openConnection(regIp, regPort, Integer.parseInt(test), tokens[4], Integer.parseInt(tokens[5]), tokens[6], Integer.parseInt(tokens[7]), tokens[8], Integer.parseInt(tokens[9]));
            }
            else if(test.equals("0"))
            {
            	System.out.println("This is the first node in our distributed network");
            }
            else if(test.equals("9999"))
            {
            	System.out.println("failed, there is error in registering.");
            }
            else if(test.equals("9998"))
            {
            	System.out.println("failed, already registered to you. Unregister first.");
            }
            else
            {
            	System.out.println("Some other problem!!!");
            }
            
            
        	
            if(test.equals("1"))
            {
            	//System.out.println("There is only one node!!!");
            	node2.listenConnection(regIp, regPort, Integer.parseInt(test),tokens[4], Integer.parseInt(tokens[5]), null, 0, null, 0, manageIp, managePort);
            }
            else if(test.equals("2"))
            {
            	node2.listenConnection(regIp, regPort, Integer.parseInt(test),tokens[4], Integer.parseInt(tokens[5]), tokens[6], Integer.parseInt(tokens[7]), null, 0, manageIp, managePort);
            }
            else if(test.equals("3"))
            {
            	node2.listenConnection(regIp, regPort, Integer.parseInt(test), tokens[4], Integer.parseInt(tokens[5]), tokens[6], Integer.parseInt(tokens[7]), tokens[8], Integer.parseInt(tokens[9]), manageIp, managePort);
            }
            else if(test.equals("0"))
            {
            	System.out.println("This is the first node in our distributed network");
            	node2.listenConnection(regIp, regPort, Integer.parseInt(test), null, 0, null, 0, null, 0, manageIp, managePort);
            }
            else if(test.equals("9999"))
            {
            	System.out.println("failed, there is error in registering.");
            }
            else if(test.equals("9998"))
            {
            	System.out.println("failed, already registered to you. Unregister first.");
            }
            else
            {
            	System.out.println("Some other problem!!!");
            }
            
//	        node1.sendRequest("Hellllll", serip, whicservport);
	        //node2.listenConnection(regIp, regPort);

	    }
	    public static void resourceDistribution(int numberNode) throws IOException
	    {
	    	ArrayList<ResourceList> RL = new ArrayList<ResourceList>();
	    	FileList = new ArrayList<String>();
	    	ResourceList ResLst;
	    	Random rand = new Random();
	    	int i = 0, count = 0;
	    	BufferedReader br1 = new BufferedReader(new FileReader("resources.txt")); //opening the file for reading
			String readf = null;
			
			while ((readf = br1.readLine()) != null) 
			{
				if(readf.startsWith("#"))
				{
					continue;
				}
				else
				{
					i = i+1;
					ResLst = new ResourceList(i, readf);
					RL.add(ResLst);
				}
			}
			
			if (numberNode == 20)
			{
				while(count != 8)
				{
					int value = rand.nextInt(160) + 1;
					FileList.add(RL.get(value).filename);
					count++;
				}
			}
			else if (numberNode == 40)
			{
				while(count != 4)
				{
					int value = rand.nextInt(160) + 1;
					FileList.add(RL.get(value).filename);
					count++;
				}
			}
			else if (numberNode == 80)
			{
				while(count != 2)
				{
					int value = rand.nextInt(160) + 1;
					FileList.add(RL.get(value).filename);
					count++;
				}
			}
			
			
			for(int j = 0;j< FileList.size(); j++)
        	{
        		System.out.println("Resource Info:" +FileList.get(j));
        	}
	    }
	    
	    public static void zipfDistribution() throws IOException
	    {
	    	HashMap<String, Integer> h=new HashMap<String, Integer>();                        
	    	FileInputStream fin=new FileInputStream("resources.txt");
	    	BufferedReader br = new BufferedReader(new InputStreamReader(fin));
	    	String n;
	    	
	    	
	    	while((n=br.readLine())!=null)
	    	{
	    		if(n.startsWith("#"))
				{
					continue;
				}
	    		else{
	    		StringTokenizer defaultTokenizer = new StringTokenizer(n);
	    		
	    		while (defaultTokenizer.hasMoreTokens())
	    		{
	    		    String token = defaultTokenizer.nextToken();
	    		
	    		    if(h.containsKey(token))
	    		    {
	    		    	int i=(Integer)h.get(token);
	    		    	h.put(token,(i+1));
	    		    }
	    		    else
	    		    {
	    		    	h.put(token, 1);
	    		    }
	    		}
	    	}
	    	Iterator iterator = h.keySet().iterator();
	    	
	    	while (iterator.hasNext()) {
	    	   String key = iterator.next().toString();
	    	   Integer value = h.get(key);

	    	   System.out.println(key + " " + value);
	    	}
	    	Map<String, Integer> map = sortByValues(h);
	    	Set set2 = map.entrySet();
	        Iterator iterator2 = set2.iterator();
	        while(iterator2.hasNext()) {
	             Map.Entry me2 = (Map.Entry)iterator2.next();
	             System.out.print(me2.getKey() + ": ");
	             System.out.println(me2.getValue());
	        } }
	    }
	    private static HashMap sortByValues(HashMap map) { 
	        List list = new LinkedList(map.entrySet());
	        // Defined Custom Comparator here
	        Collections.sort(list, new Comparator() {
	             public int compare(Object o1, Object o2) {
	                return ((Comparable) ((Map.Entry) (o1)).getValue())
	                   .compareTo(((Map.Entry) (o2)).getValue());
	             }
	        });

	        HashMap sortedHashMap = new LinkedHashMap();
	        for (Iterator it = list.iterator(); it.hasNext();) {
	               Map.Entry entry = (Map.Entry) it.next();
	               sortedHashMap.put(entry.getKey(), entry.getValue());
	        } 
	        return sortedHashMap;
	   }
	}
	  

