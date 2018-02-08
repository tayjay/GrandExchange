package com.tayjay.grandexchange.external;

import com.google.gson.JsonObject;
import com.tayjay.gecommon.Ref;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2018-01-18.
 */
public class ExchangeItem
{
    public String disp_name,reg_name,mod_name,mc_version,nbt_string;
    public NBTTagCompound item_nbt;
    int slot;

    public ExchangeItem(ItemStack stack)
    {
        item_nbt = stack.serializeNBT();
        nbt_string = item_nbt.toString();
        disp_name = stack.getDisplayName();
        reg_name = stack.getItem().getRegistryName().getResourcePath();
        mod_name = stack.getItem().getRegistryName().getResourceDomain();
        mc_version = MinecraftForge.MC_VERSION;
    }

    public ExchangeItem(JsonObject object,int slot)
    {
        disp_name = object.get(Ref.DISPLAY_NAME).getAsString();
        reg_name = object.get(Ref.REGISTRY_NAME).getAsString();
        mod_name = object.get(Ref.MOD_NAME).getAsString();
        mc_version = object.get(Ref.MC_VERSION).getAsString();
        nbt_string = object.get(Ref.ITEM_NBT).getAsString();
        try
        {
            item_nbt = JsonToNBT.getTagFromJson(nbt_string);
        } catch (NBTException e)
        {
            e.printStackTrace();
            item_nbt = null;
        }
    }

    public ExchangeItem(JsonObject object)
    {
        this(object, -1);
    }

    public void setSlot(int slot)
    {
        this.slot = slot;
    }

    public String toString()
    {
        JsonObject object = new JsonObject();
        object.addProperty(Ref.DISPLAY_NAME,disp_name);
        object.addProperty(Ref.REGISTRY_NAME,reg_name);
        object.addProperty(Ref.MOD_NAME,mod_name);
        object.addProperty(Ref.MC_VERSION,mc_version);
        object.addProperty(Ref.ITEM_NBT,nbt_string);

        //String returning = "{\"disp_name\":\"%s\",\"reg_name\":\"%s\",\"mod_name\":\"%s\",\"mc_version\":\"%s\",\"nbt_string\":\"%s\"}";

        return object.toString();
    }

    public JsonObject getAsJsonObject()
    {
        JsonObject object = new JsonObject();
        object.addProperty(Ref.DISPLAY_NAME,disp_name);
        object.addProperty(Ref.REGISTRY_NAME,reg_name);
        object.addProperty(Ref.MOD_NAME,mod_name);
        object.addProperty(Ref.MC_VERSION,mc_version);
        object.addProperty(Ref.ITEM_NBT,nbt_string);

        //String returning = "{\"disp_name\":\"%s\",\"reg_name\":\"%s\",\"mod_name\":\"%s\",\"mc_version\":\"%s\",\"nbt_string\":\"%s\"}";

        return object;
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

    public ITextComponent getChatComponent()
    {
        TextComponentString chat = new TextComponentString((slot==-1?"":"Slot #"+slot+": ")+"["+disp_name+"]");
        if(createItemStack()!=null)
        {
            chat.getStyle().setColor(TextFormatting.DARK_GREEN);
            chat.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new TextComponentString(nbt_string)));
            chat.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/ge getitem "+slot));

        } else{

            chat.getStyle().setColor(TextFormatting.DARK_RED);
            chat.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(disp_name + "\n" + reg_name + "\n" + mod_name + "\n" + mc_version+"\nInvalid Item")));//item.item_nbt.toString()
            chat.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/ge setitem "+slot));
        }
        return chat;
    }
}
