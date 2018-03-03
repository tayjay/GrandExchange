package com.tayjay.grandexchange;

import com.google.common.base.Throwables;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.tayjay.grandexchange.command.CommandExchange;
import com.tayjay.grandexchange.external.ExchangeConnection;
import com.tayjay.grandexchange.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.net.Proxy;
import java.util.Map;

/**
 * Created by tayjay on 2018-01-14.
 */
@Mod(modid = GrandExchange.modid)
public class GrandExchange
{
    public static final String modid = "grandexchange";

    public static ExchangeConnection exchangeConnection;
    //public static ExClientNetHandler netHandler;

    @Mod.Instance
    public static GrandExchange INSTANCE;

    @SidedProxy(serverSide = "com.tayjay.grandexchange.proxy.CommonProxy",clientSide = "com.tayjay.grandexchange.proxy.ClientProxy")
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
        //GrandExchange.netHandler = new ExchangeClientHandler();
        System.out.println("Refreshing Exchange Connection...");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    /*private void attemptLogin(Map<String, String> argMap) {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername((String)argMap.get("username"));
        auth.setPassword((String)argMap.get("password"));
        argMap.put("password", (Object)null);

        try {
            auth.logIn();
        } catch (AuthenticationException var4) {
            LOGGER.error("-- Login failed!  " + var4.getMessage());
            Throwables.propagate(var4);
            return;
        }

        LOGGER.info("Login Succesful!");
        argMap.put("accessToken", auth.getAuthenticatedToken());
        argMap.put("uuid", auth.getSelectedProfile().getId().toString().replace("-", ""));
        argMap.put("username", auth.getSelectedProfile().getName());
        argMap.put("userType", auth.getUserType().getName());
        argMap.put("userProperties", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().toJson(auth.getUserProperties()));
    }*/
}
