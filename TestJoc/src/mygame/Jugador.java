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

import multiplayer.MultiplayerConstants;


/**
 *
 * @author JORGE
 */
public class Jugador{
    
    /*
     * (Modificado por Marc Bolaños)
     * 
     * Las armas y modelos de los personajes estan referenciados 
     * en MultiplayerConstants.
     * 
     * Falta añadir los siguientes atributos para el juego online:
     *      int action;     // describe la acción que està realizando el jugador.
     */
    private Node character;
    private Node robot;
    private CharacterControl player;
    private CapsuleCollisionShape capsuleShape;
    
    private int online_id; 
    private int online_team; // Id del equipo al que pertenece
    private int costume; // Modelo del jugador
    
    private int vida, escudo, TOTAL_GUNS=10, actualGuns=0, gun=0;
    
    private Arma[] armas= new Arma[TOTAL_GUNS];

    private int count=1; // contador per les armes
    
    private boolean sniperMode = false;
    
    /* Indica si el modelo del player se ha inicializado. */
    private boolean initialized = false;
    
    /* Ens indiquen si s'ha inicialitzat el món i la info del WelcomeMessage */
    private boolean initWorld = false;
    private boolean initWelcome = false;
    
    

    public Jugador(int costume, int team){
        vida = 100;
        escudo = 100;
        
        this.costume = costume;
        this.online_team = team;
        
    }
    
    public void init(AssetManager assetManager, Vector3f pos, Vector3f view){
        
         
        capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);
        player.setGravity(60);
        
        assetManager.registerLocator("oto.zip", ZipLocator.class);   
        
        character = new Node();
        character.addControl(player);
        player.setPhysicsLocation(pos);
        player.setViewDirection(view);
        
        robot = (Node)assetManager.loadModel((String)MultiplayerConstants.COSTUMES.get(costume));
        robot.setName("robot");
        robot.setLocalScale(0.5f);
        robot.setLocalTranslation(new Vector3f(0, 10, 0));
        robot.addControl(player);
        
        armas[0] = new Arma(assetManager, MultiplayerConstants.PSG_WEAPON, new Vector3f(-2.0f,-1.5f,3.5f), "Sounds/Effects/Guns/shotgun-old_school.ogg");
        armas[0].setWeaponType("sniper");
        armas[0].rotate(45.45f,0.0f, 0.0f);
        armas[0].setScale(0.14f);
        armas[1] = new Arma(assetManager, MultiplayerConstants.GLOCK_WEAPON,  new Vector3f(-2.8f,-1.4f,5.8f), "Sounds/Effects/Guns/shot_m9.ogg");
        armas[1].rotate(124.0f, 0.0f, 0.0f);
        armas[1].setScale(0.07f);
        
    }
    
    
    public void respawn(){
        
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
        datos.add(0);
        datos.add(this.online_id);
        //datos.add("accion");
        return datos;
    }

    public boolean isPlayer(int id) {
        return this.online_id == id;
    }

    public boolean isTeam(int team) {
        return this.online_team == team;
    }
    
    public boolean getInitialized(){
        return initialized;
    }
    
    public void setInitialized(boolean init){
        initialized = init;
    }
    
    /**
     * Ens diu si el món de joc ja està inicialitzat.
     * 
     * @return 
     */
    public boolean getInitWorld(){
        return initWorld;
    }
    
    public void setInitWorld(boolean i){
        initWorld = i;
    }
    
    /**
     * Ens diu si ja s'ha completat el llistat de players online i offine del WelcomeMessage
     * 
     * @return 
     */
    public boolean getInitWelcome(){
        return initWelcome;
    }
    
    
    public void setInitWelcome(boolean i){
        initWelcome = i;
    }
    
}
