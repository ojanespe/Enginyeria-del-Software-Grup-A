package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import multiplayer.Player;
import com.jme3.network.serializing.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server answer to a HelloMessage.
 * TCP
 * Server to Client.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class WelcomeMessage extends AbstractMessage {

    private int id;
    private Vector3f spawnPosition;
    private Vector3f spawnView;		// VRP of the camera
    private ConcurrentHashMap<Integer, Player> players;  // List of players that are in the match
    
    public WelcomeMessage(){}
    
    public WelcomeMessage(int id, Vector3f sp, Vector3f sv, ConcurrentHashMap<Integer, Player> p){
        this.id = id;
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
    
    public ConcurrentHashMap<Integer, Player> getPlayers(){
        return players;
    }
    
    public int getId() {
        return id;
    }
    
}
