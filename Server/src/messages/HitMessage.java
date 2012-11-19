package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies a player that it has been shot.
 * TCP?
 * Server to Client.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class HitMessage extends AbstractMessage {

    int substracted_life;
    
    public HitMessage(){}
    
    public HitMessage(int sl){
        substracted_life = sl;
    }
    
    public int getSubstractedLife(){
        return substracted_life;
    }
    
}
