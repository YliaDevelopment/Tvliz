package org.tvliz.minecraft.packet;

import com.whirvis.jraknet.Packet;

public class OfflineConnectionRequest1 {
    public final short MTU;
    public final boolean useSecurity;
    public long serverGUID;

    public OfflineConnectionRequest1(Packet packet) {
        packet.checkMagic();

        this.serverGUID = packet.readLongLE();
        this.useSecurity = packet.readBoolean();
        this.MTU = packet.readShortLE();
    }
}
