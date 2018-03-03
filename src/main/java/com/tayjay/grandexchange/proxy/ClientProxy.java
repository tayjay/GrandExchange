package com.tayjay.grandexchange.proxy;

import com.tayjay.grandexchange.handler.KeyInputHandler;
import com.tayjay.grandexchange.util.ImageHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * Created by tayjay on 2018-01-14.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        registerKeyBindings();
    }

    public void registerKeyBindings()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        for(KeyInputHandler.Keybindings key : KeyInputHandler.Keybindings.values())
        {
            ClientRegistry.registerKeyBinding(key.getKeyBinding());
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        ImageHelper.init();
    }
}
