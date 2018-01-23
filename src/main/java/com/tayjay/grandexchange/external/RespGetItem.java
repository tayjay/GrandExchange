package com.tayjay.grandexchange.external;

import com.tayjay.grandexchange.external.packets.ExchangePacket;

/**
 * Created by tayjay on 2018-01-18.
 */
public class RespGetItem extends ExchangePacket
{
    String disp_name,reg_name,mod_name,mc_version,nbt_string;
    public RespGetItem()
    {
        super(3);
    }

    @Override
    public void processPacket(String packet)
    {

    }

    @Override
    public String createPacket()
    {
        return null;
    }
}
