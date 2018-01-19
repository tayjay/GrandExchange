package com.tayjay.grandexchange.external;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2018-01-18.
 */
public class ExchangeItem
{
    String disp_name,reg_name,mod_name,mc_version,nbt_string;
    NBTTagCompound item_nbt;

    public ExchangeItem(ItemStack stack)
    {
        item_nbt = stack.serializeNBT();
        nbt_string = item_nbt.toString();
        disp_name = stack.getDisplayName();
        reg_name = stack.getItem().getRegistryName().getResourcePath();
        mod_name = stack.getItem().getRegistryName().getResourceDomain();
        mc_version = MinecraftForge.MC_VERSION;
    }

    public ItemStack createItemStack()
    {
        //Validate that this item is valid in this world

        if(MinecraftForge.MC_VERSION.equals(mc_version))
        {
            //Same version of Minecraft
        }
        else
        {
            //Warn that they are different versions
        }
        NBTTagCompound tag = new NBTTagCompound();
        // taking inspireation from GameRegistry.makeItemStack()
        Item item = GameRegistry.findItem(mod_name, reg_name);
        //Item item = GameData.getItemRegistry().getObject(new ResourceLocation(mod_name+":"+reg_name));
        if (item == null)
        {
            //This item does not exist
            for (ModContainer mod : Loader.instance().getModList())
            {
                if (mod.getModId().equals(mod_name))
                {
                    //The mod for it is present
                    break;
                }
            }
            return null;
        }
        //Item is valid
        try
        {
            tag = JsonToNBT.getTagFromJson(nbt_string);
            return ItemStack.loadItemStackFromNBT(tag);

        } catch (NBTException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
