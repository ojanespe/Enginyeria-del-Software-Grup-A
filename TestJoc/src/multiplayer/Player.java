
package multiplayer;


import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;


/**
 * Interface for the Player online objects
 *
 * @author Marc Bolaños Solà
 */
@Serializable
public abstract class Player {
    
    private int user_ID;
    private int team;
    private int costume;
    private int gun;
    
    private Vector3f position;
    private Vector3f view;
    private Vector3f direction;
    private int action;

    
    public Player(){}
    
    public Player(int id, int team, int costume, int gun, Vector3f pos){
        
        this.user_ID = id;
        this.team = team;
        this.costume = costume;
        this.gun = gun;
        this.position = pos;
        
    }
    
    public int getAction(){
        return this.action;
    }
    
    public void setAction(int a){
        action = a;
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
    
    public int getGunId(){
        return this.gun;
    }
    
    public void setGunId(int g){
        this.gun = g;
    }
    
    public Vector3f getPosition(){
        return position;
    }
    
    public void setPosition(Vector3f p){
        position = p;
    }
    
    public Vector3f getView(){
        return view;
    }
    
    public void setView(Vector3f v){
        view = v;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public void setDirection(Vector3f d){
        direction = d;
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
    
}
