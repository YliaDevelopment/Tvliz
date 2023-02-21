package org.tvliz;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.identifier.MinecraftIdentifier;
import com.whirvis.jraknet.server.RakNetServerListener;
import com.whirvis.jraknet.server.ServerPing;
import com.whirvis.jraknet.session.RakNetClientSession;

import java.net.InetSocketAddress;

public class TvlizServerListener implements RakNetServerListener {

    private final Tvliz tvliz;

    public TvlizServerListener(Tvliz tvliz) {
        this.tvliz = tvliz;
    }

    @Override
    public void onClientPreConnect(InetSocketAddress address) {
        System.out.println("Client from " + address + " is trying to connect, waiting for NewIncomingConnection packet");
    }

    @Override
    public void onClientPreDisconnect(InetSocketAddress address, String reason) {
        System.out.println("Client from " + address + " failed to connect. Reason: " + reason);
    }

    @Override
    public void onClientConnect(RakNetClientSession session) {
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason) {
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket packet, int channel) {
    }

    @Override
    public void handlePing(ServerPing ping) {
        var server = this.getTvliz().getServer();
        ping.setIdentifier(new MinecraftIdentifier("Proxy Server", 84, "0.15.10", server.getSessionCount(), server.getMaxConnections(), server.getGloballyUniqueId(), "Proxy World", "Survival"));
    }

    public Tvliz getTvliz() {
        return tvliz;
    }
}
