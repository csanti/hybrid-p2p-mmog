using DotNetty.Buffers;
using DotNetty.Transport.Channels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace clientgui.Network
{
    class GameClientHandler : SimpleChannelInboundHandler<GameMessage>
    {
        protected override void ChannelRead0(IChannelHandlerContext ctx, GameMessage msg)
        {
            Log.Info("Recibido mensaje ID: " + msg.getId() + " payload: " + msg.getPayload().ToString());
            
        }

        public override void ChannelActive(IChannelHandlerContext context)
        {
            Log.Info("Channel active");
        }

        public override void HandlerAdded(IChannelHandlerContext context)
        {
            Log.Info("Handler added");
        }


    }
}
