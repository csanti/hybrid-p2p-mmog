import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class GameClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        logger.info(byteBuf.toString());
    }

}
