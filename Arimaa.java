package arimaaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ArimaaMain implements ActionListener {

    enum Player {
        GOLD, SILVER, NONE;
    }

    enum Piece {
        RABBIT(1), CAT(2), DOG(3), HORSE(4), CAMEL(5), ELEPHANT(6), NONE(0);

        public final int strength;
        public boolean isFrozen = false;

        Piece(int strength) {
            this.strength = strength;
        }
    }

    enum GameState {
        GOLD_SETUP, SILVER_SETUP, GOLD_TURN, SILVER_TURN, GOLD_WIN, SILVER_WIN, SILVER_PUSHING, GOLD_PUSHING, SILVER_PULLING, GOLD_PULLING;
    }

    class BoardButton extends JButton {

        public Player player = Player.NONE;
        public Piece piece = Piece.NONE;

        public int x;
        public int y;

        public BoardButton(int x, int y) {
            super();

            this.x = x;
            this.y = y;
        }

        public void setPlayer(Player player) {
            this.player = player;

            this.setBackground(player == Player.GOLD ? new Color(255, 215, 0) : new Color(192, 192, 192));
            this.setOpaque(true);
        }

        /**
         * Resets the spot
         */
        public void reset() {
            this.player = Player.NONE;
            this.piece = Piece.NONE;
            this.setBackground(null);
            this.setOpaque(false);
            this.setIcon(null);
        }
        
        
        /**
         * Resets the spot
         */
        public void trapPiece() {
            this.player = Player.NONE;
            this.piece = Piece.NONE;
            this.setBackground(Color.RED);
            this.setOpaque(true);
            this.setIcon(null);
        }
        
        
        
        

        /**
         * Copies the information from another spot
         * @param other
         */
        public void copy(BoardButton other) {
            this.player = other.player;
            this.piece = other.piece;
            this.piece.isFrozen = other.piece.isFrozen;
            this.setBackground(other.getBackground());
            this.setOpaque(true);
            this.setIcon(other.getIcon());
        }
    }

    //Image variables for pieces:
    ImageIcon elephantImg = new ImageIcon("Images/elephant.png");
    ImageIcon camelImg = new ImageIcon("Images/camel.png");
    ImageIcon horseImg = new ImageIcon("Images/horse.png");
    ImageIcon dogImg = new ImageIcon("Images/dog.png");
    ImageIcon catImg = new ImageIcon("Images/cat.png");
    ImageIcon rabbitImg = new ImageIcon("Images/rabbit.png");

    Container north = new Container();
    JLabel gameStateLabel = new JLabel();
    JLabel movesRemainingLabel = new JLabel();
    JLabel empty1 = new JLabel();
    JLabel empty2 = new JLabel();
    JLabel empty3 = new JLabel();
    JLabel empty4 = new JLabel();

    JFrame panel = new JFrame("Arimaa");
    Container center = new Container();
    BoardButton[][] board = new BoardButton[8][8];

    GameState state;

    int clicks = 0;
    int moveCount = 0;
    BoardButton from;
    BoardButton to;
    BoardButton trap;
    BoardButton pushed;
    BoardButton pulled;

    public ArimaaMain() {
        this.createInterface();

        this.setGameState(GameState.GOLD_SETUP);
    }

    public static void main(String[] args) {
        new ArimaaMain();
    }

    /**
     * Constructs GUI
     */
    private void createInterface() {
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

        gameStateLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        movesRemainingLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        // North layout
        north.setLayout(new GridLayout(3, 2));
        north.add(empty1);
        north.add(empty2);
        north.add(gameStateLabel);
        north.add(movesRemainingLabel);
        north.add(empty3);
        north.add(empty4);


        panel.add(north, BorderLayout.NORTH);

        //center layout
        center.setLayout(new GridLayout(8, 8));
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                board[y][x] = new BoardButton(x, y);

                if (y == 2 || y == 5) {
                    if (x == 2 || x == 5) {
                        board[y][x].setBackground(Color.RED);
                        board[y][x].setOpaque(true);
                    }
                }

                center.add(board[y][x]);
                board[y][x].addActionListener(this);
            }
        }

        panel.add(center, BorderLayout.CENTER);

        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setVisible(true);
    }

    /**
     * Runs through the setup sequence for a player
     * @param player
     * @param selectedSpot
     */
    public void performSetup(Player player, BoardButton selectedSpot) {

        boolean didSelectInValidRows = (player == Player.GOLD && (selectedSpot.y == 6 || selectedSpot.y == 7)) || (player == Player.SILVER && (selectedSpot.y == 0 || selectedSpot.y == 1));
        String playerName = player == Player.GOLD ? "Gold" : "Silver";

        if (didSelectInValidRows && selectedSpot.piece == Piece.NONE) { // Selected spot is in the first rows and contains no piece
            if (clicks == 0) { //change background of button to an elephant
                selectedSpot.setIcon(elephantImg);
                selectedSpot.piece = Piece.ELEPHANT;
                selectedSpot.setPlayer(player);

                //JOptionPane.showMessageDialog(panel, playerName + ", place your camel.");
                clicks++;
            }
            else if (clicks == 1) { // change button to camel
                selectedSpot.setIcon(camelImg);
                selectedSpot.piece = Piece.CAMEL;
                selectedSpot.setPlayer(player);

                //JOptionPane.showMessageDialog(panel, playerName + ", place your horses.");
                clicks++;
            }
            else if (clicks == 2 || clicks == 3) { //change button to horse for two clicks
                selectedSpot.setIcon(horseImg);
                selectedSpot.piece = Piece.HORSE;
                selectedSpot.setPlayer(player);

                //if (clicks == 3) JOptionPane.showMessageDialog(panel, playerName + ", place your dogs.");
                clicks++;
            }
            else if (clicks == 4 || clicks == 5) {//change button to dog for two clicks
                selectedSpot.setIcon(dogImg);
                selectedSpot.piece = Piece.DOG;
                selectedSpot.setPlayer(player);

                //if (clicks == 5) JOptionPane.showMessageDialog(panel, playerName + ", place your cats.");
                clicks++;
            }
            else if (clicks == 6 || clicks == 7) { //change button to cat for two clicks
                selectedSpot.setIcon(catImg);
                selectedSpot.piece = Piece.CAT;
                selectedSpot.setPlayer(player);

                //if (clicks == 7) JOptionPane.showMessageDialog(panel, playerName + ", place your rabbits.");
                clicks++;
            }
            else if (8 <= clicks && clicks <= 15) { //change button to rabbit for seven clicks
                selectedSpot.setIcon(rabbitImg);
                selectedSpot.piece = Piece.RABBIT;
                selectedSpot.setPlayer(player);

                if (clicks == 15) { // if that was the last placement reset clicks for next player or start of game
                    this.setGameState(state == GameState.GOLD_SETUP ? GameState.SILVER_SETUP : GameState.GOLD_TURN);
                    clicks = 0;
                }
                else {
                    clicks++;
                }
            }
            else {
                JOptionPane.showMessageDialog(panel, "Not a valid spot!");
            }
        }
    }

    /**
     * left: x - 1, y
     * right: x + 1, y
     * top: x, y - 1
     * down: x, y + 1
     * @param spot
     */
    public boolean isInFreezablePosition(BoardButton spot) {
        
    	boolean res = false;
    	
    	if(spot.y - 1 >= 0) { //left exists
    		if (board[spot.y - 1][spot.x].player == spot.player) { // if friendly, isn't frozen. Checks to the left
                return false;
            }
            else if (board[spot.y - 1][spot.x].piece != Piece.NONE && board[spot.x - 1][spot.y].piece.strength > spot.piece.strength) {             
            	res = true;
            }
    	}
    	
    	if (spot.y + 1 <= 7) { // right exists
            if (board[spot.y + 1][spot.x].player == spot.player) { // if friendly, isn't frozen. Checks to the right
                return false;
            }
            else if (board[spot.y + 1][spot.x].piece != Piece.NONE && board[spot.x + 1][spot.y].piece.strength > spot.piece.strength) {
                
            	res = true;
            }
        }
    	
    	 if (spot.x - 1 >= 0) { // top exists
             if (board[spot.y][spot.x - 1].player == spot.player) { // if friendly, isn't frozen. Checks north
                 return false;
             }
             else if (board[spot.y][spot.x - 1].piece != Piece.NONE && board[spot.x][spot.y - 1].piece.strength > spot.piece.strength) {
            	 res = true;
             }
         }
    	
    	 if (spot.x + 1 <= 7) { // bottom exists
             if (board[spot.y][spot.x + 1].player == spot.player) { // if friendly, isn't frozen. Checks south
                 return false;
             }
             else if (board[spot.y][spot.x + 1].piece != Piece.NONE && board[spot.x][spot.y + 1].piece.strength > spot.piece.strength) {   
            	 res = true;
             }
         }
    	 return res;
    }
    	

    
    /**
     * will a piece on the board to frozen which will 
     * prevent it from being able to move.
     * @param spot
     */
    private void handleFreezing(BoardButton spot) {
        if (isInFreezablePosition(spot)) {
            spot.piece.isFrozen = true;
        }
        if(!isInFreezablePosition(spot)) {
        	spot.piece.isFrozen = false;
        }
    }
    
    /**
     * Recolors the trap if there are no pieces on it
     */
    public void recolorTrap() {
    	if(board[2][2].piece == Piece.NONE) {
    		board[2][2].setBackground(Color.RED);
    		board[2][2].setOpaque(true);
    	}
    	if(board[2][5].piece == Piece.NONE) {
    		board[2][5].setBackground(Color.RED);
    		board[2][5].setOpaque(true);
    	}
    	if(board[5][2].piece == Piece.NONE) {
    		board[5][2].setBackground(Color.RED);
    		board[5][2].setOpaque(true);
    	}
    	if(board[5][5].piece == Piece.NONE) {
    		board[5][5].setBackground(Color.RED);
    		board[5][5].setOpaque(true);
    	}
    }

    /**
     * First checks if the move being made is a Rabbit trying to move backwards, and if not, checks if the move is orthogonal. If both are true, the move is valid.
     * @param player
     * @param from
     * @param to
     * @return
     */
    private boolean isValidMove(Player player, BoardButton from, BoardButton to) {

        // Checks piece is rabbit moving backwards
        if ((player == Player.GOLD && from.piece == Piece.RABBIT && from.y - to.y == -1) || (player == Player.SILVER && from.piece == Piece.RABBIT && from.y - to.y == 1)) {
            return false;
        }

        if (from.piece.isFrozen) {
            return false;
        }

        // Checks if move is orthogonal
        if ((Math.abs(to.x - from.x) + Math.abs(to.y - from.y)) == 1) {
            // is orthogonal

            // Verifies that board spot isn't taken
           // if (to.piece == Piece.NONE) {
                // spot isn't taken

                return true;
            //}
        }

        return false;
    }

    /**
     * For a valid move, the old spot is reset and the new spot takes the information of the old spot
     * The move count is incremented here
     */
    private void executeValidMove(Player player) {
        to.copy(from);
        if(canBePulled(from, player)) {
        	if(player == Player.GOLD) {
    			state = GameState.GOLD_PULLING;
    		}
    		else {
    			state = GameState.SILVER_PULLING;
    		}
        }
        from.reset();
        from = null;
       

        this.incrementMoveCount();
    }

    /**
     * Determines if an opponents piece can be pulled and highlights possible
     * pieces to be pulled.
     * @param spot
     * @return
     */
    public boolean canBePulled(BoardButton spot, Player player) {
		if(spot.y - 1 >= 0) {// top exists
			if(board[spot.y - 1][spot.x].piece != Piece.NONE && board[spot.y - 1][spot.x].player != player && board[spot.y - 1][spot.x].piece.strength < spot.piece.strength) {// opponent on adjacent square	
				board[spot.y - 1][spot.x].setBackground(Color.BLUE);
				return true;
			}
		}
		if (spot.y + 1 <= 7) { // below exists
            if (board[spot.y + 1][spot.x].piece != Piece.NONE && board[spot.y + 1][spot.x].player != player && board[spot.y + 1][spot.x].piece.strength < spot.piece.strength) { // opponent on adjacent square
            	board[spot.y + 1][spot.x].setBackground(Color.BLUE);
            	return true;
            }
            
        }
   	 	if (spot.x - 1 >= 0) { // left exists
            if (board[spot.x][spot.x - 1].piece != Piece.NONE && board[spot.y][spot.x - 1].player != player && board[spot.y][spot.x - 1].piece.strength < spot.piece.strength) { // opponent on adjacent square
            	board[spot.y][spot.x - 1].setBackground(Color.BLUE);
            	return true;
            }
        }
   	 	if (spot.x + 1 <= 7) { // right exists
            if (board[spot.y][spot.x + 1].piece != Piece.NONE && board[spot.y][spot.x + 1].player != player && board[spot.y][spot.x + 1].piece.strength < spot.piece.strength) { // opponent on adjacent square
            	board[spot.y][spot.x + 1].setBackground(Color.BLUE);
            	return true;
            }
   	 	}
    	return false;
    }
    
    
    /**
     * If a players moves run out or they decide to skip moves, the move count is reset and the game state is set to the other players turn
     */
    private void handleTurnSwap() {
    	if(state == GameState.SILVER_TURN || state == GameState.GOLD_TURN) {
	        if (moveCount == 4 || JOptionPane.showConfirmDialog(panel, "Move again?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
	            this.setGameState(state == GameState.GOLD_TURN ? GameState.SILVER_TURN : GameState.GOLD_TURN);
	            this.resetMoveCount();
	        }
    	}
    }

    /**
     * Performs a move for a player and to the selected spot
     * @param player
     * @param selectedSpot
     */
    private void performMove(Player player, BoardButton selectedSpot) {
        if (selectedSpot.piece != Piece.NONE && from == null) { // directs the player into selecting the from spot
            if (selectedSpot.player == player) {
                from = selectedSpot;
                pulled = from;
                
                handleFreezing(from);
             
            }
        }
        else if (from != null) { // means the player is selecting the to spot
            if (selectedSpot.piece == Piece.NONE && isValidMove(player, from, selectedSpot)) {
                to = selectedSpot;

                handleWins();
                handleFreezing(to);
                this.executeValidMove(player);
                this.handleTurnSwap();
                trap();
                recolorTrap();
            }
            else if(selectedSpot.player != player && selectedSpot.piece != Piece.NONE && isValidMove(player, from, selectedSpot)) {
            	
            	to = selectedSpot;
            	
            	if(canBePushed(to)) {
            		pushed = to;
            		highlightPushSpots(pushed);
            		if(player == Player.GOLD) {
            			state = GameState.GOLD_PUSHING;
            		}
            		else {
            			state = GameState.SILVER_PUSHING;
            		}
            		
            		
            	}
            	else {
            		JOptionPane.showMessageDialog(panel, "Can't push this piece.");
            	}
            	
            	handleWins();
                handleFreezing(to);
                this.handleTurnSwap();
                trap();
                recolorTrap();
               
            }
            else {
            	JOptionPane.showMessageDialog(panel, "Not a valid move.");
            }
        }
    }
    
    /**
     * Copies the piece getting pushed onto the button clicked
     */
    public void performPush(Player player, BoardButton spot) {
    	if(checkHighlight(spot)) {
    		spot.copy(pushed);
    		this.executeValidMove(player);
    		removeBlueHighlights();
    		if(player == Player.GOLD) {
    			state = GameState.GOLD_TURN;
    		}else if(player == Player.SILVER) {
    			state = GameState.SILVER_TURN;
    		}
    		
    	}
    	
    }
    
    
    /**
     * copies opponent's piece clicked on to pull to the spot
     * the player has just moved from.
     * @param player
     * @param spot
     */
    public void performPull(Player player, BoardButton spot) {
    	if(checkHighlight(spot)) {
    		pulled.copy(spot);	
    		removeBlueHighlights();
    		if(player == Player.GOLD) {
    			pulled.setBackground(new Color(192, 192, 192));
    			pulled.setOpaque(true);
    			state = GameState.GOLD_TURN;
    		}else if(player == Player.SILVER) {
    			pulled.setBackground(new Color(255, 215, 0));
    			pulled.setOpaque(true);
    			state = GameState.SILVER_TURN;
    		}
    		spot.reset();
    	}
    }
    
    
    /**
	 * Removes the blue highlighted boxes
	 */
    public void removeBlueHighlights() {
    	for(int x = 0; x < board.length; x++) {
    		for(int y = 0; y < board.length; y++) {
    			if(board[y][x].getBackground().equals(Color.BLUE)) {
    				board[y][x].setBackground(null);
    				board[y][x].setOpaque(false);
    			}
    		}
    	}
    }
    
    /*
     * checks if a button clicked is highlighted blue
     */
    public boolean checkHighlight(BoardButton spot) {
    	if(spot.getBackground().equals(Color.BLUE)) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Highlights empty squares adjacent to the piece getting pushed
     */
    public void highlightPushSpots(BoardButton spot) {
    	if(spot.y - 1 >= 0) {// top exists
			if(board[spot.y - 1][spot.x].piece == Piece.NONE) {// empty adjacent square
				board[spot.y - 1][spot.x].setBackground(Color.BLUE);
				board[spot.y - 1][spot.x].setOpaque(true);
			}
		}
		if (spot.y + 1 <= 7) { // below exists
            if (board[spot.y + 1][spot.x].piece == Piece.NONE) { // empty adjacent square
            	board[spot.y + 1][spot.x].setBackground(Color.BLUE);
				board[spot.y + 1][spot.x].setOpaque(true);
            }
            
        }
   	 	if (spot.x - 1 >= 0) { // left exists
            if (board[spot.y][spot.x - 1].piece == Piece.NONE) { // empty adjacent square
            	board[spot.y][spot.x - 1].setBackground(Color.BLUE);
				board[spot.y][spot.x - 1].setOpaque(true);
            }
        }
   	 	if (spot.x + 1 <= 7) { // right exists
            if (board[spot.y][spot.x + 1].piece == Piece.NONE) { // empty adjacent square
            	board[spot.y][spot.x + 1].setBackground(Color.BLUE);
				board[spot.y][spot.x + 1].setOpaque(true);
            }
   	 	}
    }
    
    /**
     * Determines if a player can push a piece clicked on 
     */
    public boolean canBePushed(BoardButton spot) {
    	if(from.piece.strength > spot.piece.strength) {//the pushing piece is stronger
    		if(spot.y - 1 >= 0) {// top exists
    			if(board[spot.y - 1][spot.x].piece == Piece.NONE) {// empty adjacent square	
    				return true;
    			}
    		}
    		if (spot.y + 1 <= 7) { // below exists
                if (board[spot.y + 1][spot.x].piece == Piece.NONE) { // empty adjacent square
                    return true;
                }
                
            }
       	 	if (spot.x - 1 >= 0) { // left exists
                if (board[spot.y][spot.x - 1].piece == Piece.NONE) { // empty adjacent square
                    return true;
                }
            }
       	 	if (spot.x + 1 <= 7) { // right exists
                if (board[spot.y][spot.x + 1].piece == Piece.NONE) { // empty adjacent square
                    return true;
                }
       	 	}
    		
    	}
    
    	 return false;
    }
    
    /**
     * Determines if there is a winning setup.
     * Two ways to win:
     * If there is an opposing rabbit on the other's side.
     * If one team's rabbits are all trapped.
     */
    public void handleWins() {
    	int goldRabbits = 0;
    	int silverRabbits = 0;
    	
    	//to determine if there are no more rabbits:
    	for(int x = 0; x < board.length; x++) {
    		for(int y = 0; y < board.length; y++) {
    			if(board[y][x].piece == Piece.RABBIT && board[y][x].player == Player.GOLD) { //golds rabbit detected
    				goldRabbits++;
    			}
    			if(board[y][x].piece == Piece.RABBIT && board[y][x].player == Player.SILVER) { // silver rabbit detected
    				silverRabbits++;
    			}
    		}
    	}
    	if(goldRabbits <= 0) {//if there are no gold rabbits on the board, silver wins
    		setGameState(GameState.SILVER_WIN);
			disableButtons();
    	}else if(silverRabbits <= 0) {//if there are no silver rabbits on the board, gold wins
    		setGameState(GameState.GOLD_WIN);
    		disableButtons();
    	}
    	
    	//to determine if a rabbit made it to the other side of the board:
    	for(int x = 0; x < board.length; x++) {
    		if(board[0][x].piece == Piece.RABBIT && board[0][x].player == Player.GOLD) { //if a gold rabbit made it across, they win
    			System.out.println("gold  is across");
    			setGameState(GameState.GOLD_WIN);
    			disableButtons();
    		}
    		if(board[7][x].piece == Piece.RABBIT && board[7][x].player == Player.SILVER) {//if a silver rabbit made it across, they win
    			System.out.println("silver  is across");
    			setGameState(GameState.SILVER_WIN);
    			disableButtons();
    		}
    	}
    }
    
    
    /**
     * Disables all the buttons after a game is won
     */
    private void disableButtons() {
    	for(int x = 0; x < board.length; x++) {
    		for(int y = 0; y < board.length; y++) {
    			board[y][x].setEnabled(false);
    		}
    	}
    }

    
    /**
     * Checks to see if the spot is a trap
     * @param spot
     * @return
     */
    private void trap() {
        if(board[2][2].piece != Piece.NONE) {//piece on the top left trap
        	trap = board[2][2];
        	  if(!checkForFriends(trap)) { //no friends adjacent to piece on trap
          		trap.trapPiece();
          	}
        }
        if(board[2][5].piece != Piece.NONE) {//piece on the top right trap
        	trap = board[2][5];
        	  if(!checkForFriends(trap)) { //no friends adjacent to piece on trap
          		trap.trapPiece();
          	}
        }
        if(board[5][2].piece != Piece.NONE) {//piece on the bottom left trap
        	trap = board[5][2];
        	  if(!checkForFriends(trap)) { //no friends adjacent to piece on trap
          		trap.trapPiece();
          	}
        }
        if(board[5][5].piece != Piece.NONE) {//piece on the bottom right trap
        	trap = board[5][5];
        	  if(!checkForFriends(trap)) { //no friends adjacent to piece on trap
          		trap.trapPiece();
          	}
        }  
    }

    
    /*
     * To check if the piece selected has a friendly piece adjacent
     * Check each adjacent spot and if there is a piece with the same background return true.
     */
    public boolean checkForFriends(BoardButton spot) {
    	if(spot.x + 1 <= 7) { //if the spot is not outside of the board
    		if(spot.player == board[spot.y][spot.x+1].player) {//if they are on the same team
    			return true;
    		}
    	}
    	if(spot.x - 1 >= 0) {//if the spot is not outside the board
    		if(spot.player == board[spot.y][spot.x-1].player) {
    			return true;
    		}
    	}
    	if(spot.y + 1 <= 7) {//if spot is not outside board
    		if(spot.player == board[spot.y + 1][spot.x].player) {
    			return true;
    		}
    	}
    	if(spot.y - 1 >= 0) {//if spot is not outside board
    		if(spot.player == board[spot.y - 1][spot.x].player) {
    			return true;
    		}
    	}

    	return false;
    }
    
    
    /**
     * Increments move count by one and updates moves remaining label
     */
    private void incrementMoveCount() {
        moveCount++;
        this.movesRemainingLabel.setText("Moves remaining: " + (4 - moveCount));
    }

   
    /**
     * Resets the move counter and updates moves remaining label
     */
    private void resetMoveCount() {
        moveCount = 0;
        this.movesRemainingLabel.setText("Moves remaining: 4");
    }


    /**
     * Sets the game state to the new state and updates the game state label with a message depending on the state
     * @param newState
     */
    private void setGameState(GameState newState) {
        this.state = newState;

        switch (state) {
            case GOLD_SETUP -> this.gameStateLabel.setText("Gold is setting up the board!");
            case SILVER_SETUP -> this.gameStateLabel.setText("Silver is setting up the board!");
            case GOLD_TURN -> this.gameStateLabel.setText("It's Gold's turn!");
            case SILVER_TURN -> this.gameStateLabel.setText("It's Silver's turn!");
            case GOLD_WIN ->  this.gameStateLabel.setText("Gold has won!!!");
            case SILVER_WIN ->  this.gameStateLabel.setText("Silver has won!!!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof BoardButton) {
            BoardButton selectedSpot = (BoardButton) e.getSource();

            if (state == GameState.GOLD_SETUP) {
                performSetup(Player.GOLD, selectedSpot);
            }
            else if (state == GameState.SILVER_SETUP) {
                performSetup(Player.SILVER, selectedSpot);
            }
            else if (state == GameState.GOLD_TURN) {
                performMove(Player.GOLD, selectedSpot);
            }
            else if (state == GameState.SILVER_TURN) {
                performMove(Player.SILVER, selectedSpot);
            }
            else if (state == GameState.GOLD_PUSHING) {
            	performPush(Player.GOLD, selectedSpot);
            }
            else if (state == GameState.SILVER_PUSHING) {
            	performPush(Player.SILVER, selectedSpot);
            }
            else if (state == GameState.GOLD_PULLING) {
            	performPull(Player.GOLD, selectedSpot);
            }
            else if (state == GameState.SILVER_PULLING) {
            	performPull(Player.SILVER, selectedSpot);
            }

        }
    }
}
