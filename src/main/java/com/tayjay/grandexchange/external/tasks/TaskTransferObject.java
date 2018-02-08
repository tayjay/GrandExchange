package com.tayjay.grandexchange.external.tasks;

import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.CPacketInit;
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
            initConnection();
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            ExClient client = CommonUtil.createClient(requester);
            CPacketInit packetInit = new CPacketInit(client, Ref.RequestType.ECHO_CLIENT);
            outToServer.writeObject(packetInit);

            String response = inFromServer.readUTF();
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
