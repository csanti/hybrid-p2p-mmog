using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using clientgui.Network;

namespace clientgui
{
    public class PlayState : IGameState
    {
        
        


        public bool OnEnter()
        {
            Log.Info("Iniciando cliente...");
            Client client = new Client();
            Log.Info("Cliente ejecutado async");   
            return true;
            
        }

        public bool OnExit()
        {
            
            return true;         
        }

        public void Render(SpriteBatch spriteBatch)
        {
            
            //throw new NotImplementedException();
        }

        public void Update(GameTime gameTime)
        {
           
            //throw new NotImplementedException();
        }
    }
}
