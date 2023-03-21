import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.crypto.Data;

public class Server 
{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ExecutorService service = null;
	private DataHandler data = null;

	public Server() {
		try {
            serverSocket = new ServerSocket(6660);
			service = Executors.newFixedThreadPool(30);
			data = new DataHandler();
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 6660.");
            System.exit(1);
        }
	}

	public void runServer() {

		while ( true )
		{
			try 
			{
				clientSocket = serverSocket.accept();
				service.submit( new ClientHandler(clientSocket, data) );
			}
			catch (IOException e)
			{
				System.err.println(e);
				System.exit(1);
			}	
		}
	}

	public static void main( String[] args )
	{
		Server server = new Server();

		server.runServer();
	}
}