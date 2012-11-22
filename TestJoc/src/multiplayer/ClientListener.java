/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.ArrayList;
import messages.*;
import mygame.testJoc;

/**
 *
 * @author Marc Bolaños
 */
public class ClientListener implements MessageListener<Client> {  
    
    private testJoc game;
    
    public ClientListener(testJoc tj){
        game = tj;
    }

    // Por cada tipo de mensaje que recibimos hacemos las acciones pertinentes
    public void messageReceived(Client source, Message message) {
        
          if (message instanceof DisconnectMessage) {
              DisconnectMessage m = (DisconnectMessage) message;
              removePlayer(m.getUserID());

          } else if(message instanceof FinishGameMessage){
              FinishGameMessage m = (FinishGameMessage) message;
              
              // TODO: acabar partida
              
          } else if(message instanceof HitMessage){
              HitMessage m = (HitMessage) message;
              
          } else if(message instanceof KillMessage){
              KillMessage m = (KillMessage) message;
              
          } else if(message instanceof NewUserMessage){
              NewUserMessage m = (NewUserMessage) message;
              
          } else if(message instanceof RefreshMessage){
              RefreshMessage m = (RefreshMessage) message;
              
          } else if(message instanceof WelcomeMessage){
              WelcomeMessage m = (WelcomeMessage) message;
              
          } 
    }
 
    
    /**
     * Elimina un jugador del mapa i indica que s'ha desconnectat.
     * 
     * @param id int Id del jugador que s'ha desconnectat.
     */
    private void removePlayer(int id){
        
        synchronized(game.players){
            ArrayList<PlayerClient> players = game.players;
            int len = players.size();
            int i = 0;
            boolean found = false;
            while(!found && i < len){
                if(players.get(i).getID() == id){
                    found = true;
                }
                i++;
            }
            if(found){
                // TODO: hacer desaparecer el player del mapa
                // TODO: mostrar mensaje "El jugador X ha abandonado la partida."
                game.players.remove(i-1);
            }
        }
    }
    
    
}

