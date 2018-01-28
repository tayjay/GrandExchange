package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by tayjay on 2018-01-21.
 */
public abstract class TaskBase<T> implements ITask
{
    protected Future<T> output;
    protected EntityPlayerMP requester;
    protected Socket socket;
    protected Scanner in;
    protected PrintWriter out;

    public TaskBase(EntityPlayerMP requester)
    {
        this.requester = requester;
    }

    protected void initConnection()
    {
        try
        {
            socket = new Socket("138.68.12.167", 20123);
            socket.setSoTimeout(3000);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            in = new Scanner(inputStream);
            out = new PrintWriter(outputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    protected void startThread()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        this.output = executor.submit(new Callable<T>()
        {
            @Override
            public T call() throws Exception
            {
                return runInThread();
            }
        });
    }

    /**
     * This will be ran out of sync from the game world, inside the call method of another thread
     * DO NOT ACCESS ANYTHING ON MC SIDE!!!
     * @return
     */
    protected abstract T runInThread();



    @Override
    public T output()
    {
        if(this.isDone())
        {
            try
            {
                return output.get();
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

    JsonParser parser = new JsonParser();
    protected JsonObject getRepsonse()
    {
        String resStr = in.nextLine();
        JsonObject response = parser.parse(resStr).getAsJsonObject();
        return response;
    }

    protected void sendRequest(JsonObject request)
    {
        out.write(request.toString() + "\n");
        out.flush();
    }


    @Override
    public EntityPlayerMP getRequester()
    {
        return requester;
    }

    @Override
    public boolean isDone()
    {
        return output != null && output.isDone();
    }
}
