package com.tayjay.grandexchange.client.gui;

import com.tayjay.grandexchange.inv.ContainerSellItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by tayjay on 2018-02-12.
 */
public class ModGuiHandler implements IGuiHandler
{
    public static final int SELL_ITEM_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == SELL_ITEM_GUI)
        {
            return new ContainerSellItem(player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == SELL_ITEM_GUI)
        {
            return new GuiSellItem(player.inventory);
        }
        return null;
    }
}
