
package multiplayer;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 * Stores the necessary information to describe an instance of an online player.
 *
 * @author Marc Bolaños
 */
@Serializable
public class PlayerClient extends Player{

    private Node character;
    private CharacterControl player;
    private CapsuleCollisionShape capsuleShape;
    
    public PlayerClient(){}
    
    public PlayerClient(int id, int team, int costume, int gun, Vector3f pos, AssetManager am){
        
        super(id, team, costume, gun, pos);
        init(am);
        
    }
    
    public void init(AssetManager assetManager){
        // TODO: igual que refresh pero cogiendo la info de los atributos
        // TODO: Create the model depending on 'costume'
        character = (Node)assetManager.loadModel((String)MultiplayerConstants.COSTUMES.get(super.getCostume()));
        character.setName("robot");
        character.setLocalScale(0.5f);
        character.setLocalTranslation(new Vector3f(0, 10, 0));
        character.addControl(player);
        
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);
        player.setGravity(60);
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(super.getPosition());
    }


    
    public void refresh(int act, Vector3f pos, Vector3f view, Vector3f dir){

        
    }
    
}
