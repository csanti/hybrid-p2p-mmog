package edu.upc.tfg.core.client;

import edu.upc.tfg.core.GamePacketDecoder;
import edu.upc.tfg.core.GamePacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.log4j.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());

    private Channel channel = null;

    private final String host;
    private final int port;
    private final String clientName;

    public GameClient(String host, int port, String clientName) {
        this.host = host;
        this.port = port;
        this.clientName = clientName;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();

                    //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.nulDelimiter()));
                    //pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(0x100000, 0, 4, 0, 4));
                    pipeline.addLast("decoder", new GamePacketDecoder());
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new GamePacketEncoder());

                    pipeline.addLast(new GameClientHandler(clientName));
                }
            });

            channel = bootstrap.connect(this.host, this.port).sync().channel();
        }
        catch (Exception e) {
            logger.error("Client error: ", e);
            group.shutdownGracefully();
        }
    }

    public void stop() {
        channel.close();
    }





}
