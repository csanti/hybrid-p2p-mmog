package edu.upc.tfg.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class GameServerHandler extends SimpleChannelInboundHandler<GamePacket> {

    private static final Logger logger = Logger.getLogger(GameServerHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GamePacket packet) throws Exception {
        logger.info("Message received: "+packet.getId()+" "+packet.getPayload());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("Channel read complete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Handler exception: ", cause);
    }
}
