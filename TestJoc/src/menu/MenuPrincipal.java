/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class MenuPrincipal extends AbstractAppState implements ScreenController,
        ActionListener
{
   
    private SimpleApplication app;  
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private Node guiNode;
    private Nifty nifty;
    private Screen screen;
    private boolean isRunningMenuPrincipal = true;
    
    public MenuPrincipal(SimpleApplication app){
        this.rootNode      = app.getRootNode();
        this.viewPort      = app.getViewPort();
        this.guiNode       = app.getGuiNode();
        this.assetManager  = app.getAssetManager();  
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        this.app = (SimpleApplication)app;   
        
        //Menu
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                      this.app.getInputManager(),
                                                      this.app.getAudioRenderer(),
                                                      this.app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MenuPrincipal/MPrincipal.xml", "start",this);

        // attach the nifty display to the gui view port as a processor
        app.getGuiViewPort().addProcessor(niftyDisplay);
        this.app.getInputManager().setCursorVisible(true);
   } 
    
    /*
     * 
     */
    public void onAction(String name, boolean isPressed, float tpf) {
        
    }
    
    public void newGame(){
       nifty.gotoScreen("end");
       this.app.getInputManager().setCursorVisible(false);
       this.setIsRunningMenuPrincipal(false);
    }
    
    public void exit(){
        this.app.stop();
    }
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
       
    }

    public void onEndScreen() {
       
    }
    
    public boolean getIsRunningMenuPrincipal(){
        return this.isRunningMenuPrincipal;
    }
    public void setIsRunningMenuPrincipal(boolean IsRunning){
        this.isRunningMenuPrincipal = IsRunning;
    }
    
}
