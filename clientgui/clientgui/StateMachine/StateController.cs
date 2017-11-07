using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace clientgui
{
    public class StateController
    {
        private Stack<IGameState> GameStates = new Stack<IGameState>();

        public void pushState(IGameState pushState)
        {
            GameStates.Push(pushState);
            GameStates.Peek().OnEnter();
        }
        public void popState()
        {            
            if (GameStates.Count > 0)
            {
                if (GameStates.Peek().OnExit())
                {
                    GameStates.Pop();
                }
            }
        }
        public void Update(GameTime gameTime)
        {
            if (GameStates.Count > 0)
            {
                GameStates.Peek().Update(gameTime);
            }
        }
        public void Draw(SpriteBatch spriteBatch)
        {
            if (GameStates.Count > 0)
            {
                GameStates.Peek().Render(spriteBatch);
            }
        }

    }
}
