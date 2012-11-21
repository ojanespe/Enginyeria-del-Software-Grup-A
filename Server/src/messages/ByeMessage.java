package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Client exists the game.
 * TCP
 * Client to Server.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class ByeMessage extends AbstractMessage {
    
    public ByeMessage(){}
}
