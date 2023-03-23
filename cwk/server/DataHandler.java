import java.util.*;
import java.io.PrintWriter;

public class DataHandler
{
    // Hash maps to store item, bid and ip of bid
    private HashMap<String, Double> bids = null;
    private HashMap<String, String> ips = null;

    public DataHandler()
    {
        bids = new HashMap<String, Double>();
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
        bids.put(item, 0.00);
        ips.put(item, "<no bids>");

        // Return 0 indicating success
        return 0;
    }

    public void outputAllData(PrintWriter out)
    {
        if (bids.isEmpty())
        {
            out.println("There are currently no items in this auction.");
        }
        for ( Map.Entry<String, Double> entry : bids.entrySet()) 
        {
            out.println(entry.getKey() + " : " + entry.getValue() + " : " + ips.get(entry.getKey()));
        }
    }

    public int updateBid(String item, Double userBid, String ip)
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
                    // Update table with new bid
                    bids.put(items, userBid);
                    ips.put(items, ip.substring(1));
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