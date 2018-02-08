package com.tayjay.grandexchange.external.tasks;

import com.google.gson.JsonObject;
import com.tayjay.gecommon.Ref;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.concurrent.ExecutionException;

/**
 * Created by tayjay on 2018-01-21.
 */
public class TaskTest extends TaskBase<String>
{
    protected String sending;

    public TaskTest(String sending)
    {
        this(null,sending);

    }

    public TaskTest(EntityPlayerMP req,String sending)
    {
        super(req);
        this.sending = sending;
    }

    @Override
    public void start()
    {
        //Notify player of anything before hand
        startThread();
    }

    @Override
    protected String runInThread()
    {
        System.out.println("Starting to send.");

        initConnection();

        long currentTime = System.currentTimeMillis();
        //Tell server this is a test packet
        JsonObject request = new JsonObject();
        request.addProperty(Ref.REQUEST, Ref.TEST_PACKET);
        request.addProperty("message",this.sending);
        sendRequest(request);

        JsonObject response = getRepsonse();
        String returning = response.get(Ref.RESPONSE).getAsString();

        System.out.println(returning);
        return returning+" || Took "+(System.currentTimeMillis()-currentTime)+"ms";

    }

    @Override
    public void update()
    {
        if (isDone())
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

    @Override
    public void finish()
    {

    }
}
