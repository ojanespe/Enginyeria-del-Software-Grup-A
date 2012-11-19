/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    /*********************/
    /*      WEAPONS      */
    /*********************/
    
    public static final String GLOCK_WEAPON = "Models/Glock/GlockAnimated2.j3o"; //Models/Oto/Oto.mesh.xml
    public static final String MLP_WEAPON = "Models/Mlp/Mlp_ANIMADA.j3o";
    public static final String PSG_WEAPON = "Models/Psg/PSG_ANIMADA.j3o";
    
    /** 
     * HashMap that relates the Strings of the path of each weapon with its 'id'
     * on both directions ( String -> int ) and ( int -> String ).
     * 
     * All new weapons must be added here.
     */
    public static final Map WEAPONS = new HashMap(){
        {
            put(GLOCK_WEAPON, 1);
            put(1, GLOCK_WEAPON);  
            put(MLP_WEAPON, 2);
            put(2, MLP_WEAPON);
            put(PSG_WEAPON, 3);
            put(3, PSG_WEAPON);
        }
    };
    
    /**********************/
    /*      COSTUMES      */
    /**********************/
}
