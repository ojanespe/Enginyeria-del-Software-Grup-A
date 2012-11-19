package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies all clients that another left the game.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class DisconnectMessage extends AbstractMessage {
    
    int userID;
    
    public DisconnectMessage(){}
    
    public DisconnectMessage(int uid){
        userID = uid;
    }
    
    public int getUserID(){
        return userID;
    }
    
}
