
// imports all the necessary JSwing classes
import javax.swing.*;

// this class sets up the game window where user plays PacMan
@SuppressWarnings("serial")
public class PlayGame extends JFrame {

	// creates score display
	public static UtilityPanel score = new UtilityPanel();
	
	// creates the game board
	public static Board board = new Board();

	// constructor method
	public PlayGame() {

		// set up GUI frame
		setSize(615, 680);
		setLayout(null);
		setTitle("PacMan - Christina Huang");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set up game board
		addKeyListener(board);
		add(board);
		
		// set up score display
		add(score);
		
		// makes game screen frame visible
		setVisible(true);
		

	}

}
