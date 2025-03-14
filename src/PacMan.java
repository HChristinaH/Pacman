import javax.swing.ImageIcon;

// this class creates an instance of PacMan
@SuppressWarnings("serial")
public class PacMan extends Mover {
	
	// PacMan's images
	public static final ImageIcon[][] IMAGE = {
			
			{new ImageIcon("images/PacLeftClosed.bmp"), new ImageIcon("images/PacLeftOpen.bmp")},
			{new ImageIcon("images/PacUpClosed.bmp"), new ImageIcon("images/PacUpOpen.bmp")},
			{new ImageIcon("images/PacRightClosed.bmp"), new ImageIcon("images/PacRightOpen.bmp")},
			{new ImageIcon("images/PacDownClosed.bmp"), new ImageIcon("images/PacDownOpen.bmp")},
				
	};
	
	// constructor
	public PacMan() {
		
		// start PacMan facing left with a closed mouth
		setIcon(IMAGE[0][0]);
	}
	
	

}
