package com.tayjay.grandexchange;

import com.tayjay.grandexchange.command.CommandExchange;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by tayjay on 2018-01-14.
 */
@Mod(modid = GrandExchange.modid)
public class GrandExchange
{
    public static final String modid = "grandexchange";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandExchange());
    }
}
