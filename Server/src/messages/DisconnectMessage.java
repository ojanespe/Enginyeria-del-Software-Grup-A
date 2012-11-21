package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Server notifies all clients that another left the game.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class DisconnectMessage extends AbstractMessage {
    
    private int userID;
    
    public DisconnectMessage(){}
    
    public DisconnectMessage(int uid){
        userID = uid;
    }
    
    public int getUserID(){
        return userID;
    }
    
}
