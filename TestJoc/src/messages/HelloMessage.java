package messages;

import com.jme3.network.AbstractMessage;

/**
 * Client request for join to server.
 * TCP
 * Client to Server.
 * @author albertohuelamosegura
 */
public class HelloMessage extends AbstractMessage {
    
    int team;
    int costume;
    
}
