import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

public class SimpleBot {
    private static final Logger logger = Logger.getLogger(SimpleBot.class.getName());

    private Channel channel = null;

    private final String host;
    private final int port;

    public SimpleBot(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new GameClientInitializer());

            Channel channel = bootstrap.connect(this.host, this.port).sync().channel();
        }
        catch (Exception e) {
            logger.error("Client error: ", e);
            group.shutdownGracefully();
        }
    }




}
