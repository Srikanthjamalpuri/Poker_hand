import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CardSorter {
	public static final String TIE = "There is a tie.";
	public static void main(String[] args) {
		int player1_wins = 0;
		int player2_wins = 0;

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			
			while (true) {
				//Reading input
                                String input = br.readLine();
				if (input == null || input.length() == 0) {
					break;
				}
				if(!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")){
					System.out.println("Please Check input format.It is wrong.");
					break;
				}
				
				String[] cards = input.split(" ");
				String[] player1_cards = Arrays.copyOfRange(cards, 0, 5);
				String[] player2_cards = Arrays.copyOfRange(cards, 5, 10);
				PokerHand player1 = new PokerHand(player1_cards);
				PokerHand player2 = new PokerHand(player2_cards);

				//Sorting the cards of players
                                player1.sortCards();
				player2.sortCards();
				
                                //Assign ranks to cards
				player1.assignRank();
				player2.assignRank();
                                
                                //Check the winner
				int results_players = winner(player1, player2);
				
                                if (results_players == 1) {
					player1_wins++;
				} else if (results_players == 2) {
					player2_wins++;
				} else {
					System.out.println(TIE);
				}
			}

			System.out.println("Player 1: " + player1_wins + " hands");
			System.out.println("Player 2: " + player2_wins + " hands");

			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
        //Method to find winners based on card ranks
	public static int winner(PokerHand player1_hand, PokerHand player2_hand) {
		

		if (player1_hand.getHandCategory().getRank() > player2_hand.getHandCategory().getRank()) {
			return 1;
		} else if (player1_hand.getHandCategory().getRank() < player2_hand.getHandCategory().getRank()) {
			return 2;
		} else if (player1_hand.getHandValue() > player2_hand.getHandValue()) {
			return 1;
		} else if (player1_hand.getHandValue() < player2_hand.getHandValue()) {
			return 2;
		} else {
			// final tie break check
			for (int i = 4; i >= 0; i--) {
				if (player1_hand.getCard(i).getValue() > player2_hand.getCard(i).getValue()) {
					return 1;
				} else if (player1_hand.getCard(i).getValue() < player2_hand.getCard(i).getValue()) {
					return 2;
				}
			}
			return -1;
		}

	}
}




