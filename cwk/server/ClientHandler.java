import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread
{
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private DataHandler data = null;

    public ClientHandler(Socket socket, DataHandler data) 
    {
        super("ClientHandler");
        this.socket = socket;
        this.data = data;
    }

    public void run() 
    {
        try
			{
				InetAddress inet = socket.getInetAddress();
				Date date = new Date();
				System.out.println("\nDate " + date.toString());
				System.out.println("connection made from " + inet.getHostName());
	  
			    out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(
    	    	                		new InputStreamReader(
    	                        			socket.getInputStream()));
                String inputLine, outputLine;

                outputLine = "Connection Initiated";
                out.println(outputLine);

				inputLine = in.readLine();
                
                readInput(inputLine);
                
                out.println("bye");

                out.close();
                in.close();
                socket.close();
			}
			catch (IOException e)
			{
				System.err.println(e);
				System.exit(1);
			}
    }

    private void readInput(String input)
    {
        String[] inputItems = input.split(" ");

        if (inputItems[0].equals("show")) 
        {
            data.outputAllData(out);;
        }
        else if (inputItems[0].equals("item")) 
        {
            item(inputItems[1]);
        }
        else if (inputItems[0].equals("bid")) 
        {
            bid(inputItems[1], inputItems[2]);
        }

    }

    private void item(String item)
    {
        out.println("Add " + item + " to table with a starting bid of 1");

        data.addData(item, socket.getRemoteSocketAddress().toString());
    }

    private void bid(String item, String bid)
    {
        out.println("bid for " + item + " at " + bid);

        data.updateBid(item, Integer.parseInt(bid));
    }
}