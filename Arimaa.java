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
	JLabel camelP1 = new JLabel();
	JLabel horseP1 = new JLabel();
	JLabel dogP1 = new JLabel();
	JLabel catP1 = new JLabel();
	JLabel rabbitP1 = new JLabel();
	
	Container east = new Container();
	JButton player2 = new JButton("Player 2");
	JLabel elephantP2 = new JLabel();
	JLabel camelP2 = new JLabel();
	JLabel horseP2 = new JLabel();
	JLabel dogP2 = new JLabel();
	JLabel catP2 = new JLabel();
	JLabel rabbitP2 = new JLabel();
	
	int PLAYER_ONE = 1;
	int PLAYER_TWO = 2;
	int turn = 0;
	boolean setUpStatus = false;
	

	public Arimaa() {
		//Image resizing elephant
		ImageIcon elephantImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image eImage = elephantImg.getImage();
		Image newEImage = eImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		elephantImg = new ImageIcon(newEImage);
		
		//Image resizing camel
		ImageIcon camelImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image cImage = camelImg.getImage();
		Image newCImage = cImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		camelImg = new ImageIcon(newCImage);
		
		//Image resizing horse
		ImageIcon horseImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image eImage = horseImg.getImage();
		Image newHImage = hImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		horseImg = new ImageIcon(newHImage);
		
		//Image resizing dog
		ImageIcon dogImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image dImage = dogImg.getImage();
		Image newDImage = dImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dogImg = new ImageIcon(newDImage);
		
		//Image resizing cat
		ImageIcon catImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image cImage = catImg.getImage();
		Image newCImage = cImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		catImg = new ImageIcon(newCImage);
		
		//Image resizing rabbit
		ImageIcon rabbitImg = new ImageIcon("C:\\Users\\398256\\Downloads\\elephantImg-removebg-preview.png"); //need to fix this ask
		Image rImage = rabbitImg.getImage();
		Image newRImage = rImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		rabbitImg = new ImageIcon(newRImage);
		
		
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
		west.setLayout(new GridLayout(9, 3));
		west.add(player1);
		player1.addActionListener(this);
		elephantP1.setIcon(elephantImg);
		west.add(elephantP1);
		camelP1.setIcon(camelImg);
		west.add(camelP1);
		horseP1.setIcon(horseImg);
		west.add(horseP1);
		dogP1.setIcon(dogImg);
		west.add(dogP1);
		catP1.setIcon(catImg);
		west.add(catP1);
		rabbitP1.setIcon(rabbitImg);
		west.add(rabbitP1);
		panel.add(west, BorderLayout.WEST);
		
	
		//east Layout
		east.setLayout(new GridLayout(9, 3));
		east.add(player2);
		player1.addActionListener(this);
		elephantP2.setIcon(elephantImg);
		west.add(elephantP2);
		camelP2.setIcon(camelImg);
		west.add(camelP2);
		horseP2.setIcon(horseImg);
		west.add(horseP2);
		dogP2.setIcon(dogImg);
		west.add(dogP2);
		catP2.setIcon(catImg);
		west.add(catP2);
		rabbitP2.setIcon(rabbitImg);
		west.add(rabbitP2);
		panel.add(east, BorderLayout.EAST);

		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Arimaa();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton originalSpot = new JButton();
		JButton newSpot = new JButton();
		int clicks = 0;
		
		if(e.getSource().equals(player1)) {//if player one is going first
			turn = PLAYER_ONE;
			setUp();
		}else if(e.getSource().equals(player2)) {//if player two is going first
			turn = PLAYER_TWO;
			setUp();
		}
		
		for(int y = 0; y < board.length; y++) {
			for(int x = 0; x < board.length; x++) {
				if(e.getSource().equals(board[y][x])) {//if a board button is clicked
					if(setUpStatus) {
						originalSpot = board[y][x];
						//if it is a valid spot on the board for the setUp, add the image to the button
					}else {
						if(clicks % 1 == 0){//if clicks are even (means this click is where player wants to move)
							//check if this is a valid spot on the board
							//check if pushing or pulling will occur
							//check if trapping will happen for any pieces
							//change the background of that button and remove the background of the original spot
							clicks++;
						}else {//clicks are odd (means this is the original position, the piece to move
							//check if this is the players piece
							//highlight
							clicks++;
						}
					}
				}
			}
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
