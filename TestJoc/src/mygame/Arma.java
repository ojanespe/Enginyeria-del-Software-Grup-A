/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
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
        gun.setLocalTranslation(1, 1, -3);
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
}
