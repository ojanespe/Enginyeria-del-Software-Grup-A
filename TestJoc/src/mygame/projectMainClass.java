/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
public class projectMainClass extends SimpleApplication
implements ActionListener {

private BulletAppState bulletAppState;
private RigidBodyControl landscape;
private CharacterControl player;
private CharacterControl enemy1_phy;
private CharacterControl enemy2_phy;
private Vector3f walkDirection = new Vector3f();
private boolean left = false, right = false, up = false, down = false;
private Node shootables;
private BitmapText scoreText;
private BitmapText winText;
private MotionPath path;
private MotionPath path2;
private Node inventory;
private Geometry myTarget;
private AnimChannel channel;
private AnimControl control;
private AnimChannel channel2;
private AnimControl control2;
private Node enemy1;
private Node enemy2;
private int y;
private AudioNode audio_gun;
private AudioNode audio_nature;
private MotionEvent motionControl;

public static void main(String[] args) {
projectMainClass app = new projectMainClass();
app.start();
}
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);
        initCrossHairs(); // a "+" in the middle of the screen to help aiming
        initScore();
        initAudio();
        initWin();
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        shootables.attachChild(makeCube(
        "a Dragon", -20f, -15f, 70f));
shootables.attachChild(makeCube2(
        "a tin can", 20f, -15f, -70f));
shootables.attachChild(makeCube3(
        "the Sheriff", -50f, -15f, -60f));
shootables.attachChild(makeCube4(
        "the Deputy", 30f, -15f, 40f));
/** Set up Physics */
bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);

// We re-use the flyby camera for rotation, while positioning is handled by physics
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        flyCam.setMoveSpeed(100);
        setUpKeys();
        setUpLight();

// We load the scene from the zip file and adjust its size.
//        Spatial sceneModel = assetManager.loadModel(
//        "Scenes/wildhouse/main.scene");
//sceneModel.setLocalScale(2f);

//        assetManager.registerLocator("town.zip", ZipLocator.class);
//    Spatial sceneModel = assetManager.loadModel("main.scene");
    
    assetManager.registerLocator("http://jmonkeyengine.googlecode.com/files/wildhouse.zip", 
                             HttpZipLocator.class);
Spatial sceneModel = assetManager.loadModel("main.scene");
// We set up collision detection for the scene by creating a
// compound collision shape and a static RigidBodyControl with mass zero.
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) sceneModel);
        landscape = new RigidBodyControl(sceneShape, 0);
        sceneModel.addControl(landscape);
        landscape.setFriction(1.0f);
