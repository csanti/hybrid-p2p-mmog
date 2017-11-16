package edu.upc.tfg.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class GamePacketEncoder extends MessageToByteEncoder<GameMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GameMessage gamePacket, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(gamePacket.getId());
        byteBuf.writeBytes(gamePacket.getPayload());
    }
}
