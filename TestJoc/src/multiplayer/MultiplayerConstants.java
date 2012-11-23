
package multiplayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to store all the constants used in the multiplayer messages, 
 * for example the costumes or the weapons.
 *
 * @author Marc Bolaños
 */
public class MultiplayerConstants {
    
    public static final String IP = "localhost";
    public static final int PORT = 6143;
    
    /*********************/
    /*      WEAPONS      */
    /*********************/
    
    public static final String GLOCK_WEAPON = "Models/gun/gun.j3o"; //Models/Oto/Oto.mesh.xml
    public static final String PSG_WEAPON = "Models/rifle/rifle.j3o";
    
    /** 
     * HashMap that relates the Strings of the path of each weapon with its 'id'
     * on both directions ( String -> int ) and ( int -> String ).
     * 
     * All new weapons must be added here.
     */
    public static final Map WEAPONS = new HashMap(){
        {
            put(GLOCK_WEAPON, 0);
            put(0, GLOCK_WEAPON);
            put(PSG_WEAPON, 1);
            put(1, PSG_WEAPON);
        }
    };
    
    /**********************/
    /*      COSTUMES      */
    /**********************/
}
