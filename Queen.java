
public class Queen extends Piece
{

	public Queen(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "Queen";
	}
	
	public Piece getInstance()
	{
		return new Queen(getType(), getRow(), getCol());
	}
	
	public boolean canMove(int r, int c)
	{
		if ((r != getRow() && c == getCol()) || (r == getRow() && c != getCol()) || (Math.abs(row - r) == Math.abs(col - c))) //if the move is horizontal or diagonal
		{
			if (Chess.grid[r][c] != null && Chess.grid[r][c].getType() == getType())
			{
				return false; //return false if captured piece is same as current piece
			}

			return checkPath(Math.abs(row - r), row - r, col - c); //checks if there are any pieces in the path
		}
		return false; //return false if the move is not horizontal or diagonal
	}
}