// We set up collision detection for the player and the enemies by creating
// three capsule collision shapes and three CharacterControls.
// We also put the player and the enemies in their starting positions.
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        CapsuleCollisionShape capsuleShape2 = new CapsuleCollisionShape(1.5f, 2f, 1);
        CapsuleCollisionShape capsuleShape3 = new CapsuleCollisionShape(1.5f, 2f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(new Vector3f(0, 5, -40));

        enemy1 = (Node) assetManager.loadModel(
        "Models/Oto/Oto.mesh.xml");
enemy1_phy = new CharacterControl(capsuleShape2, 1f);
        enemy1.addControl(enemy1_phy);
        enemy1_phy.setPhysicsLocation(new Vector3f(10, 5, 0));
        enemy1.setLocalScale(0.5f);
        enemy1.setLocalTranslation(1.0f, -15.0f, 30.0f);
        shootables.attachChild(enemy1);
        control = enemy1.getControl(AnimControl.class);
        for (String anim : control.getAnimationNames()) {
            System.out.println(anim);
        }
        channel = control.createChannel();
        channel.setAnim(
        "Walk");

enemy2 = (Node) assetManager.loadModel(
        "Models/Ninja/Ninja.mesh.xml");
/*Because the pivot point of the ninja model is at its feet, we attach the ninja model to a node in order to position him
lower on the node so that when we attach the node to the capsule the ninja will be in the center of the capsule*/
Node enemy2_node = new Node(
        "Model holder");
enemy2_node.attachChild(enemy2);
        enemy2.rotate(0, 5, 0);
        enemy2.move(0, -3f, 0);
        enemy2_phy = new CharacterControl(capsuleShape3, 1f);
        enemy2_node.addControl(enemy2_phy);
        enemy2_phy.setPhysicsLocation(new Vector3f(-20, 5, -20));
        enemy2.setLocalScale(0.03f);
        shootables.attachChild(enemy2_node);
        control2 = enemy2.getControl(AnimControl.class);
        for (String anim : control2.getAnimationNames()) {
            System.out.println("Ninja ="+anim); 
        }
channel2 = control2.createChannel();
        channel2.setAnim(
        "Walk");

//We create a fire on the house to add an effect.
ParticleEmitter fire =
                new ParticleEmitter(
        "Emitter", ParticleMesh.Type.Triangle
        , 30);
Material mat_red = new Material(assetManager,

        "Common/MatDefs/Misc/Particle.j3md");
mat_red.setTexture(
        "Texture", assetManager.loadTexture(

        "Effects/Explosion/flame.png"));
fire.setMaterial(mat_red);
        fire.setImagesX(2);
        fire.setImagesY(2); // 2×2 texture animation
        fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f)); // red
        fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fire.setStartSize(1.5f);
        fire.setEndSize(0.1f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(1f);
        fire.setHighLife(3f);
        fire.getParticleInfluencer().setVelocityVariation(0.3f);
        fire.move(5, -16f, 5);
        rootNode.attachChild(fire);

        path = new MotionPath();
        path.addWayPoint(new Vector3f(-35, -30, -25));
        path.addWayPoint(new Vector3f(-35, -30, -10));
        path.addWayPoint(new Vector3f(-85, -30, -10));
        path.addWayPoint(new Vector3f(-85, -30, -20));
        path.addWayPoint(new Vector3f(-85, -30, -20));
        path.addWayPoint(new Vector3f(-35, -30, -20));
        path.addWayPoint(new Vector3f(-35, -30, -10));
        path.addWayPoint(new Vector3f(-35, -30, -10));
        path.addWayPoint(new Vector3f(-35, -30, -25));
        path.enableDebugShape(assetManager, rootNode);

        motionControl = new MotionEvent(enemy2_node, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(0.5f);

        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
            }
        });
        motionControl.play();
        motionControl.setLoopMode(LoopMode.Loop);

        path2 = new MotionPath();
        path2.addWayPoint(new Vector3f(10, -14.2f, 0));
        path2.addWayPoint(new Vector3f(10, -14.2f, 10));
        path2.addWayPoint(new Vector3f(0, -14.2f, 10));
        path2.addWayPoint(new Vector3f(0, -14.2f, 0));
        path2.addWayPoint(new Vector3f(10, -14.2f, 0));
        path2.enableDebugShape(assetManager, rootNode);

        motionControl = new MotionEvent(enemy1, path2);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(0.5f);
        guiFont = assetManager.loadFont(
        "Interface/Fonts/Default.fnt");

path2.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
            }
        });
        motionControl.play();
        motionControl.setLoopMode(LoopMode.Loop);

// We attach the scene and the player to the rootNode and the physics space,
// to make them appear in the game world.
        rootNode.attachChild(sceneModel);
        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(player);
        bulletAppState.getPhysicsSpace().add(enemy1);
        bulletAppState.getPhysicsSpace().add(enemy2_node);
    }
