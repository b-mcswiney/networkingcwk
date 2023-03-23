import java.net.*;
import java.time.*;
import java.io.*;

public class ClientHandler extends Thread
{
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private DataHandler data = null;

    public ClientHandler(Socket socket, DataHandler data) 
    {
        // Set up client handler with current socket and data handler
        super("ClientHandler");
        this.socket = socket;
        this.data = data;
    }

    public void run() 
    {
        try
			{	  
                // Create input and output for the server
			    out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(
    	    	                		new InputStreamReader(
    	                        			socket.getInputStream()));
                String inputLine;

                // Let the client know it has been connected
                // outputLine = "Connection Initiated";
                // out.println(outputLine);

                // Get input from the client
				inputLine = in.readLine();

                // Log the connection
                logConnection(inputLine);

                // Handle the input
                readInput(inputLine);
                
                // Let the client know the connection has ended
                out.println("bye");

                // Free this thread for later use
                out.close();
                in.close();
                socket.close();
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
    }

    private void logConnection(String request)
    {
        try
        {
            // Getting the info to be outputted
            String connectionAddress = socket.getInetAddress().toString();
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            
            // Create file stream
            FileOutputStream logOut = new FileOutputStream("log.txt", true);

            // Write data to log
            logOut.write((date.toString() + "|" + time.toString() + "|" + connectionAddress.substring(1) + "|" + request + "\n").getBytes());

            logOut.close();
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
    }

    private void readInput(String input)
    {
        // Split the cmd line arguments to get the commands
        String[] inputItems = input.split(" ");

        // Check if the command is an accepted one
        if (inputItems[0].equals("show")) 
        {
            // Call data handler to output all values
            data.outputAllData(out);
        }
        else if (inputItems[0].equals("item")) 
        {
            item(inputItems[1]);
        }
        else if (inputItems[0].equals("bid")) 
        {
            bid(inputItems[1], inputItems[2]);
        }

        // If not accepted output error message
        else
        {
            out.println("Usage error: invalid command.\n Usage: java Client [show | item <string> | bid <item name> <value>]");
        }

    }

    private void item(String item)
    {
        // Call data handler to add an item to the auction house
        Integer addStatus = data.addData(item, socket.getRemoteSocketAddress().toString());

        // Check if there were any errors in adding the item
        if(addStatus == 1)
        {
            out.println("Failure");
        }
    }

    private void bid(String item, String bid)
    {
        // Call data handler to update bid if it needs to be
        Integer addStatus = data.updateBid(item, Double.parseDouble(bid), socket.getInetAddress().toString());

        // Check if there were any errors in updating bid
        if(addStatus == 1)
        {
            out.println("Failure");
        }
        if(addStatus == 2)
        {
            out.println("Rejected");
        }
    }
}