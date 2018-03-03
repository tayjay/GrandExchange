package com.tayjay.grandexchange.external.tasks;

import com.tayjay.gecommon.BuySell;
import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.Ref;
import com.tayjay.gecommon.packets.RequestPacket;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.util.List;

/**
 * Created by tayjay on 2018-02-11.
 */
public class TaskBuyResource extends TaskBase<TaskBuyResource.Result>
{

    String resourceName;
    int quantity;
    ItemStack buying;

    public TaskBuyResource(EntityPlayerMP requester,String resourceName, int quantity)
    {
        super(requester);
        this.resourceName = resourceName;
        this.quantity = quantity;
    }

    @Override
    public void start()
    {

        if(BuySell.isValid(resourceName))
        {
            List<ItemStack> oreItems = OreDictionary.getOres(resourceName);

            if (oreItems.isEmpty())
            {
                buying = new ItemStack(Item.getByNameOrId(resourceName), quantity);
            }
            else
            {
                buying = oreItems.get(0);
                buying.stackSize = quantity;
            }

            if (buying == null)
            {
                requester.addChatComponentMessage(new TextComponentString("Item not valid in this world."));
                return;
            }

            super.start();
        }
        else
        {
            requester.addChatComponentMessage(new TextComponentString("Invalid resource name."));
        }


    }

    @Override
    protected Result runInThread()
    {
        ExClient client = CommonUtil.createClient(requester);
        try
        {
            sendRequest(new RequestPacket(client, Ref.RequestType.BUY_RESOURCE));
            out.writeUTF(resourceName);
            out.flush();
            out.writeInt(quantity);
            out.flush();

            boolean pass = in.readBoolean();
            int availableQuantity = in.readInt();

            return new Result(pass, availableQuantity);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return new Result(false,0);
    }

    @Override
    public void finish()
    {
        if (output().pass)
        {
            if (output().newQuantity != quantity)
            {
                requester.addChatComponentMessage(new TextComponentString("Insufficient resources, buying "+output().newQuantity));
                buying.stackSize = output().newQuantity;
            }
            else
            {
                requester.addChatComponentMessage(new TextComponentString("Successfully bought " + output().newQuantity + " " + buying.getDisplayName()));
            }

            requester.worldObj.spawnEntityInWorld(new EntityItem(requester.worldObj, requester.posX, requester.posY, requester.posZ, buying));

        }
    }

    public class Result
    {
        boolean pass;
        int newQuantity;

        public Result(boolean pass, int newQuantity)
        {
            this.pass = pass;
            this.newQuantity = newQuantity;
        }


    }
}
