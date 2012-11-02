/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import sound.SoundManager;

/**
 * Carrega del openBox amb els dos tipus de cub solids.
 */
public class testJoc extends SimpleApplication
        implements ActionListener {
    
  private SoundManager soundManager;
  private Material stone_mat;
  private RigidBodyControl ball_phy;
  private static final Sphere sphere;
  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private Jugador s;
  private PantallaPrimeraPersona ps;
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false, rotate=false;
  private CameraNode cameraNode;
 
  public static void main(String[] args) {
    testJoc app = new testJoc();
    app.start();
  }

  static{
      sphere = new Sphere(24, 24, 0.1f, true, false);
      sphere.setTextureMode(TextureMode.Projected);
  }
  
  public void simpleInitApp() {
    // Set up the sound
    soundManager = new SoundManager(assetManager, rootNode);
    soundManager.playAmbientSound("Sounds/Ambient/fog_bound.ogg", 3);
    
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);

    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    flyCam.setMoveSpeed(100);
    setUpKeys();
    setUpLight();
    
    // We load the scene from the zip file and adjust its size.
    sceneModel = assetManager.loadModel("Scene/Estacio/estacio0_4.scene");
    sceneModel.setLocalScale(8f);
    


    /*Spatial cube1 = assetManager.loadModel("Models/Psg/PSG_ANIMADA.j3o");
    cube1.setLocalScale(0.5f);
    cube1.setLocalTranslation(10f, 10f, 0f);*/
    
    //Spatial cube2 = assetManager.loadModel("Models/cube2.mesh.xml");
    //cube2.setLocalScale(2f);
    //cube2.setLocalTranslation(10f, 25f, -30f);
    // We set up collision detection for the scene by creating a
    // compound collision shape and a static RigidBodyControl with mass zero.
    CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape((Node) sceneModel);
    landscape = new RigidBodyControl(sceneShape, 0);
    sceneModel.addControl(landscape);
    
    
   // RigidBodyControl cubeControl = new RigidBodyControl(5f);
    //cube1.addControl(cubeControl);
    
    //RigidBodyControl cube2Control = new RigidBodyControl(1f);
    //cube2.addControl(cube2Control);
    
    // We set up collision detection for the player by creating
    // a capsule collision shape and a CharacterControl.
    // The CharacterControl offers extra settings for
    // size, stepheight, jumping, falling, and gravity.
    // We also put the player in its starting position.
    s = new Jugador(assetManager);

    // Cargamos el arma
    s.chooseGun(0);
    
    // Pantalla
    ps = new PantallaPrimeraPersona(assetManager, settings, guiFont);
    
    //Glock cam location
    //cam.setLocation(new Vector3f(0,3,-5));

    //Mlp location
    cam.setLocation(new Vector3f(-2.5f,-1.4f,-6));
    cam.lookAt(s.getArma().getGun().getLocalTranslation(), Vector3f.UNIT_Y);
    
    cameraNode = new CameraNode("camera", cam);
    cameraNode.attachChild(s.getNode());
    //Rotate the camNode to look at the target:
    // We attach the scene and the player to the rootnode and the physics space,
    // to make them appear in the game world.
    rootNode.attachChild(sceneModel);
    rootNode.attachChild(cameraNode);
    bulletAppState.getPhysicsSpace().add(landscape);
    bulletAppState.getPhysicsSpace().add(s.getNode());
    
    initMaterials();    
  }

  private void setUpLight() {
    // We add light so we see the scene
    AmbientLight al = new AmbientLight();
    al.setColor(ColorRGBA.White.mult(1.3f));
    rootNode.addLight(al);

    DirectionalLight dl = new DirectionalLight();
    dl.setColor(ColorRGBA.White);
    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
    rootNode.addLight(dl); /** Add fog to a scene */
  }

  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setUpKeys() {
    inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("Change", new KeyTrigger(KeyInput.KEY_Q));
    inputManager.addMapping("rotateRight", new MouseAxisTrigger(MouseInput.AXIS_X, true));
    inputManager.addMapping("rotateLeft", new MouseAxisTrigger(MouseInput.AXIS_X, false));
    inputManager.addListener(this, "shoot");
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
    inputManager.addListener(this, "Change");
    inputManager.addListener(this, "rotateRight", "rotateLeft");
  }

  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("rotateRight")) {
        /*Vector3f vec1= new Vector3f();
        vec1.x=s.getArma().getLocation().x + walkDirection.x;
        vec1.y=s.getArma().getLocation().y;
        vec1.z=s.getArma().getLocation().z;
      s.getArma().updateGun(vec1);*/
    }
    if (binding.equals("rotateLeft")) {
      //s.getArma().getGun().setLocalTranslation(s.getArma().getLocation().x + walkDirection.x,s.getArma().getLocation().y + walkDirection.y,s.getArma().getLocation().z + walkDirection.z);
    }
    if (binding.equals("Left")) {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } else if (binding.equals("Jump")) {
      s.getPlayer().jump();
    } else if (binding.equals("shoot")) {
      s.incremenDisparos();
      s.setVida(s.getVida()-1);
      //makeCannonBall();
      //soundManager.play(gun.getShotSound(), 1);
      soundManager.playSituationalSound("Sounds/Effects/shot_m9.ogg", 1);
    } else if (binding.equals("Change")) {
       s.changeArm();       
       //cam.setLocation(new Vector3f(-2.5f,-1.4f,-6));
       cam.lookAt(s.getArma().getGun().getLocalTranslation(), Vector3f.UNIT_Y);
       // Sound of weapon change
    }

  }
  
  
  
  public void initMaterials(){
    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);    
    stone_mat.setTexture("ColorMap", tex2);
    
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
  }

  
  public void makeCannonBall() {
    /** Create a cannon ball geometry and attach to scene graph. */
    Geometry ball_geo = new Geometry("cannon ball", sphere);
    ball_geo.setMaterial(stone_mat);
    rootNode.attachChild(ball_geo);
    /** Position the cannon ball  */
    ball_geo.setLocalTranslation(cam.getLocation());
    /** Make the ball physcial with a mass > 0.0f */
    ball_phy = new RigidBodyControl(1f);
    /** Add physical ball to physics space. */
    ball_geo.addControl(ball_phy);
    bulletAppState.getPhysicsSpace().add(ball_phy);
    /** Accelerate the physcial ball to shoot it. */
    ball_phy.setLinearVelocity(cam.getDirection().mult(25));
  }
 
  /** A plus sign used as crosshairs to help the player with aiming.*/
  protected void initCrossHairs() {
    /*guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 5);
    ch.setText("+");        // fake crosshairs :)
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
      settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);*/

  }
  
  protected void refrexCrossHairs() {    
    // Texto puntero
    guiNode.attachChild(ps.getcruzPuntero());
    
    // Texto disparos
    ps.setTextDisparos(s.getDisparos());
    guiNode.attachChild(ps.getDisparos());
    
    //Texto vida
    ps.setTextVida(s.getVida());
    guiNode.attachChild(ps.getVida());
    
    //Texto escudo
    ps.setTextEscudo(s.getEscudo());
    guiNode.attachChild(ps.getDisparos());
        
    //Imagen Cruz Vida
    guiNode.attachChild(ps.getPicVida(s.getVida(), assetManager));
    
    //Imagen Escudo
    guiNode.attachChild(ps.getPicEscudo());
    
    //Imagen Bala
    guiNode.attachChild(ps.getPicBala());
    
    //Imagen Bala
    guiNode.attachChild(ps.getBlood());   
    
  }        
  
  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  
  @Override
  public void simpleUpdate(float tpf) {
    Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
    Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
    walkDirection.set(0, 0, 0);
    if (left)  { walkDirection.addLocal(camLeft); }
    if (right) { walkDirection.addLocal(camLeft.negate()); }
    if (up)    { walkDirection.addLocal(camDir); }
    if (down)  { walkDirection.addLocal(camDir.negate());}
    //if (rotate)  { s.getArma().rotate(0, 5 * tpf, 0); }
    s.getPlayer().setWalkDirection(walkDirection);
    
    s.getGun().lookAt(s.getPlayer().getWalkDirection(), cam.getUp());
    cameraNode.setLocalRotation(cam.getRotation());
    cameraNode.setLocalTranslation(s.getPlayer().getPhysicsLocation());
    
    cam.setLocation(s.getPlayer().getPhysicsLocation()); 
    //System.out.println(cam.getDirection());
    //s.getGun().rotateUpTo(cam.getDirection());
    refrexCrossHairs();
    
    // Movement sound
    if (up || down) {
        soundManager.playSituationalSound("Sounds/Effects/Movement/paso_caminando.ogg", 1);
    }
  }
}
