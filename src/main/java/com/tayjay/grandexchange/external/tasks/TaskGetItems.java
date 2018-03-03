package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.ExchangeItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-22.
 */
public class TaskGetItems extends TaskBase<ArrayList<ExchangeItem>>
{
    String name,uuid;
    public TaskGetItems(EntityPlayerMP requester,String name,UUID uuid)
    {
        super(requester);
        this.name= name;
        this.uuid = uuid.toString();
    }

    @Override
    protected ArrayList<ExchangeItem> runInThread()
    {
        /*try
        {
            initConnection();
            ArrayList<ExchangeItem> exchangeItems = new ArrayList<ExchangeItem>(10);

            JsonObject request = new JsonObject();
            request.addProperty(Ref.REQUEST, Ref.GET_ITEMS_PACKET);
            request.addProperty(Ref.USERNAME, this.name);
            request.addProperty(Ref.UUID, this.uuid);

            sendRequest(request);

            JsonObject response = getRepsonse();

            if (response.get(Ref.ERROR) != null)
            {
                System.out.println(response.get(Ref.ERROR).getAsString());
                return null;
            }
            JsonArray itemArray = response.getAsJsonArray(Ref.ITEMS);
            Iterator<JsonElement> itemIterator = itemArray.iterator();
            JsonElement currentItem;
            while (itemIterator.hasNext())
            {
                currentItem = itemIterator.next();
                System.out.println("Adding item " + currentItem.toString());
                exchangeItems.add(new ExchangeItem(currentItem.getAsJsonObject()));
            }
            System.out.println("Leaving the loop. " + exchangeItems.toString());
            return exchangeItems;
        } catch (Exception e)
        {
            e.printStackTrace();
        }*/
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
        try
        {
            if (output.get() == null)
            {
                requester.addChatComponentMessage(new TextComponentString("Error when getting items."));
            }
            else
            {
                ArrayList<ExchangeItem> items = output.get();
                requester.addChatComponentMessage(new TextComponentString("Got items..."));
                for(int i=0;i<items.size();i++)
                {
                    ExchangeItem item = items.get(i);
                    item.setSlot(i);
                    //requester.addChatComponentMessage(item.getChatComponent());
                }
                for (ExchangeItem item : items)
                {

                }
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
