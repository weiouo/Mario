package com.game.obj.util;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip clip;
	
	public Sound(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
			AudioFormat baseFormat = audio.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
					16, baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
					
			AudioInputStream decodeAudio = AudioSystem.getAudioInputStream(decodeFormat,audio);
			
			clip = AudioSystem.getClip();
			clip.open(decodeAudio);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
	public void stop() {
		if (clip.isRunning()) clip.stop();
	}
}
