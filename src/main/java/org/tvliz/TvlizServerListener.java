package org.tvliz;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.identifier.MinecraftIdentifier;
import com.whirvis.jraknet.server.RakNetServerListener;
import com.whirvis.jraknet.server.ServerPing;
import com.whirvis.jraknet.session.RakNetClientSession;
import com.whirvis.jraknet.session.RakNetSession;
import com.whirvis.jraknet.util.RakNetUtils;

public class TvlizServerListener implements RakNetServerListener
{

    private final Tvliz tvliz;
    private Map<RakNetSession, Client> clients = new HashMap<>();

    public TvlizServerListener(Tvliz tvliz)
    {
        this.tvliz = tvliz;
    }

    @Override
    public void onClientPreConnect(InetSocketAddress address)
    {
        System.out.println("Client from " + address + " is trying to connect, waiting for NewIncomingConnection packet");
    }

    @Override
    public void onClientPreDisconnect(InetSocketAddress address, String reason)
    {
        System.out.println("Client from " + address + " failed to connect. Reason: " + reason);
    }

    @Override
    public void onClientConnect(RakNetClientSession session)
    {
        var client = new Client(session);
        client.onConnect();
        this.clients.put(session, client);
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason)
    {
        var client = this.clients.get(session);
        client.onDisconnect(reason);
        this.clients.remove(session);
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket packet, int channel)
    {
               var client = this.clients.get(session);
               client.onNewPacket(packet, channel);

    }

    @Override
    public void handlePing(ServerPing ping)
    {
        var server = this.getTvliz().getServer();
        ping.setIdentifier(new MinecraftIdentifier("Proxy Server", 84, "0.15.10", server.getSessionCount(), server.getMaxConnections(), server.getGloballyUniqueId(), "Proxy World", "Survival"));
    }

    public Tvliz getTvliz()
    {
        return tvliz;
    }

}
