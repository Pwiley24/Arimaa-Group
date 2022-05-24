package arimaaProject;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Arimaa implements ActionListener{
	JFrame panel = new JFrame("Arimaa");
	Container center = new Container();
	JButton[][] board = new JButton[8][8];

	Container west = new Container();
	JButton player1 = new JButton("Player 1");
	JLabel elephantP1 = new JLabel();
	ImageIcon elephantImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg");
	

	Container east = new Container();
	JButton player2 = new JButton("Player 2");
	
	int PLAYER_ONE = 1;
	int PLAYER_TWO = 2;
	int turn = 0;
	boolean setUpStatus = false;
	

	public Arimaa() {
		//Frame layout
		panel.setSize(800, 800);
		panel.setLayout(new BorderLayout());

		//center layout
		center.setLayout(new GridLayout(8,8));
		for(int y = 0; y < board.length; y++){
			for(int x = 0; x < board.length; x++){
				board[y][x] = new JButton(y + ", " + x);
				if(y == 2 || y == 5){
					if(x == 2 || x == 5){
						//set background of button to red
					}
	}
				center.add(board[y][x]);
				board[y][x].addActionListener(this);
			}
		}
		
		panel.add(center, BorderLayout.CENTER);

		//west layout
		west.setLayout(new GridLayout(9, 1));
		west.add(player1);
		player1.addActionListener(this);
		elephantP1.setIcon(elephantImg);
		west.add(elephantP1);
		panel.add(west, BorderLayout.WEST);
		
		/*west.add(camelP1);
		camelP1.addActionListener(this);
		west.add(horseP1);
		horseP1.addActionListener(this);
		west.add(horse2P1);
		horse2P1.addActionListener(this);*/

		//east Layout
		east.setLayout(new GridLayout(9, 1));
		east.add(player2);
		player1.addActionListener(this);
		elephantP1.setIcon(elephantImg);
		east.add(elephantP1);
		panel.add(east, BorderLayout.EAST);

		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Arimaa();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(player1)) {//if player one is going first
			turn = PLAYER_ONE;
			setUp();
		}else if(e.getSource().equals(player2)) {//if player two is going first
			turn = PLAYER_TWO;
			setUp();
		}
		
	}
	
	/*
	 * This method sets up the board pieces by allowing players to only click
	 * on the first two rows of their side to place their pieces one by one.
	 */
	public void setUp() {
		setUpStatus = true;
		if(turn == PLAYER_ONE) {
			//set player one pieces up
			
		}else {
			//set up player two pieces
		}
	}

}
