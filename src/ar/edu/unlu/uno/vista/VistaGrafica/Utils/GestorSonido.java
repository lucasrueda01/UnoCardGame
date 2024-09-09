package ar.edu.unlu.uno.vista.VistaGrafica.Utils;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GestorSonido {
    private Clip clip;

    public void cargarSonido(String rutaSonido) {
        try {
            URL sonidoUrl = getClass().getResource("/sounds/" + rutaSonido);
            if (sonidoUrl == null) {
                throw new RuntimeException("Archivo de sonido no encontrado: " + rutaSonido);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoUrl);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reproducir(boolean enBucle) {
        if (enBucle) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
        } else {
            clip.loop(0); 
        }
        clip.start();
    }

    public void setVolumen(double d) {
        FloatControl controlVolumen = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (controlVolumen != null) {
        	double rangoMaximo = controlVolumen.getMaximum();
        	double rangoMinimo = controlVolumen.getMinimum();
        	double valorControl = rangoMinimo + (rangoMaximo - rangoMinimo) * d;
            controlVolumen.setValue((float) valorControl);
         
        }
    }

    public void detener() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }
}

