package com.tayjay.grandexchange.external;

import com.tayjay.grandexchange.external.tasks.ITask;
import com.tayjay.grandexchange.external.tasks.TaskGetPlayer;
import com.tayjay.grandexchange.external.tasks.TaskTest;
import net.minecraft.entity.player.EntityPlayerMP;

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

    public void getPlayerFromExchange(EntityPlayerMP requester, String name)//, UUID uuid
    {
        registerTask(new TaskGetPlayer(requester,name));
    }

    public void sendTestToExchange(EntityPlayerMP requester, String toSend)
    {
        registerTask(new TaskTest(requester, toSend));
    }


}
