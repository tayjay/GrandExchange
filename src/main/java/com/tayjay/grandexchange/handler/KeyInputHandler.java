package com.tayjay.grandexchange.handler;

import com.tayjay.grandexchange.GrandExchange;
import com.tayjay.grandexchange.client.gui.ModGuiHandler;
import com.tayjay.grandexchange.network.NetworkHandler;
import com.tayjay.grandexchange.network.packets.PacketOpenGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;



/**
 * Created by tayjay on 2018-02-12.
 */
public class KeyInputHandler
{

    private Keybindings getPressedKey()
    {
        for (Keybindings key : Keybindings.values())
        {
            if (key.isPressed())
            {
                return key;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Keybindings key = getPressedKey();

        if (key != null)
        {
            switch (key)
            {
                case SELL_ITEM:
                    //Minecraft.getMinecraft().thePlayer.openGui(GrandExchange.INSTANCE, ModGuiHandler.SELL_ITEM_GUI, Minecraft.getMinecraft().theWorld, 0, 0, 0);
                    NetworkHandler.sendToServer(new PacketOpenGui(ModGuiHandler.SELL_ITEM_GUI));
                    break;
                default:
                    break;
            }
        }
    }


    public static enum Keybindings
    {
        SELL_ITEM("key.grandexchange.sell_item", Keyboard.KEY_I);

        private final KeyBinding keyBinding;

        Keybindings(String keyName, int defaultKeyCode)
        {
            keyBinding = new KeyBinding(keyName, defaultKeyCode, "keys.grandexchange.category");
        }

        public KeyBinding getKeyBinding()
        {
            return keyBinding;
        }

        public boolean isPressed()
        {
            return keyBinding.isPressed();
        }
    }
}
