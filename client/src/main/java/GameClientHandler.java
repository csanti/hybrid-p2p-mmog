import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.gameclient.LocalWorld;
import edu.upc.tfg.common.packets.PacketMapping;
import edu.upc.tfg.common.packets.ServerPacket;
import edu.upc.tfg.common.packets.client.ConnectPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;

public class GameClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        GameMessage packet = (GameMessage) msg;
        logger.info("Message received: "+packet.getId()+" Size: "+ packet.getPayload().capacity()+" - "+ DatatypeConverter.printHexBinary(packet.getPayload().array()));

        if(PacketMapping.serverIdPacketMap.containsKey(packet.getId())) {
            ServerPacket cp = PacketMapping.serverIdPacketMap.get(packet.getId()).newInstance();
            cp.read(packet.getPayload());
            cp.handle(ctx);
        }
        else {
            logger.warn("Unknown packetid - "+packet.getId());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Conectado");
        //byte[] array = {0x00};
        //ctx.write(new GameMessage(0x00, Unpooled.copiedBuffer(array)));
        //ctx.writeAndFlush(new ConnectPacket("bot").write());
        LocalWorld.getInstance().init(ctx, "bot");

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
