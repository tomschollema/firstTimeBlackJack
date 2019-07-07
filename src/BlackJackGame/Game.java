package BlackJackGame;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	int amountPlayers;
	Scanner scanner = new Scanner(System.in);
	ArrayList<Player>players = new ArrayList<>();
	Deck deck;
	Player dealer;
	
	public void welcome() {
		System.out.println("Welkom aan de blackjack-tafel! Met hoeveel spelers gaan we starten? '1','2'of '3'.");
		amountPlayers = scanner.nextInt();
		if(amountPlayers==1||amountPlayers==2||amountPlayers==3) {
			for(int i=0;i<amountPlayers;i++) {
				Player player = new Player();
				player.playerNumber = (i+1);
				player.name="Player "+(i+1);
				players.add(player);
			}
		}else {
			System.out.println("Geen geldige invoer.");
			welcome();
		}
		dealer = new Player();
		dealer.name="De deler ";
	}
	
	public void createDeck() {
		this.deck = new Deck();
	}
	
	public void shuffleDeck() {
		deck.shuffleDeck();
	}
	
	public void startNewRound() {
		createDeck();
		deck.shuffleDeck();
		giveStartHand();
	}
	
	public void clearHand() {
		for(Player player:players) {
			player.hand.clear();
			player.handValue=0;
			player.finalHandValue=0;
			player.bet=0;
		}
		dealer.hand.clear();
	}
	
	public void runGame() {
		startNewRound();
		takeTurn();
		clearHand();
		if(quit()!=true){
		//do nothing. aka - stop.
		}
		else if(amountPlayers==0) {
			System.out.println("Er is niemand meer die wil spelen.");
		}
		else {
			runGame();
		}
	}
	
	
	//***********************************************************
	//FIRST ACTION EACH ROUNDE IS TO INIT THE HANDS + DEALERHAND.
	//***********************************************************
	public void giveStartHand() {
		
		// FOR EACH PLAYER - DRAW 2 CARDS. REMOVE THEM FROM DECK.
		for(Player player:players) {
			System.out.println(player.getName()+ " heeft:");
			Card card = deck.getCardFromDeck();
			Card card2 = deck.getCardFromDeck();
			
			// EVERY ROUND COST 2 COINS. IF BROKE PLAYER IS OUT. NOT FINISHED.
			player.wallet -= 2;
			player.bet +=2;
			
			player.hand.add(card);
			deck.deck.remove(card);
			player.hand.add(card2);
			deck.deck.remove(card2);
			
			// PRINT THE HAND OF EACH PLAYER. AND SET OBJECT VARIABLE -> HANDVALUE;
			player.printHand();	
			player.setHandValue();
			System.out.println(" ");
		}
		
		//****************************************DEALER*********************************************************************
		//*******************************************************************************************************************
		//GIVE DEALER 2 CARDS FROM DECK AND REMOVE BOTH FROM DECK.
		System.out.println(dealer.name+ " heeft:");
		Card card1 = deck.getCardFromDeck();
		Card card2 = deck.getCardFromDeck();
		dealer.hand.add(card1);
		deck.deck.remove(card1);
		dealer.hand.add(card2);
		deck.deck.remove(card2);
		
		//SET THE DEALER HIS HANDVALUE. (INSTANCE VAR. IN OBJECT: PLAYER);
		dealer.setHandValue();
		System.out.println("DEALERHANDVALUE: 2kaarten: "+dealer.handValue);
		
		//TWO POSSIBLE OUTCOMES: DEALER HAS <17 - OR >17. 
		//DEALER MUST DRAW IF IT'S <17!\
		//THE GAME DOES NOT CONSIDER THE POSSIBILITY THAT DEALER CAN GET 2xA (NO TIME LEFT TO ADJUST CODE :( ).
		if(dealer.handValue<17) {
			// <17? DRAW 3RD CARD AND REMOVE FROM DECK. UPDATE HANDHALVUE.
			Card card3 = deck.getCardFromDeck();
			dealer.hand.add(card3);
			deck.deck.remove(card3);
			dealer.setHandValue();
			System.out.println("DEALERHANDVALUE: 3kaarten: "+dealer.handValue);
				
			// IF DEALER HAS >21 BUT GOT AN A. THE VALUE OF THE A WILL BE ADJUST TO 1.
				if(dealer.handValue>21&&dealer.gotA()) {
					for(Card card:dealer.hand) {	
						if(card.A==true && dealer.getHandValue()>21) {
							System.out.println("*LOL DEALER* executed");
							card.setValueA();
							dealer.setHandValue();
							
							// IF DEALER HAS AFTER ADJUST A = <17 THEN DRAW 4TH CARD.
							if(dealer.handValue<17) {
								Card card4 = deck.getCardFromDeck();
								dealer.hand.add(card4);
								deck.deck.remove(card4);
								dealer.setHandValue();
							}
							// IF DEALER HAND IS >21 SET HANDVALUE to 0; ELSE SET FINALHANDVALUE;
							if(dealer.handValue>21) {
								dealer.handValue=0;
								dealer.finalHandValue=dealer.handValue;
							}
							else {
							dealer.finalHandValue=dealer.handValue;
							System.out.println("LOL DEALERFINALHANDVALUE^: "+dealer.finalHandValue);
							}
							
						}
					}
				}
				
				// IF DEALER HAS LESS THEN 17 POINTS WITH 3 CARDS THEN DRAW 4TH CARD.
				else if(dealer.handValue<17) {
						Card card4 = deck.getCardFromDeck();
						dealer.hand.add(card4);
						deck.deck.remove(card4);
						dealer.setHandValue();
						
						// IF DEALER HAS LESS THEN 17 POINTS WITH 4 CARDS THEN DRAW 5TH CARD.
						if(dealer.handValue<17) {
							Card card5 = deck.getCardFromDeck();
							dealer.hand.add(card5);
							deck.deck.remove(card5);
							dealer.setHandValue();
							// IF DEALER HAS > 21 POINTS WITH 5 CARDS THEN SET FINALHANDVALUE to 0;
							if(dealer.finalHandValue>21) {
								dealer.finalHandValue=0;
							// ELSE (< 21 POINTS WITH 5 CARDS) SET FINALHANDVALUE.
							}else {
								dealer.finalHandValue=dealer.handValue;
							}
							
							System.out.println("DEALERHANDVALUE: 5kaarten: "+dealer.handValue);
							System.out.println("DEALERFINALHANDVALUE: 5kaarten: "+dealer.finalHandValue);
						}
						// IF DEALER HAS MORE THEN 17 POINTS BUT LESS OR EQUAL TO 21 POINTS WITH 4 CARDS THEN SET FINALHANDVALUE.
						else if(dealer.handValue<=21) {
								dealer.finalHandValue=dealer.handValue;
								System.out.println("DEALERFINALHANDVALUE 4kaarten: "+dealer.finalHandValue);
								System.out.println("DEALERHANDVALUE: 4kaarten: "+dealer.handValue);
						}// IF DEALER HAS MORE THEN 21 POINTS WITH 4 CARDS SET FINALHANDVALUE.
						else {
							dealer.finalHandValue=0;
							System.out.println("DEALERFINALHANDVALUE >21 met 4 kaarten: "+dealer.finalHandValue);
							System.out.println("DEALERHANDVALUE > 21 met 4kaarten: "+dealer.handValue);
						}
					}
				// IF DEALER HAS MORE THEN 21 POINTS WITH 3 CARDS SET FINALHANDVALUE
				else if(dealer.handValue>21) {
					dealer.finalHandValue=0;
					System.out.println("DEALERFINALHANDVALUE >21 met 3 kaarten: "+dealer.finalHandValue);
					System.out.println("DEALERHANDVALUE > 21 met 3 kaarten: "+dealer.handValue);
				}
				
				// IF DEALER HAS NOT MORE THEN 21 POINTS WITH 3 CARDS AND
				//  IF DEALER HAS NOT LESS THEN 17 POINTS WITH 3 CARDS.
				else {
				dealer.finalHandValue=dealer.handValue;
				System.out.println("DEALERFINALHANDVALUE >=17 <21 met 3 kaarten: "+dealer.finalHandValue);
					}
				}
		
		// IF DEALER HAS MORE THEN 17 POINTS WITH 2 CARDS 
		else {
			dealer.finalHandValue=dealer.handValue;
			System.out.println("DEALERFINALHANDVALUE >=17 met 2 kaarten: "+dealer.finalHandValue);
		}
		
		// SHOW THE DEALER HIS HAND ( ONLY 1 CARD VISIBLE!);
		System.out.println("DEALERHAND: ");
		dealer.printDealerHand();
	}
	//****************************************DEALER*********************************************************************
	//*******************************************************************************************************************
	
	
	//EACH PLAYER TAKES HIS TURN AFTER THE CARDS ARE ON THE TABLE.
	//AFTER THE TURN IS OVER. CHECK THE (FINALHAND)SCORES.
	//RESET DEALER'S HANDVALUE;
	public void takeTurn() {	
			for(int i=0;i<players.size();i++) {
			Player player = players.get(i);
			//startRound(player) METHOD TO START ROUND. 
			startRound(player);
		}
			checkScores();
			dealer.handValue=0;
	}
	
	
	// CHECK AFTER EACH ROUND IF SOMEONE WANTS TO STOP PLAYING.
	public boolean quit() {
		boolean quit = false;
		System.out.println("Wil er iemand stoppen? 'ja' of 'nee'.");
		String input = scanner.next();
		amountPlayers--;
		
		if(input.equals("ja")) {
			System.out.println("Wie wil er stoppen? Type het nummer van de speler in.");
			int id = scanner.nextInt();
			
			if(players.size()==2 && id ==3) {
				players.remove(1);
				quit = true;
			}else {
			players.remove(id-1);
			quit = true;
			}
		}
		else if(input.equals("nee")) {
			System.out.println("Niemand wil stoppen. We gaan door naar de volgende ronde.");
			quit = true;
		}
		return quit;
	}

	
	//METHOD TO START A ROUND @PARA = Player player.
	public void startRound(Player player) {
		
			// IF HANDVALUE <21 BUT >0 THEN PRINT PLAYERNAME + HIS HANDVALUE. 
			// EXECUTE METHOD HANDLESS21(PLAYER).
			if(player.getHandValue()<21 && player.getHandValue()>0) {
				System.out.println(player.name+" zijn handwaarde is: "+ player.getHandValue());
				handLess21(player);
				
					// IF HANDVALUE IS STILL <21 - AFTER 1x HANDLESS 21 - THEN PRINT HANDVALUE AND REPEAT METHOD HANDLESS 21.
					// AFTER 2nd TIME HANDLESS 21 - WITH 4 CARDS RE-ENTER THIS STARTROUND METHOD.
					if(player.getHandValue()<21 && player.getHandValue()>0) {
						System.out.println("HandValue: * "+player.getHandValue());
						handLess21(player);
						startRound(player);
					// IF HANDVALUE IS 21 WITH 3 CARDS AFTER 1x HANDLESS 21. PRINT FINAL HANDVALUE.
					if(player.getHandValue()==21) {
						player.finalHandValue=21;
						System.out.println("FinalHandValue: * "+player.finalHandValue);
						}
					}
					
			// IF HANDVALUE >21 AND PLAYER DOES NOT HAVE A. HAND IS LOST. UPDATE FINALHANDVALUE;
			else if(player.getHandValue()>21 && !player.gotA()) {
						player.finalHandValue=0;
						System.out.println("FinalHandValue >21 punten , geen A: "+player.finalHandValue);
					}
			// IF HANDVALUE >21 AND PLAYER DOES HAVE A. THE A HAS TO BE SET TO VALUE =1. UPDATE HANDVALUE!. 
			// gotA() CHECKS IF NUMBER CARD is 14 (WHICH IS A.) AND ALSO CHECKS IF VALUE IS STILL 11 SINCE IT NEEDS TO 
			// SEPERATE THE DIFFRENT A'S (ADJUSTED A OR 11 POINT A).
					
			else if(player.getHandValue()>21 && player.gotA()) {
				for(Card card:player.hand) {
						// SHOW THE DOWNGRADE EXECUTE.
						if(card.A==true && player.getHandValue()>21) {
							System.out.println("*A IS SUCCESFULLY EXECUTED.");
							card.value=1;
							player.setHandValue();
							}
						}
						// AFTER THE DOWNGRADE - HAND IS <21 SO START METHOD HANDLESS 21 - AFTER LOOP TROUGH START ROUND WITH ALL CARDS.
						System.out.println("HANDVALUE AFTER A DOWNGRADE: "+player.getHandValue());
						handLess21(player);
						startRound(player);
					}
					
			// IF HANDVALUE == 21 BUT NOT A BLACKJACK. UPDATE FINALHAND WITH 21.
			}
			else if(player.getHandValue()==21 && player.hand.size()>2) {
				System.out.println(player.name+" SCORE IS 21 PUNTEN.");
				player.setFinalHandValue();
				System.out.println(player.name+" FINALHANDVALUE UPDATED - ." +player.finalHandValue);
				//player.handValue=0;
			}
			else if(player.getHandValue()==21 && player.hand.size()==2) {
				System.out.println(player.name+" heeft BLACKJACK!!!!");
				player.setFinalHandValue();
				System.out.println(player.name+" UPDATE THE BLACKJACK - FINALHANDVALUE: " +player.finalHandValue);
				//player.handValue=0;
			}
			// DUBBEL A - VERANDER 1x A naar value = 1.
			else if(player.getHandValue()>21) {
				System.out.println("Value is: &*&"+ player.getHandValue());
				for(Card card:player.hand) {
					if(card.A==true && player.getHandValue()>21) {
						System.out.println("A's gesignaleerd");
						player.handValue -=10;
//						player.setHandValue();
					}
				}
				if(player.getHandValue()>21){
					player.finalHandValue=0;
				}
				
			}
		}
	
	
	//CHECK THE SCORES METHOD. PLAYER.FINALHANDVALUE VERSUS DEALER.FINALHANDVALUE.
	public void checkScores() {
		for(Player player:players) {
			if(player.finalHandValue>dealer.finalHandValue) {
				System.out.println(player.name+" wint van de deler en verdubbelt zijn inzet.");
				player.wallet += player.bet*2;
				System.out.println(player.name+" heeft na deze ronde "+player.wallet+": coins.");
			}
			else if(player.finalHandValue>0 && player.finalHandValue==dealer.finalHandValue) {
				System.out.println(player.name+" gelijk aan de deler. Inzet terug.");
				player.wallet += player.bet;
				System.out.println(player.name+" heeft na deze ronde "+player.wallet+": coins.");
			}
			else if(player.finalHandValue==21 && player.hand.size()==2) {
				System.out.println(player.name+" Heeft blackjack en verdubbelt zijn inzet.");
				player.wallet += player.bet*2;
				System.out.println(player.name+" heeft na deze ronde "+player.wallet+": coins.");
			}
			else {
				System.out.println(player.name+" heeft na deze ronde "+player.wallet+": coins.");
			}
		}
	}
	
	//METHOD FOR HANDVALUELESS THEN 21!
	public void handLess21(Player player) {
		System.out.println(" *METHOD handLess21 WILL BE EXECUTED FROM HERE.* ");
		
		// IF START HAND = 9, 10 or 11 : OFFER DOUBLE.
		// NOT IDIOT-PROOF YET.
			if(player.hand.size()<3 && player.handValue == (9)||player.handValue==(10)||player.handValue==(11)) {
				System.out.println(player.name+" is aan de beurt. HandValue: "+player.handValue+" Kies: 'hit', 'double'of 'stand'.");
			}
			// NOT 9,10 or 11 ? - HIT OR STAND.
			else {
				System.out.println(player.name+" is aan de beurt. HandValue: "+player.handValue+" Kies: 'hit' of 'stand'.");
			}
			
		String input = scanner.next();
		// IF HANDVALUE <21 THEN U CAN EITHER HIT A NEW CARD OR STAND TO SIT OUT.
		if(input.equals("hit")) {
			Card card3 = deck.getCardFromDeck();
			player.hand.add(card3);
			deck.deck.remove(card3);
			player.printHand();
			player.setHandValue();
			System.out.println(" *METHOD handLess21 exectued* ");
			System.out.println("hand value: "+player.handValue);
		}
		// DOUBLE THE COINS.
		if(input.equals("double")&&player.wallet>=2) {
			player.wallet-=2;
			player.bet+=2;
			System.out.println("TOTAL BET: "+player.bet);
			System.out.println("TOTAL WALLET: "+player.wallet);
			Card card3 = deck.getCardFromDeck();
			player.hand.add(card3);
			deck.deck.remove(card3);
			player.printHand();
			player.setHandValue();
			player.setFinalHandValue();
			System.out.println(" *stand na double* finalhandvalue: ");
			System.out.println(player.finalHandValue);
		}
		// TO POOR TO DOUBLE.
		else if(input.equals("double")&&player.wallet<2) {
			System.out.println("NOT ENOUGHT IN YOUR WALLET TO DOUBLE.");
			System.out.println("TOTAL WALLET: "+player.wallet);
			handLess21(player);
		}
		
		// SET THE FINALHANDVALUE.
		else if(input.equals("stand")) {
			player.setFinalHandValue();
			System.out.println(" *stand* finalhandvalue: ");
			System.out.println(player.finalHandValue);
		}
	}
}
	
