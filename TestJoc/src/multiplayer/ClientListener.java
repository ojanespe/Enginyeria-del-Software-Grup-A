/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import messages.*;

/**
 *
 * @author Marc Bolaños
 */
public class ClientListener implements MessageListener<Client> {
    

    // Por cada tipo de mensaje que recibimos hacemos las acciones pertinentes
  public void messageReceived(Client source, Message message) {
      // HelloMessage
    if (message instanceof HelloMessage) {
      // do something with the message
      HelloMessage helloMessage = (HelloMessage) message;
      //System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getMessage()+"'");
    } // else...
  }
    
}

