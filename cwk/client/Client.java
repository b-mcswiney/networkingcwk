import java.io.*;
import java.net.*;
import java.util.*;

public class Client 
{

	public void connect( String userInput )
	{
		// Initialise Reader and writer
		Socket s = null;
		PrintWriter socketOutput = null;
		BufferedReader socketInputer = null;

		try {
			// Set up connection to the server
			s = new Socket("localhost", 6660);
			
			// Set up writing stream with the server
			socketOutput = new PrintWriter(s.getOutputStream(), true);

			// Set up reading stream with the server
			socketInputer = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch( UnknownHostException e)
		{
			System.err.println("Cannot connect to that host");
			System.exit(1);
		}
		catch( IOException e)
		{
			System.err.println(e);
			System.exit(1);
		}

        String fromServer;
        String fromUser;

		try {
			fromServer=socketInputer.readLine();
			// Echo response from the server
			System.out.println("Server response: " + fromServer);

			if(userInput != null) 
			{
				// Echo client string.
				System.out.println( "Client: " + userInput );

				// Write to server.
				socketOutput.println(userInput);

				fromServer=socketInputer.readLine();

				// Echo response from the server
				System.out.println("Server response: " + fromServer);
			}

			while((fromServer=socketInputer.readLine()) !=null)
			{
				if(fromServer.equals("bye"))
				{
					break;
				}
				System.out.println(fromServer);
			}

			

			socketOutput.close();
            socketInputer.close();
            s.close();
		}
		catch(IOException e) {
            System.err.println("I/O exception during execution\n");
            System.exit(1);
        }
	}
	public static void main( String[] args )
	{
		Client client = new Client();
		String userInput = String.join(" ", args);
		client.connect( userInput );
	}
}