package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonObject;
import com.tayjay.grandexchange.external.ExchangeItem;
import com.tayjay.grandexchange.lib.Ref;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.net.SocketException;
import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-27.
 */
public class TaskOfferItem extends TaskBase<Boolean>
{
    String username;
    ItemStack stack;
    int slot;

    public TaskOfferItem(EntityPlayerMP requester, ItemStack stack, int slot, String username)
    {
        super(requester);
        this.username = username;
        this.stack = stack;
        this.slot = slot;
    }

    @Override
    protected Boolean runInThread()
    {
        initConnection();
        try
        {
            socket.setSoTimeout(3000);
            ExchangeItem item = new ExchangeItem(this.stack);

            JsonObject request = new JsonObject();
            request.addProperty(Ref.REQUEST, Ref.OFFER_ITEM_PACKET);
            request.addProperty(Ref.USERNAME, this.username);
            request.add(Ref.EXCHANGE_ITEM,item.getAsJsonObject());
            request.addProperty(Ref.SLOT,this.slot);
            //Also send uuid

            sendRequest(request);

            JsonObject response = getRepsonse();

            if (response.get(Ref.ERROR) != null)
            {
                //Error occured
                return false;
            }
            else
            {
                return response.get(Ref.RESPONSE).getAsBoolean();
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void start()
    {
        if(stack!=null)
        {
            ItemStack tempStack = stack.copy();
            //stack.stackSize = 0;//Remove stack from player.
            requester.inventory.setInventorySlotContents(requester.inventory.currentItem, null);
            this.stack = tempStack;
            startThread();
        } else{
            requester.addChatComponentMessage(new TextComponentString("Attempted to offer nothing."));
            Thread.currentThread().interrupt();
        }
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
        try
        {
            if (output.get() != null && output.get().booleanValue())
            {
                requester.addChatComponentMessage(new TextComponentString("Offer Successful."));
            }
            else
            {
                requester.addChatComponentMessage(new TextComponentString("Failed to offer, returning item."));
                requester.worldObj.spawnEntityInWorld(new EntityItem(requester.worldObj, requester.posX, requester.posY, requester.posZ, this.stack));
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}
