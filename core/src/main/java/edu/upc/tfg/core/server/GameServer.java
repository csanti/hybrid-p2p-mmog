package edu.upc.tfg.core.server;

import edu.upc.tfg.core.GamePacketDecoder;
import edu.upc.tfg.core.GamePacketEncoder;
import edu.upc.tfg.core.instances.MasterGameInstance;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.log4j.Logger;

public class GameServer {
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    private MasterGameInstance instance;

    public GameServer(MasterGameInstance instance) {
        this.instance= instance;
    }

    public void run(int port) throws Exception {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();

                    pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(0x100000, 0, 4, 0, 4));
                    pipeline.addLast("decoder", new GamePacketDecoder());
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new GamePacketEncoder());

                    pipeline.addLast(new GameServerHandler(instance));
                }
            });

            b.bind("localhost", port).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()) {
                        logger.info("Server listening: "+channelFuture.channel().localAddress());
                    } else {
                        logger.error("Could not bind to host");
                    }
                }
            }).sync();

        } catch(Exception ex) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            throw ex;
        }
    }
}
