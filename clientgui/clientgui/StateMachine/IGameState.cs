using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace clientgui
{
    public interface IGameState
    {        
        void Update(GameTime gameTime);
        void Render(SpriteBatch spriteBatch);
        bool OnEnter();
        bool OnExit();
    }
}
