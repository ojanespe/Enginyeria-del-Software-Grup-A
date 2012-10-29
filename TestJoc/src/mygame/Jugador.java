/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author JORGE
 */
public class Jugador {
    private CharacterControl player;        
    private int disparos, vida, escudo, posX, posY, posZ;
    private CapsuleCollisionShape capsuleShape;
    private int TOTAL_GUNS=10;
    private Arma[] armas= new Arma[TOTAL_GUNS];
    private int gun=0;
    private int actualGuns=0;
    private Node character;

    public Jugador(){
        disparos = 0;
        vida = 100;
        escudo = 0;
        
        posX = 0;
        posY = 100;
        posZ = 10;
        
    
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(60);
        player.setGravity(60);
        player.setPhysicsLocation(new Vector3f(0,10,0));
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(new Vector3f(0,10,0));
        
    }
    
    public void setGun(Arma gun){
        if(actualGuns<TOTAL_GUNS)
        {
            armas[actualGuns]=gun;
            actualGuns++;
        }
    }
    
    public void chooseGun(int numGun)
    {
        //S'ha de borrar l'anterior i posar la nova arma
        //character.removeControl(player);
        gun=numGun;
        character.attachChild(armas[numGun].getGun());
    }
    
    public Spatial getGun()
    {
        return armas[gun].getGun();
    }

    public Node getNode(){
        return character;
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
        vida = life;
    }
    
    public int getEscudo(){
        return escudo;
    }
    
    public void setEscudo(int s){
        escudo = s;
    }    
    
}
