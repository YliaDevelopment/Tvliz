package org.tvliz;

import com.whirvis.jraknet.session.RakNetSession;

import java.net.InetAddress;

public class Player {

    private final RakNetSession session;
    private final InetAddress address;
    private final short port;

    public Player(RakNetSession session) {
        this.session = session;
        this.address = session.getInetAddress();
        this.port = (short) session.getInetPort();
    }

    public RakNetSession getSession() {
        return session;
    }

    public InetAddress getAddress() {
        return address;
    }

    public short getPort() {
        return port;
    }

}
