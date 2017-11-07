using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace clientgui
{
    class InputBindings
    {
        private Dictionary<InputActions, Keys> KeyboardBindings = new Dictionary<InputActions, Keys>();

        public void LoadKeyboardBindings()
        {
            KeyboardBindings.Add(InputActions.MoveDown, Keys.S);
            KeyboardBindings.Add(InputActions.MoveLeft, Keys.A);
            KeyboardBindings.Add(InputActions.MoveRight, Keys.D);
            KeyboardBindings.Add(InputActions.MoveUp, Keys.W);
        }

        public Dictionary<InputActions, Keys> getBindings()
        {
            return KeyboardBindings;
        }

        public void addBinding(InputActions action, Keys key)
        {
           
        }

        public bool removeBinding(InputActions action, Keys key)
        {
            return false;
        }
    }
}
