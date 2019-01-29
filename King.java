
public class King extends Piece
{
	public boolean canCastle = true;
	private int vertOffset = 0; //for the checkPath method
	private int horizOffset = 0; //for the checkPath method
	private String checkName; //name of piece attacking king
	private int checkRow; //row for piece attacking king
	private int checkCol; //column for piece attacking king
	
	public King(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "King";
		canCastle = true;
	}
	
	public Piece getInstance()
	{
		return new King(getType(), getRow(), getCol());
	}
	public boolean inCheck()
	{
		//check paths in all 8 directions
		if (checkPath(8 - getRow(), 1, 0) || checkPath(getRow() + 1, -1, 0) || checkPath(8 - getCol(), 0, 1) || checkPath(getCol() + 1, 0, -1))
		{
			if (checkName == "Queen" || checkName == "Rook")
			{		
				return true;
			}
		}
		if (checkPath(8, 1, 1) || checkPath(8, 1, -1) || checkPath(8, -1, 1) || checkPath(8, -1, -1))
		{
			if (checkName == "Queen" || checkName == "Bishop")
			{	
				return true;
			}
			//pawn case for black king
			if (checkName == "Pawn" && type == false && checkRow - getRow() == 1 && Math.abs(checkCol - getCol()) == 1)
			{			
				return true;
			}
			//pawn case for white king
			if (checkName == "Pawn" && type == true && checkRow - getRow() == -1 && Math.abs(checkCol - getCol()) == 1)
			{			
				return true;
			}
			
		}
		//knight case
		if (getRow() > 0 && getCol() > 1 && Chess.grid[getRow() - 1][getCol() - 2] != null && Chess.grid[getRow() - 1][getCol() - 2].getName() == "Knight" && Chess.grid[getRow() - 1][getCol() - 2].getType() != type)
		{			
			return true;
		}
		if (getRow() < 7 && getCol() > 1 && Chess.grid[getRow() + 1][getCol() - 2] != null && Chess.grid[getRow() + 1][getCol() - 2].getName() == "Knight" && Chess.grid[getRow() + 1][getCol() - 2].getType() != type)
		{			
			return true;
		}
		if (getRow() > 0 && getCol() < 6 &&Chess.grid[getRow() - 1][getCol() + 2] != null && Chess.grid[getRow() - 1][getCol() + 2].getName() == "Knight" && Chess.grid[getRow() - 1][getCol() + 2].getType() != type)
		{	
			return true;
		}
		if (getRow() < 7 && getCol() < 6 &&Chess.grid[getRow() + 1][getCol() + 2] != null && Chess.grid[getRow() + 1][getCol() + 2].getName() == "Knight" && Chess.grid[getRow() + 1][getCol() + 2].getType() != type)
		{		
			return true;
		}
		if (getRow() > 1 && getCol() > 0 &&Chess.grid[getRow() - 2][getCol() - 1] != null && Chess.grid[getRow() - 2][getCol() - 1].getName() == "Knight" && Chess.grid[getRow() - 2][getCol() - 1].getType() != type)
		{		
			return true;
		}
		if (getRow() < 6 && getCol() > 0 &&Chess.grid[getRow() + 2][getCol() - 1] != null && Chess.grid[getRow() + 2][getCol() - 1].getName() == "Knight" && Chess.grid[getRow() + 2][getCol() - 1].getType() != type)
		{		
			return true;
		}
		if (getRow() > 1 && getCol() < 7 &&Chess.grid[getRow() - 2][getCol() + 1] != null && Chess.grid[getRow() - 2][getCol() + 1].getName() == "Knight" && Chess.grid[getRow() - 2][getCol() + 1].getType() != type)
		{
			return true;
		}
		if (getRow() < 6 && getCol() < 7 &&Chess.grid[getRow() + 2][getCol() + 1] != null && Chess.grid[getRow() + 2][getCol() + 1].getName() == "Knight" && Chess.grid[getRow() + 2][getCol() + 1].getType() != type)
		{
			return true;
		}
		
		canCastle = true;
		return false;
	}
	
