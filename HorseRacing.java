package com.bayviewglen.hrsrcng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HorseRacing {

  public static final Scanner INPUT = new Scanner(System.in);

  public static void main(String[] args) throws InterruptedException {

    introduction();
    boolean done = false;
    while (!done) {

      String[] playerNames = getNames();
      int[] playerWallets = getWallet();
      String[] horses = getHorses();
      String[] horsesInRace = getRaceHorses(horses);
      int[][] playerBets = new int[playerNames.length][horsesInRace.length];

      playerBets = getBet(playerNames, playerWallets, horsesInRace);

      int winHorse = race(horsesInRace);

      afterRace(playerNames, playerWallets, horsesInRace, winHorse, playerBets);

      done = gameEnd(playerNames, playerWallets);
    }
    Conclusion();
  }

  private static void introduction() throws InterruptedException {
    System.out.println("Welcome to Horse Racing!");
    Thread.sleep(2000);
    System.out.println("By: Aadithya Shankar");
    Thread.sleep(2000);
    System.out.println("Get ready to lose some money!!!");
  }

  private static String[] getHorses() {
    String[] horses = null;

    try {
      Scanner scanner = new Scanner(new File("input/horseData.dat"));
      int numHorses = Integer.parseInt(scanner.nextLine());
      horses = new String[numHorses];

      for (int i = 0; i < numHorses; i++) {
        horses[i] = scanner.nextLine();
      }

      scanner.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return horses;
  }

  private static boolean gameEnd(String[] playerNames, int[] playerWallets) {
    System.out.println("");

    try {
      PrintWriter printer = new PrintWriter(new FileWriter("Input/playerData.dat"));

      int n = playerNames.length;
      String numberOfPlayers = String.valueOf(n);
      printer.write(numberOfPlayers);

      for (int i = 0; i < playerNames.length; i++) {
        printer.write("\n" + playerNames[i] + " " + playerWallets[i]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.print("Would you like to play again? ");
    boolean correctInput = false;

    while (!correctInput) {
      String input = INPUT.nextLine();
      if ("yes".equalsIgnoreCase(input) || "play again".equalsIgnoreCase(input)
          || "go ahead".equalsIgnoreCase(input)) {
        return false;
      } else
        if ("no".equalsIgnoreCase(input) || "done".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
        return true;
      } else {
        System.out.println("Please enter a correct action: ");
      }
    }
    return true;
  }

  private static void afterRace(String[] playerNames, int[] playerWallets, String[] horsesInRace, int winHorse,
      int[][] playerBets) {

    for (int i = 0; i < playerBets.length; i++) {
      if (playerBets[i][winHorse] != 0) {
        playerWallets[i] += 2 * playerBets[i][winHorse];
        System.out
            .println("Congratulations! " + playerNames[i] + " has won $" + playerBets[i][winHorse] + " !");
      }
    }

    for (int i = 0; i < playerBets.length; i++) {
      for (int j = 0; j < playerBets[i].length; j++) {
        if (j != winHorse && playerBets[i][j] != 0) {
          System.out.println("Sorry! " + playerNames[i] + " has lost...");
          break;
        }
      }
    }
  }

  private static int race(String[] horsesInRace) throws InterruptedException {
    boolean doneRace = false;
    int number = horsesInRace.length;
    int raceTrack = 100;
    int[] steps = new int[horsesInRace.length];
    String[] spaces = new String[horsesInRace.length];
    String[] spacesForNames = new String[horsesInRace.length];

    for (int i = 0; i < horsesInRace.length; i++) {
      spacesForNames[i] = "";
    }

    for (int i = 0; i < horsesInRace.length; i++) {
      int x = 20 - horsesInRace[i].length();
      for (int j = 0; j <= x; j++) {
        spacesForNames[i] += " ";
      }
    }

    System.out.println("\nRace Starts!!!");
    System.out.println(
        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    for (int i = 0; i < horsesInRace.length; i++) {
      spaces[i] = "";
    }

    for (int i = 0; i < horsesInRace.length; i++) {
      for (int j = 0; j < raceTrack - 1; j++) {
        spaces[i] += " ";
      }
    }

    for (int i = 0; i < number; i++) {
      System.out.print("|" + horsesInRace[i] + spacesForNames[i] + "|" + (i + 1));
      System.out.println(
          "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    while (!doneRace) {
      System.out.println("");
      System.out.println("");
      System.out.println("");

      int[] runSteps = new int[horsesInRace.length];
      for (int i = 0; i < horsesInRace.length; i++) {
        runSteps[i] = (int) (Math.random() * 5) + 1;
      }

      for (int i = 0; i < runSteps.length; i++) {
        steps[i] += runSteps[i];
        if (steps[i] > 100) {
          steps[i] = 100;
        }
      }

      for (int i = 0; i < spaces.length; i++) {
        for (int j = 0; j <= runSteps.length; j++) {
          spaces[i] += " ";
        }
      }

      System.out.println(
          "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      for (int i = 0; i < number; i++) {
        System.out.print("|" + horsesInRace[i] + spacesForNames[i] + "|");

        for (int j = 0; j < steps[i]; j++) {
          System.out.print(" ");
        }
        System.out.print(i + 1);
        System.out.println(
            "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      }

      for (int i = 0; i < steps.length; i++) {
        if (steps[i] >= raceTrack) {
          System.out.println(
              "\nRace finished! Horse #" + (i + 1) + " - " + horsesInRace[i] + " is the first place!");
          return i;
        }
      }

      Thread.sleep(1000);

    }
    return -1;
  }

  private static int[][] getBet(String[] playerNames, int[] playerWallets, String[] horsesInRace) {
    int[][] bet = new int[playerNames.length][horsesInRace.length];

    boolean choosePlayer = false;

    while (!choosePlayer) {
      displayPlayers(playerNames, playerWallets);
      System.out.print("\nPlease choose a player: ");

      int playerIndex = choosePlayer(playerNames, playerWallets, horsesInRace);
      int amountOfBet = individualPlayerBet(bet, playerIndex, playerNames, playerWallets, horsesInRace);

      if (amountOfBet != 0) {
        int horseIndex = ChooseHorse(playerIndex, horsesInRace, playerNames);
        bet[playerIndex][horseIndex] += amountOfBet;
        System.out.println(playerNames[playerIndex] + ", you have placed a bet of $"
            + bet[playerIndex][horseIndex] + " on horse " + horsesInRace[horseIndex] + ".");
      }
      choosePlayer = continueChosing();
    }
    return bet;
  }

  private static boolean continueChosing() {
    System.out.println("What would you like to do? 1. Choose other player\t2.Start racing!");
    boolean correctInput = false;

    while (!correctInput) {
      String input = INPUT.nextLine();
      boolean ifInteger = CheckIfInteger(input);

      if (ifInteger) {
        int x = Integer.parseInt(input);
        if (x == 2) {
          return true;
        } else if (x == 1) {
          return false;
        } else {
          System.out.print("Please enter a correct action:");
        }
      } else {
        if ("Choose".equalsIgnoreCase(input) || "bet".equalsIgnoreCase(input)
            || "choose other player".equalsIgnoreCase(input)) {
          return false;
        } else if ("START".equalsIgnoreCase(input) || "start racing".equalsIgnoreCase(input)
            || "race!".equalsIgnoreCase(input)) {
          return true;
        } else {
          System.out.print("Please enter a correct action:");
        }
      }
    }
    return false;
  }

  private static int ChooseHorse(int playerIndex, String[] horsesInRace, String[] playerNames) {
    System.out.println("\nWhich horse will you be betting on?");
    displayHorses(horsesInRace);
    int horseIndex = -1;

    System.out.print("\nPlease choose a horse from above: ");
    boolean choosingHorse = false;
    while (!choosingHorse) {
      String input = INPUT.nextLine();
      boolean ifInteger = CheckIfInteger(input);
      if (ifInteger) {
        int x = Integer.parseInt(input);
        for (int i = 0; i < horsesInRace.length; i++) {
          if (i == x - 1) {
            horseIndex = i;
            return horseIndex;
          }
        }
      } else {
        for (int i = 0; i < horsesInRace.length; i++) {
          if (horsesInRace[i].equalsIgnoreCase(input)) {
            horseIndex = i;
            return horseIndex;
          }
        }
      }

      System.out.print("Please enter a correct horse from above: ");
    }
    return horseIndex;
  }

  private static void displayHorses(String[] horsesInRace) {
    System.out.println();
    System.out.println("#" + "|" + "    Horses Names    " + "|");
    for (int i = 0; i < horsesInRace.length; i++) {
      System.out.printf("%1s%1s%20s%1s\n", "~", "|", "~~~~~~~~~~~~~~~~~~~~", "|");
      System.out.printf("%1s%1s%20s%1s\n", i + 1, "|", horsesInRace[i], "|");
    }
  }

  private static int individualPlayerBet(int[][] bet, int playerIndex, String[] playerNames, int[] playerWallets,
      String[] horsesInRace) {

    System.out.print(playerNames[playerIndex] + ", you have $" + playerWallets[playerIndex]
        + ", how much would you want to bet? (Note: Any bet that is not a whole number will be converted to whole number) ");
    Double money = 0.0;
    int wallet = playerWallets[playerIndex];

    boolean betting = false;

    if (wallet == 0) {
      System.out.println("You have $0! You cannot bet anymore!");
      return 0;
    }

    while (!betting) {
      String userBet = INPUT.nextLine();
      try {
        money = Double.parseDouble(userBet);
        betting = true;

        if (money > wallet) {
          System.out.print("You don't have enough money! Enter new bet: ");
          betting = false;
        } else if (money < 1 && money > 0) {
          System.out.print("You must bet a whole number amount of money! Enter new bet: ");
          betting = false;
        } else if (money == 0) {
          System.out.print("You cannot bet nothing! Enter new bet: ");
          betting = false;
        } else if (money <= 0) {
          System.out.print("You cannot bet a negative amount of money! Enter new bet: ");
          betting = false;
        } else {
          betting = true;
        }

      } catch (NumberFormatException ex) {
        if ("Quit".equalsIgnoreCase(userBet)) {
          money = 0.0;
          betting = true;
        } else {
          System.out.print("That is not a proper amount of money! Please enter a number you want to bet: ");
        }
      }
    }

    int moneyINT = money.intValue();
    playerWallets[playerIndex] -= moneyINT;

    return moneyINT;
  }

  private static int choosePlayer(String[] playerNames, int[] playerWallets, String[] horsesInRace) {
    int playerIndex = -1;
    boolean checkingName = false;

    while (!checkingName) {
      String nameEntered = INPUT.nextLine();
      boolean ifInteger = CheckIfInteger(nameEntered);

      if (ifInteger) {
        int x = Integer.parseInt(nameEntered);
        for (int i = 0; i < playerNames.length; i++) {
          if (i == x - 1) {
            playerIndex = i;
          }
        }
      } else {
        for (int i = 0; i < playerNames.length; i++) {
          if (playerNames[i].equalsIgnoreCase(nameEntered)) {
            playerIndex = i;
          }
        }
      }

      if (playerIndex == -1) {
        System.out.print("Please enter a proper name in the list above: ");
      } else {
        checkingName = true;
      }
    }
    return playerIndex;
  }

  private static boolean CheckIfInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private static void displayPlayers(String[] playerNames, int[] playerWallets) {
    System.out.println();
    System.out.printf("%1s%1s%16s%1s%16s%1s\n", "#", "|", "  Player Names  ", "|", " Player Wallets ", "|");
    for (int i = 0; i < playerNames.length; i++) {
      System.out.printf("%1s%1s%16s%1s%16s%1s\n", "~", "|", "~~~~~~~~~~~~~~~~", "|", "~~~~~~~~~~~~~~~~", "|");
      System.out.printf("%1s%1s%16s%1s%16s%1s\n", i + 1, "|", playerNames[i], "|", playerWallets[i], "|");
    }

  }

  private static String[] getRaceHorses(String[] horses) {
    int x = (int) (Math.random() * 4) + 5;
    String[] raceHorses = new String[x];

    for (int i = 0; i < x; i++) {
      boolean isHorseInRace = false;
      while (!isHorseInRace) {
        int horseNum = (int) (Math.random() * horses.length);
        String theHorse = horses[horseNum];
        boolean inRace = alreadyInRace(theHorse, raceHorses);
        if (inRace == false) {
          raceHorses[i] = theHorse;
          isHorseInRace = true;
        }
      }
    }
    return raceHorses;
  }

  private static boolean alreadyInRace(String theHorse, String[] raceHorses) {

    for (int i = 0; i < raceHorses.length; i++) {
      if (theHorse.equalsIgnoreCase(raceHorses[i])) {
        return true;
      }
    }
    return false;
  }

  private static int[] getWallet() {
    int[] wallet = null;

    try {
      Scanner scanner = new Scanner(new File("input/playerData.dat"));
      int numPlayers = Integer.parseInt(scanner.nextLine());
      wallet = new int[numPlayers];

      for (int i = 0; i < numPlayers; i++) {
        String x = scanner.nextLine();
        String[] parts = x.split(" ");
        wallet[i] = Integer.parseInt(parts[1]);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return wallet;
  }

  private static String[] getNames() {
    String[] names = null;

    try {
      Scanner scanner = new Scanner(new File("Input/playerData.dat"));
      int numPlayers = Integer.parseInt(scanner.nextLine());
      names = new String[numPlayers];

      for (int i = 0; i < numPlayers; i++) {
        String x = scanner.nextLine();
        String[] parts = x.split(" ");
        names[i] = parts[0];
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return names;
  }

  private static void Conclusion() throws InterruptedException {
    Thread.sleep(2000);
    System.out.println("Thank you for playing! :)");
    Thread.sleep(1000);
    System.exit(1);
  }

}