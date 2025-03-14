
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.sampled.Clip;
import javax.swing.*;

// This class displays the game board where PacMan and ghosts move
@SuppressWarnings("serial")
public class Board extends JPanel implements KeyListener, ActionListener {

	// create a game timer (controls the speed of the game)
	private Timer gameTimer = new Timer(250, this);

	// create an animation timer for PacMan
	private Timer animateTimer = new Timer(10, this);

	// create a timer to delay transition to end screen
	private Timer gameOverTimer = new Timer(1000, this);

	// intro music
	public static Clip introMusic = AudioPlayer.getAudio(AudioPlayer.intro);

	// create game graphic objects
	private static final ImageIcon WALL = new ImageIcon("images/StdWall.bmp");
	private static final ImageIcon FOOD = new ImageIcon("images/StdFood.bmp");
	private static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");
	private static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");
	private static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");
	private static final ImageIcon SCARE = new ImageIcon("images/PowerPellet.jpg");
	private static final ImageIcon SCAREDGHOST = new ImageIcon("images/BlueGhost.bmp");
	private static final ImageIcon GATE = new ImageIcon("images/Gate.png");

	// create an array for the characters of the maze text file
	public static char[][] maze = new char[25][27];

	// create an array for the images of each cell in the maze
	public static JLabel[][] cell = new JLabel[25][27];

	// initialize game characters
	public static PacMan pacMan;
	private Ghost[] ghost = new Ghost[3];

	// keeps track of total pellets
	private int pellets = 0;

	// keeps track of pellets eaten
	public static int score = 0;

	// keep track of PacMan's movements
	private int pStep;

	// keep track of how long ghosts are scared for
	private int gScared = 0;

	// counter for seconds after game ends
	private int gameOverSec = 0;

	// constructor
	public Board() {

		// set up board
		setLayout(new GridLayout(25, 27));
		setBackground(Color.BLACK);
		setBounds(0, 50, 600, 600);

		// create PacMan
		pacMan = new PacMan();

		// create ghosts
		ghost[0] = new Ghost(0);
		ghost[1] = new Ghost(1);
		ghost[2] = new Ghost(2);

		// load the board's graphics
		loadBoard();

	}

	// this method loads the board's graphics
	void loadBoard() {

		// create a counter for the maze's rows
		int r = 0;

		Scanner input;

		// fill maze board cell arrays
		try {

			// scan the maze text file
			input = new Scanner(new File("maze.txt"));

			// read maze text file into character array
			while (input.hasNext()) {

				maze[r] = input.nextLine().toCharArray();

				// fill array of maze's JLabel objects
				for (int c = 0; c < maze[r].length; c++) {

					// create a JLabel for the cell
					cell[r][c] = new JLabel();

					// set the appropriate image for each cell
					
					// wall
					if (maze[r][c] == 'W') {

						cell[r][c].setIcon(WALL);

					}

					// gate
					else if (maze[r][c] == 'G') {

						cell[r][c].setIcon(GATE);
					}

					// power pellet
					else if (maze[r][c] == 'S') {

						cell[r][c].setIcon(SCARE);
					}

					// food pellet
					else if (maze[r][c] == 'F') {

						cell[r][c].setIcon(FOOD);
						pellets++;

					}

					// PacMan
					else if (maze[r][c] == 'P') {

						cell[r][c].setIcon(pacMan.getIcon());
						pacMan.setRow(r);
						pacMan.setColumn(c);
						pacMan.setDirection(0); // left

					}

					// ghosts
					else if (maze[r][c] == '0' || maze[r][c] == '1' || maze[r][c] == '2') {

						// convert ASCII code of maze character to an integer
						int ghostNum = (int) (maze[r][c]) - 48;

						cell[r][c].setIcon(ghost[ghostNum].getIcon());
						ghost[ghostNum].setRow(r);
						ghost[ghostNum].setColumn(c);

					}

					// door
					else if (maze[r][c] == 'D') {

						cell[r][c].setIcon(DOOR);
					}

					// add the cell's image to the game board
					add(cell[r][c]);

				}

				// move on to next row
				r++;

			}

			// close maze file
			input.close();

			// returns a message if there is a file error
		} catch (FileNotFoundException error) {

			System.out.println("File not found");

		}

	}

	// this method lets the user control PacMan's direction with arrow keys
	@Override
	public void keyPressed(KeyEvent key) {

		// starts game when a key is pressed
		if (!gameTimer.isRunning() && !pacMan.isDead()) {

			// play intro music if user has sound on
			if (!PacManGame.muted) {
				
				introMusic.setMicrosecondPosition(0); // start audio from beginning
				introMusic.start();
				
			}
				

			// start the game timer
			gameTimer.start();

		}

		// checks that pacMan is allowed to move
		if (!pacMan.isDead() && score != pellets) {

			// determine the key pressed, and convert ASCII code to an integer
			int direction = key.getKeyCode() - 37;

			// move left
			if (direction == 0 && maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'W')
				pacMan.setDirection(direction);

			// move up
			else if (direction == 1 && maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'W')
				pacMan.setDirection(direction);

			// move right
			else if (direction == 2 && maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'W')
				pacMan.setDirection(direction);

			// move down
			else if (direction == 3 && maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'W'
					&& maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'G')
				pacMan.setDirection(direction);

		}

	}

