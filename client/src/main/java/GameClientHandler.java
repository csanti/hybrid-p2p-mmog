import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.packets.client.ConnectPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class GameClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        logger.info(byteBuf.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Conectado");
        //byte[] array = {0x00};
        //ctx.write(new GameMessage(0x00, Unpooled.copiedBuffer(array)));
        ctx.write(new ConnectPacket("bot").write());
        ctx.flush();
    }
}
