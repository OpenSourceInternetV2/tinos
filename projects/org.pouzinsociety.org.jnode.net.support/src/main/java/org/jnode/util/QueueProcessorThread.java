/*
 * $Id: QueueProcessorThread.java 4159 2008-05-30 16:15:41Z lsantha $
 *
 * JNode.org
 * Copyright (C) 2003-2006 JNode.org
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
/*
 * 2008 - 2010 (c) Waterford Institute of Technology
 *		   TSSG, EU ICT 4WARD
 *
 * 2010 (c) Pouzin Society
 *   - Forked from EU ICT 4WARD Open Source Distribution.
 *   - Organisation Strings updated to reflect fork.
 *
 *
 * Author        : pphelan(at)tssg.org
 *
 * Modifications : Changes to port JNode code base to OSGi platform.
 *                 - log4j -> apache commons.
 *
 */
package org.jnode.util;

import org.apache.commons.logging.*;

/**
 * @author epr
 */
public class QueueProcessorThread<T> extends Thread {
    private static final Log log = LogFactory.getLog(QueueProcessorThread.class);

    /**
     * The queue i'm processing
     */
    private final Queue<T> queue;

    /**
     * The actual processor
     */
    private final QueueProcessor<T> processor;

    private boolean stop;

    /**
     * Create a new instance
     *
     * @param name
     * @param queue
     * @param processor
     */
    public QueueProcessorThread(String name, Queue<T> queue,
                                QueueProcessor<T> processor) {
        super(name);
        this.queue = queue;
        this.processor = processor;
        this.stop = false;
    }

    /**
     * Create a new instance. A new queue is automatically created.
     *
     * @param name
     * @param processor
     * @see #getQueue()
     */
    public QueueProcessorThread(String name, QueueProcessor<T> processor) {
        this(name, new Queue<T>(), processor);
    }

    /**
     * Stop the processor
     */
    public void stopProcessor() {
        this.stop = true;
        this.queue.close();
    }

    /**
     * Handle an exception thrown during the processing of the object.
     *
     * @param ex
     */
    protected void handleException(Exception ex) {
        log.error("Exception in QueueProcessor: " + getName(), ex);
    }

    /**
     * Handle an exception thrown during the processing of the object.
     *
     * @param ex
     */
    protected void handleError(Error ex) {
        log.error("Error in QueueProcessor: " + getName(), ex);
    }

    /**
     * Thread runner
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (!stop) {
            try {
                final T object = queue.get(false);
                if (object != null) {
                    processor.process(object);
                }
            } catch (Exception ex) {
                handleException(ex);
            } catch (Error ex) {
                handleError(ex);
            }
        }
    }

    /**
     * Gets this queue this thread works on.
     *
     * @return The queue
     */
    public Queue<T> getQueue() {
        return queue;
    }
}
