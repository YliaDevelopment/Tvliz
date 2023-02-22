package org.tvliz;

import org.tvliz.server.TvlizServer;

import com.whirvis.jraknet.server.RakNetServer;

public class Tvliz
{

    public static void main(String[] args)
    {
        var server = new TvlizServer(new RakNetServer(19132, 10));
        if (!server.startServer())
            return; // TODO: Proper error Handling
    }

}
