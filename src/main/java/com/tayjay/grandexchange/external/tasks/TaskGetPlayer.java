package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tayjay.grandexchange.lib.Ref;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-21.
 */
public class TaskGetPlayer extends TaskBase<String>
{
    String name;
    public TaskGetPlayer(EntityPlayerMP requester,String name)
    {
        super(requester);
        this.name = name;
    }

    @Override
    protected String runInThread()
    {
        Socket socket = null;

        try{
            socket = new Socket("138.68.12.167", 20123);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream);

            JsonObject request = new JsonObject();
            request.addProperty(Ref.REQUEST,Ref.GET_PLAYER_PACKET);
            request.addProperty(Ref.USERNAME, this.name);

            out.write(request.toString() + "\n");
            out.flush();

            JsonParser parser = new JsonParser();

            //Thread.sleep(1000);
            String resStr = in.nextLine();
            JsonObject response = parser.parse(resStr).getAsJsonObject();

            if (response.get(Ref.ERROR) != null)
            {
                //Handle error
                return "Error returned: "+request.get(Ref.ERROR).getAsString();
            }else
            {
                JsonElement code = response.get(Ref.RESPONSE);//String describing result
                if (code != null && code.getAsBoolean())
                {
                    String nameElement = response.get(Ref.USERNAME).getAsString();
                    int index = response.get(Ref.PLAYER_ID).getAsInt();


                    return "Found "+nameElement+" at index "+index;


                }else{
                    String nameElement = response.get(Ref.USERNAME).getAsString();
                    return "Could not find "+nameElement+" in database.";
                }
            }

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        /*try
        {
            socket = new Socket("138.68.12.167", 20123);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream);


            //Packet Type10, query player
            out.write(10+"\n");
            out.flush();

            //Player to look for
            out.write(name+"\n");
            out.flush();

            int index = Integer.parseInt(in.nextLine());
            String nameBack = in.nextLine();

            socket.close();

            if (index != -1)
            {
                return "Found "+nameBack+" at index "+index;
            }
            else
                return "Could not find "+nameBack+" in database.";


        } catch (IOException e)
        {
            e.printStackTrace();
        }*/


        return "";
    }

    @Override
    public void start()
    {
        startThread();
    }

    @Override
    public void update()
    {
        if(isDone())
        {
            finish();
        }
    }

    @Override
    public void finish()
    {
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
    }
}
