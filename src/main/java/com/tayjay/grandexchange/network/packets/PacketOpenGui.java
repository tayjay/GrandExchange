package com.tayjay.grandexchange.network.packets;

import com.tayjay.grandexchange.GrandExchange;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2018-02-12.
 */
public class PacketOpenGui extends PacketRunnable<PacketOpenGui>
{
    private int guiId;

    public PacketOpenGui()
    {

    }

    public PacketOpenGui(int guiId)
    {
        this.guiId = guiId;
    }

    @Override
    public void handleServer(PacketOpenGui message, MessageContext ctx)
    {
        ctx.getServerHandler().playerEntity.openGui(GrandExchange.INSTANCE,message.guiId,ctx.getServerHandler().playerEntity.worldObj,0,0,0);
    }

    @Override
    public void handleClient(PacketOpenGui message, MessageContext ctx)
    {
        //NOOP
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.guiId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.guiId);
    }
}
