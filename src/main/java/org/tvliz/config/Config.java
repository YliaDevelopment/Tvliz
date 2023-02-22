package org.tvliz.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

public class Config {
  private final RawConfig raw;
  public InetAddress remoteAddress;
  public int remotePort;

  public Config(String configPath) throws UnknownHostException {
    this.raw = new RawConfig(configPath, new LinkedHashMap<>() {
      {
        put("RemoteServer", new LinkedHashMap<String, Object>() {
          {
            put("Address", "127.0.0.1");
            put("Port", 19133);
          }
        });
      }
    });

    if (!this.raw.containsKey("RemoteServer") ||
        !this.raw.getAsMap("RemoteServer").containsKey("Address") ||
        !this.raw.getAsMap("RemoteServer").containsKey("Port")) {

      throw new RuntimeException(
          "The RemoteServer key is non-existent or it does not have the Address and Port field in it!");
    }

    var rm = this.raw.getAsMap("RemoteServer");

    this.remoteAddress = InetAddress.getByName((String) rm.get("Address"));
    this.remotePort = Short.parseShort(String.valueOf((rm.get("Port"))));
  }

  public boolean isValid() {
    return this.raw.isValid();
  }

  public void save() {
    this.raw.save();
  }
}
