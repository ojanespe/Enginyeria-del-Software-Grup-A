package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Xavier & Oriol
 * Tracta les col.lisions projectil-objecte.
 * Ampliable a danys per explosio, poders probablement
 */
public class ShotCollision {
    
    Node shootables;    /* Objectes susceptibles de ser disparats */
    Node rootNode;      /* Node root de l'escena */
    Geometry mark;
    AssetManager assetManager;
    
    public ShotCollision(Node rootNode,AssetManager assetManager)
    {
        shootables = new Node("Shootables");
        this.rootNode = rootNode;
        this.rootNode.attachChild(shootables);
        this.assetManager = assetManager;
        initMark();
    }
    
    /* Afegir un objecte com a shotable */
    public void setShotable(com.jme3.scene.Spatial child)
    {
        shootables.attachChild(child);
    }
    
    
    
    public void shot(String name, boolean keyPressed, float tpf, Camera cam)
    {
        if (name.equals("shoot") && !keyPressed)
        {
            CollisionResults results = new CollisionResults();
            // Es dispara un raig en la direcció del punter
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            // S'obtenen els elements que intersecten amb el raig
            System.out.println("----------" + shootables.getChildren().size());
            shootables.collideWith(ray, results);

            System.out.println("----- #Collisions: " + results.size() + "-----");
            //S'obtenen tots els punts en la direcció del tret
            for (int i = 0; i < results.size(); i++)
            {
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String hit = results.getCollision(i).getGeometry().getName();
                System.out.println("* Collision #" + i);
                System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
                
            }
            //Si hi ha alguna colisió..
            if (results.size() > 3)
            {
                // Colisió més propera
                CollisionResult closest = results.getClosestCollision();
                // Es marca el punt de colisió amb un cercle vermell
                mark.setLocalTranslation(closest.getContactPoint());
                rootNode.attachChild(mark);
            }
            else
            {
                //eliminar als "20" segons
                rootNode.detachChild(mark);
            }
        }
    }
    
    //Marca que simbolitza el tret
    protected void initMark()
    {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }
}
