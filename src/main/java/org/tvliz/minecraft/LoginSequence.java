package org.tvliz.minecraft;

import com.whirvis.jraknet.Packet;
import org.tvliz.minecraft.packet.OfflineConnectionRequest1;

public class LoginSequence {
    private boolean completed;

    public LoginSequence() {
        this.completed = false;
    }

    public void parsePacket(short packetID, Packet packet) {
        switch (packetID) {
            case 0x06 -> {
                var request = new OfflineConnectionRequest1(packet);
                System.out.printf("DENOVO NAO OAAAAAAAAAAA: %d %d\n", request.serverGUID, request.MTU);
            }
        }
    }

    public boolean isComplete() {
        return this.completed;
    }
}
