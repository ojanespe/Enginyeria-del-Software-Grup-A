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
    private int vida, escudo, TOTAL_GUNS=10, actualGuns=0, gun=0;
    private float posX, posY, posZ;
    private CapsuleCollisionShape capsuleShape;
    private Arma[] armas= new Arma[TOTAL_GUNS];
    private Node character;    
    private String glockWeapon="Models/Glock/GlockAnimated2.j3o"; //Models/Oto/Oto.mesh.xml
    //private String mlpWeapon="Models/Mlp/Mlp_ANIMADA.j3o";
    private String psgWeapon="Models/Psg/PSG_ANIMADA.j3o";
    
    private boolean sniperMode = false;

    public Jugador(AssetManager assetManager){
        vida = 100;
        escudo = 100;
        
        posX = 0.0f;
        posY = 10.0f;
        posZ = 0.0f;
            
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);
        player.setGravity(60);
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(new Vector3f(posX,posY,posZ));
        
        armas[0] = new Arma(assetManager, psgWeapon, new Vector3f(-2.0f,-1.5f,5.5f));
        armas[0].setWeaponType("sniper");
        armas[0].rotate(0.0f, 330.0f, 0.0f);
        //armas[1] = new Arma(assetManager, mlpWeapon, new Vector3f(-2.0f, -3f, 5.5f));         
        armas[1] = new Arma(assetManager, glockWeapon,  new Vector3f(-0.5f, -0.25f, 1.25f)); 
        
    }
    
    public void setGun(Arma gun){
        if(actualGuns<TOTAL_GUNS)
        {
            armas[actualGuns]=gun;
            actualGuns++;
        }
    }
    
    public void chooseGun(int numGun) {
        character.detachAllChildren();
        if (armas[numGun] != null) {
            gun=numGun;
        }        
        character.attachChild(armas[gun].getGun());
    }
    
    public void changeArm() {
        if (armas[gun+1] == null) {
            gun = 0;
        }
        else {
            gun++;
        }
        character.detachAllChildren();        
        character.attachChild(armas[gun].getGun());
    }
    
    public Spatial getGun() {
        return armas[gun].getGun();
    }

    public Node getNode(){
        return character;
    }
    
    public CharacterControl getPlayer() {
        return player;
    }

    public int getDisparos() {
        return armas[gun].getBales();
    }
    
    public void incremenDisparos(){
        armas[gun].setBales(armas[gun].getBales()+1);
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
    
    public void setSniperMode(boolean b) {
        this.sniperMode = b;
    }
    
    public boolean getSniperMode() {
        return this.sniperMode;
    }
    
}
