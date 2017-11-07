using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace clientgui
{
    public enum InputActions
    {
        MoveLeft,
        MoveRight,
        MoveDown,
        MoveUp
    }    

    public class InputHandler
    {
        private InputBindings Bindings = new InputBindings();

        private KeyboardState KeyboardStateCurrent, KeyboardStatePrevious;

        public InputHandler()
        {
            Bindings.LoadKeyboardBindings();
            KeyboardStatePrevious = Keyboard.GetState();
        }
        public void CheckKeyboardInputs()
        {
            KeyboardStateCurrent = Keyboard.GetState();

            foreach (KeyValuePair<InputActions, Keys> keyBinding in Bindings.getBindings())
            {
                // fire events when key pressed
                if (KeyboardStateCurrent.IsKeyDown(keyBinding.Value) && KeyboardStatePrevious.IsKeyUp(keyBinding.Value))
                {
                    switch (keyBinding.Key)
                    {
                        case InputActions.MoveLeft:
                            InputEvents.onMoveLeft();
                            break;

                        case InputActions.MoveRight:
                            InputEvents.onMoveRight();
                            break;

                        case InputActions.MoveUp:
                            InputEvents.onMoveUp();
                            break;
                        case InputActions.MoveDown:
                            InputEvents.onMoveDown();
                            break;
                    }
                }

                // fire events key held
                else if (KeyboardStateCurrent.IsKeyDown(keyBinding.Value) && KeyboardStatePrevious.IsKeyDown(keyBinding.Value))
                {
                    switch (keyBinding.Key)
                    {
                        
                    }
                }

                // fire events when key released
                else if (KeyboardStateCurrent.IsKeyUp(keyBinding.Value) && KeyboardStatePrevious.IsKeyDown(keyBinding.Value))
                {
                    
                }
            }
            KeyboardStatePrevious = KeyboardStateCurrent;
        }

    }
}
