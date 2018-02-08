package com.tayjay.gecommon.packets;

import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.Ref;

/**
 * Created by tayjay on 2018-02-07.
 */
public class CPacketInit extends PacketBase
{
    ExClient client;
    Ref.RequestType requestType;//Taken from Ref, dictates what following packets will entail

    public CPacketInit(ExClient client,Ref.RequestType requestType)
    {
        this.client = client;
        this.requestType = requestType;
    }

    public ExClient getClient()
    {
        return client;
    }

    public Ref.RequestType getRequestType()
    {
        return requestType;
    }
}
