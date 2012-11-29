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
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
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
    private int indexPersonaje = 0;
    private ArrayList<String> playersImages;
    
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
        
        playersImages = new ArrayList<String>();
        playersImages.add("Interface/PlayersMenu/player1.png");
        playersImages.add("Interface/PlayersMenu/player2.png");
        playersImages.add("Interface/PlayersMenu/player3.png");
        
        //Menu
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                      this.app.getInputManager(),
                                                      this.app.getAudioRenderer(),
                                                      this.app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MainMenu/InitialMenu.xml", "start",this);

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
        nifty.gotoScreen("choosePlayer");
    }
    
    public void acceptChoosePlayer() {
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
    
    public String getPlayerImage() {
        System.out.println((String) playersImages.get(indexPersonaje));
        return (String) playersImages.get(indexPersonaje);
    }
    
    public boolean getIsRunningMenuPrincipal(){
        return this.isRunningMenuPrincipal;
    }
    public void setIsRunningMenuPrincipal(boolean IsRunning){
        this.isRunningMenuPrincipal = IsRunning;
    }
    
    public void prevPerson() {
        indexPersonaje--;
        if(indexPersonaje == 0) {
            indexPersonaje = playersImages.size()-1;
        }
        cambioImagenPersonaje();
    }
    
    public void nextPerson() {
        indexPersonaje++;
        if(indexPersonaje == playersImages.size()) {
            indexPersonaje = 0;
        }
        cambioImagenPersonaje();
    }
    
    private void cambioImagenPersonaje() {
        NiftyImage newImage = nifty.getRenderEngine().createImage((String) playersImages.get(indexPersonaje), false); // false means don't linear filter the image, true would apply linear filtering
        Element element = screen.findElementByName("personImage");
        element.getRenderer(ImageRenderer.class).setImage(newImage);
    }
}
