package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;

/**
 * Client notifies that it has shot.
 * TCP?
 * Client to Server.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class ShootMessage extends AbstractMessage {
    
    private Vector3f shootPosition;  //Position of the player
    private Vector3f shootDirection; //Camera pov.
    
    //info about weapon
    
    public ShootMessage(){}
    
    public ShootMessage(Vector3f sp, Vector3f sd){
        this.shootDirection = sd;
        this.shootPosition = sp;
    }
    
    public Vector3f getShootPosition(){
        return shootPosition;
    }
    
    public Vector3f getShootDirection(){
        return shootDirection;
    }
    
}
