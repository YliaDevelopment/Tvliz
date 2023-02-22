package org.tvliz.server;

import org.tvliz.server.listener.TvlizListener;

import com.whirvis.jraknet.server.RakNetServer;

public class TvlizServer
{

    private final RakNetServer server;

    public TvlizServer(RakNetServer server)
    {
        this.server = server;
    }

    public boolean startServer()
    {
        this.getServer().addListener(new TvlizListener(this.getServer()));
        this.getServer().startThreaded();
        return true;
    }

    public RakNetServer getServer()
    {
        return server;
    }

}
