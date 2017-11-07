using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Net;
using DotNetty.Common;
using System.Threading.Tasks;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;

namespace clientgui.Network
{
    public class Client
    {
        public Client()
        {
            Initialization = RunClientAsync();
        }
        
        public Task Initialization { get; private set; }

        public static async Task RunClientAsync()
        {
            var group = new MultithreadEventLoopGroup();

            try
            {
                var bootstrap = new Bootstrap();
                bootstrap
                    .Group(group)
                    .Channel<TcpSocketChannel>()
                    .Option(ChannelOption.TcpNodelay, true)
                    .Handler(new ActionChannelInitializer<ISocketChannel>(channel =>
                    {
                        IChannelPipeline pipeline = channel.Pipeline;

                        //pipeline.AddLast(new DelimiterBasedFrameDecoder(8192, Delimiters.LineDelimiter()));
                        //pipeline.AddLast(new StringEncoder(), new StringDecoder(), new SecureChatClientHandler());
                        pipeline.AddLast(new GamePacketDecoder());
                        pipeline.AddLast(new GamePacketEncoder());
                        pipeline.AddLast(new GameClientHandler());


                    }));

                IChannel bootstrapChannel = await bootstrap.ConnectAsync(new IPEndPoint(IPAddress.Parse("localhost"), 8182));

               
            }
            catch(Exception ex)
            {
                Log.ErrorException("Client init", ex);
            }
            finally
            {
                group.ShutdownGracefullyAsync().Wait(1000);
            }
        }
    }
}
