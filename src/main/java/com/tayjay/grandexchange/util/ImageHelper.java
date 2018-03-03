package com.tayjay.grandexchange.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.EXTFramebufferObject.*;

public class ImageHelper
{
    static protected int framebufferID;
    static ModelManager modelManager;
    static ItemColors itemColors;

    static protected int iconWidth = 32;
    static protected int iconHeight = 32;

    public static void init()
    {
        modelManager = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "modelManager");
        itemColors = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "itemColors");
        //renderItem = new RenderItem(Minecraft.getMinecraft().getTextureManager(),new ModelManager(Minecraft.getMinecraft().getTextureMapBlocks()),new ItemColors());
        renderItem = Minecraft.getMinecraft().getRenderItem();
    }



    protected static RenderItem renderItem;

    public static void renderItem(ItemStack itemStack)
    {

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);

        GL11.glViewport(0, 0, iconWidth, iconHeight);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        GL11.glClearColor(1, 1, 1, 1f);


        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        //initFBO();
        renderItem.renderItemIntoGUI(itemStack, 0, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glFlush();

        GL11.glReadBuffer(GL11.GL_FRONT);

        int width = iconWidth;
        int height = iconHeight;
        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        saveImage(itemStack.getDisplayName(), width, height, bpp, buffer);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }

    static protected void saveImage(String name, int width, int height, int bpp, ByteBuffer buffer)
    {
        File file = new File("C:\\Users\\tayjm_000\\Desktop\\item.png"); // The file to save to.
        String format = "PNG";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width * height * bpp; x++)
        {
            byte value = buffer.get(x);
            x = x;
        }
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        try
        {
            ImageIO.write(image, format, file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int[] itemIconToIntArray(ItemStack stack)
    {
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer);

        int[][] textureArray = model.getParticleTexture().getFrameTextureData(0);
        int[][] textureMipMap = TextureUtil.generateMipmapData(2, 16, textureArray);

        int[] icon = new int[textureArray[0].length];

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                //int rgb = (int) textureArray[0][y + (x * 16)] << 24 | (int) textureArray[0][y + (x * 16)] << 16 | (int) textureArray[0][y + (x * 16)] << 8 | (int) textureArray[0][y + (x * 16)];
                //image.setRGB(y, x, rgb);
                if(textureMipMap[0][y + (x * 16)]!=0 && textureMipMap[0][y + (x * 16)]>>24!=0)
                    icon[y + (x * 16)] = textureMipMap[0][y + (x * 16)];
            }
        }

        List<BakedQuad> quads = model.getQuads(null, null, 0);

        for(int i=0;i<quads.size();i++)
        {
            int tint = quads.get(i).getFormat().getOffset(0);
            int[][] textureArray1 = quads.get(i).getSprite().getFrameTextureData(0);
            for (int x = 0; x < 16; x++)
            {
                for (int y = 0; y < 16; y++)
                {

                    if(textureArray1[0][y + (x * 16)]!=0 && textureArray1[0][y + (x * 16)]>>24!=0)
                    {
                        //int rgb = (int) textureArray[0][y + (x * 16)] << 16 | (int) textureArray[0][y + (x * 16)] << 8 | (int) textureArray[0][y + (x * 16)];
                        //image.setRGB(y, x, rgb);
                        icon[y + (x * 16)] = textureArray1[0][y + (x * 16)];
                    }

                }
            }
        }

        /*for (int x = 0; x < 16; x++)
        {
                if(textureMipMap[0][x]!=0 && textureMipMap[0][x]>>24!=0)
                    icon[x] = textureMipMap[0][x];
        }

        List<BakedQuad> quads = model.getQuads(null, null, 0);

        for(int i=0;i<quads.size();i++)
        {
            int tint = quads.get(i).getFormat().getOffset(0);
            int[][] textureArray1 = quads.get(i).getSprite().getFrameTextureData(0);
            for (int x = 0; x < 16; x++)
            {
                if(textureArray1[0][x]!=0 && textureArray1[0][x]>>24!=0)
                    icon[x] = textureArray1[0][x];
            }
        }*/

        return icon;
    }


}
