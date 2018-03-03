package com.tayjay.grandexchange.client.gui;

import com.google.gson.*;
import com.tayjay.gecommon.ExchangeItem;
import com.tayjay.grandexchange.command.CommandExchange;
import com.tayjay.grandexchange.inv.ContainerSellItem;
import com.tayjay.grandexchange.util.CommonUtil;
import com.tayjay.grandexchange.util.ImageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import scala.util.parsing.json.JSON;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tayjay on 2018-02-12.
 */
public class GuiSellItem extends GuiContainer
{
    public GuiSellItem(IInventory playerInv)
    {
        super(new ContainerSellItem(playerInv));

        this.xSize = 176;
        this.ySize = 166;
        //addButton(new GuiButtonImage(0,guiLeft+(xSize/2)-32,guiTop+80,"Take Image",this));
        //initGui();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(this.buttonEvaluate = new GuiButton(0, 10, 10, "Evaluate Item"));
    }

    GuiButton buttonImage,buttonEvaluate;


    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == this.buttonImage)
        {
            //this.takeItemPicture();
            ImageHelper.renderItem(this.inventorySlots.inventorySlots.get(0).getStack());
        }
        else if(button == this.buttonEvaluate)
        {

            ItemStack stack = inventorySlots.inventorySlots.get(0).getStack();
            ExchangeItem item = CommonUtil.createExchangeItem(stack);

            if (stack != null)
            {
                try
                {
                    sendChatMessage(stack.getDisplayName());

                    String nbtString = item.nbt_string;
                    System.out.println(nbtString);

                    /*JsonParser parser = new JsonParser();
                    JsonObject itemJSON = ((JsonObject) parser.parse(nbtString));*/

                    Map<Enchantment,Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                    Iterator<Map.Entry<Enchantment,Integer>> itr = enchantments.entrySet().iterator();
                    while (itr.hasNext())
                    {
                        Map.Entry<Enchantment,Integer> entry = itr.next();
                        sendChatMessage(entry.getKey().getName()+"-"+entry.getValue());
                    }



                } catch (Exception e)
                {
                    e.printStackTrace();
                }



            }

        }
        super.actionPerformed(button);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

        this.drawGradientRect(guiLeft,guiTop,guiLeft+xSize,guiTop+ySize, Color.GRAY.getRGB(),Color.GRAY.getRGB());
        //this.buttonList.get(0).drawButton(Minecraft.getMinecraft(),mouseX,mouseY);
        ItemStack stack = this.inventorySlots.inventorySlots.get(0).getStack();

        if (stack != null)
        {

            GlStateManager.pushMatrix();
            GlStateManager.translate(guiLeft+(xSize/2)-32,guiTop+24,0);
            this.drawGradientRect(0,0,48,48,Color.WHITE.getRGB(),Color.WHITE.getRGB());
            GlStateManager.scale(3,3,3);
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
            GlStateManager.popMatrix();

            ExchangeItem exchangeItem = CommonUtil.createExchangeItem(stack);
            GlStateManager.pushMatrix();


            this.drawHoveringText(exchangeItem.toStringList(),guiLeft+(xSize/2)+8,guiTop+24);
            GlStateManager.popMatrix();
        }


    }



}
