package messages;

import com.jme3.network.AbstractMessage;
import multiplayer.PlayerInterface;

/**
 * The server notifies all clients that another client has connected.
 * TCP
 * Server broadcast.
 * @author albertohuelamosegura
 */
public class NewUserMessage extends AbstractMessage  {
    
    PlayerInterface player;
    
}
