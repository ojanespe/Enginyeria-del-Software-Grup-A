package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies all clients that one player killed another.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class KillMessage extends AbstractMessage {
    
    int killer; // user_ids
    int killed;
    
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
