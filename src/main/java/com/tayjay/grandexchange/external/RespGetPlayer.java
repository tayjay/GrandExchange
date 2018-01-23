package com.tayjay.grandexchange.external;

import com.google.gson.*;
import com.tayjay.grandexchange.external.packets.ExchangePacket;

/**
 * Created by tayjay on 2018-01-14.
 * Response created from ReqGetPlayer.
 * Gets whether
 */
public class RespGetPlayer extends ExchangePacket
{
    boolean exists;
    int player_id;
    String player_name;

    public RespGetPlayer()
    {
        super(1);
    }

    public RespGetPlayer(String packetIn)
    {
        this();
        processPacket(packetIn);
    }

    @Override
    public void processPacket(String packet)
    {
        //Parse response from server

        try
        {
            System.out.println("Exchange Packet Received: " + packet);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(packet).getAsJsonObject();
            JsonElement opcode = jsonObject.get("opcode");
            opcode.getAsInt();
        } catch (JsonSyntaxException ex)
        {
            ex.printStackTrace();
            opcode = 1;
            player_id = -1;
            player_name = "null";
        }
        //... Continue

    }


    @Override
    public String createPacket()
    {
        return null;
    }
}
