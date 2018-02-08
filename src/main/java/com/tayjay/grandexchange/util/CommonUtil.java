package com.tayjay.grandexchange.util;

import com.tayjay.gecommon.ExClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.UUID;

/**
 * Created by tayjay on 2018-02-07.
 */
public class CommonUtil
{
    public static ExClient createClient(EntityPlayerMP player)
    {
        WorldServer worldServer = player.getServerWorld();
        MinecraftServer server = player.mcServer;
        String playerIP = player.getPlayerIP();
        String username = player.getName();
        UUID uuid = player.getUniqueID();
        boolean isCheat = worldServer.getWorldInfo().areCommandsAllowed();
        boolean isCreative = player.isCreative();
        boolean isOnline = player.getGameProfile().isComplete()&&!server.isServerInOnlineMode();
        int hash = worldServer.hashCode()+username.hashCode()+uuid.hashCode();

        return new ExClient(username, playerIP, uuid, isCheat, isCreative, isOnline, hash);
    }
}
