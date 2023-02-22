package org.tvliz.server.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tvliz.server.protocol.packet.OpenConnectionReplyOne;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.session.RakNetSession;

public interface ITvlizClient
{

    default void handleClientConnection()
    {
    }

    default void handleClientDisconnection(String reason)
    {
    }

    default void handleClientMessage(RakNetPacket packet)
    {
    }

    public static class TvlizClient implements ITvlizClient
    {

        private final Logger logger = LoggerFactory.getLogger(ITvlizClient.TvlizClient.class);

        private final RakNetSession session;

        public TvlizClient(RakNetSession session)
        {
            this.session = session;
        }

        @Override
        public void handleClientConnection()
        {
            this.getLogger().info("Handled connection for " + this.getSession().getConnectionType().getName() + " client with address " + this.getSession().getAddress());
        }

        @Override
        public void handleClientDisconnection(String reason)
        {
            this.getLogger().info("Handled disconnection for " + this.getSession().getConnectionType().getName() + " client with address " + this.getSession().getAddress());
        }

        @Override
        public void handleClientMessage(RakNetPacket packet)
        {
            this.getLogger().info("Handled Message with id " + packet.readUnsignedByte() + " for " + this.getSession().getConnectionType().getName() + " client with address " + this.getSession().getAddress());

            var packetId = packet.readUnsignedByte();

            if (packetId == 6)
            {
                var pk = new OpenConnectionReplyOne(packet);
                pk.decodePayload();

                this.getLogger().info("OpenConnectionReplyOne: " + pk.getMagic() + "/" + pk.getServerId() + "/" + pk.getServerSecurity() + "/" + pk.getMtuSize());
            }

        }

        public Logger getLogger()
        {
            return logger;
        }

        public RakNetSession getSession()
        {
            return session;
        }

    }

}