	// this method lets user control sound effects
	@Override
	public void keyTyped(KeyEvent key) {

		// if the user types the 'm' key
		if (key.getKeyChar() == 'm') {

			// if the intro is being played, mute it
			if (introMusic.isRunning())
				introMusic.stop();

			// if the sound is off, turn it on
			if (PacManGame.muted)
				
				// unmute game
				UtilityPanel.setMuted(false);

			// if sound is on, turn it off
			else
				
				// mute game
				UtilityPanel.setMuted(true);
		}
	}

	// default KeyListener class
	@Override
	public void keyReleased(KeyEvent e) {

		// default class needed to run KeyListener

	}

	// this method animates the movement of game characters
	private void performMove(Mover mover) {

		// if character enters a door, make character teleport beside other door
		if (mover.getColumn() == 1) {

			// left to right side
			mover.setColumn(24);
			cell[12][1].setIcon(DOOR);

		} else if (mover.getColumn() == 25) {

			// right to left side
			mover.setColumn(2);
			cell[12][25].setIcon(DOOR);

		}

		// prevent characters from walking back into ghost house or into a wall
		if ((maze[mover.getNextRow()][mover.getNextColumn()] == 'G' && mover.getDirection() == 3)
				|| maze[mover.getNextRow()][mover.getNextColumn()] == 'W')
			return;

		// otherwise, the character is allowed to move
		else {

			// move PacMan
			if (mover == pacMan)
				animateTimer.start();

			// move a ghost
			else {

				// make ghosts walk over gate
				if (maze[mover.getRow()][mover.getColumn()] == 'G')
					cell[mover.getRow()][mover.getColumn()].setIcon(GATE);

				// make ghosts ignore food
				else if (maze[mover.getRow()][mover.getColumn()] == 'F')
					cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);

				// make ghosts ignore power pellets
				else if (maze[mover.getRow()][mover.getColumn()] == 'S')
					cell[mover.getRow()][mover.getColumn()].setIcon(SCARE);

				// if ghost walks over a blank, replace the ghost with a blank space
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);

				// update the ghost's new location
				mover.move();

				// make PacMan die when he touches a ghost
				if (collided())
					death();

				// move the ghost to its new location
				else

					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());

