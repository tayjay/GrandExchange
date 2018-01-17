package com.tayjay.grandexchange.handler;

import com.tayjay.grandexchange.GrandExchange;
import com.tayjay.grandexchange.external.ExchangeConnection;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2018-01-14.
 */
public class ServerHandler
{

    @SubscribeEvent
    public void tickServer(TickEvent.ServerTickEvent event)
    {
        if (GrandExchange.exchangeConnection != null)
        {
            GrandExchange.exchangeConnection.updateTasksOnGameLoop();
        }
    }
}
