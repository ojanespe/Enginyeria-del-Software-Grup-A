/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 *
 * @author JORGE
 */
public class PantallaPrimeraPersona {
    int indiceVida, indiceEscudo;
    BitmapText ch, contDisp, life, escudo;
    Picture picCruz, picEscudo, picBala;
    
    public PantallaPrimeraPersona(AssetManager assetManager, AppSettings settings, BitmapFont guiFont) {
        // Texto puntero
        ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 5);
        ch.setText("+");
        ch.setColor(ColorRGBA.Green);
        ch.setLocalTranslation( 
          settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
          settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);

        // Texto disparos
        contDisp = new BitmapText(guiFont, false);
        contDisp.setSize(guiFont.getCharSet().getRenderedSize());
        contDisp.setText("Disparos:\n0");
        contDisp.setColor(ColorRGBA.Black);
        contDisp.setLocalTranslation(settings.getWidth() - contDisp.getLineWidth(), contDisp.getLineHeight()*2, 0);

        //Texto vida
        life = new BitmapText(guiFont, false); 
        life.setSize(guiFont.getCharSet().getRenderedSize());
        life.setText("Vida:\n100");
        life.setColor(ColorRGBA.Black);
        life.setLocalTranslation(10, life.getLineHeight()*2, 0);

        //Texto escudo
        escudo = new BitmapText(guiFont, false);
        escudo.setSize(guiFont.getCharSet().getRenderedSize());
        escudo.setText("Escudo:\n0");
        escudo.setColor(ColorRGBA.Black);
        escudo.setLocalTranslation(escudo.getLineWidth(), escudo.getLineHeight(), 0);

        //Imagen Cruz Vida
        picCruz = new Picture("HUD Picture");
        picCruz.setImage(assetManager, "Icons/cruzvida.png", true);
        picCruz.scale(20);
        picCruz.setPosition(10, life.getLineHeight()*2);

        //Imagen Escudo
        picEscudo = new Picture("HUD Picture"); 
        picEscudo.setImage(assetManager, "Icons/escudo.png", true);
        picEscudo.scale(20);
        picEscudo.setPosition(escudo.getLineWidth()+50, escudo.getLineHeight()*2);

        //Imagen Bala
        picBala = new Picture("HUD Picture");
        picBala.setImage(assetManager, "Icons/bala.png", true);
        picBala.scale(20);
        picBala.setPosition(settings.getWidth() - contDisp.getLineWidth(), contDisp.getLineHeight()*2);
    }    
    
    public BitmapText getcruzPuntero(){
        return ch;
    }
    
    public BitmapText getDisparos(){
        return contDisp;
    }
    
    public void setTextDisparos(int disp){
        contDisp.setText("Disparos:\n"+ disp);
    }
    
    public BitmapText getVida(){
        return life;
    }
    
    public void setTextVida(int vida){
        life.setText("Vida:\n"+ vida);
    }
    
    public BitmapText getEscudo(){
        return escudo;
    }
    
    public void setTextEscudo(int proc){
        escudo.setText("Escudo:\n"+ proc);
    }
    
    public Picture getPicVida(){
        return picCruz;
    }
    
    public Picture getPicEscudo(){
        return picEscudo;
    }
    
    public Picture getPicBala(){
        return picBala;
    }
}
