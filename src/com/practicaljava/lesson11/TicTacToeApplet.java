package com.practicaljava.lesson11;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.util.Random;

@SuppressWarnings("serial")
public class TicTacToeApplet extends JApplet implements MouseListener
{
    private static final String PLAYERX = "Player X";
    private static final String PLAYERO = "Player O";
    
    // Count of buttons
    private static final int BUTTONS_COUNT = 9;   
    
    private static final int HUMAN_MOVE    =  1;  
    private static final int COMPUTER_MOVE = -1;  
    private static final int EMPTY_MOVE    =  0;
    
    private final Random randomGenerator = new Random() ;   
	
    private String playerName = PLAYERX;
    
    // Create new array of buttons
    private JButton buttons[] = new JButton[BUTTONS_COUNT];
    private int countOfEmptyMoves = BUTTONS_COUNT ; 
    private int moves[] = new int[BUTTONS_COUNT]; // Array of moves
    
	private boolean isSomebodyWinner = false ;
    
    private javax.swing.JLabel playerNumber;
    
    // superPanel -> metaPanel -> buttonsPanel
    private java.awt.Panel superPanel; 
    private java.awt.Panel metaPanel;    // A child of superPanel
    private java.awt.Panel buttonsPanel; // A child of metaPanel  

    public void init(){
        initComponents();
    }

    private void initComponents(){

    	JButton buttonPlayAgain = new JButton("Play Again") ;
    	buttonPlayAgain.setSize(100, 20);
    	
    	superPanel   = new java.awt.Panel();
    	metaPanel    = new java.awt.Panel();
    	buttonsPanel = new java.awt.Panel();
    	
        superPanel.setLayout(new java.awt.GridLayout(2, 1));
        metaPanel.setLayout(new java.awt.GridLayout(2, 1));
        buttonsPanel.setLayout(new java.awt.GridLayout(3, 3));
        
        superPanel.add(buttonsPanel);
        superPanel.add(metaPanel);

        playerNumber = new javax.swing.JLabel(playerName, SwingConstants.CENTER);
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 30);
        
        int _count = 0 ;
        for (_count=0; _count<BUTTONS_COUNT;_count++) {
        	buttons[_count] = new JButton();
        	buttons[_count].addMouseListener(this);
        	buttons[_count].setFont(buttonFont);
        	buttons[_count].setBackground(Color.WHITE);
        	buttonsPanel.add(buttons[_count]);
        }        
        
        setPlayerName(PLAYERX); 
        metaPanel.add(playerNumber);
        buttonPlayAgain.addMouseListener(this);
        metaPanel.add(buttonPlayAgain);
        
        add(superPanel);
    }
	
    private void setPlayerName(String playerName){
        this.playerName = playerName;
        playerNumber.setText(playerName  + " your turn. ");
    }
	
    private void reset(){ 
        
    	isSomebodyWinner = false ;
    	
    	// Reset buttons properties
    	for (JButton _item: buttons) {
        	_item.setText("");
        	_item.setBackground(Color.WHITE) ;
        }
        
        // Clear the history of the moves
    	countOfEmptyMoves = BUTTONS_COUNT ; 
        int _count ;
        for (_count=0;_count<BUTTONS_COUNT;_count++) {
        	moves[_count]=EMPTY_MOVE; 
        }
        
        setPlayerName(PLAYERX);
        
    }
	
    public boolean checkForWinner(){

	   int [] winIndexes = findThreeInARow() ; 
        
       if(winIndexes!=null){

    	   String winnerName=(playerName == PLAYERX)?PLAYERO:PLAYERX;
            
           // Paint buttons
           for (int _item: winIndexes) {
            	buttons[_item].setBackground(Color.YELLOW);
           }
           playerNumber.setText(winnerName.concat(" won!!! Congratulations!!!"));
           
           return true; // We found a winner!

       }    
       else{
    	   return false; // We didn't found a winner 
       }
        
    }
	
    public void mouseClicked(MouseEvent e) {
        JButton currentButton = (JButton)e.getComponent();
        if (currentButton.getText() == "Play Again") {
        	reset();
        	playerNumber.setText(this.playerName  + " your turn. ");
        }
        else if ( (isSomebodyWinner==false) && (currentButton.getText() == "") ){
	        	
        	int _count;
        	for (_count=0;_count<BUTTONS_COUNT;_count++){
        		if (buttons[_count]==currentButton){
        			break;
        		}
        	}
        	
        	// Handle a human move
            currentButton.setText("X");
            setPlayerName(PLAYERO);
            moves[_count]=HUMAN_MOVE;
            countOfEmptyMoves--;
            isSomebodyWinner = checkForWinner();
            if ( isSomebodyWinner ) {
            	return ; // Game is over - human is a winner! 
            }
            
            // Do we have empty squares for the next computer move?
            if (countOfEmptyMoves>0) {
                // Make a computer move
                int _nextComputerMove = randomGenerator.nextInt(BUTTONS_COUNT-1) ; 
                while (moves[_nextComputerMove]!=EMPTY_MOVE){
                	_nextComputerMove = randomGenerator.nextInt(BUTTONS_COUNT-1) ;
                }
                moves[_nextComputerMove] = COMPUTER_MOVE;
                countOfEmptyMoves--; // Decrease the number of available empty moves
                buttons[_nextComputerMove].setText("O");
                setPlayerName(PLAYERX);
                isSomebodyWinner = checkForWinner() ;
            }
        } 
    }

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	private int[] findThreeInARow(){
		
	    if ( buttons[0].getText() == buttons[1].getText() && buttons[1].getText() == buttons[2].getText() && buttons[0].getText() != "" ) 
	    	return new int[]{0,1,2};
	    
	    else if (buttons[3].getText() == buttons[4].getText() && buttons[4].getText() == buttons[5].getText() && buttons[3].getText() != "") 
	    	return new int[]{3,4,5};
	    
	    else if (buttons[6].getText() == buttons[7].getText() && buttons[7].getText() == buttons[8].getText() && buttons[6].getText() != "") 
	    	return new int[]{6,7,8};
	    
	    else if	(buttons[0].getText() == buttons[3].getText() && buttons[3].getText() == buttons[6].getText() && buttons[0].getText() != "") 
	    	return new int[]{0,3,6};
	    
	    else if (buttons[1].getText() == buttons[4].getText() && buttons[4].getText() == buttons[7].getText() && buttons[1].getText() != "") 
	    	return new int[]{1,4,7};
	    
	    else if (buttons[2].getText() == buttons[5].getText() && buttons[5].getText() == buttons[8].getText() && buttons[2].getText() != "")
	    	return new int[]{2,5,8};
	    
	    else if (buttons[0].getText() == buttons[4].getText() && buttons[4].getText() == buttons[8].getText() && buttons[0].getText() != "") 
	    	return new int[]{0,4,8};

	    else if (buttons[2].getText() == buttons[4].getText() && buttons[4].getText() == buttons[6].getText() && buttons[2].getText() != "")
	    	return new int[]{2,4,6};
	    
		else
	        return null;
	    	
    }
}