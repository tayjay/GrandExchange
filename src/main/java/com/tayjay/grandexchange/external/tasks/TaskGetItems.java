package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tayjay.grandexchange.external.ExchangeItem;
import com.tayjay.grandexchange.lib.Ref;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
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
        ArrayList<ExchangeItem> exchangeItems = new ArrayList<ExchangeItem>(10);
        try
        {
            socket = new Socket("138.68.12.167", 20123);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream);

            JsonObject request = new JsonObject();
            request.addProperty(Ref.REQUEST, Ref.GET_ITEMS_PACKET);
            request.addProperty(Ref.USERNAME, this.name);
            request.addProperty(Ref.UUID, this.uuid);

            out.write(request.toString() + "\n");
            out.flush();

            String resStr = in.nextLine();
            JsonObject response = new JsonParser().parse(resStr).getAsJsonObject();
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
                exchangeItems.add(new ExchangeItem(currentItem.getAsJsonObject()));
            }

            return (ExchangeItem[]) exchangeItems.toArray();


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
