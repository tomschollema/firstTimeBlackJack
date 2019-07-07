package BlackJackGame;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	
	// FIELDS AKA INSTANCE VARIABLES.
	int playerNumber;
	String name;
	ArrayList<Card>hand;
	int handValue;
	int finalHandValue;
	Scanner scanner = new Scanner(System.in);
	int bet;
	int wallet;
	
	// CONSTRUCTOR. CREATE ARRAYLIST HAND - FOR EACH CREATED PLAYER.
	public Player() {
		hand = new ArrayList<>();
		wallet = 6;
	}
	
	// GET PLAYER NAME.
	public String getName() {
		return name;
	}
	
	// PRINT THE HAND OF A PLAYER BY LOOPING TROUGHT THE HAND AND printCard() METHOD ON EACH CARD IN THE HAND.
	public void printHand() {
		for(Card card:hand) {
			card.printCard();
		}
	}
	// PRINT DEALER HAND. SHOW ONLY 1 CARD!! DEALER PRIVILEGE lol.
	public void printDealerHand() {
		Card card=hand.get(1);
		card.printCard();
	}
	
	// SET HANDVALUE; FIRST TO 0 AND THEN LOOP TROUGH HAND AND + THE VALUE'S
	public void setHandValue() {
		handValue = 0;
		for(Card card:hand) {
			this.handValue += card.getValue();
		}
	}
	
	// RETURN HANDVALUE. PROBABLY THE DUMBEST METHOD EVER.
	public int getHandValue() {
		return handValue;
	}
	
	//LOOP TROUGH HAND TO SEE IF THERE IS AN A WITH THE VALUE STILL ON 11!
	public boolean gotA() {
		for(Card card:hand) {
			if(card.number==14&&card.value==11) {
				return true;
			}
		}
		return false;
	}

	public void setFinalHandValue() {
		// SET FINALHANDVALUE BY LOOPING TROUGH THE HAND AND + THE VALUE'S. CAN HANDLE A' CONVERTS 1-11.
		// SETS HANDVALUE TO 0 MAKE SURE IT IS NOT AVAILABLE FOR METHOD: HANDLESS 21 ANYMORE. 
		for(Card card:hand) {
			this.finalHandValue += card.getValue();
			handValue = 0;
		}
	}
}

