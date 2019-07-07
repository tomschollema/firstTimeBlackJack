package BlackJackGame;

public class Card {
	int value;
	int number;
	String type;
	boolean A;
	
	//CARD CONSTRUCTOR. GIVE IT A NUMBER (1 = 2, 2 = 3, etc,etc, 13 = K, 14 = A,
	//CARD HAS TO HAVE TYPE - HEARTS, DIAMOND ETC ETC.
	//CARD HAS A VALUE. 2-10 = 2-10, B=10, Q=10, K=10, A=11 (or 1 if hand = >21). 
	public Card(int number,String type) {
		this.number = number;
		this.type = type;
		setValue();
	}
	
	// SET THE VALUE'S
	//CARD HAS A VALUE. 2-10 = 2-10, B=10, Q=10, K=10, A=11 (or 1 if hand = >21). 
	public void setValue() {
		if(number<11) {
			this.value=number;
		}
		else if(number<=13) {
			this.value=10;
		}
		else {
			this.value=11;
			this.A=true;
		}
	}
	//OVERLOAD THE PRINTCARD METHOD. 
	public void printCard(Card card) {
		number = card.number;
		if(number<11) {
			//this.value=number;
			System.out.println(type+" "+number);
			}
			else if(number==11) {
			System.out.println(type+" Boer");
			}
			else if(number==12) {
			System.out.println(type+" Vrouw");
			}
			else if(number==13) {
				System.out.println(type+" Heer");
			}
			else {
				System.out.println(type+"Aas");
			}
	}
	// PRINT CARD WITH TYPE'S - WHILE TYPING THIS I CAN SEE HOW STUPID THIS WAS.
	// SHOULD HAVE USED THE TYPE-FIELD TO MAKE LIFE EASIER. ANYHOW IT WORKS FOR NOW.
	public void printCard() {
		if(number<11) {
		//this.value=number;
		System.out.println(type+" "+number);
		}
		else if(number==11) {
		System.out.println(type+" Boer");
		}
		else if(number==12) {
		System.out.println(type+" Vrouw");
		}
		else if(number==13) {
			System.out.println(type+" Heer");
		}
		else {
			System.out.println(type+" Aas");
		}
	}

	//PRINT VALUE OF THE CARD. AGAIN NOT REALLY USEFULL.
	public void printValue() {
		// TODO Auto-generated method stub
		System.out.println("Value: "+value);
		System.out.println(" ");
	}
	
	//GET THE VALUE OF A CARD - RETURN INT.
	public int getValue() {
		return value;
	}
	
	//SET THE VALUE OF THE UNIQUE A-CARD TO 1.
	public int setValueA() {
		this.value = 1;
		return value;
	}
	
	public int getNumber() {
		return number;
	}
}
