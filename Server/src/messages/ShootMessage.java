package messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Client notifies that it has shot.
 * TCP?
 * Client to Server.
 * @author albertohuelamosegura i Marc Bolaños
 */
@Serializable
public class ShootMessage extends AbstractMessage {
    
    private int idShooter;
    private int idShooted;
    private int life;

    //info about weapon
    
    public ShootMessage(){}
    
    public ShootMessage(int idShooter, int idShooted, int life) {
        this.idShooter = idShooter;
        this.idShooted = idShooted;
        this.life = life;
    }

    public int getIdShooter() {
        return idShooter;
    }

    public int getIdShooted() {
        return idShooted;
    }

    public int getLife() {
        return life;
    }
    
}
