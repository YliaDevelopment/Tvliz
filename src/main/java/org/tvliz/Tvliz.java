package org.tvliz;

import com.whirvis.jraknet.RakNetException;
import com.whirvis.jraknet.server.RakNetServer;

public class Tvliz {

  private final RakNetServer server;

  public Tvliz(RakNetServer server) throws RakNetException {
    this.server = server;
    this.getServer().addListener(new TvlizServerListener(this));
    this.server.start();
  }

  public static void main(String[] args) {
    try {
      new Tvliz(new RakNetServer(9999, 10));
    } catch (IllegalStateException | RakNetException e) {
      e.printStackTrace();
    }
  }

  public RakNetServer getServer() {
    return server;
  }

}