/** A centred plus sign to help the player aim. */
protected void initCrossHairs() {
guiNode.detachAllChildren();
guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
BitmapText ch = new BitmapText(guiFont, false);
ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
ch.setText("+"); // crosshairs
ch.setLocalTranslation(settings.getWidth()/2 - guiFont.getCharSet().getRenderedSize()/3 * 2,settings.getHeight()/2 + ch.getLineHeight()/2, 0);
guiNode.attachChild(ch);
}
/** Four cube objects to pick them up in the game */
protected Geometry makeCube(String name, float x, float y, float z) {
Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
Geometry cube = new Geometry(name, box);
Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
mat.setColor("Color", ColorRGBA.Pink);
cube.setMaterial(mat);
return cube;
}
protected Geometry makeCube2(String name, float x, float y, float z) {
Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
Geometry cube2 = new Geometry(name, box);
Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
mat1.setColor("Color", ColorRGBA.Red);
cube2.setMaterial(mat1);
return cube2;
}
protected Geometry makeCube3(String name, float x, float y, float z) {
Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
Geometry cube3 = new Geometry(name, box);
Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
mat1.setColor("Color", ColorRGBA.Yellow);
cube3.setMaterial(mat1);
return cube3;
}
protected Geometry makeCube4(String name, float x, float y, float z) {
Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
Geometry cube4 = new Geometry(name, box);
Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
mat1.setColor("Color", ColorRGBA.Green);
cube4.setMaterial(mat1);
return cube4;
}
/** We add light to the scene. */
private void setUpLight() {
AmbientLight al = new AmbientLight();
al.setColor(ColorRGBA.White.mult(1.3f));
rootNode.addLight(al);
DirectionalLight dl = new DirectionalLight();
dl.setColor(ColorRGBA.White);
dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
rootNode.addLight(dl);
}
/** We over-write some navigational key mappings here, so we can
* add physics-controlled walking and jumping: */
private void setUpKeys() {
inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
inputManager.addListener(this, "Left");
inputManager.addListener(this, "Right");
inputManager.addListener(this, "Up");
inputManager.addListener(this, "Down");
inputManager.addListener(this, "Jump");
inputManager.addListener(this, "Shoot");
}
/** These are our custom actions triggered by key presses.
* We do not walk yet, we just keep track of the direction the user pressed. */
public void onAction(String binding, boolean value, float tpf) {
if (binding.equals("Left")) {
left = value;
} else if (binding.equals("Right")) {
right = value;
} else if (binding.equals("Up")) {
up = value;
} else if (binding.equals("Down")) {
down = value;
} else if (binding.equals("Jump")) {
player.jump();
}
else if(binding.equals("Shoot")){
// 1. Reset results list.
CollisionResults results = new CollisionResults();
// 2. Aim the ray from cam loc to cam direction.
Ray ray = new Ray(cam.getLocation(), cam.getDirection());
// 3. Collect intersections between Ray and Shootables in results list.
shootables.collideWith(ray, results);
// 4. Print the results
System.out.println("—– Collisions? " + results.size() + "—–");
for (int i = 0; i < results.size(); i++) {
// The closest collision point is what was truly hit:
CollisionResult closest = results.getClosestCollision();
// We trigger the sound since the item was hit.
audio_gun.playInstance();
// We increase the score.
y=y+100;
// We dettach the object hit from the rootNode so that it dissapears from the scene when we hit it.
rootNode.detachChild(closest.getGeometry());
myTarget=closest.getGeometry();
myTarget.setLocalScale(10);
// We attach the onject to the guiNode so that we create sort of an inventory of the objects hit.
guiNode.attachChild(myTarget);
// We check the score so that when it reaches 700 the player has won and the winText is displayed.
if (y==700){
guiNode.detachAllChildren();
guiNode.attachChild(winText);

}
}
}
}
/**
* This is the main event loop–walking happens here.
* We check in which direction the player is walking by interpreting
* the camera direction forward (camDir) and to the side (camLeft).
* The setWalkDirection() command is what lets a physics-controlled player walk.
* We also make sure here that the camera moves with player.
*/
@Override
public void simpleUpdate(float tpf) {
//With these commands we create multi-directional 3D sound
listener.setLocation(cam.getLocation());
listener.setRotation(cam.getRotation());
//With this command we update the score text on the screen when the y is changed.
scoreText.setText("Score: " + y);
//With these commands we create our character movement.
Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
walkDirection.set(0, 0, 0);
if (left) { walkDirection.addLocal(camLeft); }
if (right) { walkDirection.addLocal(camLeft.negate()); }
if (up) { walkDirection.addLocal(camDir); }
if (down) { walkDirection.addLocal(camDir.negate()); }
player.setWalkDirection(walkDirection);
cam.setLocation(player.getPhysicsLocation());
}
protected void initScore(){
//With this method we create a text on the lower part of the screen to display the score.
guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
scoreText = new BitmapText(guiFont, false);
scoreText.setBox(new Rectangle((settings.getWidth()/2 - guiFont.getCharSet().getRenderedSize()/3 * 2)-40, 0, 96, 256)); // Set box the text should center in
scoreText.setAlignment(BitmapFont.Align.Center); // Center the text
scoreText.setSize(guiFont.getCharSet().getRenderedSize());
scoreText.setText("Score: "+y);
scoreText.setLocalTranslation(0, scoreText.getLineHeight(), 0);
guiNode.attachChild(scoreText);
}
protected void initWin(){
//With this method we create a text in the center of the screen to be used when the character wins the game.
guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
winText = new BitmapText(guiFont, false);
winText.setSize(80f);
winText.setText("YOU WIN!");
winText.setLocalTranslation((settings.getWidth()/2 - guiFont.getCharSet().getRenderedSize()/3 * 2)-180,
settings.getHeight()/2 + winText.getLineHeight()/2, 0);
}
private void initAudio() {
////With this method we create sound to be played when the player picks up an item and a looped sound for the
////enviroment.
//
///* pick up item sound is to be triggered by a mouse click. */
//audio_gun = new AudioNode(assetManager, "Sounds/button.wav", false);
//audio_gun.setLooping(false);
//audio_gun.setVolume(1);
//rootNode.attachChild(audio_gun);
///* nature sound – keeps playing in a loop. */
//audio_nature = new AudioNode(assetManager, "Sound/Environment/Nature.ogg", false);
//audio_nature.setLooping(true); // activate continuous playing
//audio_nature.setPositional(true);
//audio_nature.setLocalTranslation(Vector3f.ZERO.clone());
//audio_nature.setVolume(1);
//rootNode.attachChild(audio_nature);
//audio_nature.play(); // play continuously!
}
}