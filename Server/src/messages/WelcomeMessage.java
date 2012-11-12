package messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import java.util.ArrayList;
import multiplayer.PlayerInterface;

/**
 * Server answer to a HelloMessage.
 * TCP
 * Server to Client.
 * @author albertohuelamosegura
 */
public class WelcomeMessage extends AbstractMessage {

    Vector3f spawnPosition;
    Vector3f spawnView;		// VRP of the camera
    ArrayList<PlayerInterface> player;  // List of players that are in the match
    
}
