/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author JORGE
 */
public class Arma {
    int nBalas;
    private Spatial gun;
    
    public Arma(AssetManager assetManager){
        gun = assetManager.loadModel("Models/Glock/Glock.j3o");
        gun.setLocalScale(0.5f);
        gun.setLocalTranslation(-0.7f, -0.7f, 1.8f);
    }
    
    public Spatial getGun(){
        return gun;
    }
    int getBales(){
        return nBalas;
    }
    
    void setBales(int balas){
        nBalas = balas;
    }
    
    public void updateGun(Vector3f player_pos){
        gun.setLocalTranslation(player_pos.add(0, -10, 0).add(-0.7f, -0.7f, 1.8f));
    }
}
