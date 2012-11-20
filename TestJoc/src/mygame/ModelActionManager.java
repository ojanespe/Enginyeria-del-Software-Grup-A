/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Carlos
 */
public class ModelActionManager  implements ActionListener{
    private AnimChannel channel_walk;
    private AnimControl AC;
    private String Action = "";
    private float speed = 0;
    private KeyTrigger KT;    
    
    
    public ModelActionManager(AnimControl AC, String Action, float speed, int tecla) {        
        this.AC = AC;
        this.Action = Action;
        this.speed = speed;
        this.KT = new KeyTrigger(tecla);                
    }
    
    public void initChannel(){
        this.channel_walk = this.AC.createChannel();    
        this.channel_walk.setAnim("stand");
    }
    
    public String getAction(){
        return this.Action;
    }
    
    public void setAction(String Action){
        this.Action = Action;
    }
    
    public KeyTrigger getKT(){
        return this.KT;
    }
        
    public void onAction(String name, boolean keyPressed, float tpf) {
        if(name.equals(Action) && !keyPressed){
            if(!channel_walk.getAnimationName().equals(Action)){
                channel_walk.setLoopMode(LoopMode.DontLoop);
                channel_walk.setAnim(Action, 0.5f);
                channel_walk.setSpeed(speed);
            }
        }
    }
    
    public void onAnimCycleDone(String animName) {
        if (animName.equals(this.Action)){
            this.channel_walk.setAnim("stand", 0.50f);
            this.channel_walk.setLoopMode(LoopMode.DontLoop);
            this.channel_walk.setSpeed(1f);
        }
    }
}
