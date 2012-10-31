/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
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
    
    private String glockWeapon="Models/Glock/GlockAnimated2.j3o";
    private String mlpWeapon="Models/Mlp/Mlp_ANIMADA.j3o";
    private String psgWeapon="Models/Psg/PSG_ANIMADA.j3o";

    public Jugador(AssetManager assetManager){
        disparos = 0;
        vida = 100;
        escudo = 0;
        
        posX = 0;
        posY = 10;
        posZ = 0;
            
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(60);
        player.setGravity(60);
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(new Vector3f(posX,posY,posZ));
        
        Vector3f location=new Vector3f();
        location.x=-2.0f;
        location.y=-1.5f;
        location.z=5.5f;
    
        armas[0] = new Arma(assetManager, psgWeapon, location);
        armas[1] = new Arma(assetManager, mlpWeapon, new Vector3f(-2.0f, -3f, 5.5f)); //rotar 180º en z
        armas[2] = new Arma(assetManager, glockWeapon,  new Vector3f(-1f, -1f, 1.5f)); //rotar 180º en z
        
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
    
    public void changeArm(){
        if (armas[gun+1] == null) {
            gun = 0;
        }
        else {
            gun++;
        }
        character.detachAllChildren();
        character.attachChild(armas[gun].getGun());
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
    
    public Arma getArma() {
       return armas[gun];
    }
    
}