	public boolean checkPath(int iterations, int vert, int horiz) //checks each square in path for a piece
	{

		if (iterations > 1) //if there are iterations left
		{
			//increases the distance of the square being checked
			if (vert < 0)
			{
				vertOffset += 1;
			}
			
			if (vert > 0)
			{
				vertOffset -= 1;
			}
			
			if (horiz < 0)
			{
				horizOffset += 1;
			}
			
			if (horiz > 0)
			{
				horizOffset -= 1;
			}
			//checks if out of bounds
			if (getRow() + vertOffset > 7)
			{
				return true;
			}
			if (getCol() + horizOffset > 7)
			{
				return true;
			}
			if (getRow() + vertOffset < 0)
			{
				return true;
			}
			if (getCol() + horizOffset < 0)
			{
				return true;
			}
			
			if (Chess.grid[getRow() + vertOffset][getCol() + horizOffset] != null && Chess.grid[getRow() + vertOffset][getCol() + horizOffset].getType() != type)
			{
				checkName = Chess.grid[getRow() + vertOffset][getCol() + horizOffset].getName();
				checkRow = getRow() + vertOffset;
				checkCol = getCol() + horizOffset;
				horizOffset = 0;
				vertOffset = 0;
				return true;
			}
			if (Chess.grid[getRow() + vertOffset][getCol() + horizOffset] != null && Chess.grid[getRow() + vertOffset][getCol() + horizOffset].getType() == type)
			{
				horizOffset = 0;
				vertOffset = 0;
				return false;
			}
			
			iterations -= 1; //number of recursions left
			return(checkPath(iterations,vert,horiz));
		}
		horizOffset = 0;
		vertOffset = 0;
		return false; //path is clear
	}
		
	public boolean inMate()
	{
		for (int z = 0; z < 8; z++)
		{
			for (int zz = 0; zz < 8; zz++)
			{
				if (Chess.grid[z][zz] != null && Chess.grid[z][zz].getType() == getType())
				{
					for (int i = 0; i < 8; i++)
					{
						for (int j = 0; j < 8; j++)
						{
							if (!(i == z && j == zz) && Chess.grid[z][zz].canMove(i, j))
							{
								if (Chess.grid[z][zz].checkMove(i, j))
								{
									return false;
								}
							}	
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean canMove(int r, int c)
	{
		if ((Math.abs(getRow() - r) <= 1 && Math.abs(getCol() - c) <= 1) && (Math.abs(getRow() - r) >= 0 && Math.abs(getCol() - c) >= 0)) 
		{
			if (Chess.grid[r][c] != null && Chess.grid[r][c].getType() != type)
			{
				setMoved(true);
				return true; //True if move is one square in any direction
			}
			
			if (Chess.grid[r][c] == null)
			{
				setMoved(true);
				return true;
			}
		}
		
		if (r < 7 && Chess.grid[r + 1][c] != null && Chess.grid[r + 1][c].getName() == "King")
		{
			return false;
		}
		if (r < 7 && c < 7 && Chess.grid[r + 1][c + 1] != null && Chess.grid[r + 1][c + 1].getName() == "King")
		{
			return false;
		}
		if (r < 7 && c > 0 && Chess.grid[r + 1][c - 1] != null && Chess.grid[r + 1][c - 1].getName() == "King")
		{
			return false;
		}
		if (c < 7 && Chess.grid[r][c + 1] != null && Chess.grid[r][c + 1].getName() == "King")
		{
			return false;
		}
		if (c > 0 && Chess.grid[r][c - 1] != null && Chess.grid[r][c - 1].getName() == "King")
		{
			return false;
		}
		if (r > 0 && c < 7 && Chess.grid[r - 1][c + 1] != null && Chess.grid[r - 1][c + 1].getName() == "King")
		{
			return false;
		}
		if (r > 0 && c > 0 && Chess.grid[r - 1][c - 1] != null && Chess.grid[r - 1][c - 1].getName() == "King")
		{
			return false;
		}
		if (r > 0 && Chess.grid[r - 1][c] != null && Chess.grid[r - 1][c].getName() == "King")
		{
			return false;
		}
		
		//castle 
		if (canCastle)
		{
			if (getMoved() == false)
			{
				if (Chess.grid[r][c] == null)
				{
					if (getRow() == r && c == 2) //clicked two squares to the left
					{
						if (Chess.grid[r][c + 1] == null && Chess.grid[r][c - 1] == null) //checks if there are any pieces in the way
						{
							if (Chess.grid[r][0] != null && Chess.grid[r][0].getName() == "Rook" && Chess.grid[r][0].getMoved() == false) //if rook has not moved
							{
								Chess.grid[r][0].movePiece(r, c + 1); //castle
									return true;
							}
							}
					}
					
						if (getRow() == r && c - getCol() == 2) //clicked two squares to the right
					{
						if (Chess.grid[r][c - 1] == null) //checks if path is empty
						{
							if (Chess.grid[r][c + 1].getMoved() == false) //if rook has not moved
							{
									Chess.grid[r][c + 1].movePiece(r, c - 1); //castle
								return true;
							}
						}
					}
				}
			}
		}
		return false; //False if more than one square in any direction
	}

}
