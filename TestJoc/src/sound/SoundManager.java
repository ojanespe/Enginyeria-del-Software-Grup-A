package sound;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * SoundManager: 
 *
 * @author Juan Carlos Calvo
 */
public class SoundManager {
    
    private AssetManager assetManager;
    
    private Node rootNode;
    
    private AudioNode ambient;
    
    private AudioNode movement;
    
    private AudioNode weaponChange;
    
    private static final String ambientSoundPath = "Sounds/Ambient/fog_bound.ogg";
    
    private static final String movementSoundPath = "Sounds/Effects/Movement/paso_caminando.ogg";
    
    private static final String weaponChangeSoundPath = "Sounds/Effects/Guns/reload_1stperson.ogg";
    
    public static final int WEAPON_CHANGE = 1;
    
    public static final int MOVEMENT = 2;
    
    /**
     * Constructor
     * 
     * @param assetManager
     * @param rootNode 
     */
    public SoundManager(AssetManager assetManager, Node rootNode) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        initAmbientSound();
        initEffectsSounds();
    }
    
    /**
     * setAmbient: 
     */
    private void initAmbientSound() {
        ambient = new AudioNode(assetManager, ambientSoundPath, false);
        ambient.setVolume(2);
        ambient.setLooping(true);
    }
    
    private void initEffectsSounds() {
        // Movement sound
        movement = new AudioNode(assetManager, movementSoundPath, false);
        rootNode.attachChild(movement);
        movement.setVolume(1);
        
        // Change weapon sound
        weaponChange = new AudioNode(assetManager, weaponChangeSoundPath, false);
        rootNode.attachChild(weaponChange);
        weaponChange.setVolume(1);
    }
    
    /**
     * playInstance: 
     * 
     * @param path
     * @param volume 
     */    
    public void playInstance(String path, int volume) {
        AudioNode audio = new AudioNode(assetManager, path, false);
        rootNode.attachChild(audio);
        audio.setVolume(volume);
        audio.playInstance();
    }
    
    /**
     * singlePlay: 
     * 
     * @param path
     * @param volume 
     */
    public void singlePlay(String path, int volume) {
        AudioNode audio = new AudioNode(assetManager, path, false);
        rootNode.attachChild(audio);
        audio.setVolume(volume);
        audio.play();
    }
    
    /**
     * playAmbientSound: 
     */
    public void playAmbientSound() {
        ambient.play();
    }
    
    public void playEffectSound(int effect) {        
        switch(effect) {
            case WEAPON_CHANGE:
                weaponChange.play();
                break;
            case MOVEMENT:
                movement.play();
                break;
        }
    }
    
}
