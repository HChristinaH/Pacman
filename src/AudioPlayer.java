
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// this class formats audio files to be played
public class AudioPlayer {

	// audio files
	public static File endMusic = new File("sounds/Pac-Man Fever.wav");
	public static File chomp = new File("sounds/pacchomp.wav");
	public static File death = new File("sounds/killed.wav");
	public static File intro = new File("sounds/intro.wav");
	public static File powerEat = new File("sounds/eatPowerPellet.wav");

	// this method creates a clip of an audio file
	public static Clip getAudio(File audioFile) {

		try {

			// formats the file as a playable audio clip
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream); // source: https://www.geeksforgeeks.org/play-audio-file-using-java/
			return clip;

		}

		// display the errors (if any)
		catch (Exception error) {

			error.printStackTrace();

		}

		return null;

	}

}
