package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Server notifies a player that it has been shot.
 * TCP?
 * Server to Client.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class HitMessage extends AbstractMessage {

    private int substracted_life;
    
    public HitMessage(){}
    
    public HitMessage(int sl){
        substracted_life = sl;
    }
    
    public int getSubstractedLife(){
        return substracted_life;
    }
    
}
