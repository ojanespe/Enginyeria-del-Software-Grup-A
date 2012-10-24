/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author JORGE
 */
public class Soldado {
    private CharacterControl player;        
    private int disparos, vida, escudo;
    
   
    public Soldado(){
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(60);
        player.setGravity(60);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        disparos = 0;
        vida = 100;
        escudo = 0;
    }

    public CharacterControl getPlayer() {
        return player;
    }

    public int getDisparos() {
        return disparos;
    }
    
    public void incremenDisparos(){
        disparos++;
    }
    
    public int getVida(){
        return vida;
    }
    
    public void setVida(int life){
        vida=life;
    }
    
    public int getEscudo(){
        return escudo;
    }
    
    public void setEscudo(int s){
        escudo=s;
    }
    
    
}
