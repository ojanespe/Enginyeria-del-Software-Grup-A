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
import messages.*;

/**
 *
 * @author Mark
 */
public class ServerListener implements MessageListener<HostedConnection> {
    
    Server myS;
    
    public ServerListener(Server s){
        myS = s;
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
        } // else....
  }
  
  // Envia un missatge de resposta a tots els clients (excepte el que s'acaba de conectar.
  public void resend(HostedConnection source){
      
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

}
