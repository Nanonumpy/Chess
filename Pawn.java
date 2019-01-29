
public class Pawn extends Piece
{
	public boolean enPassant = false; //special boolean to track if en passant is possible
	
	public Pawn(boolean t, int r, int c)
	{
		super(t,r,c);
		name = "Pawn";
	}
	public Piece getInstance()
	{
		return new Pawn(getType(), getRow(), getCol());
	}
	
	public boolean canMove(int r, int c)
	{
		if(getType() == true) //white turn
		{
			//en passant check
			if (getRow() == 4)
			{
				enPassant = false;
			}
			
			if (Chess.grid[r][c] == null && getCol() == c)
			{
				//first turn check
				if (getRow() == 6)
				{
					//2 square move
					if (getRow() - r == 2)
					{
						enPassant = true;
						return true;
					}
				}
				
				//1 square move
				if (getRow() - r == 1)
				{
					if (r == 0) //promote
					{
						Chess.grid[getRow()][getCol()] = new Queen(true, getRow(), getCol());
					}
					return true;
				}
			}
			//capture move
			if (c != getCol())
			{
				//if there is a piece
				if(Chess.grid[r][c] != null)
				{
					//if the move is valid
					if (getRow() - r == 1 && (c - getCol() == 1 || getCol() - c == 1))
					{
						if (r == 0) //promote
						{
							Chess.grid[getRow()][getCol()] = new Queen(true, getRow(), getCol());
						}
						return true;
					}
				}
				
				//en passant
				if (r < 7 && Chess.grid[r][c] == null && Chess.grid[r + 1][c] != null)
				{
					if (getRow() - r == 1 && (c - getCol() == 1 || getCol() - c == 1))
					{
						if(Chess.grid[r + 1][c].getName() == "Pawn" && ((Pawn)Chess.grid[r + 1][c]).enPassant == true) //checks if en passant is possible
						{
							Chess.grid[r + 1][c] = null;
							return true;
						}
					}
				}
			}
			
		}
		
		else //black turn
		{
			
			//en passant check
			if (getRow() == 3)
			{
				enPassant = false;
			}
			
			if (Chess.grid[r][c] == null && getCol() == c)
			{
				//first turn check
				if (getRow() == 1)
				{
					//2 square move
					if (r - getRow() == 2)
					{
						enPassant = true;
						return true;
					}
				}
				
				//1 square move
				if (r - getRow() == 1)
				{
					if (r == 7) //promote
					{
						Chess.grid[getRow()][getCol()] = new Queen(false, getRow(), getCol());
					}
					
					return true;
				}
			}
			
			//capture move
			if (c != getCol())
			{
				//if there is a piece
				if(Chess.grid[r][c] != null)
				{
					//if the move is valid
					if (r - getRow() == 1 && (c - getCol() == 1 || getCol() - c == 1))
					{
						if (r == 7) //promote
						{
							Chess.grid[getRow()][getCol()] = new Queen(false, getRow(), getCol());
						}
						return true;
					}
				}
				
				//en passant
				
				if (r > 0 && Chess.grid[r][c] == null && Chess.grid[r - 1][c] != null)
				{
					if (r - getRow() == 1 && (c - getCol() == 1 || getCol() - c == 1))
					{
						if(Chess.grid[r - 1][c].getName() == "Pawn" && ((Pawn)Chess.grid[r - 1][c]).enPassant == true) //checks if en passant is possible
						{
							Chess.grid[r - 1][c] = null;
							return true;
						}
					}
				}
			}
		}
		return false; //if move is invalid
	}
}
