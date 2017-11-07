using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DotNetty.Buffers;

namespace clientgui.Network
{
    class GameMessage
    {
        private int id;
        private IByteBuffer payload;

        public GameMessage(int id, IByteBuffer payload)
        {
            this.id = id;
            this.payload = payload;
        }

        public int getId()
        {
            return id;
        }

        public IByteBuffer getPayload()
        {
            return payload;
        }
    }
}
