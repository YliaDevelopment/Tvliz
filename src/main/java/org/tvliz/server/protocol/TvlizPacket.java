package org.tvliz.server.protocol;

import com.whirvis.jraknet.Packet;

public abstract class TvlizPacket extends Packet
{

    public static final byte[] MAGIC = new byte[] {
                    (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00,
                    (byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfe,
                    (byte)0xfd, (byte)0xfd, (byte)0xfd, (byte)0xfd,
                    (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78
    };

    public TvlizPacket(byte[] data)
    {
        super(data);
    }

    public TvlizPacket(Packet packet)
    {
        super(packet);
    }

    public abstract void decodePayload();

    public abstract void encodePayload();

}
