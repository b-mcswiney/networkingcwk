import java.io.*;
import java.net.*;
import java.util.*;

public class Client 
{

	public void connect()
	{
		// Initialise Reader and writer
		Socket s = null;
		PrintWriter socketOutput = null;
		BufferedReader socketInputer = null;

		try {
			// Set up connection to the server
			s = new Socket("localhost", 2323);
			
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

		// Reader for the keyboard
		BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in) );
        String fromServer;
        String fromUser;

		try {
			while((fromServer=socketInputer.readLine()) != null)
			{
				// Echo response from the server
				System.out.println("Server response: " + fromServer);

				// Client types in response
				fromUser = stdIn.readLine();
				if(fromUser != null) {
					// Echo client string.
					System.out.println( "Client: " + fromUser );

					// Write to server.
					socketOutput.println(fromUser);
				}
			}
			socketOutput.close();
            socketInputer.close();
            stdIn.close();
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
		client.connect();
	}
}