/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.bot;

import java.util.Vector;

class Queue {

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