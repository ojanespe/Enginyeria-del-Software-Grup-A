package messages;

import com.jme3.network.AbstractMessage;

/**
 * Server notifies all clients that the game finished.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura
 */
public class FinishGameMessage extends AbstractMessage {

    int team1Result;
    int team2Result;
    
}
