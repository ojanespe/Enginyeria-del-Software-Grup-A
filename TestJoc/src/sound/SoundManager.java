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
    
    public void play(String path) {
        AudioNode audio = new AudioNode(assetManager, path, false);
        rootNode.attachChild(audio);
        
        audio.playInstance();
    }
    
}
