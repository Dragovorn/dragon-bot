/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or
 *  substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *  PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.bot.scheduler;

import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BotScheduler implements Scheduler {

    private final Object lock;

    private final AtomicInteger taskCounter;

    private final TIntObjectMap<BotTask> tasks;

    private final Multimap<BotPlugin, BotTask> tasksByPlugin;

    private final Unsafe unsafe;

    public BotScheduler() {
        this.lock = new Object();
        this.taskCounter = new AtomicInteger();
        this.unsafe = BotPlugin::getExecutorService;
        this.tasks = TCollections.synchronizedMap(new TIntObjectHashMap<BotTask>());
        this.tasksByPlugin = Multimaps.synchronizedMultimap(HashMultimap.<BotPlugin, BotTask>create());
    }

    void cancelTask(BotTask task) {
        synchronized (this.lock) {
            this.tasks.remove(task.getId());
            this.tasksByPlugin.values().remove(task);
        }
    }

    @Override
    public void cancel(int id) {
        BotTask task = this.tasks.get(id);
        Preconditions.checkArgument(task != null, "No task with id %s", id);

        task.cancel();
    }

    @Override
    public void cancel(ScheduledTask task) {
        task.cancel();
    }

    @Override
    public int cancel(BotPlugin plugin) {
        Set<ScheduledTask> remove = new HashSet<>();

        for (ScheduledTask task : this.tasksByPlugin.get(plugin)) {
            remove.add(task);
        }

        for (ScheduledTask task : remove) {
            cancel(task);
        }

        return remove.size();
    }

    @Override
    public ScheduledTask runAsync(BotPlugin owner, Runnable task) {
        return schedule(owner, task, 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledTask schedule(BotPlugin owner, Runnable task, long delay, TimeUnit unit) {
        return schedule(owner, task, delay, 0, unit);
    }

    @Override
    public ScheduledTask schedule(BotPlugin owner, Runnable task, long delay, long period, TimeUnit unit) {
        Preconditions.checkNotNull(owner, "owner");
        Preconditions.checkNotNull(task, "task");

        BotTask prepared = new BotTask(this, this.taskCounter.getAndIncrement(), owner, task, delay, period, unit);

        synchronized (this.lock) {
            this.tasks.put(prepared.getId(), prepared);
            this.tasksByPlugin.put(owner, prepared);
        }

        owner.getExecutorService().execute(prepared);

        return prepared;
    }

    @Override
    public Unsafe unsafe() {
        return this.unsafe;
    }
}