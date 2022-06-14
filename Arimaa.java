package arimaaProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ArimaaMain implements ActionListener {

    enum Player {
        GOLD, SILVER, NONE;
    }

    enum Piece {
        RABBIT(1), CAT(2), DOG(3), HORSE(4), CAMEL(5), ELEPHANT(6), NONE(0);

        public final int strength;
        public final boolean isFrozen = false;

        Piece(int strength) {
            this.strength = strength;
        }
    }

    enum GameState {
        GOLD_SETUP, SILVER_SETUP, GOLD_TURN, SILVER_TURN, GOLD_WIN, SILVER_WIN;
    }

    class BoardButton extends JButton {

        public Player player = Player.NONE;
        public Piece piece = Piece.NONE;

        public int x;
        public int y;
        
        //x setter 
        public void setX(int newx) {
        	x = newx;
        }
        //y setter
        public void setY(int newy) {
        	y = newy;
        }

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
         * Copies the information from another spot
         * @param other
         */
        public void copy(BoardButton other) {
            this.player = other.player;
            this.piece = other.piece;
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
    BoardButton adjacents;

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

                JOptionPane.showMessageDialog(panel, playerName + ", place your camel.");
                clicks++;
            }
            else if (clicks == 1) { // change button to camel
                selectedSpot.setIcon(camelImg);
                selectedSpot.piece = Piece.CAMEL;
                selectedSpot.setPlayer(player);

                JOptionPane.showMessageDialog(panel, playerName + ", place your horses.");
                clicks++;
            }
            else if (clicks == 2 || clicks == 3) { //change button to horse for two clicks
                selectedSpot.setIcon(horseImg);
                selectedSpot.piece = Piece.HORSE;
                selectedSpot.setPlayer(player);

                if (clicks == 3) JOptionPane.showMessageDialog(panel, playerName + ", place your dogs.");
                clicks++;
            }
            else if (clicks == 4 || clicks == 5) {//change button to dog for two clicks
                selectedSpot.setIcon(dogImg);
                selectedSpot.piece = Piece.DOG;
                selectedSpot.setPlayer(player);

                if (clicks == 5) JOptionPane.showMessageDialog(panel, playerName + ", place your cats.");
                clicks++;
            }
            else if (clicks == 6 || clicks == 7) { //change button to cat for two clicks
                selectedSpot.setIcon(catImg);
                selectedSpot.piece = Piece.CAT;
                selectedSpot.setPlayer(player);

                if (clicks == 7) JOptionPane.showMessageDialog(panel, playerName + ", place your rabbits.");
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
     * First checks if the move being made is a Rabbit trying to move backwards, and if not, checks if the move is orthogonal. If both are true, the move is valid.
     * @param player
     * @param from
     * @param to
     * @return
     */
    private boolean isValidMove(Player player, BoardButton from, BoardButton to) {
    	
    	setAdjacent(0, 1); //checks the piece above or below
    	checkFreeze();
    	
    	setAdjacent(0, -1);//checks the piece above or below
    	checkFreeze();
    	
    	setAdjacent(1, 0); //checks the piece to the right
    	checkFreeze();
    	
    	setAdjacent(-1, 0); //checks the piece to the left
    	checkFreeze();
    	
        // Checks piece is rabbit moving backwards
        if ((player == Player.GOLD && from.piece == Piece.RABBIT && from.y - to.y == -1) || (player == Player.SILVER && from.piece == Piece.RABBIT && from.y - to.y == 1)) {
            return false;
        }

        // Checks if move is orthogonal
        if ((Math.abs(to.x - from.x) + Math.abs(to.y - from.y)) == 1) {
            // is orthogonal

            // Verifies that board spot isn't taken
            if (to.piece == Piece.NONE) {
                // spot isn't taken
                return true;
            }
        }

        return false;
    }
    
    public void setAdjacent(int xMove, int yMove) {
    	adjacents.setX(from.x + xMove);
    	adjacents.setY(from.y + yMove);
    }
    
    /**
     * To check if the piece selected (from piece) is frozen.
     * Is frozen if there is a stronger enemy piece adjacent.
     * And if there are no friend pieces adjacent.
     * Returns true is that piece is frozen.
     */
    public boolean checkFreeze(){
    	
    	
    	if(!from.getBackground().equals(adjacents.getBackground())) { //if they are not the same team
    		if(from.piece.strength < adjacents.piece.strength) { //if the adjacent piece is stronger
    			if(checkForFriends() == false) {//if there are no friends adjacent
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    /*
     * To check if the piece selected (from piece) has a friendly piece adjacent
     * Check each adjacent spot and if there is a piece with the same background return true.
     */
    public boolean checkForFriends() {
    	adjacents.setX(from.x);
    	adjacents.setY(from.y + 1); //check the piece above or below
    	
    	if(from.getBackground().equals(adjacents.getBackground())) {//if the adjacent piece is on your team
    		return true;
    	}
    	
    	adjacents.setX(from.x);
    	adjacents.setY(from.y - 1);//check the piece above or below
    	
    	
    	
    	return false;
    }

    /**
     * For a valid move, the old spot is reset and the new spot takes the information of the old spot
     * The move count is incremented here
     */
    private void executeValidMove() {
        to.copy(from);
        from.reset();
        from = null;

        this.incrementMoveCount();
    }

    /**
     * If a players moves run out or they decide to skip moves, the move count is reset and the game state is set to the other players turn
     */
    private void handleTurnSwap() {
        if (moveCount == 4 || JOptionPane.showConfirmDialog(panel, "Move again?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            this.setGameState(state == GameState.GOLD_TURN ? GameState.SILVER_TURN : GameState.GOLD_TURN);
            this.resetMoveCount();
        }
    }

    /**
     * Performs a move for a player and to the selected spot
     * @param player
     * @param selectedSpot
     */
    private void performMove(Player player, BoardButton selectedSpot) {

        if (from == null && selectedSpot.piece != Piece.NONE) { // directs the player into selecting the from spot
            if (selectedSpot.player == player) {
                from = selectedSpot;
            }
        }
        else if (from != null) { // means the player is selecting the to spot

            if (selectedSpot.piece == Piece.NONE && isValidMove(player, from, selectedSpot)) {
                to = selectedSpot;

                this.executeValidMove();
                this.handleTurnSwap();
            }
        }
    }

    /**
     * Checks to see if the spot is a trap
     * @param spot
     * @return
     */
    private boolean isTrap(BoardButton spot) {
        return (spot.x == 2 || spot.x == 5) && (spot.x == 2 || spot.y == 5);
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
        }
    }
}

