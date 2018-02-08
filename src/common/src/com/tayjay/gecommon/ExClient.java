package com.tayjay.gecommon;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by tayjay on 2018-02-07.
 */
public class ExClient implements Serializable
{
    private String username, playerIP;
    private UUID uuid;
    //transient WorldServer worldServer;
    //transient MinecraftServer server;
    private int hash;
    private boolean isCheat, isCreative, isOnline;

    public ExClient()
    {

    }

    public ExClient(String username, String playerIP, UUID uuid, boolean isCheat, boolean isCreative, boolean isOnline, int hash)
    {
        this.playerIP = playerIP;//IP for actual player, socket will have ip from server, if separate
        this.username = username;
        this.uuid = uuid;
        this.isCheat = isCheat;
        this.isCreative = isCreative;
        this.isOnline = isOnline;
        this.hash = hash;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPlayerIP()
    {
        return playerIP;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public int getHash()
    {
        return hash;
    }

    public boolean isCheat()
    {
        return isCheat;
    }

    public boolean isCreative()
    {
        return isCreative;
    }

    public boolean isOnline()
    {
        return isOnline;
    }

    @Override
    public String toString()
    {
        return "ExClient [username=" + username + ", uuid=" + uuid.toString() +", Client IP="+playerIP+ ", isCheat=" + isCheat + ", isCreative=" + isCreative + ", isOnline=" + isOnline + "]";
    }
}
