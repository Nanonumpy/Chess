

public abstract class Piece 
{
	public boolean type; //current color (white or black)
	public String name; //current piece name (pawn, knight, king etc.)
	public int row; //current row
	public int col; //current column
	private int vertOffset = 0; //for the checkPath method
	private int horizOffset = 0; //for the checkPath method
	public boolean hasMoved = false; //checks if piece has moved
	public boolean canCastle = false; //checks if king can castle	
		
	public Piece(boolean t, int r, int c)
	{
		type = t;
		row = r;
		col = c;
	}
	
	public boolean inCheck() {return false;} //java is dumb so this useless method is here
	
	//getter methods
	public boolean getType()
	{
		return type;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public boolean getMoved()
	{
		return hasMoved;
	}
	
	public String getName()
	{
		return name;
	}
	//setter methods
	public void setRow(int r)
	{
		row = r;
	}
	
	public void setCol(int c)
	{
		col = c;
	}
	
	public void setMoved(boolean m) 
	{
		hasMoved = m;
	}
	//abstract method to be instantiated by each piece depending on how it moves
	public abstract boolean canMove(int r, int c);
	//creates a temporary instance of the piece to be used when checking certain pieces
	public abstract Piece getInstance();
	
	public boolean checkMove(int r, int c) //returns true if the move takes the king out of check
	{
		Piece temp = Chess.grid[getRow()][getCol()].getInstance();
		Piece temp2;
		if (Chess.grid[r][c] != null)
		{
			temp2 = Chess.grid[r][c].getInstance();
		}
		
		else
		{
			temp2 = null;
		}
		
		int tempr = getRow();
		int tempc = getCol();
		
		Chess.grid[r][c] = null;
		Chess.grid[getRow()][getCol()] = null;
		Chess.grid[r][c] = temp;
		setRow(r);
		setCol(c);
		//if the king is in check after the move return false
		if((Chess.whiteTurn && Chess.whiteKing.inCheck()) || (!Chess.whiteTurn && Chess.blackKing.inCheck()))
		{
			setCol(tempc);
			setRow(tempr);
			Chess.grid[getRow()][getCol()] = temp;
			Chess.grid[r][c] = temp2;
			return false;
		}
		//or if the king moved into check return false
		if (getName() == "King" && inCheck())
		{
			setCol(tempc);
			setRow(tempr);
			Chess.grid[getRow()][getCol()] = temp;
			Chess.grid[r][c] = temp2;
			return false;
		}
		//otherwise return true
		setCol(tempc);
		setRow(tempr);
		Chess.grid[getRow()][getCol()] = temp;
		Chess.grid[r][c] = temp2;
		return true;
	}
	
	
	public void movePiece(int r, int c)
	{
		Chess.grid[r][c] = Chess.grid[getRow()][getCol()];
		Chess.grid[getRow()][getCol()] = null;
		setRow(r);
		setCol(c);

	}
	
	public boolean checkPath(int iterations, int vert, int horiz) //checks each square in path for a piece
	{

		if (iterations > 1)
		{
			//increases distance every loop
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
				
			if (Chess.grid[getRow() + vertOffset][getCol() + horizOffset] != null)
			{
				horizOffset = 0;
				vertOffset = 0;
				return false;
			}
			iterations -= 1; //number of recursions left
			if (checkPath(iterations, vert, horiz) == false) //makes sure one false recursion returns false for the method
			{
				return false;
			}
		}
		horizOffset = 0;
		vertOffset = 0;
		return true; //path is clear
	}
	
	
}
