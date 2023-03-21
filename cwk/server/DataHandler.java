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

    public void addData(String item, String ip)
    {
        bids.put(item, 1);
        ips.put(item, ip);
    }

    public void outputAllData(PrintWriter out)
    {
        out.println("Show current bids");
        for ( Map.Entry<String, Integer> entry : bids.entrySet()) {
            out.println("Item: " + entry.getKey() + " Current bid " + entry.getValue() + " from ip: " + ips.get(entry.getKey()));
        }
    }

    public void updateBid(String item, Integer userBid)
    {
        for(String items: bids.keySet())
        {
            if(items.equals(item))
            {
                if(bids.get(items) < userBid)
                {
                    bids.put(items, userBid);
                }
            }
        }
    }
}