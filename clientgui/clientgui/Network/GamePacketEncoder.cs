using DotNetty.Codecs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DotNetty.Buffers;
using DotNetty.Transport.Channels;

namespace clientgui.Network
{
    class GamePacketEncoder : MessageToByteEncoder<GameMessage>
    {
        protected override void Encode(IChannelHandlerContext context, GameMessage message, IByteBuffer output)
        {
            output.WriteInt(message.getId());
            output.WriteBytes(message.getPayload());
        }
    }
}
