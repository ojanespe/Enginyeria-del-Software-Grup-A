package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies all clients that another left the game.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura
 */
public class DisconnectMessage extends AbstractMessage {
    
    int userID;
    
}
