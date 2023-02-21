package org.tvliz;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.tvliz.config.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

public class TvlizServer {

    private final LinkedHashMap<String, Player> sessions;

    private Config configuration;

    private boolean running;

    private InetAddress remoteAddress;
    private int remotePort;

    public TvlizServer() {
        this.running = false;
        this.sessions = new LinkedHashMap<String, Player>();
    }

    public void init(String[] args) throws ParseException, UnknownHostException {
        var options = new Options()
                .addOption(Option.builder("c")
                        .longOpt("config-path")
                        .desc("Set the tvliz proxy configuration path")
                        .build());

        var parser = new DefaultParser();
        var cmdline = parser.parse(options, args);

        var configPath = "tvliz.yml";

        if (cmdline.hasOption("c")) {
            configPath = cmdline.getOptionValue("c");
        }

        this.configuration = new Config(configPath, new LinkedHashMap<>() {{
            put("RemoteServer", new LinkedHashMap<String, Object>() {{
                put("Address", "127.0.0.1");
                put("Port", 19133);
            }});
        }});

        if (!this.configuration.isValid()) {
            System.out.println("Warning: The configuration file is not valid!");
        }

        this.configuration.save();

        if (!this.configuration.containsKey("RemoteServer") ||
                !this.configuration.getAsMap("RemoteServer").containsKey("Address") ||
                !this.configuration.getAsMap("RemoteServer").containsKey("Port")) {

            throw new RuntimeException("The RemoteServer key is non-existent or it does not have the Address and Port field in it!");
        }

        var rm = this.configuration.getAsMap("RemoteServer");

        this.remoteAddress = InetAddress.getByName((String) rm.get("Address"));
        this.remotePort = Short.parseShort(String.valueOf((rm.get("Port"))));

        System.out.println("Configuration loaded!");
    }

    public boolean isRunning() {
        return this.running;
    }

    public void tick() {
    }

    public void destroy() {
    }

    public void start() throws IOException {
        if (this.running)
            throw new IllegalStateException("You can't start a server that is already running!");

        if (this.remoteAddress == null ||
                !this.remoteAddress.isReachable(1) ||
                this.remotePort < 0 || this.remotePort > 65535) {

        }

        this.running = true;
    }


}
