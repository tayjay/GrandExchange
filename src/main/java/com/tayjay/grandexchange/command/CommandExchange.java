package com.tayjay.grandexchange.command;

import com.tayjay.grandexchange.GrandExchange;
import com.tayjay.grandexchange.external.TaskTest;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
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
                if (GrandExchange.exchangeConnection == null)
                {
                    System.out.println("No exchange connection.");
                }
                if(args.length<2 || args[1]==null)
                {
                    System.out.println("Missing last argument.");
                }
                GrandExchange.exchangeConnection.getPlayerFromExchange(((EntityPlayerMP) sender),args[1], ((EntityPlayerMP) sender).getUniqueID());
                //sender.addChatMessage(new TextComponentString(args[1]));
            } else if ("ping".equals(args[0]))
            {
                sender.addChatMessage(new TextComponentString("Sending a ping!"));
                new TaskTest().start();
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
