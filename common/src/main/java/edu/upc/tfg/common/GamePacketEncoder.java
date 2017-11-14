package edu.upc.tfg.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;

public class GamePacketEncoder extends MessageToByteEncoder<GameMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GameMessage gamePacket, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(gamePacket.getId());
        byteBuf.writeBytes(gamePacket.getPayload());
    }
}
