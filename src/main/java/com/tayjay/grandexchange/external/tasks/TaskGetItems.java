package com.tayjay.grandexchange.external.tasks;

import com.tayjay.grandexchange.external.ExchangeItem;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by tayjay on 2018-01-22.
 */
public class TaskGetItems extends TaskBase<ExchangeItem[]>
{
    String name,uuid;
    public TaskGetItems(EntityPlayerMP requester,String name,UUID uuid)
    {
        super(requester);
        this.name= name;
        this.uuid = uuid.toString();
    }

    @Override
    protected ExchangeItem[] runInThread()
    {
        Socket socket = null;
        try
        {
            socket = new Socket("138.68.12.167", 20123);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream);

            out.write(30 + "\n");
            out.flush();




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
        if (isDone())
        {
            finish();
        }
    }

    @Override
    public void finish()
    {

    }
}
