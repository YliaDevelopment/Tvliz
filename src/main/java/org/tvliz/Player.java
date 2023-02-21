package org.tvliz;

import java.net.InetAddress;

import com.whirvis.jraknet.session.RakNetSession;

public class Player {
    
    private RakNetSession session;
    private InetAddress address;
    private short port;

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
