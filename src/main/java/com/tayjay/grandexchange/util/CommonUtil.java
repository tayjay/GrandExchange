package com.tayjay.grandexchange.util;

import com.tayjay.gecommon.ExClient;
import com.tayjay.gecommon.ExchangeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.UUID;

/**
 * Created by tayjay on 2018-02-07.
 */
public class CommonUtil
{
    public static ExClient createClient(EntityPlayerMP player)
    {
        WorldServer worldServer = player.getServerWorld();
        MinecraftServer server = player.mcServer;
        String playerIP = player.getPlayerIP();
        String username = player.getName();
        UUID uuid = player.getUniqueID();
        boolean isCheat = worldServer.getWorldInfo().areCommandsAllowed();
        boolean isCreative = player.isCreative();
        boolean isOnline = player.getGameProfile().isComplete()&&!server.isServerInOnlineMode();
        int hash = worldServer.hashCode()+username.hashCode()+uuid.hashCode();

        return new ExClient(username, playerIP, uuid, isCheat, isCreative, isOnline, hash);
    }

    public static ItemStack createItemStackFromExhange(ExchangeItem item)
    {
        //Validate that this item is valid in this world
        String disp_name= item.disp_name;
        String reg_name = item.reg_name;
        String mod_name = item.mod_name;
        String mc_version = item.mc_version;
        String nbt_string = item.nbt_string;

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
        Item item1 = GameRegistry.findItem(mod_name, reg_name);
        //Item item = GameData.getItemRegistry().getObject(new ResourceLocation(mod_name+":"+reg_name));
        if (item1 == null)
        {
            boolean modValid=false;
            //This item does not exist
            for (ModContainer mod : Loader.instance().getModList())
            {
                if (mod.getModId().equals(mod_name))
                {
                    //The mod for it is present
                    modValid = true;
                    break;
                }
            }
            if(!modValid)
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

    public static ExchangeItem createExchangeItem(ItemStack stack)
    {
        NBTTagCompound item_nbt = stack.serializeNBT();
        String nbt_string = item_nbt.toString();
        String disp_name = stack.getDisplayName();
        String reg_name = stack.getItem().getRegistryName().getResourcePath();
        String mod_name = stack.getItem().getRegistryName().getResourceDomain();
        String mc_version = MinecraftForge.MC_VERSION;
        byte stacksize = (byte) stack.stackSize;
        short metadata = (short) stack.getMetadata();
        byte rarity = ((byte) stack.getRarity().ordinal());
        ExchangeItem exchangeItem = new ExchangeItem(disp_name,reg_name,mod_name,mc_version,stacksize, metadata,rarity,stack.getTooltip(Minecraft.getMinecraft().thePlayer,true),nbt_string);
        return exchangeItem;
    }
}
