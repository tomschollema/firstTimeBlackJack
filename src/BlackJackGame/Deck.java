package BlackJackGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	ArrayList<Card>deck;
	
	public Deck(){
		createDeck();
	}
	// MAKE CARDS BY USING FOR-LOOP AND ADD THEM TO THE DECK. 
	public void createDeck() {
		deck = new ArrayList<>();
		for(int i=2;i<15;i++) {
			deck.add(new Card(i,"Harten"));
			deck.add(new Card(i,"Schoppen"));
			deck.add(new Card(i,"Klaver"));
			deck.add(new Card(i,"Ruiten"));
			//printDeck();
		}
	}
	// PRINT DECK - THIS METHOD MAKES IT EASY TO CHECK MY createDeck() METHOD.
	public void printDeck() {
		for(Card card:deck) {
			card.printCard();
			card.printValue();
		}
	}
	
	// USE TO SHUFFLE THE CARDS IN DECK.
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	// PULL RANDOM CARD FROM DECK.
	public Card getCardFromDeck() {
		Random random = new Random();
		int a = random.nextInt(deck.size()-1);
		return(deck.get(a));
	}
}
