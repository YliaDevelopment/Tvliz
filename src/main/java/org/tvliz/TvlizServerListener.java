package org.tvliz;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.identifier.MinecraftIdentifier;
import com.whirvis.jraknet.server.RakNetServer;
import com.whirvis.jraknet.server.RakNetServerListener;
import com.whirvis.jraknet.server.ServerPing;
import com.whirvis.jraknet.session.RakNetClientSession;
import com.whirvis.jraknet.session.RakNetSession;
import org.tvliz.handler.Client;

import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public class TvlizServerListener implements RakNetServerListener {
    public Map<RakNetSession, Client> sessions = new LinkedHashMap<>();
    private final RakNetServer server;

    public TvlizServerListener(RakNetServer server) {
        this.server = server;
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
        var client = new Client(session);
        client.onConnect();
        this.sessions.put(session, client);
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason) {
        Client client = this.sessions.get(session);
        client.onDisconnect(reason);
        this.sessions.remove(session);
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket packet, int channel) {
        Client client = this.sessions.get(session);

        try {
            client.onPacket(packet, channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handlePing(ServerPing ping) {
        ping.setIdentifier(new MinecraftIdentifier("Proxy Server", 84, "0.15.10", server.getSessionCount(), server.getMaxConnections(), server.getGloballyUniqueId(), "Proxy World", "Survival"));
    }
}
