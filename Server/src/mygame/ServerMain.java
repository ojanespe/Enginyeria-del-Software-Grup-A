package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.*;
import multiplayer.*;

/**
 * test
 * @author normenhansen
 */
public class ServerMain extends SimpleApplication {

    Server myServer = null;
    ArrayList<PlayerServer> players;
    
    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless); // headless type for servers!
    }

    @Override
    public void simpleInitApp() {
        
        try {
            myServer = Network.createServer(6143);
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        myServer.start();
        
        // Registrar cada tipo de mensaje
        Serializer.registerClass(ByeMessage.class);
        Serializer.registerClass(DisconnectMessage.class);
        Serializer.registerClass(FinishGameMessage.class);
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(HitMessage.class);
        Serializer.registerClass(KillMessage.class);
        Serializer.registerClass(NewUserMessage.class);
        Serializer.registerClass(RefreshMessage.class);
        Serializer.registerClass(ShootMessage.class);
        Serializer.registerClass(WelcomeMessage.class);

        
        // Registrar los Listeners de cada tipo de mensaje
        myServer.addMessageListener(new ServerListener(myServer), ByeMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), DisconnectMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), FinishGameMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), HelloMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), HitMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), KillMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), NewUserMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), RefreshMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), ShootMessage.class);
        myServer.addMessageListener(new ServerListener(myServer), WelcomeMessage.class);
        
        players  = new ArrayList<PlayerServer>();
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
    
    // Necesitamos cerrar la conexión antes de apagar el server
    @Override
    public void destroy() {
      myServer.close();
      super.destroy();
    }
}
