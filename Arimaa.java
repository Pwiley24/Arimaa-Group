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
import javax.swing.JOptionPane;

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
	int piece = 0;
	int ELEPHANT = 1;
	int CAMEL = 2;
	int HORSE = 3;
	int DOG = 4;
	int CAT = 5;
	int RABBIT =6;
	int eleCount = 0;
	int camelCount = 0;
	int horseCount = 0;
	int dogCount = 0;
	int catCount = 0;
	int rabCount = 0;
	

	public Arimaa() {
		/*
		 * I used code from stackoverflow.com
		 * URL: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
		 * I used this code to resize my Jlabel images to fit better in the JFrame.
		 */
		//Image resizing elephant
		ImageIcon elephantImg = new ImageIcon("Images/elephantImg-removebg-preview.png"); //need to fix this ask
		Image eImage = elephantImg.getImage();
		Image newEImage = eImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		elephantImg = new ImageIcon(newEImage);
		
		//Image resizing camel
		ImageIcon camelImg = new ImageIcon("C:\\Users\\398256\\eclipse\\Arima Project\\camelImg-removebg-preview.png"); //need to fix this ask
		Image cImage = camelImg.getImage();
		Image newCImage = cImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		camelImg = new ImageIcon(newCImage);
		
		//Image resizing horse
		ImageIcon horseImg = new ImageIcon("C:\\Users\\398256\\eclipse\\Arima Project\\horseImg-removebg-preview.png"); //need to fix this ask
		Image hImage = horseImg.getImage();
		Image newHImage = hImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		horseImg = new ImageIcon(newHImage);
		
		//Image resizing dog
		ImageIcon dogImg = new ImageIcon("C:\\Users\\398256\\eclipse\\Arima Project\\dogImg-removebg-preview.png"); //need to fix this ask
		Image dImage = dogImg.getImage();
		Image newDImage = dImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dogImg = new ImageIcon(newDImage);
		
		//Image resizing cat
		ImageIcon catImg = new ImageIcon("C:\\Users\\398256\\eclipse\\Arima Project\\catImg-removebg-preview"); //need to fix this ask
		Image catImage = catImg.getImage();
		Image newCatImage = catImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		catImg = new ImageIcon(newCImage);
		
		//Image resizing rabbit
		ImageIcon rabbitImg = new ImageIcon("C:\\Users\\398256\\eclipse\\Arima Project\\rabbitImg-removebg-preview.png"); //need to fix this ask
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
						if(checkValid()) {//if a move is valid 
							
						}
						//change pieces after the one, two, or seven are selected
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
	
	public boolean checkValid() {
		if (setUpStatus) {//to check if a move is a valid set up move
			//valid if player one is between x-y
			//valid if player two is between z-o
			
		}else {//to check if a move during the game is valid
			
		}
		return false;
		
	}
	
	
	
	/*
	 * This method sets up the board pieces by allowing players to only click
	 * on the first two rows of their side to place their pieces one by one.
	 */
	public void setUp() {
		setUpStatus = true;
		
		//set players pieces up
		for(int i = 0; i < 2; i++) {//let both player one and two set up their pieces
			piece = ELEPHANT;
			JOptionPane.showMessageDialog(panel, "Place your elephant.");
			if(piece == ELEPHANT && eleCount == 1) {//once one elephant is placed
				piece = CAMEL;
				JOptionPane.showMessageDialog(panel, "Place your camel.");
			}else if(piece == CAMEL && camelCount == 1) {//once one camel is placed
				piece = HORSE;
				JOptionPane.showMessageDialog(panel, "Place your 2 horses.");
			}else if(piece == HORSE && horseCount == 2) {//once two horses are placed
				piece = DOG;
				JOptionPane.showMessageDialog(panel, "Place your 2 dogs.");
			}else if(piece == DOG && dogCount == 2) {//once two dogs are placed
				piece = CAT;
				JOptionPane.showMessageDialog(panel, "Place your 2 cats.");
			}else if(piece == CAT && catCount == 2) {//once two cats are placed
				piece = RABBIT;
				JOptionPane.showMessageDialog(panel, "Place your 7 rabbits.");
			}else if(piece == RABBIT && rabCount == 7) {//once seven rabbits are placed
				if(turn == PLAYER_ONE) {
					turn = PLAYER_TWO;
				}else {
					turn = PLAYER_ONE;
				}
			}
		}
		
		setUpStatus = false;
			
	}

}
