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
import java.util.ArrayList;

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
     */
    
    private CharacterControl player;        

    private int online_id; 
    private int online_team; // Id del equipo al que pertenece
    private String costume; // Modelo del jugador
    
    private int vida, escudo, TOTAL_GUNS=10, actualGuns=0, gun=0;
    private float posX, posY, posZ;
    private CapsuleCollisionShape capsuleShape;
    private Arma[] armas= new Arma[TOTAL_GUNS];
    private Node character;    
    //private String gunWeapon="Models/gun/gun.j3o"; //Models/Oto/Oto.mesh.xml
    //private String rileWeapon="Models/rifle/rifle.j3o";
    private int count=1;
    
    private boolean sniperMode = false;
    
    private Node robot;
    
    

    public Jugador(AssetManager assetManager){
        vida = 100;
        escudo = 100;
        
        costume = MultiplayerConstants.OTO;
        
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

        
        robot = (Node)assetManager.loadModel(costume);
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
        if (gun == 0) {
            if(count==2)
            {
                gun = 1;
                count=0;
            }
        }
        else if(gun==1) {
            if(count==2)
            {
                gun = 0;
                count=0;
            }
        }
        count++;
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
        return online_id;
    }

    public int getTeam() {
        return online_team;
    }
    
    public void setID(int id){
        this.online_id = id;
    }
    
    public void setTeam(int t){
        this.online_team = t;
    }

    public int getCostume() {
        Object o = MultiplayerConstants.COSTUMES.get(costume);
        return (Integer)o;
    }

    public int getGunId() {
        return this.gun;
    }

    public void setGunId(int g) {
        this.gun = g;
    }

    public void refresh(ArrayList datos) {
        // TODO: completar refresh
        player.setPhysicsLocation((Vector3f)datos.get(0));
        player.setViewDirection((Vector3f)datos.get(1));
        player.setWalkDirection((Vector3f)datos.get(2));
        //hacer accion
    }
    
    /**
     * Retornar (Vector3f position, Vector3f view, Vector3f direction, int action, int user_id)
     * 
     * @return 
     */
    public ArrayList getRefresh(){
        // TODO: retornar dades getRefresh
        ArrayList<Object> datos = new ArrayList<Object>();
        datos.add(player.getPhysicsLocation());
        datos.add(player.getViewDirection());
        datos.add(player.getWalkDirection());
        //datos.add("accion");
        return datos;
    }

    public boolean isPlayer(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isTeam(int team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
