
/*

 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Chess
{
	public static JFrame jf; //Frame on which the game is located
	public static ChessBoard op; //Panel which handles drawing and input
	public static JLabel jl; //Label to display current turn
	public static JButton reset; //Button for resetting game
	public static Piece[][] grid = new Piece[8][8]; //Create grid in which to store pieces
	public static boolean whiteTurn; //True if current player is white 
	
	public static King blackKing = new King(false, 0, 4);
	public static King whiteKing = new King(true, 7, 4);

	public static void main(String[] args)
	{
		jf = new JFrame("Chess"); //Initialize components
		op = new ChessBoard();
		jl = new JLabel("Current player: Black");
		reset = new JButton("Reset");
		whiteTurn = true;

		op.setPreferredSize(new Dimension(800, 800)); //Set sizes
		jl.setSize(800, 20);
		reset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				reset();
			}
		});

		jf.add(op, BorderLayout.NORTH); //Add components to frame
		jf.add(jl, BorderLayout.CENTER);
		jf.add(reset, BorderLayout.SOUTH);

		jf.revalidate(); //Validate component hierarchy
		jf.pack(); //Size frame to fit components
		jf.setFocusTraversalKeysEnabled(true); //Allow key traversal
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set up close operation
		reset();	//Reset initial variables
		jf.setVisible(true); //Set visible
	}
	
	public static void gameOver()
	{
		JOptionPane.showMessageDialog(null, "Checkmate " + ((whiteTurn) ? "Black wins!" : "White wins!"));
		reset();
	}
	
	//This method resets the whole board
	public static void reset()
	{
		whiteTurn = true; //Reset Turn
		grid = new Piece[8][8]; //Reset grid
		//Draw white pieces
		grid[7][4] = whiteKing;
		grid[7][3] = new Queen(true, 7, 3);
		grid[7][5] = new Bishop(true, 7, 5); 
		grid[7][2] = new Bishop(true, 7, 2);
		grid[7][6] = new Knight(true, 7, 6);
		grid[7][1] = new Knight(true, 7, 1);
		grid[7][7] = new Rook(true, 7, 7); 
		grid[7][0] = new Rook(true, 7, 0);
		//Draw black pieces
		grid[0][4] = blackKing;
		grid[0][3] = new Queen(false, 0, 3);
		grid[0][5] = new Bishop(false, 0, 5); 
		grid[0][2] = new Bishop(false, 0, 2);
		grid[0][6] = new Knight(false, 0, 6);
		grid[0][1] = new Knight(false, 0, 1);
		grid[0][7] = new Rook(false, 0, 7); 
		grid[0][0] = new Rook(false, 0, 0);
		//Draw pawns
		for (int i = 0; i < 8; i ++)
		{
			grid[6][i] = new Pawn(true, 6, i);
			grid[1][i] = new Pawn(false, 1, i);
		}
		
		jl.setText("Current player: " + ((whiteTurn) ? "White" : "Black"));
		jf.repaint();
	}
}

