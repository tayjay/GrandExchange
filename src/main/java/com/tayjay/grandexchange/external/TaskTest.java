package com.tayjay.grandexchange.external;

import net.minecraft.entity.player.EntityPlayerMP;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by tayjay on 2018-01-20.
 */
public class TaskTest implements ITask
{
    Future<String> output;
    @Override
    public void start()
    {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        this.output = executor.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                Socket socket = null;
                try
                {
                    socket = new Socket("138.68.12.167",20123);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

                    System.out.println("Sending to Server.");
                    out.print("Hello World!");
                    out.print("This is from another world!!!");

                    System.out.println("Listening for server...");
                    String fromServer=in.readLine();
                    System.out.println("Server: " + fromServer);



                } catch (IOException e)
                {
                    System.out.println("IO ERROR");
                    e.printStackTrace();
                }
                return null;
            }
        });

        /*this.output = executor.submit(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });*/

    }

    @Override
    public void update()
    {
        if (isDone())
        {
            try
            {
                System.out.println(this.output.get());
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isDone()
    {
        return this.output!=null && this.output.isDone();
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
