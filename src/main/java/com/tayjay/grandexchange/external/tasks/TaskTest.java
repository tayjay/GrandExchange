package com.tayjay.grandexchange.external.tasks;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tayjay on 2018-01-21.
 */
public class TaskTest extends TaskBase<String>
{
    protected String sending;

    public TaskTest(String sending)
    {
        this(null,sending);

    }

    public TaskTest(EntityPlayerMP req,String sending)
    {
        super(req);
        this.sending = sending;
    }

    @Override
    public void start()
    {
        //Notify player of anything before hand
        startThread();
    }

    @Override
    protected String runInThread()
    {
        System.out.println("Starting to send.");
        try
        {
            Socket socket = new Socket("138.68.12.167", 20123);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream);

            System.out.println("Streams created, connection established.");

            //Tell server this is a test packet
            out.write(0+"\n");
            out.flush();


            out.write(sending+"\n");
            out.flush();
            String returning = in.nextLine();

            System.out.println(returning);
            return returning;
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("SOMETHING HAPPENED!!!");
        }

        return null;
    }

    @Override
    public void update()
    {
        if (isDone())
        {
            try
            {
                if (output.get() == null)
                    requester.addChatComponentMessage(new TextComponentString("Something happened."));
                else
                {
                    /*ITextComponent textComponent = new TextComponentString(output.get());
                    textComponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,new TextComponentString(new ItemStack(Items.DIAMOND).serializeNBT().toString())));
                    requester.addChatComponentMessage(textComponent);*/
                    requester.addChatComponentMessage(new TextComponentString(output.get()));
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            } catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void finish()
    {

    }
}