				// if ghosts are scared for a certain period of time
				if (gScared > 30) {

					// set ghosts back to normal
					Ghost.setScared(false);

					// reset ghost's scare time
					gScared = 0;

					// revert each ghost's colours
					for (int gNum = 0; gNum < 3; gNum++) {

						ghost[gNum].setIcon(Ghost.GHOST[gNum]);

					}

				}
			}

		}

	}

	// this method checks if PacMan touches a ghosts
	private boolean collided() {

		// if ghost is scared, ignore collisions with PacMan
		if (Ghost.isScared())
			return false;

		// for each ghost
		for (int g = 0; g < 3; g++) {

			// check if the ghost is in the same cell as PacMan
			if (ghost[g].getRow() == pacMan.getRow() && ghost[g].getColumn() == pacMan.getColumn())
				return true;

		}
		
		return false;
	}

	// this method kills PacMan and ends the game
	private void death() {

		// play death sound if user has sound on
		if (!PacManGame.muted)
			AudioPlayer.getAudio(AudioPlayer.death).start();

		// record PacMan as dead
		pacMan.setDead(true);

		// stop the game
		stopGame();

		// replace PacMan's character with a skull
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);

	}

	// this method transitions the game screen to the end screen
	private void stopGame() {

		// (game ends when PacMan dies or eats all pellets)

		// stop the game from running
		animateTimer.stop();
		gameTimer.stop();

		// take user to next screen after 2 seconds
		gameOverTimer.start();

	}

	// this method moves ghosts
	private void moveGhosts() {

		// for each of the three ghosts
		for (Ghost ghost : ghost) {

			// makes ghost chase or run from PacMan when they see him
			if (Ghost.seesPacMan(ghost)) {

				// if ghost is blue, run away from PacMan
				if (Ghost.isScared())
					runAway(ghost);

				// otherwise, set direction to chase PacMan
				else

					// set the ghost's direction towards PacMan
					ghost.setDirection(chasePacMan(ghost));

			}

			// exit ghost house if on the path
			else if (Ghost.onPath(ghost))
				
				// set the ghost's direction as up
				Ghost.exitPath(ghost);

			// otherwise, randomize ghost's direction
			else {

				int direction = 0;

				// generate a random direction
				do {

					direction = (int) (Math.random() * 4);

					// prevents ghost from walking backwards
				} while (Math.abs(ghost.getDirection() - direction) == 2);

				// set the ghost's direction
				ghost.setDirection(direction);

			}

			// move the ghost
			performMove(ghost);
		}

	}

	// this method makes a ghost chase PacMan by returning the direction towards him
	private int chasePacMan(Ghost ghost) {
		
		// direction of the ghost
		int direction;

		// if PacMan is in the same row
		if (ghost.getRow() == pacMan.getRow()) {

			// determine which direction to move
			if (ghost.getColumn() > pacMan.getColumn()) // if ghost is to the right of PacMan
				direction = 0; // move left
			else
				direction = 2; // move right

		}

		// if PacMan is in the same column
		else {

			// determine which direction to move
			if (ghost.getRow() > pacMan.getRow()) // if ghost is below PacMan
				direction = 1; // move up
			else
				direction = 3; // move down

		}

		return direction;
	}

	// this method make a ghost in the opposite direction from PacMan
	private void runAway(Ghost ghost) {

		// get the direction towards PacMan
		int direction = chasePacMan(ghost);
		
		// direction away from PacMan
		int oppositeDirection = direction;

		// reverse the direction of chasing PacMan
		if (direction == 0 || direction == 1)
			oppositeDirection += 2; // left to right or up to down
		else
			oppositeDirection -= 2; // right to left or down to up

		// set the ghost's direction
		ghost.setDirection(oppositeDirection);

		// if the ghost's next cell is a wall, change direction
		while (maze[ghost.getNextRow()][ghost.getNextColumn()] == 'W') {

			// ghost's new direction
			int newDirection;

			// generate another direction
			do {

				newDirection = (int) (Math.random() * 4);

			} while (newDirection == oppositeDirection);

			// set the ghost's new direction
			ghost.setDirection(newDirection);

		}

	}

	// this method moves the game characters
	@Override
	public void actionPerformed(ActionEvent e) {

		// move characters
		if (e.getSource() == gameTimer) {

			performMove(pacMan);
			moveGhosts();

			// increment ghost scared state time
			if (Ghost.isScared())
				gScared++;
		}

		// animate PacMan's mouth
		else if (e.getSource() == animateTimer) {

			// change PacMan's animation phase
			animatePacMan();

			// increment animation steps
			pStep++;

			// restart PacMan's step animation
			if (pStep == 3)
				pStep = 0;
		}

		// game over timer
		else if (e.getSource() == gameOverTimer) {

			// increment seconds after the game stops
			gameOverSec++;

			//  after 3 seconds
			if (gameOverSec >= 2) {

				// stop the timer
				gameOverTimer.stop();

				// update score
				PacManGame.gameOver.updateScore();

				// update sound icon
				GameOver.playMusic();
				
				// take user to end screen
				PacManGame.gameOver.setVisible(true);
				PacManGame.playGame.setVisible(false);

			}

		}

	}

	// this method coordinate's PacMan's chomps and steps as he moves
	private void animatePacMan() {

		// open mouth phase
		if (pStep == 0) {

			// determine which way PacMan is facing
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][1]);

			// keep PacMan's mouth open for 0.1 seconds
			animateTimer.setDelay(100);

		}

		// blank phase
		else if (pStep == 1) {

			// make PacMan disappear briefly
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

		}

		// closed mouth phase
		else if (pStep == 2) {

			// move PacMan to his new spot
			pacMan.move();

			// increase score if PacMan walks over food
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F') {

				score++;

				// play PacMan's chomp sound if user has sound on
				if (!PacManGame.muted)
					AudioPlayer.getAudio(AudioPlayer.chomp).start();

				// record food as eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				// update score
				UtilityPanel.updateScore();

				// determine if user has eaten all pellets
				if (score == pellets)

					stopGame();

			}

			// if PacMan eats a power pellet
			else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'S') {

				// play sound effect if user has sound on
				if (!PacManGame.muted)
					AudioPlayer.getAudio(AudioPlayer.powerEat).start();

				// scare ghosts
				Ghost.setScared(true);

				// makes ghosts blue
				for (Ghost g : ghost) {

					g.setIcon(SCAREDGHOST);

				}

				// record power pellet as eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				// reset ghost scare time
				gScared = 0;

			}

			// if PacMan walks on a ghost
			else if (collided())

				// kill PacMan
				death();

			animateTimer.stop();

			// if PacMan is dead, replace with skull image
			if (pacMan.isDead())

				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);

			// otherwise, close PacMan's mouth
			else

				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);

		}

	}

}
