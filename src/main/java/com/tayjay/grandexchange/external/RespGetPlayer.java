package com.tayjay.grandexchange.external;

/**
 * Created by tayjay on 2018-01-14.
 * Response created from ReqGetPlayer.
 * Gets whether
 */
public class RespGetPlayer extends ExchangePacket
{
    boolean exists;
    int player_id;

    public RespGetPlayer()
    {
        super(1);
    }

    @Override
    public void processPacket(String packet)
    {
        //Parse response from server

    }


    @Override
    public String createPacket()
    {
        return null;
    }
}
