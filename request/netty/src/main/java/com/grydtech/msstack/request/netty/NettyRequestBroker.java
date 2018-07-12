package com.grydtech.msstack.request.netty;

import com.grydtech.msstack.core.annotation.ServerComponent;
import com.grydtech.msstack.core.component.RequestBroker;
import com.grydtech.msstack.request.netty.routing.Router;


@ServerComponent
public class NettyRequestBroker extends RequestBroker {

    @Override
    public void start() throws Exception {
        final Router router = Router.build(getClasses());
        final NettyServer server = new NettyServer(this.getPort())
                .setRouter(router)
                .build();
        server.run();
    }
}