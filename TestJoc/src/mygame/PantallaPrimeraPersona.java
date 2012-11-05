/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 *
 * @author JORGE
 */
public class PantallaPrimeraPersona {
    int indiceVida, indiceEscudo;
    BitmapText ch, contDisp, life, escudo;
    Picture picCruz, picEscudo, picBala, puntero, blood;
    Picture picScope;
    
    public PantallaPrimeraPersona(AssetManager assetManager, AppSettings settings, BitmapFont guiFont) {
        /*
        // Texto puntero
        ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 5);
        ch.setText("+");
        ch.setColor(ColorRGBA.Green);
        ch.setLocalTranslation( 
          settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
          settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
*/
        
        puntero = new Picture("HUD Picture");
        puntero.setImage(assetManager, "Icons/puntdemira.png", true);
        puntero.scale(40);
        puntero.setLocalTranslation( 
          settings.getWidth() / 2 - 20,
          settings.getHeight() / 2 - 20, 0);
        
        blood = new Picture("HUD Picture");
        blood.setImage(assetManager, "Icons/res.png", true);
        blood.scale(settings.getWidth(),settings.getHeight(),0);
        blood.setLocalTranslation(0,0,0);
        
        
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
        picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo100.png", true);
        picEscudo.scale(160,20,0);
        picEscudo.setPosition(220, escudo.getLineHeight());
        
        // Snipermode
        String sauce;
        float r = (float)settings.getWidth()/(float)settings.getHeight();
        //System.out.println(r);
        if(r <= 1.29) { sauce = "Icons/Scope1_25.png"; }
        else if(r <= 1.4) { sauce = "Icons/Scope1_33.png"; }
        else if(r <= 1.6) { sauce = "Icons/Scope1_5.png"; }
        else { sauce = "Icons/Scope1_77.png"; }
        picScope = new Picture("Scope");
        picScope.setImage(assetManager, sauce, true);
        picScope.setWidth(settings.getWidth());
        picScope.setHeight(settings.getHeight());
        picScope.setPosition(0, 0);
    }    
    
    public Picture getScope() {
        return picScope;
    }
    
    public Picture getcruzPuntero(){
        return puntero;
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
        else if (vida > 5)  {
            picCruz.setImage(assetManager, "Icons/barravida/barravida10.png", true);
            blood.setImage(assetManager, "Icons/blood.png", true);       
        }
        else picCruz.setImage(assetManager, "Icons/barravida/barravida5.png", true);
        
        return picCruz;
    }
    
    public Picture getPicEscudo(int escudo, AssetManager assetManager){
        if (escudo > 95) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo100.png", true);
        else if (escudo > 90) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo95.png", true);
        else if (escudo > 85) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo90.png", true);
        else if (escudo > 80) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo85.png", true);
        else if (escudo > 75) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo80.png", true);
        else if (escudo > 70) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo75.png", true);
        else if (escudo > 65) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo70.png", true);
        else if (escudo > 60) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo65.png", true);
        else if (escudo > 55) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo60.png", true);
        else if (escudo > 50) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo55.png", true);
        else if (escudo > 45) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo50.png", true);
        else if (escudo > 40) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo45.png", true);
        else if (escudo > 35) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo40.png", true);
        else if (escudo > 30) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo35.png", true);
        else if (escudo > 25) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo30.png", true);
        else if (escudo > 20) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo25.png", true);
        else if (escudo > 15) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo20.png", true);
        else if (escudo > 10) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo15.png", true);
        else if (escudo > 5) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo10.png", true);
        else if (escudo > 0) picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo5.png", true);
        else picEscudo.setImage(assetManager, "Icons/barraescudo/barraescudo0.png", true);
        
        return picEscudo;
    }
    
    public Picture getPicBala(){
        return picBala;
    }

    Spatial getBlood() {
        return blood;
    }
    
    
}
