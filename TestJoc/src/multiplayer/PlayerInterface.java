
package multiplayer;

import com.jme3.math.Vector3f;

/**
 * Interface for the Player online objects
 *
 * @author Marc Bolaños Solà
 */
public interface PlayerInterface {
    
    
    public int getID();
    
    public int getTeam();
    
    public int getCostume();
    
    public int getGunId();
    
    public void setGunId(int g);
    
    
    public void refresh(int act, Vector3f pos, Vector3f view, Vector3f dir);
    
    
    /*
     * Checks if the 'id' is from this user.
     */
    public boolean isPlayer(int id);
    
    
    /*
     * Checks if 'team' is this user's team.
     */
    public boolean isTeam(int team);
    
}
