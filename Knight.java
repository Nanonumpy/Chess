
public class Knight extends Piece
{
	
	public Knight(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "Knight";
	}
	
	public Piece getInstance()
	{
		return new Knight
				(getType(), getRow(), getCol());
	}
	
	public boolean canMove(int r, int c)
	{
		if ((Math.abs(getRow() - r) == 1 && Math.abs(getCol() - c) == 2) || (Math.abs(getRow() - r) == 2 && Math.abs(getCol() - c) == 1))
		{
			if (Chess.grid[r][c] != null && Chess.grid[r][c].getType() != type)
			{
				return true; //return true if the move is L-shaped'
			}
			
			if (Chess.grid[r][c] == null)
			{
				return true;
			}
		}
		return false; //not L-shaped
	}

}
