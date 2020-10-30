
import java.util.Arrays;

public class PokerHand {
	public PokerCard[] playing_cards;

	public Rank combination_rank;

	public Integer handVal;

	public PokerHand(PokerCard[] cards) {
		this.playing_cards = cards;
	}

	public PokerHand(String[] player_cards) {
		if (player_cards.length != 5) {
			System.out.println("Check card count. Wrong format.");
		} else {
			PokerCard[] cards = new PokerCard[5];
			for (int i = 0; i < 5; i++) {
				cards[i] = new PokerCard(player_cards[i]);
			}
			this.playing_cards = cards;
		}
	}
	
	public PokerCard getCard(int key) {
		if (key >= 5) {
			return null;
		}
		return playing_cards[key];
	}

        public void sortCards(){
		Arrays.sort(this.playing_cards);
	}
        
	public String toString() {
		String output = "";
		for (PokerCard card : this.playing_cards) {
			output += card.toString() + " ";
		}
		if(output.length() > 0)
			output+= "(" + this.getHandCategory().getDescription() + ")";
		return output;
	}

	public Rank getHandCategory() {
		return this.combination_rank;
	}

	public Integer getHandValue() {
		return this.handVal;

	}

        public void assignRank(){

            if(this.sameSuitCards() != -1 && this.straight() != -1){
                if(this.getCard(0).getValue() == 10){
                    this.combination_rank = Rank.ROYAL_FLUSH;
                    this.handVal = 9999;
                    return;
                }else{
                    this.combination_rank = Rank.STRAIGHT_FLUSH;
                    return;
                }
            }

            if(this.four() != -1){
                this.combination_rank = Rank.FOUR_OF_A_KIND;
                return;
            }

            if(this.fullHouse() != -1){
                this.combination_rank = Rank.FULL_HOUSE;
                return;
            }

            if(this.sameSuitCards() != -1){
                this.combination_rank = Rank.FLUSH;
                return;
            }

            if(this.straight() != -1){
                this.combination_rank = Rank.STRAIGHT;
                return;
            }       

            if(this.three() != -1){
                this.combination_rank = Rank.THREE_OF_A_KIND;
                return;
            }

            if(this.twoPairs() != -1){
                this.combination_rank = Rank.TWO_PAIRS;
                return;
            }

            if(this.pair() != -1){
                this.combination_rank = Rank.ONE_PAIR;
                return;
            }

            this.handVal = this.getCard(4).getValue();
            this.combination_rank = Rank.HIGH_CARD;
    }

    private int pair(){
        int previous_card = this.playing_cards[4].getValue();
        int total = 0, count = 1;

        for(int i = 3; i >= 0; i--){
            if(this.playing_cards[i].getValue() == previous_card){
                total += this.playing_cards[i].getValue();
                count++;             
            }

            if(count == 2){
                break;
            }
            previous_card = this.playing_cards[i].getValue();
        }

        if(count == 2){
            this.handVal = total;
            return total;
        }
        return -1;
    }

    private int twoPairs(){
        int previous_card = this.playing_cards[4].getValue();
        int i = 3, total = 0, count = 1;

        for(; i >= 0; i--){
            if(this.playing_cards[i].getValue() == previous_card){
                total += this.playing_cards[i].getValue();
                count++;             
            }

            if(count == 2){
                
                break;
            }else{
                total = 0;
                count = 1;   
            }
            previous_card = this.playing_cards[i].getValue();
        }
        
        if(count == 2 && i > 0){
            count = 1;
            previous_card = this.playing_cards[i-1].getValue();
            for(i = i-2; i >= 0; i--){
                if(this.playing_cards[i].getValue() == previous_card){
                    total += this.playing_cards[i].getValue();
                    count++;             
                }
                if(count == 2){
                    break;
                }else{
                    total = 0;
                    count = 1;   
                }
                previous_card = this.playing_cards[i].getValue();
            }
        }else{
            return -1;
        }

        if(count == 2){
            this.handVal = total;
            return total;
        }
        return -1;
    }

    private int three(){
        int previous_card = this.playing_cards[4].getValue();
        int total = 0, count = 1;

        for(int i = 3; i >= 0; i--){
            if(this.playing_cards[i].getValue() == previous_card){
                total += this.playing_cards[i].getValue();
                count++;             
            }else{
                total = 0;
                count = 1;
            }           

            previous_card = this.playing_cards[i].getValue();
        }

        if(count == 3){
            this.handVal = total;
            return total;
        }
        return -1;
    }
    
     private int four(){
        
        int previous_card = this.playing_cards[4].getValue();
        int total = 0, nOfCards = 1;

        
        for(int i = 3; i >= 0 && nOfCards < 4; i--){
            if(this.playing_cards[i].getValue() == previous_card){
                total += this.playing_cards[i].getValue();
                nOfCards++;             
            }else{
                total = 0;
                nOfCards = 1;
            }   

            previous_card = this.playing_cards[i].getValue();
        }

        if(nOfCards == 4){
            this.handVal = total;
            return total;
        }
        return -1;
    }

    private int fullHouse(){
     
        boolean changed = false;
        int previous_card = this.playing_cards[4].getValue();
        int total = 0, count = 1;

        for(int i = 3; i >= 0; i--){
            if(this.playing_cards[i].getValue() == previous_card){
                total += this.playing_cards[i].getValue();
                count++;             
            
            }else if(changed == false){
                changed = true;
                if(count < 2){
                    this.handVal = -1;
                    return -1;
                }

                if(count == 3)
                    this.handVal = total;

            }else{
                this.handVal = -1;
                return -1;
            }
            previous_card = this.playing_cards[i].getValue();
        }
        //System.out.println(total);
        this.handVal = total;
        return total;
        
    }

    private int sameSuitCards(){
        
        char previous_card = this.playing_cards[0].getSuit();
        int total = this.playing_cards[0].getValue();

        for(int i = 1; i < 5; i++){
            if(this.playing_cards[i].getSuit() != previous_card){
                return -1;
            }
            total += this.playing_cards[i].getValue();
            previous_card = this.playing_cards[i].getSuit();
        }
        this.handVal = total;
        return total;
    }

    private int straight(){
        
        int previous_card = this.playing_cards[0].getValue();
        int total = previous_card;
        for(int i = 1; i < 5; i++){
            if(this.playing_cards[i].getValue() != previous_card + 1){
                return -1;
            }
            previous_card = this.playing_cards[i].getValue();
            total += 1;
        }
        this.handVal = total;
        return total;
    }
        
}