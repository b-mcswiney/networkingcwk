import java.io.*;
import java.net.*;

public class Client 
{

	public void connect( String userInput )
	{
		// Initialise socket, Reader and writer
		Socket socket = null;
		PrintWriter socketOutput = null;
		BufferedReader socketInputer = null;

		try 
		{
			// Set up connection to the server
			socket = new Socket("localhost", 6666);
			
			// Set up writing stream with the server
			socketOutput = new PrintWriter(socket.getOutputStream(), true);

			// Set up reading stream with the server
			socketInputer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch( UnknownHostException e)
		{
			System.err.println("Cannot connect to that host");
		}
		catch( IOException e)
		{
			System.err.println(e);
		}

        String fromServer;

		try 
		{
			// Check that user input is not null
			if(userInput != null) 
			{
				// Write to server.
				socketOutput.println(userInput);
			}

			while((fromServer=socketInputer.readLine()) !=null)
			{
				// If server gives goodbye message end loop
				if(fromServer.equals("bye"))
				{
					break;
				}

				// Print response from server
				System.out.println(fromServer);
			}

			// Close resources
			socketOutput.close();
            socketInputer.close();
            socket.close();
		}
		catch(IOException e) 
		{
            System.err.println("I/O exception during execution\n");
        }
	}
	public static void main( String[] args )
	{
		Client client = new Client();
		String userInput = String.join(" ", args);
		client.connect( userInput );
	}
}