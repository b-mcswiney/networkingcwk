import java.io.*;
import java.net.*;
import java.util.*;
import java.io.PrintWriter;

public class DataHandler
{
    private HashMap<String, Integer> bids = null;
    private HashMap<String, String> ips = null;

    public DataHandler()
    {
        bids = new HashMap<String, Integer>();
        ips = new HashMap<String, String>();
    }

    public int addData(String item, String ip)
    {
        for(String items: bids.keySet())
        {
            // If item is already in hash map return 1 meaning error
            if(items.equals(item))
            {
                return 1;
            }
        }

        // If loops through without finding item add to hash map
        bids.put(item, 1);
        ips.put(item, ip);

        // Return 0 indicating success
        return 0;
    }

    public void outputAllData(PrintWriter out)
    {
        out.println("Show current bids");
        for ( Map.Entry<String, Integer> entry : bids.entrySet()) {
            out.println("Item: " + entry.getKey() + " Current bid " + entry.getValue() + " from ip: " + ips.get(entry.getKey()));
        }
    }

    public int updateBid(String item, Integer userBid, String ip)
    {
        // Integer for checking the amount of items
        // with the same name as item found
        int itemFound = 0;

        // Search through every item in the hashmap to find the one to update
        for(String items: bids.keySet())
        {
            if(items.equals(item))
            {
                itemFound = itemFound + 1;
                
                if(bids.get(items) < userBid)
                {
                    bids.put(items, userBid);
                }

                // If the bid supplied is less than current bid return 2 indicating Rejection
                else
                {
                    return 2;
                }
            }
        }

        // If no items are found return 0
        if(itemFound == 0)
        {
            return 1;
        }

        return 0;
    }
}