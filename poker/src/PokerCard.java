public class PokerCard implements Comparable<PokerCard> {
    
    private char suit;	
    private int value;

	public PokerCard(String card) {
                
                
		char value = card.charAt(0);
                this.suit = card.charAt(1);		
                switch (value) {
		case 'T':
			 this.value = 10;
			break;
		case 'J':
			 this.value = 11;
			break;
		case 'Q':
			 this.value = 12;
			break;
		case 'K':
			 this.value = 13;
			break;
		case 'A':
			 this.value = 14;
			break;
		default:
			 this.value = Integer.parseInt("" + value);
			break;
		}
                
               

	}

	public String toString() {
		String str = "";
		str = String.valueOf(this.value) + this.suit;
		return str;
	}

        //Getter methods
	public int getValue() {
            return this.value;
	}

	public char getSuit() {
            return this.suit;
	}
        
        //Method to compare with other card value
        public int compareTo(PokerCard compareCard) {
            int compareValue = ((PokerCard) compareCard).getValue();
	    return this.value - compareValue;

	}
}
