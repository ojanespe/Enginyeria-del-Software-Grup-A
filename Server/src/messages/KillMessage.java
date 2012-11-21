package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Server notifies all clients that one player killed another.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class KillMessage extends AbstractMessage {
    
    private int killer; // user_ids
    private int killed;
    
    public KillMessage(){}
    
    public KillMessage(int kr, int kd){
        killer = kr;
        killed = kd;
    }
    
    public int getKiller(){
        return killer;
    }
    
    public int getKilled(){
        return killed;
    }
    
}
