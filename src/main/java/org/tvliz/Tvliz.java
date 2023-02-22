package org.tvliz;

import com.whirvis.jraknet.RakNetException;
import com.whirvis.jraknet.identifier.Identifier;
import com.whirvis.jraknet.server.RakNetServer;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Tvliz {
  public static void main(String[] args) throws ParseException, IOException, RakNetException {
    var identifier = new Identifier("Tviliz Proxy");
    var rakServer = new RakNetServer(19132, 1000, 999, identifier);
    var server = new TvlizServer(rakServer);

    server.init(args);

    server.start();

    while (server.isRunning()) {
      server.tick();
    }

    server.destroy();
  }
}
