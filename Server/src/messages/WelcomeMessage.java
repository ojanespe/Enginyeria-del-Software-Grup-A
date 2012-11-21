package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import java.util.ArrayList;
import multiplayer.PlayerInterface;

/**
 * Server answer to a HelloMessage.
 * TCP
 * Server to Client.
 * @author albertohuelamosegura i Marc Bolaños
 */
public class WelcomeMessage extends AbstractMessage {

    private Vector3f spawnPosition;
    private Vector3f spawnView;		// VRP of the camera
    private ArrayList<PlayerInterface> players;  // List of players that are in the match
    
    public WelcomeMessage(){}
    
    public WelcomeMessage(Vector3f sp, Vector3f sv, ArrayList<PlayerInterface> p){
        this.spawnPosition = sp;
        this.spawnView = sv;
        this.players = p;
    }
    
    public Vector3f getSpawnPosition(){
        return spawnPosition;
    }
    
    public Vector3f getSpawnView(){
        return spawnView;
    }
    
    public ArrayList<PlayerInterface> getPlayers(){
        return players;
    }
    
}
