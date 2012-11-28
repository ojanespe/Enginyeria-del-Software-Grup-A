package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import multiplayer.Player;

/**
 * The server notifies all clients that another client has connected.
 * TCP
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class NewUserMessage extends AbstractMessage  {
    
    private Player player;
    
    public NewUserMessage(){}
    
    public NewUserMessage(Player p){
        player = p;
    }
    
    public Player getPlayer(){
        return player;
    }
    
}
