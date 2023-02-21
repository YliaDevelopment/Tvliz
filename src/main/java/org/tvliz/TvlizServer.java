package org.tvliz;

import com.whirvis.jraknet.RakNetException;
import com.whirvis.jraknet.server.RakNetServer;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.tvliz.config.Config;
import org.tvliz.config.RawConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

public class TvlizServer {

    private Config configuration;

    private boolean running;
   private final RakNetServer inner;

    public TvlizServer(RakNetServer raknetServer) {
        this.inner = raknetServer;

        this.running = false;
        this.inner.addListener(new TvlizServerListener(raknetServer));
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

        this.configuration = new Config(configPath);

        if (!this.configuration.isValid()) {
            System.out.println("Warning: The configuration file is not valid!");
        }

        this.configuration.save();


        System.out.println("Configuration loaded!");
    }

    public boolean isRunning() {
        return this.running;
    }

    public void tick() {
    }

    public void destroy() {
    }

    public void start() throws IOException, RakNetException {
        if (this.running)
            throw new IllegalStateException("You can't start a server that is already running!");

        if (configuration.remoteAddress == null ||
                !configuration.remoteAddress.isReachable(1) ||
                configuration.remotePort < 0 || configuration.remotePort > 65535) {

            System.out.println("Remote is unreachable or invalid!");
            System.exit(1);
        }

        System.out.println("Server is running!");
        this.inner.start();
        this.running = true;
    }
}
