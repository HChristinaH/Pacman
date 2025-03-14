import javax.swing.JLabel;

// this method creates the characteristics for a character's movement
@SuppressWarnings("serial")
public abstract class Mover extends JLabel {

	// current location
	private int row;
	private int column;

	// displacement
	private int dRow;
	private int dColumn;

	// keeps track if PacMan is dead
	private boolean isDead;

	// getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	// utility methods

	// this updates the characters new position
	public void move() {

		row += dRow;
		column += dColumn;

	}

	// set the character's direction [0-left, 1-up, 2-right, 3-down]
	public void setDirection(int direction) {

		// reset direction
		dRow = 0;
		dColumn = 0;

		// determine new direction
		if (direction == 0) // left
			dColumn = -1;
		else if (direction == 1) // up
			dRow = -1;
		else if (direction == 2) // right
			dColumn = 1;
		else if (direction == 3) // down
			dRow = 1;

	}

	// this method get the character's direction
	public int getDirection() {

		// determine the character's direction
		if (dRow == 0 && dColumn == -1) // left
			return 0;
		else if (dRow == -1 && dColumn == 0) // up
			return 1;
		else if (dRow == 0 && dColumn == 1) // right
			return 2;
		else // down
			return 3;

	}

	// get the character's next row
	public int getNextRow() {

		return row + dRow;

	}

	// get the character's next column
	public int getNextColumn() {

		return column + dColumn;

	}

}
