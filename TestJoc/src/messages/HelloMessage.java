package messages;

import com.jme3.network.AbstractMessage;

/**
 * Client request to join the server.
 * TCP
 * Client to Server.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class HelloMessage extends AbstractMessage {
    
    int team;
    int costume;
    
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
