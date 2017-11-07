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
    class GamePacketDecoder : ByteToMessageDecoder
    {
        protected override void Decode(IChannelHandlerContext context, IByteBuffer input, List<object> output)
        {
            int packetLength = input.Capacity;
            if(packetLength > 0)
            {
                output.Add(new GameMessage(input.ReadInt(), input.ReadBytes(input.ReadableBytes)));
            }
        }
    }
}
