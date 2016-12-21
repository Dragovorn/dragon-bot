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
import com.dragovorn.dragonbot.bot.Bot;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class BotTask implements Runnable, ScheduledTask {

    private final BotScheduler scheduler;

    private final int id;

    private final long delay;
    private final long period;

    private final BotPlugin owner;

    private final Runnable task;

    private final AtomicBoolean running = new AtomicBoolean(true);

    public BotTask(BotScheduler scheduler, int id, BotPlugin owner, Runnable task, long delay, long period, TimeUnit unit) {
        this.scheduler = scheduler;
        this.id = id;
        this.owner = owner;
        this.task = task;
        this.delay = unit.toMillis(delay);
        this.period = unit.toMillis(period);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public BotPlugin getOwner() {
        return this.owner;
    }

    @Override
    public Runnable getTask() {
        return this.task;
    }

    @Override
    public void cancel() {
        boolean wasRunning = this.running.getAndSet(false);

        if (wasRunning) {
            this.scheduler.cancelTask(this);
        }
    }

    @Override
    public void run() {
        if (this.delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        while (this.running.get()) {
            try {
                this.task.run();
            } catch (Throwable throwable) {
                Bot.getInstance().getLogger().log(Level.SEVERE, String.format("Task %s encountered an exception", this.owner.getInfo().getName() + "." + this.id), throwable);
            }

            /* Tasks with 0 or less periods will only execute once */
            if (this.period <= 0) {
                break;
            }

            try {
                Thread.sleep(this.period);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        cancel();
    }
}
