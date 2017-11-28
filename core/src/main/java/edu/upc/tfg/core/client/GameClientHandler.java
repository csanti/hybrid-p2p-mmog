package edu.upc.tfg.core.client;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.PacketMapping;
import edu.upc.tfg.core.packets.ServerPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;

public class GameClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());
    private String clientName;
    private LocalClientInstance localClientInstance;

    public GameClientHandler(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        GameMessage packet = (GameMessage) msg;
        logger.info("Message received: "+packet.getId()+" Size: "+ packet.getPayload().capacity()+" - "+ DatatypeConverter.printHexBinary(packet.getPayload().array()));

        if(PacketMapping.serverIdPacketMap.containsKey(packet.getId())) {
            ServerPacket cp = PacketMapping.serverIdPacketMap.get(packet.getId()).newInstance();
            cp.read(packet.getPayload());

            if(localClientInstance != null)
                cp.handle(ctx, localClientInstance);
            else
                logger.warn("LocalGameInstance is null!");
        }
        else {
            logger.warn("Unknown packetid - "+packet.getId());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Conected to server, starting local game instance");

        localClientInstance = new LocalClientInstance();
        localClientInstance.init(ctx, clientName);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof ReadTimeoutException) {
            logger.warn("Connection timeout, closing connection");
            ctx.close();
        }
        logger.error("Handler exception: ", cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //logger.info("Handler added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("Handler removed");
    }
}
