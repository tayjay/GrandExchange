package com.tayjay.grandexchange.external.tasks;

import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.RequestPacket;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

/**
 * Created by tayjay on 2018-02-25.
 */
public class TaskAuthPlayer extends TaskBase<TaskAuthPlayer.Result>
{
    public TaskAuthPlayer(EntityPlayerMP requester)
    {
        super(requester);
    }

    @Override
    protected Result runInThread() throws IOException
    {
        ExClient client = CommonUtil.createClient(requester);
        sendRequest(new RequestPacket(client, Ref.RequestType.AUTH_PLAYER));

        try
        {
            boolean pass = in.readBoolean();
            String pin = in.readUTF();
            String reason = in.readUTF();
            return new Result(pass, pin,reason);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new Result(false,null,"Error on Sending!");
    }

    @Override
    public void finish()
    {
        if (output().pass)
        {
            requester.addChatComponentMessage(new TextComponentString("You have been authorized to use the Grand Exchange."));
            requester.addChatComponentMessage(new TextComponentString("Your login pin is: " + output().pin));
        }
        else
        {
            requester.addChatComponentMessage(new TextComponentString(output().reason));
        }
    }

    public class Result
    {
        boolean pass;
        String pin,reason;

        public Result(boolean pass, String pin, String reason)
        {
            this.pass = pass;
            this.pin = pin;
            this.reason = reason;
        }
    }
}
