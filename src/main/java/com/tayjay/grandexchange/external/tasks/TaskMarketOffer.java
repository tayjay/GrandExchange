package com.tayjay.grandexchange.external.tasks;

import com.sun.imageio.plugins.common.ImageUtil;
import com.tayjay.gecommon.ExchangeItem;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.RequestPacket;
import com.tayjay.grandexchange.util.CommonUtil;
import com.tayjay.grandexchange.util.ImageHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

/**
 * Created by tayjay on 2018-02-28.
 */
public class TaskMarketOffer extends TaskBase<TaskMarketOffer.Result>
{

    ExchangeItem item;
    ItemStack stack;
    float price;

    public TaskMarketOffer(EntityPlayerMP requester, ItemStack stack,float price)
    {
        super(requester);
        this.item = CommonUtil.createExchangeItem(stack);
        this.item.setIcon(ImageHelper.itemIconToIntArray(stack));
        this.stack = stack;
        this.price = price;
    }

    @Override
    public void start()
    {
        //Take item from player

        super.start();
    }

    @Override
    protected Result runInThread() throws IOException
    {
        int[] itemIcon = ImageHelper.itemIconToIntArray(this.stack);

        sendRequest(new RequestPacket(CommonUtil.createClient(requester), Ref.RequestType.MARKET_OFFER));

        out.writeObject(item);
        out.flush();
        out.writeFloat(this.price);
        out.flush();

        boolean confirm = in.readBoolean();

        if (confirm)
        {
            boolean pass = in.readBoolean();
            int market_id = in.readInt();
            String comment = in.readUTF();
            return new Result(pass, market_id, comment);
        }
        else
        {
            return new Result(false,-1,"Server did not confirm.");
        }


    }

    @Override
    public void finish()
    {
        try
        {
            //If pass then print message
            requester.addChatComponentMessage(new TextComponentString(this.output().comment));

            if (this.output().pass)
            {
                requester.addChatComponentMessage(new TextComponentString("Market ID is: " + this.output().market_id));
            }

            //If fail, then return item
            //Consider changing so it only gives back item on first check
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    public class Result
    {
        boolean pass;
        int market_id;
        String comment="NOPE";

        public Result(boolean pass, int market_id, String comment)
        {
            this.pass = pass;
            this.market_id = market_id;
            this.comment = comment;
        }
    }
}
