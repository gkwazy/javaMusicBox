package musicPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * opens clips for the files of WAV audio to play to the local speakers. The
 * clips will loop continuously until the method ButtonStop is use to stop the
 * clips audio
 * 
 * @author Garret WAsden
 *
 */
public class MusicTools {

	/**
	 * starts the clip in a continuous loop
	 * 
	 * @param music
	 */
	static void ButtonAction(Music music) {
		Clip clip = music.getClip();

		File audioFile = music.getFile();

		AudioInputStream audioStream;
		try {
			clip = AudioSystem.getClip();

			audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);

		} catch (LineUnavailableException e) {

			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		clip.setMicrosecondPosition(0);
		clip.loop((Clip.LOOP_CONTINUOUSLY));
		music.setClip(clip);

	}

	/**
	 * stops and closes the clip loop
	 * 
	 * @param music
	 */
	static void ButtonStop(Music music) {
		Clip clip = music.getClip();
		clip.stop();
	}

}
