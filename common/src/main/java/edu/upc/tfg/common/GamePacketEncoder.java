package edu.upc.tfg.common;

import edu.upc.tfg.common.packets.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class GamePacketEncoder extends MessageToByteEncoder<GamePacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GamePacket gamePacket, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(gamePacket.getId());
        byteBuf.writeBytes(gamePacket.getPayload());
    }
}
