
package multiplayer;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import messages.RefreshMessage;

/**
 * Stores the necessary information to describe an instance of an online player
 * for the server.
 *
 * @author Marc Bolaños
 */
public class PlayerServer implements PlayerInterface{
    
    private int user_ID;
    private int team;
    private int costume;
    private int gun;
    
    private Vector3f position;
    private Vector3f view;
    private Vector3f direction;
    private int action;
    
    private HostedConnection client;

    
    public PlayerServer(int id, int team, int costume, int gun, Vector3f pos, Vector3f dir, Vector3f view){
        this.user_ID = id;
        this.team = team;
        this.costume = costume;
        this.gun = gun;
        this.position = pos;
        this.view = view;
        this.direction = dir;
    }
    
    public PlayerServer(int id, int team, int costume, int gun, Vector3f pos, Vector3f dir, Vector3f view, HostedConnection client){
        this.user_ID = id;
        this.team = team;
        this.costume = costume;
        this.gun = gun;
        this.position = pos;
        this.view = view;
        this.direction = dir;
        this.client = client;
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
    
    public HostedConnection getClient() {
        return client;
    }
    
    public void setClient(HostedConnection client) {
        this.client = client;
    }
    
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getView() {
        return view;
    }

    public Vector3f getDirection() {
        return direction;
    }
    
    public void refresh(RefreshMessage message) {
        view = message.getView();
        direction = message.getDirection();
        action = message.getAction();
    }

    public int getGunId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setGunId(int g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
