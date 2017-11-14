import edu.upc.tfg.common.GamePacketDecoder;
import edu.upc.tfg.common.GamePacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class GameClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.nulDelimiter()));
        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
        pipeline.addLast("decoder", new GamePacketDecoder());
        pipeline.addLast("encoder", new GamePacketEncoder());

        pipeline.addLast(new GameClientHandler());
    }
}
