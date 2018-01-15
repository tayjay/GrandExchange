package com.tayjay.grandexchange.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by tayjay on 2018-01-14.
 * Handle connection to Database
 */
public class ExchangeConnection
{

    /*public static boolean sendExchangePacket(String sending)
    {
        //String sending = "opcode=1&d_name=Golden Sword&r_name=golden_sword&m_name=minecraft&nbt={id:\"minecraft:golden_sword\",Count:1b,tag:{ench:[0:{lvl:9001s,id:16s},1:{lvl:9001s,id:19s}],RepairCost:1},Damage:0s}";
        try
        {
            URL url = new URL("https://tayjaydb.000webhostapp.com/itemdbconnection.php");//itemdbconnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] bytestosend = sending.getBytes("UTF-8");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(bytestosend.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(bytestosend);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));


            for (int c; (c = in.read()) >= 0;)
                System.out.print((char)c);


        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int checkPlayerExists(String name, UUID uuid)
    {
        String packet = "opcode=0&name="+name+"&uuid="+uuid.toString();

    }

    public void addToSendQueue(String packet)
    {

    }*/


}
