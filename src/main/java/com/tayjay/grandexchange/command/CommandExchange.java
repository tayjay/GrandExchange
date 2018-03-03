package com.tayjay.grandexchange.command;

import com.tayjay.grandexchange.GrandExchange;

import com.tayjay.gecommon.ExchangeItem;
import com.tayjay.grandexchange.util.CommonUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjay on 2018-01-14.
 */
public class CommandExchange implements ICommand
{
    List<String> aliases;

    public CommandExchange()
    {
        aliases = new ArrayList<String>();
        aliases.add("ge");
        aliases.add("grandex");
        aliases.add("exchange");
    }
    @Override
    public String getCommandName()
    {
        return "grandexchange";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "grandexchange <command> <options>";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args[0] != null)
        {
            if ("getplayer".equals(args[0]))
            {
                if (sender == null)
                {
                    System.out.println("Command sender null.");
                }
                else if (GrandExchange.exchangeConnection == null)
                {
                    System.out.println("No exchange connection.");
                }
                else if(args.length<2 || args[1]==null)
                {
                    System.out.println("Missing last argument.");
                }
                else
                {
                    GrandExchange.exchangeConnection.getPlayerFromExchange(((EntityPlayerMP) sender), args[1]);
                }
            } else if ("ping".equals(args[0]))
            {
                sender.addChatMessage(new TextComponentString("Sending a ping!"));

                if(args.length<2 || args[1]==null)
                    GrandExchange.exchangeConnection.sendTestToExchange(((EntityPlayerMP) sender),"Ping");
                else
                    GrandExchange.exchangeConnection.sendTestToExchange(((EntityPlayerMP) sender),args[1]);
            } else if ("test".equals(args[0]))
            {
                if (((EntityPlayer) sender.getCommandSenderEntity()).getHeldItemMainhand() != null)
                {
                    ExchangeItem item = CommonUtil.createExchangeItem(((EntityPlayer) sender.getCommandSenderEntity()).getHeldItemMainhand());
                    /*TextComponentString chat = new TextComponentString(item.toString());
                    chat.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("This\nIs\nAn\nItem")));//item.item_nbt.toString()
                    sender.addChatMessage(chat);
                    EntityPlayer p = ((EntityPlayer) sender.getCommandSenderEntity());*/
                    //p.worldObj.spawnEntityInWorld(new EntityItem(p.worldObj,p.posX,p.posY,p.posZ, ItemStack.loadItemStackFromNBT(JsonToNBT.getTagFromJson("{id:\"minecraft:iron_chestplate\",Count:1b,Damage:0s}"))));
                    //item.nbt_string = item.nbt_string.concat("HKJLH");
                    //sender.addChatMessage(item.getChatComponent());
                }
            } else if ("create".equals(args[0]))
            {
                if (args.length == 3)
                {
                    GrandExchange.exchangeConnection.createPlayerOnExchange(((EntityPlayerMP) sender),args[1],args[2]);
                }
            } else if ("list".equals(args[0]))
            {
                if (args.length == 2)
                {
                    GrandExchange.exchangeConnection.listItemsOnExchange(((EntityPlayerMP) sender),args[1]);
                }
            } else if ("setitem".equals(args[0]))
            {
                if (args.length == 3)
                {
                    int slot = Integer.parseInt(args[1]);
                    GrandExchange.exchangeConnection.offerItemToExchange(((EntityPlayerMP) sender),((EntityPlayerMP) sender).getHeldItemMainhand(),slot,args[2]);
                }
            } else if ("getitem".equals(args[0]))
            {
                if (args.length == 3)
                {
                    int slot = Integer.parseInt(args[1]);
                    GrandExchange.exchangeConnection.requestItemFromExchange(((EntityPlayerMP) sender),slot,args[2]);
                }
            } else if ("client".equals(args[0]))
            {
                GrandExchange.exchangeConnection.sendClientObject(((EntityPlayerMP) sender));
            } else if ("echo".equals(args[0]))
            {
                //GrandExchange.exchangeConnection.sendEchoOnChannel((EntityPlayerMP) sender);
            } else if ("sell".equals(args[0]))
            {
                GrandExchange.exchangeConnection.sellItemToServer((EntityPlayerMP) sender,((EntityPlayerMP) sender).getHeldItemMainhand());
            } else if ("buy".equals(args[0]))
            {
                String resource;
                int quantity;
                if(args.length==3)
                {
                    resource = args[1];
                    quantity = Integer.parseInt(args[2]);
                    GrandExchange.exchangeConnection.buyItemFromServer((EntityPlayerMP) sender,resource,quantity);
                }
                else
                {
                    System.out.println("Error with command.");
                }
            } else if ("auth".equals(args[0]))
            {
                GrandExchange.exchangeConnection.authPlayerToServer(((EntityPlayerMP) sender));
            } else if ("offer".equals(args[0]))
            {
                GrandExchange.exchangeConnection.offerItemToMarketPlace(((EntityPlayerMP) sender), ((EntityPlayerMP) sender).getHeldItemMainhand(), Float.valueOf(args[1]));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }

    @Override
    public int compareTo(ICommand o)
    {
        return 0;
    }
}
