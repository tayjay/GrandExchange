package com.tayjay.grandexchange.external;

/**
 * Created by tayjay on 2018-01-18.
 */
public class ReqGetItem extends ExchangePacket
{
    String name,uuid;
    int slot;

    public ReqGetItem(String name,String uuid,int slot)
    {
        super(2);
        this.name = name;
        this.uuid = uuid;
        this.slot = slot;
    }

    @Override
    public void processPacket(String packet)
    {

    }

    @Override
    public String createPacket()
    {
        return "opcode="+opcode+"&name="+name+"&uuid="+uuid+"&slot="+slot;
    }
}
