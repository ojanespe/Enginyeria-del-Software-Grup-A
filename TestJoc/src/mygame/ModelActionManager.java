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
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class ModelActionManager  implements ActionListener{
    private AnimChannel channel_walk;
    private AnimControl AC;
    private String Action = "";
    private float speed = 0;
    private ArrayList<KeyTrigger> KT;    

    
    private boolean isPressed = false;
    
    public ModelActionManager(AnimControl AC, String Action, float speed, ArrayList<Integer> tecla) {        
        this.KT = new ArrayList<KeyTrigger>();
        this.AC = AC;
        this.Action = Action;
        this.speed = speed;
        for(int i = 0; i< tecla.size(); i++){
            this.KT.add(new KeyTrigger(tecla.get(i))); 
        }             
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
    
    public ArrayList<KeyTrigger> getKT() {
        return KT;
    }
    
    public void onAction(String name, boolean keyPressed, float tpf) {
        isPressed = keyPressed;
        if(name.equals(this.Action) && keyPressed){
            if(!channel_walk.getAnimationName().equals(Action)){
                channel_walk.setLoopMode(LoopMode.Cycle);
                channel_walk.setAnim(Action, 0.1f);
                channel_walk.setSpeed(speed);
            }
        }
    }
    
    public void onAnimCycleDone(String animName) {
        if (animName.equals(this.Action) && !isPressed){
            this.channel_walk.setAnim("stand", 0.50f);
            this.channel_walk.setLoopMode(LoopMode.DontLoop);
            this.channel_walk.setSpeed(1f);
        }
    }
}
