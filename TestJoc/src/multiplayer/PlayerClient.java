
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
    private Node robot;
    private CharacterControl player;
    private CapsuleCollisionShape capsuleShape;
    
    public PlayerClient(){}
    
    public PlayerClient(int id, int team, int costume, int gun, Vector3f pos, Vector3f dir, Vector3f view, int a, AssetManager am){
        
        super(id, team, costume, gun, pos, dir, view);
        super.setAction(a);
        init(am);
        
    }
    
    public void init(AssetManager assetManager){

        robot = (Node)assetManager.loadModel((String)MultiplayerConstants.COSTUMES.get(super.getCostume()));
        robot.setName("robot"+super.getID());
        robot.setLocalScale(0.5f);
        robot.setLocalTranslation(new Vector3f(0, 10, 0));
        robot.addControl(player);
        
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
    
    public void respawn(){
        
    }
    
    /**
     * Deletes the player model.
     */
    public void delete(){
        this.character.removeFromParent();
        this.robot.removeFromParent();
    }
    
}
