/**
 * 
 */
package com.team.engine.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Esteban
 *
 */
public class SoundClip {
	
	private Clip clip = null;
	private FloatControl gainControl;
	
			
	public SoundClip(String path) {
		
		//manejo de stream de audio
		try {
		InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
		InputStream bufferedIn = new BufferedInputStream(audioSrc);
		AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
		
		AudioFormat baseFormat = ais.getFormat();
		AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				                                   baseFormat.getSampleRate(),
				                                   16,
									               baseFormat.getChannels(),
									               baseFormat.getChannels()*2,
									               baseFormat.getSampleRate(),
									               false);
		//decoded
		AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
		
		clip = AudioSystem.getClip();
		clip.open(dais);
		
		
		gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		
		
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void start() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		
		while(!clip.isRunning()) {
			clip.start();
		}
	}
	
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.drain();
		clip.close();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		start();
	}
	
	//PERMITE CAMBIAR EL VOLUMEN EN DECIBELIOS, +20 o -20 para subir o bajar
	public void setVolume(float value) {
		gainControl.setValue(value);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
}
