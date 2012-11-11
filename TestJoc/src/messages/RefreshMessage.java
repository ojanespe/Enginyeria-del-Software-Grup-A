package messages;

import com.jme3.network.AbstractMessage;

/**
 * Update status of a client.
 * This is the most important message. It is sended continuously in order to
 * update the status of all players. The server broadcast this message to
 * all clients as well.
 * UDP
 * Client to Server and Server broadcast.
 * @author albertohuelamosegura
 */
public class RefreshMessage extends AbstractMessage {
    
    //vec3 position;    //Player's position.
    //vec3 view;        //Player's POV.
    //vec3 direction;   //Moving direction.
    //int action;       //Moving, crouching, jumping... And combinations.
    
}
