package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies a player that it has been shot.
 * TCP?
 * Server to Client.
 * @author albertohuelamosegura
 */
public class HitMessage extends AbstractMessage {

    int life;
    
}
