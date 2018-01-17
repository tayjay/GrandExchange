package com.tayjay.grandexchange.proxy;

import com.tayjay.grandexchange.handler.ServerHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by tayjay on 2018-01-14.
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ServerHandler());
    }
}
