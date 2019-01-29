
public class Bishop extends Piece
{

	public Bishop(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "Bishop";
	}
	
	public Piece getInstance()
	{
		return new Bishop(getType(), getRow(), getCol());
	}
	
	public boolean canMove(int r, int c)
	{
		if (Math.abs(row - r) == Math.abs(col - c)) //if the move is diagonal
		{
			if (Chess.grid[r][c] != null && Chess.grid[r][c].getType() == getType())
			{
				return false; //return false if captured piece is same as current piece
			}

			return checkPath(Math.abs(row - r), row - r, col - c); //checks if there are any pieces in the path
		}
		return false; //return false if the move is not diagonal
	}
	

}
