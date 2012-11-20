/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author JORGE
 */
public class Arma {
    int nBalas;
    private Spatial gun;
    private Vector3f location;
    private String weaponType;
    private String shotSound;
    
    
    public Arma(AssetManager assetManager, String rootArma, Vector3f p_location, String shot){
        gun = assetManager.loadModel(rootArma);
        location=p_location;
        gun.setLocalScale(0.25f);
        gun.setLocalTranslation(location);
        nBalas = 0;
        weaponType = "null";
        shotSound = shot;
    }
    
    public void setScale(float scale)
    {
        gun.setLocalScale(scale);
    }
    
    public void setWeaponType(String type){
        this.weaponType = type;
    }
    
    public String getWeaponType() {
        return this.weaponType;
    }
    
    public Spatial getGun(){
        return gun;
    }
    int getBales(){
        return nBalas;
    }
    
    public Vector3f getLocation()
    {
        return location;
    }
    
    void setBales(int balas){
        nBalas = balas;
    }
    
    String getShotSound() {
        return this.shotSound;
    }
    
    public void updateGun(Vector3f player_pos){
        gun.setLocalTranslation(player_pos);
    }
    
    public void rotate(float x, float y, float z){
        gun.rotate(x, y, z);
    }
}
