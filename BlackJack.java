package com.bayviewglen.gitpushes;

import java.lang.Math;
import java.util.Scanner;

public class BlackJack {

	private static final int START_WALLET = 500;
	private static final int NUM_VALUES = 13;
	private static final int NUM_SUITS = 4;
	private static final int MAX_NUM_CARDS = 21;

	private static int getRandomCard() {
		int cardValue = (int) (Math.random() * NUM_VALUES + 1);
		int cardSuit = (int) (Math.random() * NUM_SUITS + 1);
		return (cardSuit * 100 + cardValue);
	}

	private static int valueCard(int card) {
		int cardValue = card % 100;
		if (cardValue > 10) {
			cardValue = 10;
		}
		return cardValue;
	}

	private static String displayCard(int card) {
		String strCard = "";
		int cardValue = card % 100;

		if (cardValue == 1) {
			strCard += "A";
		} else if (cardValue == 11) {
			strCard += "J";
		} else if (cardValue == 12) {
			strCard += "Q";
		} else if (cardValue == 13) {
			strCard += "K";
		} else {
			strCard += cardValue;
		}

		int cardSuit = card / 100;
		if (cardSuit == 1)
			strCard += "H";
		else if (cardSuit == 2)
			strCard += "C";
		else if (cardSuit == 3)
			strCard += "S";
		else if (cardSuit == 4)
			strCard += "D";

		return strCard;
	}

	private static String displayHand(int[] cards, int cnt, boolean useXX) {
		String result = "";
		for (int i = 0; i < cnt; i++) {
			if (useXX && i > 0) {
				result = result + " XX";
			} else {
				result = result + " " + displayCard(cards[i]);
			}
		}
		return result;
	}

	private static int getSumCards(int[] cards, int cnt) {
		int result = 0;
		int numAces = 0;
		for (int i = 0; i < cnt; i++) {
			result += valueCard(cards[i]);
			if (valueCard(cards[i]) == 1) {
				numAces++;
				result += 10;
			}
		}

		while (result > 21 && numAces > 0) {
			result -= 10;
			numAces--;
		}
		return result;
	}

	private static int getBet(int wallet, Scanner input) {
		int bet = 0;
		while (bet < 1 || bet > wallet) {
			System.out.println("How much do you want to bet? Bet between 1 to " + wallet);
			if (input.hasNextInt()) {
				bet = input.nextInt();
				input.nextLine();
			} else {
				System.out.println("You have entered an invalid bet. Try again.");
				input.next();
			}
		}
		return bet;
	}

	private static boolean getPlayOption(Scanner in) {
		boolean play = true;
		System.out.println("Do you want to play again ? Y/N");
		String str = in.nextLine();
		while (!"Y".equalsIgnoreCase(str) && !"N".equalsIgnoreCase(str)) {
			System.out.println("You have entered an invalid option. Try again.");
			str = in.nextLine();
		}
		if ("N".equals(str) || "n".equals(str))
			play = false;
		return play;
	}

	private static int playHand(int sum, int bet, Scanner in) {
		int[] dCards = new int[MAX_NUM_CARDS];
		int[] pCards = new int[MAX_NUM_CARDS];
		int pCount = 0;
		int dCount = 0;
		for (int i = 0; i < 2; i++) {
			dCards[i] = getRandomCard();
			dCount++;
			pCards[i] = getRandomCard();
			pCount++;
		}

		boolean pStay = false;
		boolean pOver21 = false;
		System.out.println("-X-X-X-X-X-X-X-X-X-X-X-X-| Begin!!! |-X-X-X-X-X-X-X-X-X-X-X-X-");
		while (!pStay && !pOver21) {
			System.out.print("Dealer:" + displayHand(dCards, dCount, true));
			System.out.print("\nPlayer:" + displayHand(pCards, pCount, false));
			System.out.println("\n-X-X-X-X-X-X-X-X-X-X-X-X-| Options |-X-X-X-X-X-X-X-X-X-X-X-X-");
			System.out.println("What do you want to do? 1.Hit 2.Stay 3.Double Down");
			int action = 0;
			while (action < 1 || action > 3) {
				if (in.hasNextInt()) {
					action = in.nextInt();
					in.nextLine();
					if (action < 1 || action > 3) {
						System.out.println("You have entered an invalid input. Try again.");
					}
				} else {
					System.out.println("You have entered an invalid input. Try again.");
					in.next();
				}
			}
			if (action == 1) {
				pCards[pCount] = getRandomCard();
				pCount++;
			} else if (action == 2) {
				pStay = true;
			}
			if (action == 3) {
				if (sum < 2 * bet) {
					System.out.println("Not enough money to Double Down. Try again.");
				} else {
					pCards[pCount] = getRandomCard();
					pCount++;
					bet *= 2;
					pStay = true;
				}
			}
			if (getSumCards(pCards, pCount) > 21)
				pOver21 = true;
		}
		while (!pOver21
				&& (getSumCards(dCards, dCount) < getSumCards(pCards, pCount) || getSumCards(dCards, dCount) < 17)) {
			dCards[dCount] = getRandomCard();
			dCount++;
		}

		System.out.println("Dealer:" + displayHand(dCards, dCount, false) + " ... " + getSumCards(dCards, dCount));
		System.out.println("Player:" + displayHand(pCards, pCount, false) + " ... " + getSumCards(pCards, pCount));
		System.out.print("Result:");
		if (pOver21
				|| (getSumCards(dCards, dCount) >= getSumCards(pCards, pCount) && getSumCards(dCards, dCount) <= 21)) {
			System.out.println(" Dealer wins! " + bet);
			bet *= -1;
		} else {
			System.out.println(" Player wins! " + bet);
		}
		System.out.println("-X-X-X-X-X-X-X-X-X-X-X-X-| FINISHED |-X-X-X-X-X-X-X-X-X-X-X-X-");
		return bet;
	}

	private static void displayMoney(int sum) {
		System.out.print("You have " + sum + " in your wallet. ");
		return;
	}

	private static String getPlayerName(Scanner in) {
		String name = "";
		System.out.println("Please enter your name: ");
		name = in.nextLine();
		return name;
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String playerName = getPlayerName(input);
		while (!"quit".equalsIgnoreCase(playerName)) {
			int wallet = START_WALLET;
			boolean playerContinue = true;
			while (wallet > 0 && playerContinue) {
				displayMoney(wallet);
				int bet = getBet(wallet, input);
				int resultHand = playHand(wallet, bet, input);
				wallet += resultHand;
				displayMoney(wallet);
				if (wallet > 0)
					playerContinue = getPlayOption(input);
				else
					System.out.println("You're broke!");
			}
			playerName = getPlayerName(input);
		}
		System.out.println("Play again later!");
	}

}