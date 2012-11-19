package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;

/**
 * Update status of a client.
 * This is the most important message. It is sended continuously in order to
 * update the status of all players. The server broadcast this message to
 * all clients as well.
 * UDP
 * Client to Server and Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class RefreshMessage extends AbstractMessage {
    
    Vector3f position;    //Player's position
                        //In testJoc.java: s.getPlayer.getPhysicsLocation();
    
    Vector3f view;        //Player's POV.
                        //In testJoc.java: cam.getDirection()?
    
    Vector3f direction;   //Moving direction.
                        //In testJoc.java: booleans left right up down.
    
    int action;       //Moving, crouching, jumping... And combinations.
                        //binds or booleans controlling TODO: discuss
    
    public RefreshMessage(){}
    
    public RefreshMessage(Vector3f p, Vector3f v, Vector3f d, int a){
        position = p;
        view = v;
        direction = d;
        action = a;
    }
    
    public Vector3f getPosition(){
        return position;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public Vector3f getView(){
        return view;
    }
    
    public int getAction(){
        return action;
    }
    
}
