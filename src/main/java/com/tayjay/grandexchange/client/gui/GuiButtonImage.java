package com.tayjay.grandexchange.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by tayjay on 2018-02-12.
 */
public class GuiButtonImage extends GuiButton
{
    GuiSellItem parent;

    public GuiButtonImage(int buttonId, int x, int y, String buttonText,GuiSellItem parent)
    {
        super(buttonId, x, y, buttonText);
        this.parent = parent;
    }
}
