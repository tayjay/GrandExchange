package com.tayjay.grandexchange.external;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by tayjay on 2018-01-15.
 */
public class TaskGetPlayer implements ITask
{
    String name,uuid;
    Future<String> output;
    EntityPlayerMP requester;

    public TaskGetPlayer(EntityPlayerMP requester, String playerName, UUID playerUUID)
    {
        this.name = playerName;
        this.uuid = playerUUID.toString();
        this.requester = requester;
    }

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

    /**
     * This should run in the game loop
     */
    public void update()
    {
        if (this.isDone())
        {

            RespGetPlayer response = this.output();
            requester.addChatComponentMessage(new TextComponentString("Received response:"));
            try
            {
                requester.addChatComponentMessage(new TextComponentString(output.get()));
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            //Network manager send packet to player
            //Or just chat message to player
            String toPlayer;
            if(response.exists)
            {
                toPlayer = "Could not find user " + response.player_name;
            }
            else
            {
                toPlayer = "Player "+response.player_name+" found with ID "+response.player_id;
            }
            requester.addChatComponentMessage(new TextComponentString(toPlayer));
            this.finish();
        }
    }

    public RespGetPlayer output()
    {
        if (output != null && output.isDone())
        {
            try
            {
                return new RespGetPlayer(output.get());
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        return null;

    }

    public boolean isDone()
    {
        return output != null && output.isDone();
    }

    public void finish()
    {

    }

    @Override
    public EntityPlayerMP getRequester()
    {
        return null;
    }
}
