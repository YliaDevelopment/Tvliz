package org.tvliz;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.tvliz.config.Config;

public class TvlizServer {

    private LinkedHashMap<String, Player> sessions;

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

        this.configuration = new Config(configPath, new LinkedHashMap<String, Object>() {{
            put("RemoteServer", new LinkedHashMap<String, Object>() {{
                put("Address", "127.0.0.1");
                put("Port", 19133);
            }});
        }});

        if (!this.configuration.isValid()) {
            throw new IllegalStateException("The configuration file is not valid!");
        }

        this.configuration.save();

        if (!this.configuration.containsKey("RemoteServer") ||
            !this.configuration.getAsMap("RemoteServer").containsKey("Address") ||
            !this.configuration.getAsMap("RemoteServer").containsKey("Port")) {
            
            throw new RuntimeException("The RemoteServer key is non-existent or it does not have the Address and Port field in it!");
        }

        this.remoteAddress = InetAddress.getByName((String) this.configuration.getAsMap("RemoteServer").get("Address"));
        this.remotePort = Short.valueOf((String) this.configuration.getAsMap("RemoteServer").get("Port"));
    }

    public boolean isRunning() {
        return this.running;
    }

    public void tick() {
    }

    public void destroy() {
    }

    public void start() {
        if (this.running)
            throw new IllegalStateException("You can't start a server that is already running!");
        
        if (this.remoteAddress == null ||
            !this.remoteAddress.isReachable(1) ||
            this.remotePort < 0 || this.remotePort > 65535) {
            
        }

        this.running = true;
    }
    


}
