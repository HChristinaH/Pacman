
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

// this class displays the user's score as they play the game, and shows if sound effects are on
@SuppressWarnings("serial")
public class UtilityPanel extends JPanel {

	// create JLabels to display score
	private JLabel scoreLabel = new JLabel("Score:");
	private static JLabel scoreValue = new JLabel();
	public static JLabel soundIcon = new JLabel();

	// constructor
	public UtilityPanel() {

		// set up JPanel
		setBounds(0, 0, 600, 50);
		setLayout(null);
		setBackground(Color.BLACK);

		// score fonts
		Font scoreFont = new Font("Courier", Font.BOLD, 30);

		// score header
		scoreLabel.setFont(scoreFont);
		scoreLabel.setForeground(Color.white);
		scoreLabel.setBounds(230, 0, 150, 50);
		add(scoreLabel);

		// score value
		scoreValue.setFont(scoreFont);
		scoreValue.setForeground(Color.YELLOW);
		scoreValue.setBounds(350, 0, 100, 50);
		add(scoreValue);

		// sound icon
		soundIcon.setBounds(450, 15, 30, 30);
		soundIcon.setIcon(PacManGame.soundOnIcon); // sound on by default
		add(soundIcon);

		// mute sound label
		JLabel muteInstruction = new JLabel("Press \'m\' to mute");
		muteInstruction.setForeground(Color.gray);
		muteInstruction.setOpaque(false);
		muteInstruction.setBounds(485, 20, 100, 20);
		add(muteInstruction);

	}

	// this method mutes or unmutes the game
	public static void setMuted(boolean muted) {

		// mute game
		if (muted) {
			
			// update sound icon and status
			soundIcon.setIcon(PacManGame.soundOffIcon);
			PacManGame.muted = true;

		}

		// unmute game
		else {

			// update sound icon and status
			soundIcon.setIcon(PacManGame.soundOnIcon);
			PacManGame.muted = false;

		}

	}

	// this method update's the user's score
	public static void updateScore() {

		// set score JLabel to user's current score
		scoreValue.setText(Integer.toString(Board.score));

	}

}
