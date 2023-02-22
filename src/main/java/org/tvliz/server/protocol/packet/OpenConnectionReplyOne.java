package org.tvliz.server.protocol.packet;

import org.tvliz.server.protocol.TvlizPacket;

import com.whirvis.jraknet.Packet;

public class OpenConnectionReplyOne extends TvlizPacket
{

    private byte[] magic;
    private int serverId;
    private byte serverSecurity;
    private short mtuSize;

    public OpenConnectionReplyOne(Packet packet)
    {
        super(packet);
    }

    @Override
    public void decodePayload()
    {
        this.magic = this.read(16);
        this.serverId = this.readInt();
        this.serverSecurity = this.readByte();
        this.mtuSize = this.readShort();
    }

    @Override
    public void encodePayload()
    {
    }

    public byte[] getMagic()
    {
        return magic;
    }

    public int getServerId()
    {
        return serverId;
    }

    public byte getServerSecurity()
    {
        return serverSecurity;
    }

    public short getMtuSize()
    {
        return mtuSize;
    }

}
