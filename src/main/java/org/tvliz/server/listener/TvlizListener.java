package org.tvliz.server.listener;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tvliz.server.client.ITvlizClient;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.identifier.MinecraftIdentifier;
import com.whirvis.jraknet.server.RakNetServer;
import com.whirvis.jraknet.server.RakNetServerListener;
import com.whirvis.jraknet.server.ServerPing;
import com.whirvis.jraknet.session.RakNetClientSession;

public class TvlizListener implements RakNetServerListener
{

    private final Logger logger = LoggerFactory.getLogger(TvlizListener.class);

    private final RakNetServer server;

    private final Map<InetSocketAddress, ITvlizClient.TvlizClient> clients = new HashMap<>();

    public TvlizListener(RakNetServer server)
    {
        this.server = server;
    }

    @Override
    public void handlePing(ServerPing ping)
    {
        ping.setIdentifier(new MinecraftIdentifier(
                        "Proxy",
                        84,
                        "0.15.10",
                        this.getServer().getSessionCount(),
                        this.getServer().getMaxConnections(),
                        this.getServer().getGloballyUniqueId(),
                        "Proxy World", "Survival"));
    }

    @Override
    public void onClientConnect(RakNetClientSession session)
    {
        if (!this.getClients().containsKey(session.getAddress()))
        {
            this.getClients().putIfAbsent(
                            session.getAddress(),
                            new ITvlizClient.TvlizClient(session));
            this.getClients().get(session.getAddress()).handleClientConnection();
        }
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason)
    {
        if (this.getClients().containsKey(session.getAddress()))
        {
            this.getClients().get(session.getAddress()).handleClientDisconnection(reason);
        }
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket packet, int channel)
    {
        if (this.getClients().containsKey(session.getAddress()))
        {
            this.getLogger().info("Handled Packet for " + session.getAddress() + " on channel #" + channel);
            this.getClients().get(session.getAddress()).handleClientMessage(packet);
        }
    }

    public Logger getLogger()
    {
        return logger;
    }

    public RakNetServer getServer()
    {
        return server;
    }

    public Map<InetSocketAddress, ITvlizClient.TvlizClient> getClients()
    {
        return clients;
    }

}
