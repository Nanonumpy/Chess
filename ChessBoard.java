
/*
 * This is the panel for the Chess game
 * It will handle all inputs and drawing
 */

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class ChessBoard extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Color blackground = new Color(118, 117, 116); //Color of the background
	private static Color whiteground = new Color(207, 206, 203);
    private Font fPiece = new Font("Courier", Font.PLAIN,108); 
	private char drawChar;
	private static int offset = 5;
	public char[][] highlight = new char[8][8];
	private int redRow = -1;
	private int redCol = -1;
	
	//Default constructor
	public ChessBoard()
	{
		ChessHandler cHandler = new ChessHandler(); //Initialize handler
		addKeyListener(cHandler); //Add handlers
		addMouseListener(cHandler);
		setFocusable(true); //Allow focus
		setBackground(whiteground);
	}

	//Override painting method for unique drawing
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //Call super to paint on frame
		Graphics2D g2d = (Graphics2D) g; //Establish better graphics system
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Enable antialiasing

		drawGrid(g2d);
		drawPieces(g2d);
	}
	
	//Draws in circles for the pieces depending on what the grid contains
	private void drawPieces(Graphics2D g2d)
	{
		g2d.setFont(fPiece);
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++) //Loop through every piece
			{
				if (highlight[row][col] == 'R')
				{
					g2d.setColor(Color.red);
					g2d.fillRect(100 * col + offset, 100 * row + offset, 100 - (offset << 1), 100 - (offset << 1));
					g2d.setColor(whiteground);
					if (row % 2 == 0 && col % 2 == 1 || row % 2 == 1 && col % 2 == 0)
					{
						g2d.setColor(blackground);
					}
					g2d.fillRect(100 * col + 10, 100 * row + 10, 100 - (offset << 1) - 10, 100 - (offset << 1) - 10);
				}
				
				//checks for white pieces and draws them
				if (Chess.grid[row][col] instanceof King && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2654';
				}
				else if (Chess.grid[row][col] instanceof Queen && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2655';
				}
				else if (Chess.grid[row][col] instanceof Rook && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2656';
				}
				else if (Chess.grid[row][col] instanceof Bishop && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2657';
				}
				else if (Chess.grid[row][col] instanceof Knight && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2658';
				}
				else if (Chess.grid[row][col] instanceof Pawn && Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.white);
					drawChar = '\u2659';
				}
				//Checks for Black Pieces and draws them
				else if (Chess.grid[row][col] instanceof King && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265A';
				}
				else if (Chess.grid[row][col] instanceof Queen && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265B';
				}
				else if (Chess.grid[row][col] instanceof Rook && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265C';
				}
				else if (Chess.grid[row][col] instanceof Bishop && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265D';
				}
				else if (Chess.grid[row][col] instanceof Knight && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265E';
				}
				else if (Chess.grid[row][col] instanceof Pawn && !Chess.grid[row][col].getType())
				{
					g2d.setColor(Color.black);
					drawChar = '\u265F';
				}
				
				else
				{
					drawChar = '\0';
				}
				
				g2d.drawString(String.valueOf(drawChar), 100 * col - 3, 100 * (row + 1)- 10); //Draw the piece
			}
		}
	}

	//Draws in the grid to establish that checkerboard pattern
	private void drawGrid(Graphics2D g2d)
	{
		g2d.setColor(blackground);
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				if (row % 2 == 0 && col % 2 == 1)
				{
					g2d.fillRect(100 * row, 100 * col, 100, 100);
				}
				
				else if (row % 2 == 1 && col % 2 == 0)
				{
					g2d.fillRect(100 * row, 100 * col, 100, 100);
				}
			}
		}
	}

	/*
	 * This private class does all the input handling for the Chess Handling It
	 * will also likely control repaint rate KeyListening may prove to be pointless
	 */
	private class ChessHandler implements KeyListener, MouseListener
	{
		//@formatter:off
		// keyListener methods
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e)
		{
		}

		// mouseListener methods
		public void mouseClicked(MouseEvent e)
		{	
			int clickRow = (e.getY() + 10) / 100;
			int clickCol = (e.getX() + 3) / 100;
			boolean noRed = true; 
			//prevents clicking out of bounds
			if (clickRow > 7)
			{
				clickRow = 7;
			}
			
			if (clickCol > 7)
			{
				clickCol = 7;
			}
			
			if (clickRow < 0)
			{
				clickRow = 0;
			}
			
			if (clickCol < 0)
			{
				clickCol = 0;
			} 
			
			//checks for any selected squares
			for (int z = 0; z < 8; z++)
			{
				for (int zz = 0; zz < 8; zz++)
				{
					if (highlight[z][zz] == 'R')
					{
						noRed = false;
					}
				}
			}
			
			if (noRed) //no squares have been selected
			{
				if (Chess.grid[clickRow][clickCol] != null && Chess.grid[clickRow][clickCol].getType() == Chess.whiteTurn) // if the clicked square has piece of the current player's color on it
				{
					highlight[clickRow][clickCol] = 'R'; //selects the square
					redRow = clickRow;
					redCol = clickCol;
				}
			}
			
			else if (clickRow == redRow && clickCol == redCol) //the clicked square is already selected
			{
				highlight[redRow][redCol] = '\0'; //deselects the square
				redRow = -1;
				redCol = -1;
			}
			
			else if (Chess.grid[clickRow][clickCol] != null && Chess.grid[clickRow][clickCol].getType() == Chess.whiteTurn) //if clicked square has a piece of the same color as the current player and another square is already selected
			{
				highlight[redRow][redCol] = '\0';
				redRow = clickRow;
				redCol = clickCol;
				highlight[clickRow][clickCol] = 'R';
			}
			
			else if (redRow >= 0 && redCol >= 0) // clicked to move piece
			{
				if (Chess.grid[redRow][redCol].canMove(clickRow, clickCol)) //checks if the piece can move to clicked square
				{
					if (Chess.grid[redRow][redCol].getName() == "King") //if the king is moving
					{
						King temp = new King(Chess.grid[redRow][redCol].getType(), clickRow, clickCol);
						Chess.grid[clickRow][clickCol] = temp;
						if (!temp.checkMove(clickRow, clickCol)) //check to see if the moves puts the king in check
						{
							temp = null;
							Chess.grid[clickRow][clickCol] = null;
							return;
						}
						Chess.grid[clickRow][clickCol] = null;
						temp = null;
					}
					if (Chess.whiteTurn && Chess.grid[redRow][redCol].checkMove(clickRow, clickCol) || !Chess.whiteTurn && Chess.grid[redRow][redCol].checkMove(clickRow, clickCol)) //check if the king is in check
					{
						Chess.grid[redRow][redCol].movePiece(clickRow, clickCol);
						highlight[redRow][redCol] = '\0'; //deselects the square
						redRow = -1;
						redCol = -1;
						Chess.whiteTurn = !Chess.whiteTurn;
						Chess.jl.setText("Current player: " + ((Chess.whiteTurn) ? "White" : "Black"));
					}
				
						//set message if in check
					if (Chess.whiteTurn && Chess.whiteKing.inCheck() || !Chess.whiteTurn && Chess.blackKing.inCheck())
					{
						Chess.jl.setText("Current player: " + ((Chess.whiteTurn) ? "White is in check!" : "Black is in check!"));
						//end game if check mate
						if ((Chess.whiteTurn && Chess.whiteKing.inMate()) || (!Chess.whiteTurn && Chess.blackKing.inMate()))
						{
							repaint();
							Chess.gameOver();
						}
					}
						
				}
			}
			repaint();
		}
		
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		//@formatter:on
	}
}

