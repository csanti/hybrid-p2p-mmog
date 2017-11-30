package edu.upc.tfg.core.server;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.instances.DelegatedInstance;
import edu.upc.tfg.core.instances.MainInstance;
import edu.upc.tfg.core.instances.MasterGameInstance;
import edu.upc.tfg.core.packets.ClientPacket;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.packets.PacketMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(GameServerHandler.class.getName());

    public static List<ClientConnection> connections = new ArrayList<ClientConnection>();
    public ClientConnection conn;
    private static MasterGameInstance gameInstance;

    public GameServerHandler(int instanceId){
        // type 1 = mainInstance
        // type 2 = delgatedInstance
        if(gameInstance == null) {
            if(instanceId == 0) {
                gameInstance = new MainInstance();
            }
            else {
                gameInstance = new DelegatedInstance(instanceId);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        GameMessage packet = (GameMessage) msg;
        //logger.info("Message received: "+packet.getId()+" Size: "+ packet.getPayload().capacity()+" - "+DatatypeConverter.printHexBinary(packet.getPayload().array()));

        if(PacketMapping.clientIdPacketMap.containsKey(packet.getId())) {
            ClientPacket cp = PacketMapping.clientIdPacketMap.get(packet.getId()).newInstance();
            cp.read(packet.getPayload());
            cp.handle(conn, gameInstance);
        }
        else {
            logger.warn("Unknown packetid - "+packet.getId());
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if(cause instanceof IOException) {
            if(conn.getPlayer() != null) {
                logger.error("Client "+conn.getPlayer().getName()+" IOException, unexpeced disconnect");
            }
            else {
                logger.error("IOException, unexpected disconnect");
            }

        } else {
            logger.error("Handler exception: ", cause);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.conn = new ClientConnection(ctx);
        connections.add(this.conn);
        logger.info("Client connected "+ctx.channel().remoteAddress()+" Total clients connected: "+connections.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        connections.remove(this.conn);
        logger.info("Client disconnected "+ctx.channel().remoteAddress()+" Total clients connected: "+connections.size());
    }
}
