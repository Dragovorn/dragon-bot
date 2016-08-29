package com.dragovorn.dragonbot.bot;

import java.util.Vector;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:19 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Queue {

    private Vector queue = new Vector();

    public void add(Object object) {
        synchronized (queue) {
            queue.addElement(object);
            queue.notify();
        }
    }

    public void addFront(Object object) {
        synchronized (queue) {
            queue.insertElementAt(object, 0);
            queue.notify();
        }
    }

    public Object next() {
        Object object;

        synchronized (queue) {
            if (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException exception) {
                    return null;
                }
            }

            try {
                object = queue.firstElement();
                queue.removeElementAt(0);
            } catch (ArrayIndexOutOfBoundsException exception) {
                throw new InternalError("Race hazard in Queue Object");
            }
        }

        return object;
    }

    public boolean hasNext() {
        return (this.size() != 0);
    }

    public void clear() {
        synchronized (queue) {
            queue.removeAllElements();
        }
    }

    public int size() {
        return queue.size();
    }
}