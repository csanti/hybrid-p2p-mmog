package edu.upc.tfg.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class GamePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int packetLenght = byteBuf.capacity();
        if(packetLenght > 0) {
            list.add(new GameMessage(byteBuf.readInt(), byteBuf.readBytes(byteBuf.readableBytes())));
        }

    }
}
