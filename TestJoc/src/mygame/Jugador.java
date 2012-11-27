/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import multiplayer.PlayerClient;
import multiplayer.PlayerInterface;

import multiplayer.MultiplayerConstants;


/**
 *
 * @author JORGE
 */
public class Jugador implements PlayerInterface{
    
    /*
     * (Modificado por Marc Bolaños)
     * 
     * Las armas y modelos de los personajes estan referenciados 
     * en MultiplayerConstants.
     * 
     * Falta añadir los siguientes atributos para el juego online:
     *      int action;     // describe la acción que està realizando el jugador.
     *      int team;       // guarda el equipo al que pertenece el jugador (0 o 1).
     *      int costume;    // describe el modelo de jugador que ha escogido el jugador.
     */
    
    private CharacterControl player;        

    private int vida, escudo, TOTAL_GUNS=3, actualGuns=0, gun=0;
    private float posX, posY, posZ;
    private CapsuleCollisionShape capsuleShape;
    private Arma[] armas= new Arma[TOTAL_GUNS];
    private Node character;    
    private boolean sniperMode = false;
    
    private Node robot;
    
    

    public Jugador(AssetManager assetManager){
        vida = 100;
        escudo = 100;
        
        posX = 0.0f;
        posY = 10.0f;
        posZ = 0.0f;
        assetManager.registerLocator("oto.zip", ZipLocator.class);    
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);
        player.setGravity(60);
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(new Vector3f(posX,posY,posZ));
        

        armas[0] = new Arma(assetManager, MultiplayerConstants.PSG_WEAPON, new Vector3f(-2.0f,-1.5f,3.5f), "Sounds/Effects/Guns/shotgun-old_school.ogg");
        armas[0].setWeaponType("sniper");
        armas[0].rotate(45.45f,0.0f, 0.0f);
        armas[0].setScale(0.14f);
        armas[1] = new Arma(assetManager, MultiplayerConstants.GLOCK_WEAPON,  new Vector3f(-2.8f,-1.4f,5.8f), "Sounds/Effects/Guns/shot_m9.ogg");
        armas[1].rotate(124.0f, 0.0f, 0.0f);
        armas[1].setScale(0.07f);
        armas[2] = new Arma(assetManager, MultiplayerConstants.GRENADE_WEAPON, new Vector3f(-2.0f,-1.6f,3.5f), "Sounds/Effects/Guns/shotgun-old_school.ogg");
        armas[2].rotate(0.0f,34.3f, 0.0f);
        armas[2].setScale(0.14f);

        
        robot = (Node)assetManager.loadModel("Oto.mesh.xml");
        robot.setName("robot");
        robot.setLocalScale(0.5f);
        robot.setLocalTranslation(new Vector3f(0, 10, 0));
        robot.addControl(player);        
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
        if(gun<(TOTAL_GUNS-1))
            gun++;
        else
            gun=0;
        
        character.detachAllChildren();        
        character.attachChild(armas[gun].getGun());
    }
    
    public Spatial getGun() {
        return armas[gun].getGun();
    }

    public Node getNode(){
        return character;
    }
    
    public Node getNodeModel(){
        return robot;
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
    
    public Node getRobot() {
        return robot;
    }

    public int getID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getTeam() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCostume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getGunId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setGunId(int g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(int act, Vector3f pos, Vector3f view, Vector3f dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isPlayer(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isTeam(int team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
