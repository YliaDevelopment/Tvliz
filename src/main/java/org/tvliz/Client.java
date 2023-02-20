package org.tvliz;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.session.RakNetSession;
import com.whirvis.jraknet.util.RakNetUtils;

public class Client {
    private RakNetSession session;

    Client(RakNetSession session) {
        this.session = session;
    }

    public void onConnect() {
        System.out.println(session.getConnectionType().getName() + " client from " + session.getAddress() + " connected to the server");
    }

    public void onDisconnect(String reason) {
        System.out.println(session.getConnectionType() + " client from " + session.getAddress() + " disconnected from the server. Reason: " + reason);
    }

    public void onNewPacket(RakNetPacket packet, int channel) {
        System.out.println("Received packet from " + session.getConnectionType().getName() + " client with address " +
                            session.getAddress() + " with packet id [Byte] " + packet.readUnsignedByte()
                            + "/ [HexToStrId] " + RakNetUtils.toHexStringId(packet) + " on channel " + channel);
    }
}
