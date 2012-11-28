/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import messages.*;
import mygame.Jugador;
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
        
        // DisconnectMessage
        if (message instanceof DisconnectMessage) {
            DisconnectMessage m = (DisconnectMessage) message;
            removePlayer(m.getUserID());

        // FinishGameMessage
        } else if(message instanceof FinishGameMessage){
            FinishGameMessage m = (FinishGameMessage) message;

            /*  m.getTeam1Result();
             *  m.getTeam2Result();
             * 
             * TODO: finalizar partida */

        // HitMessage
        } else if(message instanceof HitMessage){
            HitMessage m = (HitMessage) message;

            Jugador j = game.getJugador();
            if (j.getEscudo() > 0) {
              j.setEscudo((j.getEscudo() - m.getSubstractedLife()));
            } else {
              j.setVida(j.getVida() - m.getSubstractedLife());
            }

            /* TODO: reproduir so com si es fes mal? */

        // KillMessage
        } else if(message instanceof KillMessage){
            KillMessage m = (KillMessage) message;
            
            int killedID = m.getKilled();
            int killerID = m.getKiller();
            if(game.getJugador().isPlayer(killedID)){ // Hem mort nosaltres
                /* TODO:
                 * > Morir
                 * > Reaparèixer
                 */
                game.mostrarMensajesPantalla("The player "+killerID+" killed you.");
            } else if(game.getJugador().isPlayer(killerID)){ // Hem assessinat nosaltres
                // TODO: killPlayer(killedID);
                game.mostrarMensajesPantalla("You killed the player "+killedID+".");
            } else {
                // TODO: killPlayer(killedID);
                game.mostrarMensajesPantalla("The player "+killerID+" killed "+killedID);
            }

        // NewUserMessage
        } else if(message instanceof NewUserMessage){
            NewUserMessage m = (NewUserMessage) message;
            createPlayer((PlayerClient)m.getPlayer());

        // RefreshMessage
        } else if(message instanceof RefreshMessage){
            RefreshMessage m = (RefreshMessage) message;
            
            ConcurrentHashMap<Integer, PlayerClient> players = game.getListPlayers();
            PlayerClient p_refresh;
            
            p_refresh = players.get(m.getUserID());
            
            if (p_refresh != null)
                p_refresh.refresh(m.getAction(), m.getPosition(), m.getView(), m.getDirection());

        // WelcomeMessage
        } else if(message instanceof WelcomeMessage){
            WelcomeMessage m = (WelcomeMessage) message;
            
            Jugador j = game.getJugador();
            // TODO: parameter1 = action must be "standing"
            // TODO: parameter4 = direction must be 0,0,0?
            //j.refresh(0, m.getSpawnPosition(), m.getSpawnView(), new Vector3f(0,0,0));
            
            game.mostrarMensajesPantalla("Welcome to the game "+game.getClientConnection().getGameName());
            
            ConcurrentHashMap<Integer, PlayerClient> list_p = new ConcurrentHashMap<Integer, PlayerClient>();
            ConcurrentHashMap<Integer, PlayerInterface> list_inter = m.getPlayers();
            int len = list_inter.size();
            for(int i = 0; i < len; i ++){
                list_p.put(i, (PlayerClient)list_inter.get(i));
            }
            game.setListPlayers(list_p);
            
        } 
    }
 
    
    /**
     * Elimina un jugador del mapa i indica que s'ha desconnectat.
     * 
     * @param id int Id del jugador que s'ha desconnectat.
     */
    private void removePlayer(int id){
        
        ConcurrentHashMap<Integer, PlayerClient> players = game.getListPlayers(); 
        
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
            /* TODO: hacer desaparecer el player del mapa */
            /* TODO: mostrar mensaje "El jugador X ha abandonado la partida." */
            players.remove(i-1);
        }

    }
    
    /**
     * Crea un nou jugador, el fa aparèixer i ho indica per pantalla.
     * 
     * @param pc PlayerClient nou player.
     */
    private void createPlayer(PlayerClient pc){
        
        ConcurrentHashMap<Integer, PlayerClient> players = game.getListPlayers();
        int id;
        
        players.put(players.size(), pc);
        id = players.size()-1;
        
        pc.init();
        // TODO: fer aparèixer el jugador "id"
        game.mostrarMensajesPantalla("Player " + pc.getID() + " joined the team " + pc.getTeam());
        
    }
    
}

