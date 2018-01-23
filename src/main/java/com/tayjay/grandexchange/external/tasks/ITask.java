package com.tayjay.grandexchange.external.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by tayjay on 2018-01-16.
 */
public interface ITask<T>
{
    public void start();

    public void update();

    public boolean isDone();

    public void finish();

    public T output();

    public EntityPlayerMP getRequester();
}
