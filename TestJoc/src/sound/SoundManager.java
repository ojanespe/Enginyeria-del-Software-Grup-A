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
    
    
    
    

    public SoundManager(AssetManager assetManager, Node rootNode) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }
    
    public void playSituationalSound(String path, int volume) {
        AudioNode audio = new AudioNode(assetManager, path, false);
        rootNode.attachChild(audio);
        audio.setVolume(volume);
        audio.playInstance();
    }
    
    public void playAmbientSound(String path, int volume) {
        AudioNode audio = new AudioNode(assetManager, path, false);
        audio.setVolume(volume);
        audio.setLooping(true);
        audio.play();
    }
    
}
