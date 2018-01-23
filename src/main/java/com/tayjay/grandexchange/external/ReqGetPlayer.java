package com.tayjay.grandexchange.external;

import com.tayjay.grandexchange.external.packets.ExchangePacket;

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
        //noop
    }

    @Override
    public String createPacket()
    {
        return "opcode="+opcode+"&name="+name+"&uuid="+uuid;
    }
}
