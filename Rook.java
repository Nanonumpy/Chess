
public class Rook extends Piece
{

	public Rook(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "Rook";
	}
	
	public Piece getInstance()
	{
		return new Rook(getType(), getRow(), getCol());
	}
	
	public boolean canMove(int r, int c)
	{
		if ((r != getRow() && c == getCol()) || (r == getRow() && c != getCol())) //if the move is horizontal
		{
			if (Chess.grid[r][c] != null && Chess.grid[r][c].getType() == getType())
			{
				return false; //return false if captured piece is same as current piece
			}

			return checkPath(Math.abs(row - r), row - r, col - c); //checks if there are any pieces in the path
		}
		return false; //return false if the move is not horizontal
	}

}
