
// import classes
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

// this frame appears when the game ends to displays final score results and let user exit or play again
@SuppressWarnings("serial")
public class GameOver extends JFrame implements ActionListener {

	// score text and values
	private static JLabel highestScore = new JLabel("0");
	private static JLabel currentScore = new JLabel();
	private static JLabel highScoreHeader = new JLabel("High Score:");
	private static JLabel newHighScoreIndicator = new JLabel("New");

	// utility buttons
	private static JButton muteButton = new JButton();
	private static JButton playAgain = new JButton("Play Again");
	private static JButton exit = new JButton("Exit");

	// constructor
	public GameOver() {

		// build components in the panel
		setUpGameOverPanel();

	}

	// this method sets up the elements in the game over panel
	public void setUpGameOverPanel() {

		// stops the program when user clicks exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// fonts
		Font titleFont = new Font("Courier", Font.CENTER_BASELINE, 40);
		Font headerFont = new Font("Courier", Font.CENTER_BASELINE, 34);
		Font playAgainFont = new Font("Courier", Font.BOLD, 20);
		Font exitFont = new Font("Courier", Font.PLAIN, 16);

		// panel
		setLayout(null);
		setBounds(0, 0, 615, 680);
		getContentPane().setBackground(Color.black);

		// header
		JLabel header = new JLabel("GAME OVER");
		header.setForeground(Color.white);
		header.setFont(titleFont);
		header.setBounds(240, 100, 300, 50);
		add(header);

		// PacMan GIF
		JLabel pacMan = new JLabel(new ImageIcon("images/PacMan.gif"));
		pacMan.setBounds(40, 40, 200, 166);
		add(pacMan);

		// new high score indicator
		newHighScoreIndicator.setFont(headerFont);
		newHighScoreIndicator.setForeground(Color.MAGENTA);
		newHighScoreIndicator.setBounds(90, 245, 300, 50);

		// score header labels
		JLabel scoreHeader = new JLabel("Score:");
		scoreHeader.setForeground(Color.white);
		scoreHeader.setFont(headerFont);
		scoreHeader.setBounds(270, 195, 300, 50);
		add(scoreHeader);

		highScoreHeader.setFont(headerFont);
		highScoreHeader.setForeground(Color.white);
		highScoreHeader.setBounds(170, 245, 300, 50);
		add(highScoreHeader);

		// score value label
		currentScore.setFont(headerFont);
		currentScore.setForeground(Color.yellow);
		currentScore.setBounds(400, 195, 200, 50);
		add(currentScore);

		highestScore.setFont(headerFont);
		highestScore.setForeground(Color.yellow);
		highestScore.setBounds(400, 245, 200, 50);
		add(highestScore);

		// mute button
		muteButton.setBounds(515, 35, 30, 30);
		muteButton.addActionListener(this);
		add(muteButton);

		// play again button
		playAgain.setFont(playAgainFont);
		playAgain.setBackground(Color.orange);
		playAgain.setBounds(200, 360, 200, 50);
		playAgain.addActionListener(this);
		add(playAgain);

		// exit button
		exit.setFont(exitFont);
		exit.setBackground(Color.gray);
		exit.setBounds(250, 500, 100, 30);
		exit.addActionListener(this);
		add(exit);

	}

	// this method update's the scores
	public void updateScore() {

		// update user's current score
		currentScore.setText(Integer.toString(Board.score));

		// check if user's attempt is a new record
		if (Board.score > Integer.valueOf(highestScore.getText())) {

			// set the highest score to user's current score
			highestScore.setText(Integer.toString(Board.score));

			// update high score text
			add(newHighScoreIndicator);

		}

		else
			remove(newHighScoreIndicator);

	}

	// this method plays the end screen's music
	public static void playMusic() {

		// if the game is muted
		if (PacManGame.muted) {
			
			// set sound icon as off
			muteButton.setIcon(PacManGame.soundOffIcon);
			
		}
		
		// if the game is not muted
		else {

			// set sound icon as on
			muteButton.setIcon(PacManGame.soundOnIcon);
			
			// play music on loop
			PacManGame.music.loop(Clip.LOOP_CONTINUOUSLY);

		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// if user wants to change sound settings
		if (event.getSource() == muteButton) {

			// if game is muted
			if (PacManGame.muted) {

				// start playing music
				PacManGame.music.start();
				muteButton.setIcon(PacManGame.soundOnIcon);
				PacManGame.muted = false;

			}
			// if game is not muted
			else {

				// stop playing music
				PacManGame.music.stop();
				muteButton.setIcon(PacManGame.soundOffIcon);
				PacManGame.muted = true;

			}

		}

		// if user wants to try again
		if (event.getSource() == playAgain) {

			// reset game board
			PlayGame.board.removeAll();
			PlayGame.board.loadBoard();

			// reset game variables
			Board.pacMan.setDead(false);
			Board.score = 0;
			UtilityPanel.updateScore();
			Ghost.setScared(false);

			// stop playing music
			PacManGame.music.stop();
			
			// update sound icon
			UtilityPanel.setMuted(PacManGame.muted);

			// make game screen visible
			PacManGame.playGame.setVisible(true);

			// hide game over screen
			PacManGame.gameOver.setVisible(false);

		}

		// if user wants to leave the program
		else if (event.getSource() == exit) {

			// exit the program
			System.exit(0);

		}

	}

}
