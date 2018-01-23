package com.tayjay.grandexchange.external.tasks;

import net.minecraft.entity.player.EntityPlayerMP;

import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by tayjay on 2018-01-21.
 */
public abstract class TaskBase<T> implements ITask
{
    protected Future<T> output;
    protected EntityPlayerMP requester;

    public TaskBase(EntityPlayerMP requester)
    {
        this.requester = requester;
    }

    protected void startThread()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        this.output = executor.submit(new Callable<T>()
        {
            @Override
            public T call() throws Exception
            {
                return runInThread();
            }
        });
    }

    /**
     * This will be ran out of sync from the game world, inside the call method of another thread
     * DO NOT ACCESS ANYTHING ON MC SIDE!!!
     * @return
     */
    protected abstract T runInThread();



    @Override
    public T output()
    {
        if(this.isDone())
        {
            try
            {
                return output.get();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }



    @Override
    public EntityPlayerMP getRequester()
    {
        return requester;
    }

    @Override
    public boolean isDone()
    {
        return output != null && output.isDone();
    }
}
