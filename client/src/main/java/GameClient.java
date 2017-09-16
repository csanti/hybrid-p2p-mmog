import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;
import org.apache.log4j.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());

    private final String host;
    private final int port;

    public static void main(final String[] args) {
        logger.info("Entry point");
        new GameClient("localhost", 8182).run();
    }


    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new GameClientInitializer());

            Channel channel = bootstrap.connect(this.host, this.port).sync().channel();

            channel.write("hola\r\n");

            channel.flush();
        }
        catch (Exception e) {
            logger.error("Client error: ", e);
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
