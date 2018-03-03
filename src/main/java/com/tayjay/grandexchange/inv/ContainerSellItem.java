package com.tayjay.grandexchange.inv;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2018-02-12.
 */
public class ContainerSellItem extends Container
{
    public ContainerSellItem(IInventory playerInv)
    {
        this.addSlotToContainer(new Slot(new InventorySellItem(), 0, 10, 10));

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x)
        {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack previous = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (index == 0)
            {
                //From selling slot
                if (!this.mergeItemStack(current, 1, 37, true))
                {
                    return null;
                }else{
                    if (!this.mergeItemStack(current, 0, 0, false))
                    {
                        return null;
                    }
                }
            }

            if (current.stackSize == 0)
            {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (current.stackSize == previous.stackSize)
            {
                return null;
            }
            slot.onPickupFromSlot(playerIn,current);
        }
        return previous;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        //On close drop item in the gui so it isn't lost
        playerIn.dropItem(this.inventoryItemStacks.get(0), false);
    }
}
