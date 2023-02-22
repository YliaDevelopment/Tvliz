package org.tvliz.handler;

import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.session.RakNetClientSession;
import com.whirvis.jraknet.session.RakNetSession;
import org.tvliz.minecraft.LoginSequence;

public class Client {
  RakNetSession session;
  LoginSequence loginSequence = new LoginSequence();

  public Client(RakNetSession session) {
    this.session = session;
  }

  public void onConnect() {
    System.out.println("A client connected!");
  }

  public void onDisconnect(String reason) {
    System.out.printf("Client disconnected: %s\n", reason);
  }

  public void onPacket(RakNetPacket packet, int channel) {
    short id = packet.readUnsignedByte();
    System.out.printf("Reading packet: %d\n", id);

    if (!loginSequence.isComplete()) {
      loginSequence.parsePacket(id, packet);
      return;
    }
  }
}
