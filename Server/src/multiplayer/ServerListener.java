package multiplayer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import messages.*;
import mygame.ServerMain;

/**
 *
 * @author Mark
 */
public class ServerListener implements MessageListener<HostedConnection> {

    Server myS;
    ServerMain app;

    public ServerListener(Server s, ServerMain app) {
        myS = s;
        this.app = app;
    }

    // Fem l'acció corresponent per cada un dels tipus de missatges que rebem.
    public void messageReceived(HostedConnection source, Message message) {

        //Sample code

        // HelloMessage
        if (message instanceof HelloMessage) {
            // do something with the message
          /*HelloMessage helloMessage = (HelloMessage) message;
             System.out.println("Server received '" +helloMessage. +"' from client #"+source.getId() );
             resend(source);*/
            Player newPlayer = app.registerNewPlayer(source, (HelloMessage) message);
            
            // ADDED
            ConcurrentHashMap<Integer, Player> p_hash = app.getPlayers();
            ArrayList<Player> p_array = new ArrayList<Player>();
            Enumeration<Integer> keys = p_hash.keys();
            while(keys.hasMoreElements()){
                p_array.add((Player)p_hash.get(keys.nextElement()));
            }
            //////////////////////
            
            WelcomeMessage wM = new WelcomeMessage(
                    newPlayer.getID(),
                    newPlayer.getPosition(),
                    newPlayer.getView(),
                    /*app.getPlayers())*/
                    p_array);
            broadcastNewPlayer(source, newPlayer);
            source.send(wM);
            //myS.broadcast(Filters.equalTo(source), wM);
        } else if (message instanceof ByeMessage) {
            Player p = (Player)app.getPlayer(source.getId());
            if(p != null) {
                app.removePlayer(p.getID());
                DisconnectMessage dM = new DisconnectMessage(p.getID());
                broadcastExceptOne(source, dM);
            }
        } else if (message instanceof RefreshMessage) {
            broadcastExceptOne(source, message);
            app.refreshPlayer((RefreshMessage)message);
        } else if (message instanceof ShootMessage) {
            ShootMessage m = (ShootMessage) message;
            Player p = app.getPlayer(m.getIdShooted());
            HitMessage hitM = new HitMessage(m.getLife());
            app.getClient(p).send(hitM);
        }
    }

    // Envia un missatge de resposta a tots els clients (excepte el que s'acaba de conectar.
    public void resend(HostedConnection source) {
        //Sample code
        /*HelloMessage m = new HelloMessage("Client "+ source.getId() + " said hello.");
         ArrayList<HostedConnection> cons = new ArrayList<HostedConnection> (myS.getConnections());
         boolean found = false;
         int i = 0;
         HostedConnection client = null;
         while (!found){
         client = cons.get(i);
         if(client.getId() == source.getId()){
         found = true;
         }
         i++;
         }
         myS.broadcast( Filters.notEqualTo( client ), m );*/
    }
    
    private void broadcastExceptOne(HostedConnection one, Message message) {
        ConcurrentLinkedQueue<HostedConnection> cons = new ConcurrentLinkedQueue<HostedConnection>(myS.getConnections());
        boolean found = false;
        int i = 0;
        HostedConnection client = null;
        while(!found) {
            client = cons.poll();
            if(client == null) { //TODO check if this is correct
                return;
            }
            if(client.getId() == one.getId()) {
                found = true;
            }
            i++;
        }
        myS.broadcast(Filters.notEqualTo(client), message);
        //myS.broadcast(message);
    }

    private void broadcastNewPlayer(HostedConnection source, Player newPlayer) {
        NewUserMessage newUserM = new NewUserMessage(newPlayer);
        broadcastExceptOne(source, newUserM);
    }
}
