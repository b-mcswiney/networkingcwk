import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

			// Create log file when server starts
			File log = new File("log.txt");
			log.createNewFile();

			// Ensure this new file is empty for this server
			FileWriter w = new FileWriter("log.txt", false);
			w.write("");
			w.close();
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 6660.");
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
			}	
		}
	}

	public static void main( String[] args )
	{
		Server server = new Server();

		server.runServer();
	}
}