package com.tayjay.grandexchange.external;

import com.tayjay.grandexchange.GrandExchange;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by tayjay on 2018-01-14.
 * Handle connection to Database
 */
public class ExchangeConnection
{
    List<ITask> tasks;

    public ExchangeConnection()
    {
        this.tasks = new ArrayList<ITask>();
    }

    public void updateTasksOnGameLoop()
    {
        Iterator<ITask> it = tasks.iterator();
        ITask task;
        while(it.hasNext())
        {
            task = it.next();
            task.update();
            if(task.isDone())
                it.remove();
        }
    }

    public void registerTask(ITask task)
    {
        tasks.add(task);
        task.start();
    }

    public void getPlayerFromExchange(EntityPlayerMP requester, String name, UUID uuid)
    {
        registerTask(new TaskGetPlayer(requester,name,uuid));
    }

    /*public static boolean sendExchangePacket(String sending)
    {
        //String sending = "opcode=1&d_name=Golden Sword&r_name=golden_sword&m_name=minecraft&nbt={id:\"minecraft:golden_sword\",Count:1b,tag:{ench:[0:{lvl:9001s,id:16s},1:{lvl:9001s,id:19s}],RepairCost:1},Damage:0s}";
        try
        {
            URL url = new URL("https://tayjaydb.000webhostapp.com/itemdbconnection.php");//itemdbconnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] bytestosend = sending.getBytes("UTF-8");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(bytestosend.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(bytestosend);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));


            for (int c; (c = in.read()) >= 0;)
                System.out.print((char)c);


        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int checkPlayerExists(String name, UUID uuid)
    {
        String packet = "opcode=0&name="+name+"&uuid="+uuid.toString();

    }

    public void addToSendQueue(String packet)
    {

    }*/


}
