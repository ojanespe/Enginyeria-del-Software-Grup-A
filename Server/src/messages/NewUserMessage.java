package messages;

import com.jme3.network.AbstractMessage;
import multiplayer.PlayerInterface;
import com.jme3.network.serializing.Serializable;

/**
 * The server notifies all clients that another client has connected.
 * TCP
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class NewUserMessage extends AbstractMessage  {
    
    private PlayerInterface player;
    
    public NewUserMessage(){}
    
    public NewUserMessage(PlayerInterface p){
        player = p;
    }
    
    public PlayerInterface getPlayer(){
        return player;
    }
    
}
