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

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Carrega del openBox amb els dos tipus de cub solids.
 */
public class testJoc extends SimpleApplication
        implements AnimEventListener,ActionListener, PhysicsCollisionListener {

  private Material stone_mat;
  private RigidBodyControl    ball_phy;
  private static final Sphere sphere;
  private Spatial cube2;
  int z = 10;
  RigidBodyControl cube2Control;
  RigidBodyControl botControl;
  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl player;
  private CharacterControl jambo;
  private CharacterControl jambo2;
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false;

  private AnimChannel channel_walk;
  private AnimChannel channel_walk2;
  private AnimControl bot;
  private AnimControl bot2;
  private Geometry geom;
  Spatial BotTest;
  Node robot;
  Vector3f vista;
  
  
  private ChaseCamera chaseCam;

  
  
  private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if(name.equals("Walk") && !keyPressed){
                if(!channel_walk.getAnimationName().equals("Walk")){
                    channel_walk.setLoopMode(LoopMode.Loop);
                    channel_walk.setAnim("Walk", 0.5f);
                    channel_walk.setSpeed(1.5f);
                    //player.setWalkDirection(new Vector3f(0,0,0.1f));
                    //jambo.setViewDirection(new Vector3f(0,0,1f));
                    
                }
            }
        }
    };
  
  public static void main(String[] args) {
    testJoc app = new testJoc();
    app.start();

  }

  static{
      sphere = new Sphere(32, 32, 0.4f, true, false);
      sphere.setTextureMode(TextureMode.Projected);
  }
  public void simpleInitApp() {
    /** Set up Physics */
    
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);

    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    flyCam.setMoveSpeed(100);
    //flyCam.setEnabled(false);
    vista = new Vector3f(Vector3f.UNIT_Y);
    
    
    setUpKeys();
    setUpLight();

    // We load the scene from the zip file and adjust its size.
    assetManager.registerLocator("openBox.zip", ZipLocator.class);
    sceneModel = assetManager.loadModel("Cube.mesh.xml");
    sceneModel.setLocalScale(8f);
    sceneModel.setName("caja");
    assetManager.registerLocator("oto.zip", ZipLocator.class);
    
    
    
    /*CapsuleCollisionShape capsuleShape2 = new CapsuleCollisionShape(3f, 3f, 0);
    jambo = new CharacterControl(capsuleShape2, 0.5f);
    
    
    //robot.addControl(jambo);
    
    jambo.setPhysicsLocation(new Vector3f(10f, 6.5f, 0f));
    
    bulletAppState.getPhysicsSpace().add(jambo);
    
    assetManager.registerLocator("character.zip", ZipLocator.class);
    
    CapsuleCollisionShape capsuleShape3 = new CapsuleCollisionShape(3f, 3f, 0);
    /*jambo2 = new CharacterControl(capsuleShape3, 0.5f);
    
    Node Modelo2 = (Node) assetManager.loadModel("character.mesh.xml");
    Modelo2.setName("jamboloco2");
    Modelo2.setLocalScale(2f);
    
    
    jambo2.setPhysicsLocation(new Vector3f(20f, 20f, 0f));
    jambo2.setViewDirection(new Vector3f(0,1f,0));
    rootNode.attachChild(Modelo2);
    bulletAppState.getPhysicsSpace().add(jambo2);*/
    
      
    
    /*BotTest = assetManager.loadModel("Oto.mesh.xml");
    BotTest.setLocalScale(0.5f);
    BotTest.setLocalTranslation(10f, 6.5f, 10f)
    bot2 = Modelo2.getControl(AnimControl.class);
    bot2.addListener(this);
    channel_walk2 = bot2.createChannel();
    channel_walk2.setAnim("run_01");;*/
    
     //for (String anim : bot2.getAnimationNames()){
     //       System.out.println(anim);}
    
    
    /*geom = (Geometry)((Node)BotTest).getChild(0);
    SkeletonControl skeletonControl = BotTest.getControl(SkeletonControl.class);
    Box b = new Box(.25f,3f,.25f);
    Geometry item = new Geometry("Item", b);
    item.move(0, 1.5f, 0);
    item.setMaterial(assetManager.loadMaterial("Common/Materials/RedColor.j3m"));
    Node n = skeletonControl.getAttachmentsNode("hand.right");
    n.attachChild(item);
    botControl = new RigidBodyControl(10f);
    
    BotTest.addControl(botControl);*/

    
    
    
    
    
    Spatial cube1 = assetManager.loadModel("Models/cube.j3o");
    cube1.setName("cube1");
    cube1.setLocalScale(3f);
    cube1.setLocalTranslation(10f, 7f, 20f);
    
    cube2 = assetManager.loadModel("Models/cube.j3o");
    cube2.setName("cube2");
    cube2.setLocalScale(3f);
    cube2.setLocalTranslation(10f, 7f, -20f);
    
    
    /*Node botNode = (Node) assetManager.loadModel("Oto.mesh.xml"); // load a model
    bot = botNode.getControl(AnimControl.class); // get control over this model
    
    bot.addListener(this); // add listener
    channel_walk = bot.createChannel();*/
    
    inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_U));
    inputManager.addListener(actionListener, "Walk");
    // We set up collision detection for the scene by creating a
    // compound collision shape and a static RigidBodyControl with mass zero.
    CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape((Node) sceneModel);
    landscape = new RigidBodyControl(sceneShape, 0);
    sceneModel.addControl(landscape);
    
    
    RigidBodyControl cubeControl = new RigidBodyControl(10f);
    cube1.addControl(cubeControl);
    
    cube2Control = new RigidBodyControl(10f);
    
    cube2.addControl(cube2Control);
    //cube2Control.setLinearVelocity(new Vector3f(0,0,10));
    // We set up collision detection for the player by creating
    // a capsule collision shape and a CharacterControl.
    // The CharacterControl offers extra settings for
    // size, stepheight, jumping, falling, and gravity.
    // We also put the player in its starting position.
    
    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
    player = new CharacterControl(capsuleShape, 0.05f);
    player.setJumpSpeed(20);
    player.setFallSpeed(60);
    player.setGravity(60);
    player.setPhysicsLocation(new Vector3f(0, 10, 0));
    
    robot = (Node)assetManager.loadModel("Oto.mesh.xml");
    robot.setName("jamboloco");
    robot.setLocalScale(0.5f);
    robot.setLocalTranslation(new Vector3f(0, 10, 0));
    bot = robot.getControl(AnimControl.class);
    
    bot.addListener(this);
    channel_walk = bot.createChannel();
    
    channel_walk.setAnim("stand");
    
    robot.addControl(player);
    
    
    
    //chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
    // We attach the scene and the player tthe rootnode and the physics space,
    // to make them appear in the game world.
    rootNode.attachChild(sceneModel);
    rootNode.attachChild(cube1);
    rootNode.attachChild(cube2);
    rootNode.attachChild(robot);
    //rootNode.attachChild(BotTest);
    bulletAppState.getPhysicsSpace().add(landscape);
    bulletAppState.getPhysicsSpace().add(player);
    bulletAppState.getPhysicsSpace().add(cubeControl);
    bulletAppState.getPhysicsSpace().add(cube2Control);
    bulletAppState.getPhysicsSpace().addCollisionListener(this);
    
    initMaterials();

    initCrossHairs();
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
    inputManager.addMapping("Die", new KeyTrigger(KeyInput.KEY_M));
    inputManager.addListener(this, "Die");
    inputManager.addListener(this, "shoot");
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
  }

  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Left")) {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } else if (binding.equals("Jump")) {
      player.jump();
    }else if (binding.equals("shoot") ) {
        makeCannonBall();vistaMorirse();
        
    }else if (binding.equals("Die") ) {
        vistaMorirse();
        
    }
  }
  
  public void initMaterials(){
    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);
    stone_mat.setTexture("ColorMap", tex2);
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
//      System.out.println(cam.getDirection());
//      System.out.println(cam.getLocation());
//      System.out.println("---------");
    ball_phy.setLinearVelocity(cam.getDirection().mult(25));
  }
  
  public void moverCubo(){
      
          cube2Control.setLinearVelocity(new Vector3f(10,0,z));
          
          
      
  }
 
  /** A plus sign used as crosshairs to help the player with aiming.*/
  protected void initCrossHairs() {
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");        // fake crosshairs :)
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
      settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);
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
    if (down)  { walkDirection.addLocal(camDir.negate()); }
    player.setWalkDirection(walkDirection);
    player.setViewDirection(player.getWalkDirection());
    
    Vector3f camara3p = player.getPhysicsLocation();
    camara3p.y+=4;
    cam.setLocation(camara3p);
    //cam.lookAt(camara3p, Vector3f.UNIT_Y);
    fpsText.setText(vista+"");
    
  }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("penis")){
            channel.setAnim("stand", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
            jambo.setWalkDirection(new Vector3f(0,0,0));
        }

    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }

    public void collision(PhysicsCollisionEvent event) {
        try{
        if("cube1".equals(event.getNodeA().getName()) || "cube1".equals(event.getNodeB().getName()) || "cube2".equals(event.getNodeA().getName()) || "cube2".equals(event.getNodeB().getName())){
            if("jamboloco".equals(event.getNodeA().getName()) || "jamboloco".equals(event.getNodeB().getName())){
                if(jambo.getWalkDirection().z == 0.1f){
                    jambo.setWalkDirection(new Vector3f(0,0,-0.1f));
                    jambo.setViewDirection(new Vector3f(0,0,-1f));
                }else{
                    jambo.setWalkDirection(new Vector3f(0,0,0.1f));
                    jambo.setViewDirection(new Vector3f(0,0,1f));
                }
        }
        
    }
    }catch(Exception e){
        
    }
    }
    
    public void vistaMorirse(){
        chaseCam = new ChaseCamera(cam, robot, inputManager);
        chaseCam.setSmoothMotion(true);
        chaseCam.setDragToRotate(true); 
    }
}