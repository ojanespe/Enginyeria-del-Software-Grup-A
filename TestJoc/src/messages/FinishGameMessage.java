package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Server notifies all clients that the game finished.
 * TCP?
 * Server broadcast.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class FinishGameMessage extends AbstractMessage {

    private int team1Result;
    private int team2Result;
    
    public FinishGameMessage(){}
    
    public FinishGameMessage(int t1, int t2){
        team1Result = t1;
        team2Result = t2;
    }
    
    public int getTeam1Result(){
        return team1Result;
    }
    
    public int getTeam2Result(){
        return team2Result;
    }
    
}
