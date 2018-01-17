package com.tayjay.grandexchange;

import com.tayjay.grandexchange.command.CommandExchange;
import com.tayjay.grandexchange.external.ExchangeConnection;
import com.tayjay.grandexchange.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
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

    public static ExchangeConnection exchangeConnection;

    @Mod.Instance
    public static GrandExchange INSTANCE;

    @SidedProxy(serverSide = "com.tayjay.grandexchange.proxy.ServerProxy",clientSide = "com.tayjay.grandexchange.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandExchange());
        GrandExchange.exchangeConnection = new ExchangeConnection();
        //Refresh connection on each start
        System.out.println("Refreshing Exchange Connection...");
    }
}
