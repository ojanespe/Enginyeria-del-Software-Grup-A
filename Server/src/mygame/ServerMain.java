package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
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
    ConcurrentHashMap<Integer, PlayerInterface> players;
    int idCounter;
    Object idCounterLock;
    
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
        myServer.addMessageListener(new ServerListener(myServer, this), ByeMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), DisconnectMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), FinishGameMessage.class);
        myServer.addMessageListener(new ServerListener(myServer, this), HelloMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), HitMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), KillMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), NewUserMessage.class);
        myServer.addMessageListener(new ServerListener(myServer, this), RefreshMessage.class);
        myServer.addMessageListener(new ServerListener(myServer, this), ShootMessage.class);
        //myServer.addMessageListener(new ServerListener(myServer, this), WelcomeMessage.class);
        
        players  = new ConcurrentHashMap<Integer, PlayerInterface>();
        idCounter = 0;
        idCounterLock = new Object();
        
        System.out.println("Server ready.");
        
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

    public PlayerServer registerNewPlayer(HostedConnection source, HelloMessage message) {
        int newId = getNewPlayerId();
        PlayerServer playerServer = new PlayerServer(
                newId,
                message.getTeam(),
                message.getCostume(),
                0,                     //TODO gun ids
                new Vector3f(0, 0, 0), //TODO correct position
                new Vector3f(0, 0, 0), //TODO correct direction
                new Vector3f(0, 0, 0), //TODO correct view
                source);
        players.put(newId, playerServer);
        return playerServer;
    }
    
    private int getNewPlayerId() {
        synchronized(idCounterLock) {
            idCounter++;
        }
        return idCounter;
    }
    
    public ConcurrentHashMap<Integer, PlayerInterface> getPlayers() {
        return players;
    }
    
    public PlayerServer getByHostedConnection(HostedConnection source) {
        final Collection<PlayerInterface> refs = players.values();
        PlayerServer pS;
        for(PlayerInterface p: refs) {
            pS = (PlayerServer) p;
            if(pS.getClient().equals(source)) {
                return pS;
            }
        }
        return null;
    }

    public void removePlayer(int iD) {
        players.remove(iD);
    }
    
    public PlayerServer getPlayer(int id) {
        return (PlayerServer) players.get(id);
    }

    public void refreshPlayer(RefreshMessage message) {
        PlayerServer p = getPlayer(message.getUserID());
        p.refresh(message);
    }
    
}
