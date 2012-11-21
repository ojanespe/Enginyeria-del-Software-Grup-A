package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Client request to join the server.
 * TCP
 * Client to Server.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class HelloMessage extends AbstractMessage {
    
    private int team;
    private int costume;
    
    public HelloMessage(){}
    
    public HelloMessage(int t, int c){
        team = t;
        costume = c;
    }
    
    public int getTeam(){
        return team;
    }
    
    public int getCostume(){
        return costume;
    }
    
}
