package org.tvliz;

import java.net.UnknownHostException;

import org.apache.commons.cli.ParseException;

import com.whirvis.jraknet.RakNetException;
import com.whirvis.jraknet.server.RakNetServer;

public class Tvliz {

  private final RakNetServer server;

  public Tvliz(RakNetServer server) throws RakNetException {
    this.server = server;
    this.getServer().addListener(new TvlizServerListener(this));
    this.server.start();
  }

  public static void main(String[] args) throws ParseException, UnknownHostException {
    var server = new TvlizServer();

    server.init(args);

    server.start();

    while (server.isRunning()) {
      server.tick();
    }

    server.destroy();
  }

  public RakNetServer getServer() {
    return server;
  }

}
