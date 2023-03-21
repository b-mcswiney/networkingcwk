import java.io.*;
import java.net.*;
import java.util.*;

public class Server 
{
	private ServerSocket serverSocket = null;

	public Server() {
		try {
            serverSocket = new ServerSocket(2323);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 2323.");
            System.exit(1);
        }
	}

	public void runServer() {

		while ( true )
		{
			Socket clientSocket = null;

			try 
			{
				clientSocket = serverSocket.accept();
			}
			catch (IOException e)
			{
				System.err.println(e);
				System.exit(1);
			}

			try
			{
				InetAddress inet = clientSocket.getInetAddress();
				Date date = new Date();
				System.out.println("\nDate " + date.toString());
				System.out.println("connection made from " + inet.getHostName());
	  
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				writer.println("Connection terminated.");
				writer.close();
				System.out.println("Connection terminated.");
				clientSocket.close();
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