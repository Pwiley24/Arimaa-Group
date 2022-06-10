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
	
	Container south = new Container();
	JLabel elephant = new JLabel();
	JLabel camel = new JLabel();
	JLabel horse = new JLabel();
	JLabel horse_2 = new JLabel();
	JLabel dog = new JLabel();
	JLabel dog_2 = new JLabel();
	JLabel cat = new JLabel();
	JLabel cat_2 = new JLabel();
	JLabel rabbit = new JLabel();
	JLabel rabbit_2 = new JLabel();
	JLabel rabbit_3 = new JLabel();
	JLabel rabbit_4 = new JLabel();
	JLabel rabbit_5 = new JLabel();
	JLabel rabbit_6 = new JLabel();
	JLabel rabbit_7 = new JLabel();
	JLabel rabbit_8 = new JLabel();
	JLabel empty1 = new JLabel();
	JLabel empty2 = new JLabel();
	
	Container east = new Container();
	JButton player2 = new JButton("Player 2");

	
	int PLAYER_ONE = 1;
	int PLAYER_TWO = 2;
	int turn = 0;
	String playerTurn = null;
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
	int clicks = 0;
	int originalX = 0;
	int originalY = 0;
	int newX = 0;
	int newY = 0;
	int timesThroughSetup = 0;
	boolean makingMove = false;
	
	//Image variables for pieces:
	ImageIcon elephantImg = new ImageIcon("Images/elephant.png");
	ImageIcon camelImg = new ImageIcon("Images/camel.png"); 
	ImageIcon horseImg = new ImageIcon("Images/horse.png"); 
	ImageIcon dogImg = new ImageIcon("Images/dog.png");
	ImageIcon catImg = new ImageIcon("Images/cat.png");
	ImageIcon rabbitImg = new ImageIcon("Images/rabbit.png"); 
	

	public Arimaa() {
		/*
		 * I used code from stackoverflow.com
		 * URL: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
		 * I used this code to resize my Jlabel images to fit better in the JFrame.
		 */
		//Image resizing elephant
		Image eImage = elephantImg.getImage();
		Image newEImage = eImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		elephantImg = new ImageIcon(newEImage);
		
		//Image resizing camel
		Image cImage = camelImg.getImage();
		Image newCImage = cImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		camelImg = new ImageIcon(newCImage);
		
		//Image resizing horse
		Image hImage = horseImg.getImage();
		Image newHImage = hImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		horseImg = new ImageIcon(newHImage);
		
		//Image resizing dog
		Image dImage = dogImg.getImage();
		Image newDImage = dImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dogImg = new ImageIcon(newDImage);
		
		//Image resizing cat
		Image catImage = catImg.getImage();
		Image newCatImage = catImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		catImg = new ImageIcon(newCatImage);
		
		//Image resizing rabbit
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
				board[y][x] = new JButton();
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
		west.setLayout(new GridLayout(1,1));
		west.add(player1);
		player1.addActionListener(this);
		panel.add(west, BorderLayout.WEST);
		
		//south layout
		south.setLayout(new GridLayout(2,10));
		elephant.setIcon(elephantImg);
		south.add(elephant);
		camel.setIcon(camelImg);
		south.add(camel);
		horse.setIcon(horseImg);
		south.add(horse);
		dog.setIcon(dogImg);
		south.add(dog);
		cat.setIcon(catImg);
		south.add(cat);
		rabbit.setIcon(rabbitImg);
		south.add(rabbit);
		rabbit_2.setIcon(rabbitImg);
		south.add(rabbit_2);
		rabbit_3.setIcon(rabbitImg);
		south.add(rabbit_3);
		rabbit_4.setIcon(rabbitImg);
		south.add(rabbit_4);
		
		south.add(empty1);//next row
		south.add(empty2);
		horse_2.setIcon(horseImg);
		south.add(horse_2);
		dog_2.setIcon(dogImg);
		south.add(dog_2);
		cat_2.setIcon(catImg);
		south.add(cat_2);
		rabbit_5.setIcon(rabbitImg);
		south.add(rabbit_5);
		rabbit_6.setIcon(rabbitImg);
		south.add(rabbit_6);
		rabbit_7.setIcon(rabbitImg);
		south.add(rabbit_7);
		rabbit_8.setIcon(rabbitImg);;
		south.add(rabbit_8);
		panel.add(south, BorderLayout.SOUTH);
		
	
		//east Layout
		east.setLayout(new GridLayout(1,1));
		east.add(player2);
		player2.addActionListener(this);
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
		if(e.getSource().equals(player1)) {//if player one is going first
			turn = PLAYER_ONE;
			setUp();
		}else if(e.getSource().equals(player2)) {//if player two is going first
			turn = PLAYER_TWO;
			setUp();
		}
		playerTurn = String.valueOf(turn);
		
		
		for(int y = 0; y < board.length; y++) {
			for(int x = 0; x < board.length; x++) {
				
				if(e.getSource().equals(board[y][x])) {//if a board button is clicked
					if(setUpStatus) {
						originalSpot = board[y][x];
						originalY = y;
						//if it is a valid spot on the board for the setUp, add the image to the button
						if(checkValid(originalSpot)) {//if a move is valid
							if(clicks == 0) {//change background of button to an elephant
								originalSpot.setIcon(elephantImg);
								originalSpot.setText(playerTurn + "e");
								clicks++;
								JOptionPane.showMessageDialog(panel, "Player, place your camel.");
							}else if(clicks == 1) {//change button to camel
								originalSpot.setIcon(camelImg);
								originalSpot.setText(playerTurn + "c");
								clicks++;
								JOptionPane.showMessageDialog(panel, "Player, place your horses.");
							}else if(clicks == 2 ||
									clicks == 3) {//change button to horse for two clicks
								originalSpot.setIcon(horseImg);
								originalSpot.setText(playerTurn + "h");
								if(clicks == 3) {
									JOptionPane.showMessageDialog(panel, "Player, place your dogs.");
								}
								clicks++;
							}else if(clicks == 4 ||
									clicks == 5) {//change button to dog for two clicks
								originalSpot.setIcon(dogImg);
								originalSpot.setText(playerTurn + "d");
								if(clicks == 5) {
									JOptionPane.showMessageDialog(panel, "Player, place your cats.");
								}
								clicks++;
							}else if(clicks == 6 ||
									clicks == 7) {//change button to cat for two clicks
								originalSpot.setIcon(catImg);
								originalSpot.setText(playerTurn + "ca");
								if(clicks == 7) {
									JOptionPane.showMessageDialog(panel, "Player, place your rabbits.");
								}
								clicks++;
							}else if(clicks == 8 ||
									clicks == 9 ||
									clicks == 10 ||
									clicks == 11 ||
									clicks ==12 ||
									clicks == 13 ||
									clicks == 14 ||
									clicks == 15) {//change button to rabbit for seven clicks
								originalSpot.setIcon(rabbitImg);
								originalSpot.setText(playerTurn + "r");
								if(clicks == 15) { // if that was the last placement reset clicks for next player or start of game
									clicks = 0;
									if(turn == PLAYER_ONE) {
										turn = PLAYER_TWO;
									}else {
										turn = PLAYER_ONE;
									}
									timesThroughSetup++;
									if(timesThroughSetup == 1) {
										JOptionPane.showMessageDialog(panel, "Next player, place your elephant.");
									}else if(timesThroughSetup ==2) {
										setUpStatus = false;
										if(turn == PLAYER_ONE) {
											JOptionPane.showMessageDialog(panel, "Time to start the game! Player one make your move!");
										}else {
											JOptionPane.showMessageDialog(panel, "Time to start the game! Player two make your move!");
											
										}
									}
								}else {
									clicks ++;
								}
							}
							
							
						}else {
							JOptionPane.showMessageDialog(panel, "Place a valid move.");
						}
						
					}else {//setup status = false
						if(clicks == 0){//if first click is made it is selecting the piece to move
							System.out.println("first click");
							originalX = x;
							originalY = y;
							System.out.println(newY + "y");
							System.out.println(newX + "x");
							System.out.println(originalY + "oy");
							System.out.println(originalX + "ox");
							checkValid(originalSpot);
							//check if pushing or pulling will occur
							//check if trapping will happen for any pieces
							//change the background of that button and remove the background of the original spot
							clicks++;
						}else if(clicks == 1){//if second click is made it is selecting the position to move the original piece to
							System.out.println("second click");
							newSpot = board[y][x];
							newY = y;
							newX = x;
							System.out.println(newY + "y");
							System.out.println(newX + "x");
							System.out.println(originalY + "oy");
							System.out.println(originalX + "ox");
							makingMove = true;
							if(checkValid(newSpot)) {//if it is a valid move
								System.out.println("was valid");
								if(originalSpot.getText().equals("1e") ||
									originalSpot.getText().equals("2e")) {
									newSpot.setIcon(elephantImg);
									newSpot.setText(playerTurn + "e");
								}else if(originalSpot.getText().equals("1c") ||
									originalSpot.getText().equals("2c")) {
									newSpot.setIcon(camelImg);
									newSpot.setText(playerTurn + "c");
								}else if(originalSpot.getText().equals("1h") ||
									originalSpot.getText().equals("2h")) {
									newSpot.setIcon(horseImg);
									newSpot.setText(playerTurn + "h");
								}else if(originalSpot.getText().equals("1d") ||
									originalSpot.getText().equals("2d")) {
									newSpot.setIcon(dogImg);
									newSpot.setText(playerTurn + "d");
								}else if(originalSpot.getText().equals("1ca") ||
									originalSpot.getText().equals("2ca")) {
									newSpot.setIcon(catImg);
									newSpot.setText(playerTurn + "ca");
								}else if(originalSpot.getText().equals("1r") ||
									originalSpot.getText().equals("2r")) {
									newSpot.setIcon(rabbitImg);
									newSpot.setText(playerTurn + "r");
								}
								originalSpot.setIcon(null);
								originalSpot.setText("");
								clicks = 0;
								makingMove = false;
							}
						}
					}
				}
			}
		}
		
	}
	/*
	
	public int getOriginalX(JButton[][] coordinates) {
		for(int x = 0; x < coordinates.length; x++) {
			for(int y = 0; y < coordinates.length; y++) {
				if()
			}
		}
	}
	*/
	
	
	
	public boolean checkValid(JButton placement) {
		if (setUpStatus) {//to check if a move is a valid set up move
			if(turn == PLAYER_ONE) { //valid player one set up
				if(originalY == 6 ||
				   originalY == 7) {
					if(placement.getIcon() == null) {
						return true;
					}
				}
			}else {//valid player two set up
				if(originalY == 0 ||
					originalY == 1) {
					if(placement.getIcon() == null) {
						return true;
					}
				}
			}
		}else {//to check if a move during the game is valid
			if(turn == PLAYER_ONE) {
				if(makingMove == false) {
					if(placement.getText().equals("1e") ||
					placement.getText().equals("1c") ||
					placement.getText().equals("1h") ||
					placement.getText().equals("1d") ||
					placement.getText().equals("1ca")||
					placement.getText().equals("1r")) {//if the button pressed has a friendly piece on it
						return true;
					}
				}else {//you are making a move
					if(placement.getIcon() == null) {
						if(placement.getText().equals("1e") ||
							placement.getText().equals("1c") ||
							placement.getText().equals("1h") ||
							placement.getText().equals("1d") ||
							placement.getText().equals("1ca")) {//for all pieces that can move backwards
							System.out.println("addy " + (originalY + 1));
							System.out.println("minusy " + (originalY - 1));
							if(originalY + 1 == newY ||
								originalY - 1 == newY ||
								originalX + 1 == newX ||
								originalX - 1 == newX) {//if the move was to the left right down or up one
								System.out.println("elephant move valid");
									return true;
							}else {
								JOptionPane.showMessageDialog(panel, "You can only move one jump left, right, up, or down.");
							}
							
						}else {//rabbit can't move back
							if(originalY + 1 == newY ||
								originalX + 1 == newX ||
								originalX - 1 == newX) {//if the move was to the left right or up one
								System.out.println("rabbit move valid");
									return true;
							}else {
								//rubnning here
								JOptionPane.showMessageDialog(panel, "You can only move one jump left, right, or up.");
							}
						}
					}else {
						JOptionPane.showMessageDialog(panel, "You can't push that piece.");
					}
				}
			}else if(turn == PLAYER_TWO) {
				if(makingMove == false) {
					if(placement.getText().equals("2e") ||
					placement.getText().equals("2c") ||
					placement.getText().equals("2h") ||
					placement.getText().equals("2d") ||
					placement.getText().equals("2ca")||
					placement.getText().equals("2r")) {//if the button pressed has a friendly piece on it
						return true;
					}
				}else {//you are making a move
					if(placement.getIcon() == null) {
						if(placement.getText().equals("2e") ||
							placement.getText().equals("2c") ||
							placement.getText().equals("2h") ||
							placement.getText().equals("2d") ||
							placement.getText().equals("2ca")) {//for all pieces that can move backwards
							System.out.println("addy " + (originalY + 1));
							System.out.println("minusy " + (originalY - 1));
							if(originalY + 1 == newY ||
								originalY - 1 == newY ||
								originalX + 1 == newX ||
								originalX - 1 == newX) {//if the move was to the left right down or up one
									return true;
							}else {
								JOptionPane.showMessageDialog(panel, "You can only move one jump left, right, up, or down.");
							}
							
						}else {//rabbit can't move back
							if(originalY + 1 == newY ||
								originalX + 1 == newX ||
								originalX - 1 == newX) {//if the move was to the left right or up one
									return true;
							}else {
								JOptionPane.showMessageDialog(panel, "You can only move one jump left, right, or up.");
							}
						}
					}else {
						JOptionPane.showMessageDialog(panel, "You can't push that piece.");
					}
				}
			}
				
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
		JOptionPane.showMessageDialog(panel, "Player, place your elephant.");
	}

}
