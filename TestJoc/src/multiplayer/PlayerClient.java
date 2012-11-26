
package multiplayer;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;

/**
 * Stores the necessary information to describe an instance of an online player.
 *
 * @author Marc Bolaños
 */
@Serializable
public class PlayerClient implements PlayerInterface{
    
    private int user_ID;
    private int team;
    private int costume;
    private int gun;
    
    private Vector3f position;
    private Vector3f view;
    private Vector3f direction;
    private int action;

    private Node character;
    private CharacterControl player;
    private CapsuleCollisionShape capsuleShape;
    
    public PlayerClient(){}
    
    public PlayerClient(int id, int team, int costume, int gun, Vector3f pos, AssetManager assetManager){
        
        this.user_ID = id;
        this.team = team;
        this.costume = costume;
        this.gun = gun;
        this.position = pos;
        
        // TODO: Create the model depending on 'costume'
        character = (Node)assetManager.loadModel((String)MultiplayerConstants.COSTUMES.get(costume));
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
        player.setPhysicsLocation(position);
    }
    
    public void init(){
        // TODO: igual que refresh pero cogiendo la info de los atributos
    }
    
    public int getID(){
        return this.user_ID;
    }
    
    public int getTeam(){
        return this.team;
    }
    
    public int getCostume(){
        return this.costume;
    }
    
    public int getGun(){
        return this.gun;
    }
    
    public void setGun(int g){
        gun = g;
    }
    
    
    public void refresh(int act, Vector3f pos, Vector3f view, Vector3f dir){
        position = pos;
        this.view = view;
        direction = dir;
        action = act;
        
        player.setWalkDirection(dir);
        player.setPhysicsLocation(pos);
        // refresh view
        //player.setViewDirection(view); ??
        
        // change action
        
    }
    
    
    /*
     * Checks if the 'id' is from this user.
     */
    public boolean isPlayer(int id){
        return this.user_ID == id;
    }
    
    
    /*
     * Checks if 'team' is this user's team.
     */
    public boolean isTeam(int team){
        return this.team == team;
    }

    public int getGunId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setGunId(int g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getAction() {
        return action;
    }
    
}
