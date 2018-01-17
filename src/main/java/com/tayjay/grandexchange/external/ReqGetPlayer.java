package com.tayjay.grandexchange.external;

import java.util.UUID;

/**
 * Created by tayjay on 2018-01-14.
 * Sent from client to see if a player exists in the Exchange Database
 */
public class ReqGetPlayer extends ExchangePacket
{
    String name,uuid;

    public ReqGetPlayer(String nameIn,String uuidIn)
    {
        super(0);
        this.name = nameIn;
        this.uuid = uuidIn;
    }

    @Override
    public void processPacket(String packet)
    {
        //Create a new thread and make new connection to exchange
    }

    @Override
    public String createPacket()
    {
        return "opcode="+opcode+"&name="+name+"&uuid="+uuid;
    }
}
