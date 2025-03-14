import javax.swing.ImageIcon;

// this class creates an instance of a ghost
@SuppressWarnings("serial")
public class Ghost extends Mover {

	// keeps track if ghosts are in scared state
	private static boolean isScared;

	// initialize ghost images
	public static final ImageIcon[] GHOST = {

			new ImageIcon("images/Ghost1.bmp"), new ImageIcon("images/Ghost2.bmp"), new ImageIcon("images/Ghost3.bmp"),

	};

	// constructor
	public Ghost(int ghostNum) {

		setIcon(GHOST[ghostNum]);

	}

	// make ghosts scared
	public static void setScared(boolean status) {

		isScared = status;

	}

	// check if ghosts are scared
	public static boolean isScared() {

		return isScared;

	}

	// this method takes a ghost out of the house
	public static void exitPath(Ghost ghost) {

		// make ghosts walk up
		ghost.setDirection(1);

	}

	// this method checks if a ghost is on the path to exit the house
	public static boolean onPath(Ghost ghost) {
		
		// check if ghost is on the trail or on the gate
		if (Board.maze[ghost.getRow()][ghost.getColumn()] == 'T' ||
				Board.maze[ghost.getRow()][ghost.getColumn()] == 'G')
			return true;
		
		else
			return false;

	}

	// this method checks if ghosts see PacMan
	public static boolean seesPacMan(Ghost ghost) {

		// check if PacMan is in the ghost's line of vision
		for (int direction = -1; direction <= 1; direction += 2) {

			// get the ghost's current position
			int row = ghost.getRow();
			int column = ghost.getColumn();

			// cells seen by the ghost
			int nextRow = ghost.getRow();
			int nextColumn = ghost.getColumn();

			// check if PacMan is in the same row and stop looking once ghost sees a wall or
			// door
			while (nextColumn >= 0 && nextColumn < 27 && Board.maze[row][nextColumn] != 'W'
					&& Board.maze[row][nextColumn] != 'D') {

				// check for PacMan in the next cell in the row
				if (row == Board.pacMan.getRow() && nextColumn == Board.pacMan.getColumn())
					return true;

				// move on to next cell in the row
				nextColumn += direction;

			}

			// check if Pacman is in the same column
			while (nextRow >= 0 && nextRow < 25 && Board.maze[nextRow][column] != 'W'
					&& Board.maze[nextRow][column] != 'D') {

				// check for PacMan in the next cell in the column
				if (nextRow == Board.pacMan.getRow() && column == Board.pacMan.getColumn())
					return true;

				// move on to next cell in the column
				nextRow += direction;

			}

		}

		return false;

	}

}
