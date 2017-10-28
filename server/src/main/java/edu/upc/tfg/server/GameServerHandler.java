package edu.upc.tfg.server;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.packets.ClientPacket;
import edu.upc.tfg.common.packets.GamePacket;
import edu.upc.tfg.common.packets.PacketMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

public class GameServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(GameServerHandler.class.getName());

    public static List<Connection> connections = new ArrayList<Connection>();
    public Connection conn;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Channel read");
        GamePacket packet = (GamePacket) msg;
        logger.info("Message received: "+packet.getId()+" Size: "+ packet.getPayload().capacity()+" - "+DatatypeConverter.printHexBinary(packet.getPayload().array()));

        ClientPacket cp = PacketMapping.clientPackets.get(packet.getId()).newInstance();
        cp.read(packet.getPayload());
        cp.handle(conn);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Handler exception: ", cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.conn = new Connection(ctx);
        connections.add(this.conn);
        logger.info("Client connected "+ctx.channel().remoteAddress()+" Total clients connected: "+connections.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        connections.remove(this.conn);
        logger.info("Client disconnected "+ctx.channel().remoteAddress()+" Total clients connected: "+connections.size());
    }
    
    public static void sendAll(String from, GamePacket gamePacket) {
        for (Connection con: connections) {
            if(!con.getUsername().equals(from)) {
                con.getCtx().channel().writeAndFlush(gamePacket);
            }
        }
    }
}
