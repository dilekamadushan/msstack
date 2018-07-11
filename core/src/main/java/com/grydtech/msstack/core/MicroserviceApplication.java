package com.grydtech.msstack.core;

import com.grydtech.msstack.core.component.EventBroker;
import com.grydtech.msstack.core.component.RequestBroker;
import com.grydtech.msstack.core.handler.EventHandler;
import com.grydtech.msstack.core.handler.RequestHandler;
import com.grydtech.msstack.util.ClassPathScanner;

import java.util.Set;

/**
 * Base class for a Microservice Application created using MSStack.
 * All applications should have one class that inherits this in the module root
 */
public abstract class MicroserviceApplication {

    private final Set<Class<? extends RequestHandler>> requestHandlers;
    private final Set<Class<? extends EventHandler>> eventHandlers;

    public MicroserviceApplication() {
        ClassPathScanner classPathScanner = new ClassPathScanner(getClass().getPackage().getName());
        eventHandlers = classPathScanner.getSubTypesOf(EventHandler.class);
        requestHandlers = classPathScanner.getSubTypesOf(RequestHandler.class);
    }

    /**
     * Returns the set of request handler classes in the classpath
     *
     * @return Set of Request Handler Classes
     */
    public final Set<Class<? extends RequestHandler>> getRequestHandlers() {
        return requestHandlers;
    }

    /**
     * Returns the set of event handler classes in the classpath
     *
     * @return Set of Event Handler Classes
     */
    public final Set<Class<? extends EventHandler>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Runs the Microservice
     *
     * @throws Exception If an exception occurs that is not handled within the framework
     */
    public final void run() throws Exception {

        // Brokers
        final EventBroker eventBroker = EventBroker.getInstance();
        final RequestBroker requestBroker = RequestBroker.getInstance();

        // Register Event Handlers
        this.getEventHandlers().forEach(eventBroker::subscribe);

        // Register Request Handlers
        this.getRequestHandlers().forEach(requestBroker::subscribe);

        // Start Event Broker
        EventBroker.getInstance().start();

        // Start Request Broker
        RequestBroker.getInstance().start();

        // Register Service

        // Optional
    }
}
