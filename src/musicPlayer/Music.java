package musicPlayer;

/**
 * creates the music objects for the MusicPlayer to use for the audio
 * the music objects are made from a clip type variable and a 
 * File that is read in from a file. 
 */
import java.io.File;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.Clip;

public class Music {
	public Music(Clip clip, File file) {
		this.clip = clip;
		this.file = file;
	}

	Clip clip;
	File file;

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
