package musicPlayer;

/**
 * reads files of WAV files to use in the class of MusicPlayerGui it takes the file creates a array of
 * files for the class Music to create a Music objects. 
 * writes int to a file for the class MusicPlayerGui to later read and activate buttons of the
 * save arragment. 
 * @author Garret Wasden  
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;

import java.util.Formatter;

public class SampleFile {
	static File[] audioFileArray = new File[17];
	protected static boolean running;
	static ByteArrayOutputStream out;
	static String fileName;
	static int fileNumber = 1;
	static boolean genreSwitch = false;

	/**
	 * reads a file in for the WAV files.
	 */
	public static void readFile() {
		if (fileNumber == 1) {
			fileName = ("/Volumes/CLASS/MyItems/src/hipHopSamp_2");
		} else {
			fileName = ("/Volumes/CLASS/MyItems/src/funk2");
		}
		File folder = new File(fileName);
		File[] listOfFiles = folder.listFiles();
		int i = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				audioFileArray[i] = new File(file.getPath());
				i++;
			}
		}
		Arrays.sort(audioFileArray);
	}

	/**
	 * gets the AudioFileArray array for the other classes of the program to use.
	 * 
	 * @return
	 */
	public static File[] getAudioFileArray() {
		return audioFileArray;
	}

	/**
	 * sets the fileNumber from other classes.
	 * 
	 * @param fileNumber
	 */
	public static void setFileNumber(int fileNumber) {
		SampleFile.fileNumber = fileNumber;
	}

	/**
	 * gets the AudioFileArray for the other classes of the program to use.
	 * 
	 * @return
	 */
	public static File getAudioFile(int i) {
		File audioFile = audioFileArray[i];
		return audioFile;
	}

	/**
	 * writes 4 int's onto a txt file for the class MusicPlayerGui to use latter to
	 * activate the button the user has saved. Changes the color for the button for
	 * the user to know what button have been activiated from the loaded txt file.
	 */
	public static void fileWriter() {

		try (Formatter printer = new Formatter("MusicSaved.txt")) {
			for (int i = 1; i < 17; i++) {
				if (MusicPlayerGui.getClickColor()[i] == true) {
					printer.format("%d,", i);
					System.out.println("worked" + i);
				}

			}
			printer.close();
		} catch (FileNotFoundException e) {
			System.out.println("no file");
		}
		System.out.println("done");
	}

}
