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
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.ZipLocator;
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
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import menu.MenuPrincipal;
import multiplayer.MultiplayerConstants;
import sound.SoundManager;

/**
 * Carrega del openBox amb els dos tipus de cub solids.
 */
public class testJoc extends SimpleApplication
        implements ActionListener,AnimEventListener {
  
  private AnimChannel channel;
  private AnimControl control;
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
  private boolean left = false, right = false, up = false, down = false, rotate=false, click=false;
  private CameraNode cameraNode;
  private Node shootables;
  private ShotCollision collision;
  private boolean terceraPersona = false;
  private boolean estadoAnteriorVista = false;
  private ModelActionManager MAM;
  private MenuPrincipal menuPrincipal;
  private boolean mundoInicializado = false;
  private JugadorIA otto;
  
  public static void main(String[] args) {
    testJoc app = new testJoc();
    app.start();
  }

  static{
      sphere = new Sphere(24, 24, 0.1f, true, false);
      sphere.setTextureMode(TextureMode.Projected);
  }
  
  public void simpleInitApp() {
    
    //Menu Principal
    menuPrincipal  = new MenuPrincipal(this);
    stateManager.attach(menuPrincipal);
    inicializarMundo();
    
  }
  
  public void inicializarMundo() {
    // Set up the sound
    soundManager = new SoundManager(assetManager, rootNode);
    
    collision = new ShotCollision(rootNode, assetManager);
    
//    collision.setShotable(makeCube("c1", -2f, 1f, 2f));
//    collision.setShotable(makeCube("c2", 0f, 1f, 0f));
//    collision.setShotable(makeCube("c3", 5f, 1f, 1f));
//    collision.setShotable(makeCube("c4", 10f, 1f, -4f));
//    collision.setShotable(makeFloor());
    
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
//    assetManager.registerLocator("town.zip", ZipLocator.class);
//    sceneModel = assetManager.loadModel("main.scene");
//    sceneModel.setLocalScale(2f);
    sceneModel = assetManager.loadModel("Scene/Estacio/estacio2.j3o");
    sceneModel.setLocalScale(3f);
    


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
    otto = new JugadorIA(assetManager);
    // Cargamos el arma
    s.chooseGun(2);
    
    
//    
//    /************************************************************************************************/
//    /*CREAMOS UN MODEL ACTION MANAGER*/
//    control = s.getRobot().getControl(AnimControl.class);
//    control.addListener(this);
//    ArrayList<Integer> actions = new ArrayList<Integer>();
//    actions.add(KeyInput.KEY_W);
//    actions.add(KeyInput.KEY_A);
//    actions.add(KeyInput.KEY_S);
//    actions.add(KeyInput.KEY_D);
//    MAM = new ModelActionManager(control, "Walk", 1.5f, actions);
//    
//    /*INSERTAMOS los listeners*/
//    for(int i = 0; i<actions.size();i++){
//        inputManager.addMapping(MAM.getAction(), MAM.getKT().get(i));
//    }
//    inputManager.addListener(MAM, MAM.getAction());
//    
//    /*INICIALIZAMOS EL CANAL DE LA ACCION*/
//    MAM.initChannel();
//    /************************************************************************************************/
//    
//    
//    // Pantalla
//    ps = new PantallaPrimeraPersona(assetManager, settings, guiFont);
//    
//    //Glock cam location
//    cam.setLocation(new Vector3f(0,3,-5));
//
//    //Mlp location
//    //cam.setLocation(new Vector3f(-2.5f,-1.4f,-6));
//    cam.lookAt(s.getArma().getGun().getLocalTranslation(), Vector3f.UNIT_Y);
//    
//    cameraNode = new CameraNode("camera", cam);
//    cameraNode.attachChild(s.getNode());
//    
//    
// /*   assetManager.registerLocator("oto.zip", ZipLocator.class);
//    
//    Node robot2 = (Node)assetManager.loadModel("Oto.mesh.xml");
//    robot2.setName("robot2");
//    robot2.setLocalScale(0.5f);
//    robot2.setLocalTranslation(new Vector3f(20, 3, 20));
//   */     
//    //Rotate the camNode to look at the target:
//    // We attach the scene and the player to the rootnode and the physics space,
//    // to make them appear in the game world.
//    rootNode.attachChild(sceneModel);
//    rootNode.attachChild(cameraNode);
//    rootNode.attachChild(robot2);
//    rootNode.attachChild(s.getNodeModel());
//    
//    rootNode.attachChild(otto.getNodeModel());
//    
//    collision.setShotable(otto.getNodeModel());
//    
//    bulletAppState.getPhysicsSpace().add(landscape);
//    bulletAppState.getPhysicsSpace().add(s.getNode());
//    bulletAppState.getPhysicsSpace().add(otto.getNode());
//    
////    control = s.getArma().getGun().getControl(AnimControl.class);
////    control = robot.getControl(AnimControl.class);
////    channel = control.createChannel();
////    channel.setAnim("Walk");
//    
//    /*AnimControl playerControl; // you need one Control per model
//    playerControl = robot2.getControl(AnimControl.class); // get control over this model
//    playerControl.addListener(this); // add listener
//    channel = playerControl.createChannel();
//    channel.setAnim("Walk");
//    
//    collision.setShotable(robot2);*/
//    initMaterials();
//    
//    // Play the ambient sound
//    soundManager.playAmbientSound();
  }
  
  private void attachWorld(){
      
    /************************************************************************************************/
    /*CREAMOS UN MODEL ACTION MANAGER*/
    control = s.getRobot().getControl(AnimControl.class);
    control.addListener(this);
    ArrayList<Integer> actions = new ArrayList<Integer>();
    actions.add(KeyInput.KEY_W);
    actions.add(KeyInput.KEY_A);
    actions.add(KeyInput.KEY_S);
    actions.add(KeyInput.KEY_D);
    MAM = new ModelActionManager(control, "Walk", 1.5f, actions);
    
    /*INSERTAMOS los listeners*/
    for(int i = 0; i<actions.size();i++){
        inputManager.addMapping(MAM.getAction(), MAM.getKT().get(i));
    }
    inputManager.addListener(MAM, MAM.getAction());
    
    /*INICIALIZAMOS EL CANAL DE LA ACCION*/
    MAM.initChannel();
    /************************************************************************************************/
    
    
    // Pantalla
    ps = new PantallaPrimeraPersona(assetManager, settings, guiFont);
    
    //Glock cam location
    cam.setLocation(new Vector3f(0,3,-5));

    //Mlp location
    //cam.setLocation(new Vector3f(-2.5f,-1.4f,-6));
    cam.lookAt(s.getArma().getGun().getLocalTranslation(), Vector3f.UNIT_Y);
    
    cameraNode = new CameraNode("camera", cam);
    cameraNode.attachChild(s.getNode());
    
    
 /*   assetManager.registerLocator("oto.zip", ZipLocator.class);
    
    Node robot2 = (Node)assetManager.loadModel("Oto.mesh.xml");
    robot2.setName("robot2");
    robot2.setLocalScale(0.5f);
    robot2.setLocalTranslation(new Vector3f(20, 3, 20));
   */     
    //Rotate the camNode to look at the target:
    // We attach the scene and the player to the rootnode and the physics space,
    // to make them appear in the game world.
    rootNode.attachChild(sceneModel);
    rootNode.attachChild(cameraNode);
    //rootNode.attachChild(robot2);
    rootNode.attachChild(s.getNodeModel());
    
    rootNode.attachChild(otto.getNodeModel());
    
    collision.setShotable(otto.getNodeModel());
    
    bulletAppState.getPhysicsSpace().add(landscape);
    bulletAppState.getPhysicsSpace().add(s.getNode());
    bulletAppState.getPhysicsSpace().add(otto.getNode());
    
//    control = s.getArma().getGun().getControl(AnimControl.class);
//    control = robot.getControl(AnimControl.class);
//    channel = control.createChannel();
//    channel.setAnim("Walk");
    
    /*AnimControl playerControl; // you need one Control per model
    playerControl = robot2.getControl(AnimControl.class); // get control over this model
    playerControl.addListener(this); // add listener
    channel = playerControl.createChannel();
    channel.setAnim("Walk");
    
    collision.setShotable(robot2);*/
    initMaterials();
    
    // Play the ambient sound
    soundManager.playAmbientSound();
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
    inputManager.addMapping("scope", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("Change", new KeyTrigger(KeyInput.KEY_Q));
    inputManager.addMapping("CambiarVista1per", new KeyTrigger(KeyInput.KEY_F1));
    inputManager.addMapping("CambiarVista3per", new KeyTrigger(KeyInput.KEY_F2));
    inputManager.addMapping("rotateRight", new MouseAxisTrigger(MouseInput.AXIS_X, true));
    inputManager.addMapping("rotateLeft", new MouseAxisTrigger(MouseInput.AXIS_X, false));
    inputManager.addListener(this, "shoot");
    inputManager.addListener(this, "scope");
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
    inputManager.addListener(this, "Change");
    inputManager.addListener(this, "rotateRight", "rotateLeft");
    inputManager.addListener(this, "CambiarVista1per");
    inputManager.addListener(this, "CambiarVista3per");
  }

  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
public void onAction(String binding, boolean isPressed, float tpf) {
    if (s.getVida() > 0) {
        if (binding.equals("scope") && !isPressed && s.getArma().getWeaponType().equals("sniper")) {
            float b = cam.getFrustumBottom();
            float t = cam.getFrustumTop();
            float l = cam.getFrustumLeft();
            float r = cam.getFrustumRight();
            float n = cam.getFrustumNear();
            float f = cam.getFrustumFar();

            if (!s.getSniperMode()) {
                guiNode.attachChild(ps.getScope());
                s.setSniperMode(true);
                cam.setFrustum(n, f, l * 0.25f, r * 0.25f, t * 0.25f, b * 0.25f);
                terceraPersona = false;
            } 
            else {
                guiNode.detachChild(ps.getScope());
                s.setSniperMode(false);
                cam.setFrustum(n, f, l * 4f, r * 4f, t * 4f, b * 4f);
                if(estadoAnteriorVista){
                    terceraPersona = true;  
                }
            }
        } else if (binding.equals("rotateRight")) {
            // Nada por ahora.. pero necesario??
        } else if (binding.equals("rotateLeft")) {
            // Nada por ahora.. pero necesario??
        } else if (binding.equals("CambiarVista1per")) {
            terceraPersona = false;
            estadoAnteriorVista = terceraPersona;
        } else if (binding.equals("CambiarVista3per")) {
            terceraPersona = true;
            estadoAnteriorVista = terceraPersona;
        } else if (binding.equals("Left")) {
          if (isPressed) { left = true; } else { left = false; }
        } else if (binding.equals("Right")) {
          if (isPressed) { right = true; } else { right = false; }
        } else if (binding.equals("Up")) {
          if (isPressed) { up = true; } else { up = false; }
        } else if (binding.equals("Down")) {
          if (isPressed) { down = true; } else { down = false; }
        } else if (binding.equals("Jump")) {
          s.getPlayer().jump();
        } else if (binding.equals("shoot")) {
          /*channel.setAnim("Walk",0.50f);
          channel.setLoopMode(LoopMode.DontLoop);
          channel.setSpeed(0.10f);*/
          if (!click) {
              click = true;
          } else {
            collision.shot(binding, isPressed, tpf, cam );
            s.incremenDisparos();
            click = false;
            soundManager.playInstance(s.getArma().getShotSound(), 1);
          }
          if (s.getEscudo() > 0) {
                s.setEscudo((s.getEscudo()-1));
          } else {
            s.setVida(s.getVida()-1);
          }

        } else if (binding.equals("Change") && !isPressed) {
           if (s.getSniperMode()) {
               guiNode.detachChild(ps.getScope());
               s.setSniperMode(false);
               float b = cam.getFrustumBottom();
               float t = cam.getFrustumTop();
               float l = cam.getFrustumLeft();
               float r = cam.getFrustumRight();
               float n = cam.getFrustumNear();
               float f = cam.getFrustumFar();
               cam.setFrustum(n, f, l * 4f, r * 4f, t * 4f, b * 4f);
           }
           s.changeArm();
           // Sound of weapon change
           soundManager.playEffectSound(SoundManager.WEAPON_CHANGE);
        }
    }
}
  
/**
Mètode que serveix per buscar un node amb el nom especificat
*/
private Spatial findNode(Node rootNode, String name) {
    if (name.equals(rootNode.getName())) {
        return rootNode;
    }
    return rootNode.getChild(name);
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
    if (s.getVida() > 0) {
        guiNode.attachChild(ps.getPicVida(s.getVida(), assetManager));
    } else {
        ps.setTextDie();
        guiNode.attachChild(ps.getDie());        
    }
    //Imagen Escudo
    guiNode.attachChild(ps.getPicEscudo(s.getEscudo(), assetManager));
    
    //Imagen Bala
    guiNode.attachChild(ps.getPicBala());
    
    //Imagen Bala
    guiNode.attachChild(ps.getBlood());   
    
    //Scope
    if(s.getSniperMode()) {
        guiNode.attachChild(ps.getScope());
    }
    
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
      
     if (menuPrincipal.getIsRunningMenuPrincipal()/* && !stateManager.hasState(menuPrincipal)*/){
        //stateManager.detach(bulletAppState);
        stateManager.attach(menuPrincipal);
     } else {
         //stateManager.attach(bulletAppState);
        stateManager.detach(menuPrincipal);
     
        if(!mundoInicializado) {
            mundoInicializado = true;
            attachWorld();
        }
        
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
    
        if (s.getVida() > 0) {
            if (left)  { walkDirection.addLocal(camLeft); }
            if (right) { walkDirection.addLocal(camLeft.negate()); }
            if (up)    { walkDirection.addLocal(camDir); }
            if (down)  { walkDirection.addLocal(camDir.negate());}
            //if (rotate)  { s.getArma().rotate(0, 5 * tpf, 0); }
        }
        if(!terceraPersona){
            s.getPlayer().setWalkDirection(new Vector3f(walkDirection.x,0,walkDirection.z)); //Para no cambiar la Y del modelo

            cameraNode.setLocalRotation(cam.getRotation());
            Vector3f camara3p = s.getPlayer().getPhysicsLocation(); // Colocar la camara en vista primera 1a
            camara3p.y+=0.9f;
            cameraNode.setLocalTranslation(camara3p);

            cam.setLocation(s.getPlayer().getPhysicsLocation());
            Vector3f viewDirection = new Vector3f(cam.getDirection().x,0,cam.getDirection().z); // El modelo mira hacia donde esta mirando el jugador
            s.getPlayer().setViewDirection(viewDirection);
            //System.out.println(cam.getDirection());
            //s.getGun().rotateUpTo(cam.getDirection());        
        
        }else{ // Vista en 3a persona
            s.getPlayer().setWalkDirection(new Vector3f(walkDirection.x,0,walkDirection.z));
            otto.getPlayer().setWalkDirection(new Vector3f(walkDirection.x,0,walkDirection.z));
            cameraNode.setLocalRotation(cam.getRotation());
            
            Vector3f camara3p = s.getPlayer().getPhysicsLocation();
            camara3p.y+=5;
            camara3p.z-=6*cam.getDirection().z;
            camara3p.x-=6*cam.getDirection().x;
            cameraNode.setLocalTranslation(camara3p);
            cam.setLocation(camara3p);
            Vector3f viewDirection = new Vector3f(cam.getDirection().x,0,cam.getDirection().z);
            s.getPlayer().setViewDirection(viewDirection);
        }
        refrexCrossHairs();
        // Movement sound
        if (up || down || right || left) {
            soundManager.playEffectSound(SoundManager.MOVEMENT);
        }
     }
  }
  
  protected Geometry makeCube(String name, float x, float y, float z) {
    Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
    Geometry cube = new Geometry(name, box);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.randomColor());
    cube.setMaterial(mat1);
    return cube;
  }
  
  
  /** A floor to show that the "shot" can go through several objects. */
  protected Geometry makeFloor() {
    Box box = new Box(new Vector3f(0, -4, -5), 15, .2f, 15);
    Geometry floor = new Geometry("the Floor", box);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.Gray);
    floor.setMaterial(mat1);
    return floor;
  }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {

    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        MAM.onAnimCycleDone(MAM.getAction());
    }
}
