package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;

/**
 * Client notifies that it has shot.
 * TCP?
 * Client to Server.
 * @author albertohuelamosegura
 */
public class ShootMessage extends AbstractMessage {
    
    Vector3f shotPosition;  //Position of the player
    Vector3f shotDirection; //Camera pov.
    
    //info about weapon
    
}
