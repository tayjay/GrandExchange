package com.tayjay.grandexchange.external.tasks;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
        try
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
        }


        return null;
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
