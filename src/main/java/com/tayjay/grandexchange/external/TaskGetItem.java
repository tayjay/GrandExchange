package com.tayjay.grandexchange.external;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by tayjay on 2018-01-18.
 */
public class TaskGetItem implements ITask
{
    EntityPlayerMP requester;
    String name,uuid;
    int slot;
    Future<String> output;

    public TaskGetItem(EntityPlayerMP requester, String name, UUID uuid, int slot)
    {
        this.requester = requester;
        this.name = name;
        this.uuid = uuid.toString();
        this.slot = slot;
    }

    @Override
    public void start()
    {
        requester.addChatComponentMessage(new TextComponentString("Initializing connection with Exchange server..."));
        //Initialize connection to exchange
        ExecutorService executor = Executors.newFixedThreadPool(1);
        this.output = executor.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                try
                {
                    URL url = new URL("https://tayjaydb.000webhostapp.com/itemdbconnection.php");//itemdbconnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    String sending = new ReqGetPlayer(name, uuid).createPacket();
                    byte[] bytestosend = sending.getBytes("UTF-8");
                    //todo:Change data to correct in here. Correct packet format
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(bytestosend.length));
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(bytestosend);

                    //connection.getOutputStream().write("H".getBytes("UTF-8"));

                    Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int c; (c = in.read()) >= 0; )
                        stringBuilder.append(Character.toChars(c));

                    connection.disconnect();
                    return stringBuilder.toString();


                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    System.out.println("Could not connect to site.");
                }
                return null;
            }
        });
    }

    @Override
    public void update()
    {

    }

    @Override
    public boolean isDone()
    {
        return false;
    }

    @Override
    public void finish()
    {

    }

    @Override
    public <T extends ExchangePacket> T output()
    {
        return null;
    }

    @Override
    public EntityPlayerMP getRequester()
    {
        return null;
    }
}
