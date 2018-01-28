package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tayjay.grandexchange.lib.Ref;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-25.
 */
public class TaskCreatePlayer extends TaskBase<String>
{
    String username,uuid;
    public TaskCreatePlayer(EntityPlayerMP requester,String username, String uuid)
    {
        super(requester);
        this.username = username;
        this.uuid = uuid;
    }

    @Override
    protected String runInThread()
    {
        initConnection();
        JsonObject request = new JsonObject();
        request.addProperty(Ref.REQUEST,Ref.CREATE_PLAYER_PACKET);
        //request.addProperty(Ref.USERNAME, this.requester.getName());
        //request.addProperty(Ref.UUID,this.requester.getUniqueID().toString());
        request.addProperty(Ref.USERNAME, this.username);
        request.addProperty(Ref.UUID, this.uuid);

        sendRequest(request);
        //Wait for exchange to created player entry
        JsonObject response = getRepsonse();

        if (response.get(Ref.ERROR) != null)
        {
            //Handle error
            return "Error returned: "+request.get(Ref.ERROR).getAsString();
        }else
        {
            JsonElement code = response.get(Ref.RESPONSE);//String describing result
            if (code != null)
            {
                return code.getAsString();
            }else{

            }
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
        if(isDone())
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
