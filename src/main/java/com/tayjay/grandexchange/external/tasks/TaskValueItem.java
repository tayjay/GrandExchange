package com.tayjay.grandexchange.external.tasks;

import com.tayjay.gecommon.ExchangeItem;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.RequestPacket;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;

/**
 * Created by tayjay on 2018-02-27.
 */
public class TaskValueItem extends TaskBase<TaskValueItem.Result>
{
    ExchangeItem item;
    public TaskValueItem(EntityPlayerMP requester, ExchangeItem item)
    {
        super(requester);
        this.item = item;
    }

    @Override
    protected Result runInThread() throws IOException
    {
        if (item == null)
        {
            return new Result(false,0,"No item offered.");
        }
        sendRequest(new RequestPacket(CommonUtil.createClient(this.requester), Ref.RequestType.VALUE_ITEM));
        out.writeObject(item);

        try
        {
            boolean legalItem = in.readBoolean();
            float minValue = in.readFloat();
            String reason = in.readUTF();
            return new Result(legalItem, minValue, reason);
        } catch (IOException e)
        {

        }



        return new Result(false,0,"IOError caught.");
    }

    @Override
    public void finish()
    {

    }

    public class Result
    {
        boolean pass;
        float minValue;
        String reason;

        Result(boolean pass,float minValue, String reason)
        {
            this.pass = pass;
            this.minValue = minValue;
            this.reason = reason;
        }
    }
}
