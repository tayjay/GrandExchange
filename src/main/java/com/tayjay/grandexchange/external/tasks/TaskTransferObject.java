package com.tayjay.grandexchange.external.tasks;

import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.RequestPacket;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by tayjay on 2018-02-07.
 */
public class TaskTransferObject extends TaskBase<String>
{
    public TaskTransferObject(EntityPlayerMP requester)
    {
        super(requester);
    }

    @Override
    protected String runInThread()
    {
        try
        {
            System.out.println("Transferring object, initializing connection");

            System.out.println("Read/Write created.");
            ExClient client = CommonUtil.createClient(requester);
            System.out.println("Client object created");
            RequestPacket packetInit = new RequestPacket(client, Ref.RequestType.ECHO_CLIENT);
            System.out.println("Request packet Created.");
            out.writeObject(packetInit);
            System.out.println("Sent request");

            String response = in.readUTF();
            System.out.println("Got response");

            socket.close();
            return response;


        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return "Fail";
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
        requester.addChatComponentMessage(new TextComponentString(output()));
    }
}
