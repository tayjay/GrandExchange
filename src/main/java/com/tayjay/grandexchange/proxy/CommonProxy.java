package com.tayjay.grandexchange.proxy;

import com.tayjay.grandexchange.GrandExchange;
import com.tayjay.grandexchange.client.gui.ModGuiHandler;
import com.tayjay.grandexchange.handler.ServerHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by tayjay on 2018-01-14.
 */
public class CommonProxy
{
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(GrandExchange.INSTANCE, new ModGuiHandler());
        MinecraftForge.EVENT_BUS.register(new ServerHandler());
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
