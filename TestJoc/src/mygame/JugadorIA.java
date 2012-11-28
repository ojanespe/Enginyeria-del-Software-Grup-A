/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import multiplayer.MultiplayerConstants;

/**
 *
 * @author alexpardofernandez
 */
public class JugadorIA implements PhysicsCollisionListener{
    
    Jugador main_player;
    
     private CharacterControl player;        

    private int vida, escudo, TOTAL_GUNS=3, actualGuns=0, gun=0;
    private float posX, posY, posZ;
    private CapsuleCollisionShape capsuleShape;
    private Arma[] armas= new Arma[TOTAL_GUNS];
    private Node character;    
    private boolean sniperMode = false;
    
    private Node robot;
    
    
    public AnimChannel animationChannel;
    public AnimChannel attackChannel;
    public AnimControl animationControl;


    public JugadorIA(AssetManager assetManager){
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

        
        robot = (Node)assetManager.loadModel("Oto.mesh.xml");
        robot.setName("otto");
        robot.setLocalScale(0.5f);
        robot.setLocalTranslation(new Vector3f(0, 10, 0));
        
        animationControl = robot.getControl(AnimControl.class);
        //animationControl.addListener(t);
        animationChannel = animationControl.createChannel();
        attackChannel = animationControl.createChannel();
        attackChannel.addBone(animationControl.getSkeleton().getBone("uparm.right"));
        attackChannel.addBone(animationControl.getSkeleton().getBone("arm.right"));
        attackChannel.addBone(animationControl.getSkeleton().getBone("hand.right"));
        
        
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

    public void collision(PhysicsCollisionEvent event) {
       vida -= 20;
       if(vida <= 0){
           player.jump();
           player.destroy();
       }
    }
    
}
