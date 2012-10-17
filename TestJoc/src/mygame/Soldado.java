/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author JORGE
 */
public class Soldado {
    private CharacterControl player;
        
    private int disparos;
    
   
    public Soldado(){
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(60);
        player.setGravity(60);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        disparos = 0;
    }

    public CharacterControl getPlayer() {
        return player;
    }

    int getDisparos() {
        return disparos;
    }
    
    void incremenDisparos(){
        disparos++;
    }
      
    
}
