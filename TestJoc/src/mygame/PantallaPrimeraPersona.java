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

        //Imagen Bala
        picBala = new Picture("HUD Picture");
        picBala.setImage(assetManager, "Icons/bala.png", true);
        picBala.scale(20);
        picBala.setLocalTranslation(settings.getWidth() - (contDisp.getLineWidth()/2), contDisp.getLineHeight(), 0);
        
        //Texto vida
        life = new BitmapText(guiFont, false); 
        life.setSize(guiFont.getCharSet().getRenderedSize());
        //life.setText("Vida:\n100");
        life.setColor(ColorRGBA.Black);
        life.setLocalTranslation(500, life.getLineHeight()*2, 0);
        
        //Imagen Cruz Vida
        picCruz = new Picture("HUD Picture");
        picCruz.setImage(assetManager, "Icons/barravida/barravida100.png", true);
        //picCruz.scale(160);
        picCruz.scale(160,20, 0);
        picCruz.setPosition(20, life.getLineHeight());

        //Texto escudo
        escudo = new BitmapText(guiFont, false);
        escudo.setSize(guiFont.getCharSet().getRenderedSize());
        escudo.setText("Escudo:\n0");
        escudo.setColor(ColorRGBA.Black);
        escudo.setLocalTranslation(escudo.getLineWidth(), escudo.getLineHeight(), 0);
        
        //Imagen Escudo
        picEscudo = new Picture("HUD Picture"); 
        picEscudo.setImage(assetManager, "Icons/escudo.png", true);
        picEscudo.scale(20);
        picEscudo.setPosition(escudo.getLineWidth()+350, escudo.getLineHeight());
        
    }    
    
    public BitmapText getcruzPuntero(){
        return ch;
    }
    
    public BitmapText getDisparos(){
        return contDisp;
    }
    
    public void setTextDisparos(int disp){
        //contDisp.setText("Disparos:\n"+ disp);
        contDisp.setText(disp+"");
    }
    
    public BitmapText getVida(){
        return life;
    }
    
    public void setTextVida(int vida){
        // Cambiar text por una imagen
        //life.setText("Vida:\n"+ vida);
        //life.setText(vida+"");
        
    }
    
    public BitmapText getEscudo(){
        return escudo;
    }
    
    public void setTextEscudo(int proc){
        escudo.setText("Escudo:\n"+ proc);
    }
    
    public Picture getPicVida(int vida, AssetManager assetManager){
        
        if (vida > 95) picCruz.setImage(assetManager, "Icons/barravida/barravida100.png", true);
        else if (vida > 90) picCruz.setImage(assetManager, "Icons/barravida/barravida95.png", true);
        else if (vida > 85) picCruz.setImage(assetManager, "Icons/barravida/barravida90.png", true);
        else if (vida > 80) picCruz.setImage(assetManager, "Icons/barravida/barravida85.png", true);
        else if (vida > 75) picCruz.setImage(assetManager, "Icons/barravida/barravida80.png", true);
        else if (vida > 70) picCruz.setImage(assetManager, "Icons/barravida/barravida75.png", true);
        else if (vida > 65) picCruz.setImage(assetManager, "Icons/barravida/barravida70.png", true);
        else if (vida > 60) picCruz.setImage(assetManager, "Icons/barravida/barravida65.png", true);
        else if (vida > 55) picCruz.setImage(assetManager, "Icons/barravida/barravida60.png", true);
        else if (vida > 50) picCruz.setImage(assetManager, "Icons/barravida/barravida55.png", true);
        else if (vida > 45) picCruz.setImage(assetManager, "Icons/barravida/barravida50.png", true);
        else if (vida > 40) picCruz.setImage(assetManager, "Icons/barravida/barravida45.png", true);
        else if (vida > 35) picCruz.setImage(assetManager, "Icons/barravida/barravida40.png", true);
        else if (vida > 30) picCruz.setImage(assetManager, "Icons/barravida/barravida35.png", true);
        else if (vida > 25) picCruz.setImage(assetManager, "Icons/barravida/barravida30.png", true);
        else if (vida > 20) picCruz.setImage(assetManager, "Icons/barravida/barravida25.png", true);
        else if (vida > 15) picCruz.setImage(assetManager, "Icons/barravida/barravida20.png", true);
        else if (vida > 10) picCruz.setImage(assetManager, "Icons/barravida/barravida15.png", true);
        else if (vida > 5) picCruz.setImage(assetManager, "Icons/barravida/barravida10.png", true);
        else picCruz.setImage(assetManager, "Icons/barravida/barravida5.png", true);
        
        return picCruz;
    }
    
    public Picture getPicEscudo(){
        return picEscudo;
    }
    
    public Picture getPicBala(){
        return picBala;
    }
}
