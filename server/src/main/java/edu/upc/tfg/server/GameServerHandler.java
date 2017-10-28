package edu.upc.tfg.server;

import edu.upc.tfg.common.packets.GamePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;

public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(GameServerHandler.class.getName());

/*
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GamePacket packet) throws Exception {
        logger.info("Message received: "+packet.getId()+" "+ DatatypeConverter.printHexBinary(packet.getPayload().array()));
        switch(packet.getId()) {
            case 0x01:
                new ConnectPacket().read(packet.getPayload());

                break;
            default:
                logger.info("Packet id no identificado: "+packet.getId());
                break;

        }
    }
*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Channel read");
        GamePacket packet = (GamePacket) msg;
        logger.info("Message received: "+packet.getId()+" Size: "+ packet.getPayload().capacity()+" - "+DatatypeConverter.printHexBinary(packet.getPayload().array()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Handler exception: ", cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("Client connected "+ctx.channel().remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("Client disconnected "+ctx.channel().remoteAddress());
    }
}
