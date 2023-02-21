package org.tvliz.handler;

import com.whirvis.jraknet.session.RakNetSession;

public class Client {
    RakNetSession session;

    public Client(RakNetSession session) {
        this.session = session;
    }

    public void onConnect() {
        System.out.println("A client connected!");
    }

    public void onDisconnect(String reason) {
        System.out.printf("Client disconnected: %s\n", reason);
    }
}
