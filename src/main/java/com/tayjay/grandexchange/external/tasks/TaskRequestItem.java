package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonObject;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.ExchangeItem;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by tayjay on 2018-01-27.
 */
public class TaskRequestItem extends TaskBase<ExchangeItem>
{
    String username;
    int slot;
    public TaskRequestItem(EntityPlayerMP requester, int slot, String username)
    {
        super(requester);
        this.slot = slot;
        this.username = username;
    }

    @Override
    protected ExchangeItem runInThread()
    {
        /*initConnection();

        JsonObject request = new JsonObject();
        request.addProperty(Ref.REQUEST, Ref.REQUEST_ITEM_PACKET);
        request.addProperty(Ref.USERNAME, username);
        request.addProperty(Ref.SLOT, slot);
        sendRequest(request);

        JsonObject response = getRepsonse();
        System.out.println("Got response for requestItem");

        if (response.get(Ref.ERROR) != null)
        {
            System.out.println(response.get(Ref.ERROR).getAsString());
            return null;
        }

        ExchangeItem item = new ExchangeItem(response.getAsJsonObject(Ref.EXCHANGE_ITEM));
        System.out.println("Item called "+item.toString());

        JsonObject confirm = new JsonObject();
        confirm.addProperty(Ref.RESPONSE, Ref.CONFIRM_ITEM_VALID_PACKET);
        boolean isValid = (item.createItemStack()!=null);
        confirm.addProperty(Ref.VALID,isValid);
        sendRequest(confirm);
        System.out.println("Sent confirmation " + isValid);

        JsonObject confirmBack = getRepsonse();
        if (confirmBack.get(Ref.RESPONSE).getAsBoolean())
        {
            System.out.println("Confirmed and got item");
            return item;
        }
        System.out.println("Confirmation failed.");
*/
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
        if (output() != null)
        {
            requester.worldObj.spawnEntityInWorld(new EntityItem(requester.worldObj, requester.posX, requester.posY, requester.posZ, CommonUtil.createItemStackFromExhange(output())));

        }
        else
        {
            requester.addChatComponentMessage(new TextComponentString("Failed to get item."));
        }
    }
}
