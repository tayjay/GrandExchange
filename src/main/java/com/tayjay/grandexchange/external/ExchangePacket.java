package com.tayjay.grandexchange.external;

/**
 * Created by tayjay on 2018-01-14.
 */
public abstract class ExchangePacket
{
    public static int current_packet = 0;
    public int opcode,packet_id;

    public ExchangePacket(int opcode)
    {
        this.opcode = opcode;
        packet_id = current_packet++;
    }

    public abstract void processPacket(String packet);

    public abstract String createPacket();

}
