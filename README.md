# Implementation-of-Caching-in-Unstructured-Peer-to-Peer-Networks

Compilation
------------
A Jar package has to be created for the three files named P2PConnection.java, ManageP2P.java and zipfs.java.

Procedure of Making Jar File
----------------------------
1. Create a project in eclipse for three ".java" Files. e.g-> Lab04.

2. Right click on the Java file -> Export -> Java -> Jar file -> click on next -> 
Browse the path to keep the jar file and name it as unstructpp4.jar/ManageP2P4.jar/zipf.jar -> Click on next -> Click on next
-> Select the main class as ClientServer/ManageP2P/zipf -> Finish

Running the Jar File
--------------------

1. Run the zipf in the same folder to get the data as "java -jar zipf.jar <size:160> <exponent:0.2/0.6/0.8>"


2. Run the Manage Server in one dedidated node as "java -jar ManageP2P4.jar"
	It will ask for the following user inputs:

	Enter the port of Manage Server:

<Start the searching from here after all the nodes will be registered>

	Type 1 to start search

	Type SEARCH to start the searching:

	Enter 1 for Normal search or 2 for zipf search

<If 1 is entered then it will ask for the value of Ns>

	Enter the value of Ns:

<If 2 is entered then it will ask for case1 or case2  search type>

	Enter the value of Ns:

	

3. Run the unstructpp file in all the nodes as 
"java -jar unstructpp4.jar -p <port number> -b <bootstrap server IP> -n <bootstrap server port>"

It will now ask for few inputs from the user to register and initialize the node. 

Those are:

	Enter the IP of node to register:

	Enter the username to register:

	Enter the IP of the manage server:

	Enter the port of the manage server:

	Enter the number of nodes to add in the network:

