package lastpencil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //INITIAL NUMBER OF PENCILS
        int number = initialNumberOfPencils();
        //CHOOSE FIRST PLAYER
        String[] players = new String[] {"John", "Jack"};
        int index = chooseFirstPlayer(players);
        //PLAY GAME
        playGame(players, number, index);
    }

    public static int initialNumberOfPencils() {
        System.out.println("How many pencils would you like to use: ");
        int number = -1;
        boolean flag = true;
        while(flag) {
            try {
                String value = scanner.nextLine();
                if (value.isEmpty()) throw new NumberFormatException();

                number = Integer.parseInt(value);

                if (number == 0) {
                    System.out.println("The number of pencils should be positive");
                } else if (number < 0) {
                   throw new NumberFormatException();
                } else {
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("The number of pencils should be numeric");
            }
        }
        return number;
    }

    public static int chooseFirstPlayer(String[] players) {
        System.out.println("Who will be the first (John, Jack):");
        String name = "";
        int index = -1;
        while(index == -1) {
            name = scanner.next();
            index = findName(players, name);
            if (index == -1) {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }
        return index;
    }

    public static int findName(String[] players, String name) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(name)) return i;
        }
        return -1;
    }

    public static int nextPlayer(String[] players, int currentIndex) {
        int length = players.length;
        if (length - 1 == currentIndex) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        return currentIndex;
    }

    public static void printDashes(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print("|");
        }
    }

    public static void playGame(String[] players, int number, int index) {
        boolean flag = true; //TO ALLOW TO PRINT THE STATE
        while (number > 0) {
            boolean isValidMove = true;
            int pencils = -1;

            if (flag) printCurrentTurn(number, players, index);
            //BOT'S MOVE - JACK IS GOING TO BE ALWAYS THE INDEX 1
            if (index == 1) {
                pencils = howManyPencilShouldBotTake(number);
                System.out.println(pencils);
            } else {
                try {
                    String value = scanner.next();
                    if (value.isEmpty()) throw new NumberFormatException();

                    pencils = Integer.parseInt(value);
                    if (pencils < 1 || pencils > 3) {
                        throw new NumberFormatException();
                    } else if (pencils > number) {
                        System.out.println("Too many pencils were taken");
                        isValidMove = false;
                        flag = true;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    isValidMove = false;
                    flag = false;
                }
            }
            if (isValidMove) {
                number -= pencils;
                index = nextPlayer(players, index);
                flag = true;
            }
        }

        System.out.printf("%n%s won!", players[index]);
    }

    public static int howManyPencilShouldBotTake(int number) {
        if (number == 1) return 1;

        return switch (number % 4) {
            case 2 -> 1;
            case 3 -> 2;
            case 0 -> 3;
            default -> (int) ((3 - 1) * Math.random() + 1);
        };
    }

    public static void printCurrentTurn(int number, String[] players, int index) {
        printDashes(number);
        System.out.printf("%n%s's turn!%n", players[index]);
    }
}
