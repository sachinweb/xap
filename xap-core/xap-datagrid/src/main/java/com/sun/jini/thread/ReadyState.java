/*
 * 
 * Copyright 2005 Sun Microsystems, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.sun.jini.thread;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;

/**
 * Utility class used to prevent access to a service before it has completed its initialization or
 * after it starts to shutdown.  Each public entry point to the service should call
 * <code>check</code> or <code>shutdown</code>, and initialization should call <code>ready</code>
 * when the service is ready to use.
 *
 * @author Sun Microsystems, Inc.
 * @since 2.1
 */
@com.gigaspaces.api.InternalApi
public class ReadyState {
    // flag to indicate the service is initializing
    private static final int INITIALIZE = 0;
    // flag to indicate the service is ready to use
    private static final int READY = 1;
    // flag to indicate the service is shutting down
    private static final int SHUTDOWN = 2;

    private volatile int state = INITIALIZE;


    public boolean isReady() {
        return state == READY;
    }

    /**
     * Checks if the service is ready to use, waiting if it is initializing, and throwing
     * <code>NoSuchObjectException</code> if it is shutting down.  Note that the
     * <code>NoSuchObjectException</code> will be wrapped in a <code>RemoteExceptionWrapper</code>.
     */
    public void check() {
        while (true) {
            switch (state) {
                case INITIALIZE:
                    try {
                        synchronized (this) {
                            if (state == READY) {
                                return;
                            }
                            wait(1000);
                        }
                    } catch (InterruptedException e) {
                    }
                    break;
                case READY:
                    return;
                default:
                    throw new RemoteExceptionWrapper(new NoSuchObjectException(
                            "service is unavailable"));
            }
        }
    }

    /**
     * Marks the service ready for use.  This method should only be called from the code that
     * performs the service initialization, and it should only be called once.
     */
    public void ready() {
        switch (state) {
            case INITIALIZE:
                state = READY;
                synchronized (this) {
                    notifyAll();
                }
                break;
            default:
                throw new AssertionError("ready is only called when the"
                        + " service is in the INITIALIZE"
                        + " state");
        }
    }

    /**
     * Marks the service as shutting down, waiting if it is initializing, and throwing
     * <code>NoSuchObjectException</code> if it is already shutting down.  Note that the
     * <code>NoSuchObjectException</code> will be wrapped in a <code>RemoteExceptionWrapper</code>.
     */
    public void shutdown() {
        check();
        state = SHUTDOWN;
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * Wrapper used to prevent a <code>RemoteException</code> from being wrapped in a
     * <code>ServerException</code> by the RMI implementation.
     */
    private static class RemoteExceptionWrapper extends RuntimeException {
        /**
         * added for consistency; this will never be used
         */
        private static final long serialVersionUID = 1L;
        /**
         * the exception that will be written to the output stream
         */
        private final RemoteException wrapped;

        /**
         * Simple constructor
         */
        public RemoteExceptionWrapper(RemoteException wrapped) {
            this.wrapped = wrapped;
        }

        /**
         * returns the exception to marshal
         */
        private Object writeReplace() {
            return wrapped;
        }

        @Override
        public String toString() {
            return wrapped == null ? "" : wrapped.toString();
        }
    }
}
