package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tayjay.gecommon.Ref;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-21.
 */
public class TaskGetPlayer extends TaskBase<String>
{
    String name;
    public TaskGetPlayer(EntityPlayerMP requester,String name)
    {
        super(requester);
        this.name = name;
    }

    @Override
    protected String runInThread()
    {
        initConnection();
        JsonObject request = new JsonObject();
        request.addProperty(Ref.REQUEST, Ref.GET_PLAYER_PACKET);
        request.addProperty(Ref.USERNAME, this.name);

        sendRequest(request);

        JsonObject response = getRepsonse();

        if (response.get(Ref.ERROR) != null)
        {
            //Handle error
            return "Error returned: "+request.get(Ref.ERROR).getAsString();
        }else
        {
            JsonElement code = response.get(Ref.RESPONSE);//String describing result
            if (code != null && code.getAsBoolean())
            {
                String nameElement = response.get(Ref.USERNAME).getAsString();
                int index = response.get(Ref.PLAYER_ID).getAsInt();


                return "Found "+nameElement+" at index "+index;


            }else{
                String nameElement = response.get(Ref.USERNAME).getAsString();
                return "Could not find "+nameElement+" in database.";
            }
        }
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
            requester.addChatComponentMessage(new TextComponentString(output.get()));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}
