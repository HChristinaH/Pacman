
/* Name: Christina Huang 
 * Date: May 31, 2022 
 * Course Code: ICS3U1-08 
 * Title: PacMan Game Application 
 * Project: SDP #2 PacMan Game
 * Description: This class is used to create the GUI frames to start the game
 * Major Skills: JSwing GUI components, 2D arrays, timers, KeyListener, playing audio files, abstract classes 
 * Added Features:
 *  
 * Ghost Chase - ghosts chase PacMan when they see him 
 * Power Pellet - ghosts turn blue and run away when PacMan eats a power pellet 
 * Gate - prevents characters from entering the ghost house 
 * Ghost Path - lets ghosts exit house quickly 
 * Score - display's user's score and updates as PacMan eats pellets
 * High Score - displays highest score for current session and indicates if it is a new record 
 * Play Again - lets user play as many times as they want 
 * Sound effects and mute key/button 
 * 
 * Areas of Concern: None
 */

// import classes
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

// this class creates GUI frames and variables for the game to start
public class PacManGame {

	// this variable records whether user wants to mute sound effects
	public static boolean muted;

	// create image icons for sound status
	public static ImageIcon soundOnIcon = new ImageIcon("images/VolumeOn.png");
	public static ImageIcon soundOffIcon = new ImageIcon("images/VolumeOff.jpg");

	// music (PacMan Fever) for the end screen
	public static Clip music = AudioPlayer.getAudio(AudioPlayer.endMusic);

	// this frame is where user plays PacMan
	public static PlayGame playGame = new PlayGame();

	// this frame appears when the game is over
	public static GameOver gameOver = new GameOver();

	// main method - runs the program
	public static void main(String[] args) {
		
		// set the start position of the end screen music
		music.setMicrosecondPosition(6000000); // start from drum beats of PacMan Fever

	}

}
