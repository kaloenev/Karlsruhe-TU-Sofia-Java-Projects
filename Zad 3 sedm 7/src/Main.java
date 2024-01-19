import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        // алгоритъма не винаги смята до 2022, но можеш да видиш аутпута накрая и да извадиш числата по големи от 2022 от сбора
        ArrayList<Integer> neededNums = new ArrayList<>();
        ArrayList<Integer> repeatedNums = new ArrayList<>();
        ArrayList<Integer> repeatedInRepeated = new ArrayList<>();
        // тук пишеш стартово число
        neededNums.add(1);
        // тук пишеш числата които не можеш да ползваш спрямо стартовото число
        repeatedNums.add(5);
        repeatedNums.add(8);
        // в случай че не можеш да ползваш още числа, пример: при 8 не можеш да ползваш 1, 4, 12, 15
        // repeatedNums.add(12);
        // repeatedNums.add(15);

        // алгоритъма е следния: взимаш число и го пишеш някъде, след тва пишеш числата, които отпадат заради него другаде
        // след тва взимаш числото с най много повторения, което не можеш да ползваш, и взимаш неговите числа (тях можеш да ги ползваш)
        // пример: ползваш 1, не можеш 5 и 8, взимаш 9 (5 = 9 - 4) и 12 (5 = 12 - 7), не можеш да ползваш 2, 13, 16 заради 9
        // и 5, 8, 16, 19 заради 12, 5 сме го ползвали вече, следователно взимаме следващото число, което се повтаря най много пъти
        // Тва е 16, намираме кои числа можем да ползваме заради 16 и така нататък
        for (int i = 2; i <= 20000; i++) {
            int[] occurences = new int[3000];
            for (Integer j : repeatedNums) {
                occurences[j]++;
            }
            int nextNumber = i;
            int maxOccurences = 0;
            for (int j = 1; j <= 2022; j++) {
                if (occurences[j] > maxOccurences && !repeatedInRepeated.contains(j)) {
                    maxOccurences = occurences[j];
                    nextNumber = j;
                    repeatedInRepeated.add(nextNumber);
                }
            }
            if (nextNumber - 4 > 0 && !neededNums.contains(nextNumber - 4) && !repeatedNums.contains(nextNumber - 4)) {
                neededNums.add(nextNumber - 4);
                repeatedNums.add(nextNumber + 3);
                repeatedNums.add(nextNumber);
                if (nextNumber - 8 > 0) {
                    repeatedNums.add(nextNumber - 8);
                }
                if (nextNumber - 11 > 0) {
                    repeatedNums.add(nextNumber - 11);
                }
            }
            if (nextNumber - 7 > 0 && !neededNums.contains(nextNumber - 7) && !repeatedNums.contains(nextNumber - 7)) {
                neededNums.add(nextNumber - 7);
                repeatedNums.add(nextNumber - 3);
                repeatedNums.add(nextNumber);
                if (nextNumber - 14 > 0) {
                    repeatedNums.add(nextNumber - 14);
                }
                if (nextNumber - 11 > 0) {
                    repeatedNums.add(nextNumber - 11);
                }
            }
            if (!neededNums.contains(nextNumber + 4) && !repeatedNums.contains(nextNumber + 4)) {
                neededNums.add(nextNumber + 4);
                repeatedNums.add(nextNumber);
                repeatedNums.add(nextNumber + 8);
                repeatedNums.add(nextNumber + 11);
                if (nextNumber - 3 > 0) {
                    repeatedNums.add(nextNumber - 3);
                }
            }
            if (!neededNums.contains(nextNumber + 7) && !repeatedNums.contains(nextNumber + 7)) {
                neededNums.add(nextNumber + 7);
                repeatedNums.add(nextNumber);
                repeatedNums.add(nextNumber + 3);
                repeatedNums.add(nextNumber + 11);
                repeatedNums.add(nextNumber + 14);
            }
            if (nextNumber >= 2022) {
                break;
            }
        }
        System.out.println(neededNums.size());
        System.out.println(neededNums);

//
//
//// 1 2 3 отговарят на Б З Ч
//        Random random = new Random();
//        // думи с дължина 10 до 30
//        for (int i = 10; i < 30; i++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            // принти номер на думата
//            System.out.println(i + ".");
//            // прави стринг от числа от 1 до 3
//            for (int j = 0; j < i; j++) {
//                stringBuilder.append(1 + random.nextInt(3));
//            }
//            // принти първия и последния символ
//            System.out.println(stringBuilder.charAt(0));
//            System.out.println(stringBuilder.charAt(stringBuilder.length() - 1));
//            // докато не остане 1 символ в думата луупва
//            while (stringBuilder.length() > 1) {
//                // луупва през всички букви в актуалната дума
//                for (int j = 0; j < stringBuilder.length() - 1; j++) {
//                    // случая в който са еднакви две съседни букви
//                    if (stringBuilder.charAt(j) == stringBuilder.charAt(j + 1)) {
//                        stringBuilder.replace(j, j + 1, String.valueOf(stringBuilder.charAt(j)));
//                    }
//                    // останалите случаи
//                    else if (stringBuilder.charAt(j) == '2') {
//                        if (stringBuilder.charAt(j + 1) == '3') stringBuilder.replace(j, j + 1, "1");
//                        else if (stringBuilder.charAt(j + 1) == '1') stringBuilder.replace(j, j + 1, "3");
//                    } else if (stringBuilder.charAt(j) == '1') {
//                        if (stringBuilder.charAt(j + 1) == '3') stringBuilder.replace(j, j + 1, "2");
//                        else if (stringBuilder.charAt(j + 1) == '2') stringBuilder.replace(j, j + 1, "3");
//                    } else if (stringBuilder.charAt(j) == '3') {
//                        if (stringBuilder.charAt(j + 1) == '1') stringBuilder.replace(j, j + 1, "2");
//                        else if (stringBuilder.charAt(j + 1) == '2') stringBuilder.replace(j, j + 1, "1");
//                    }
//                    if (j == stringBuilder.length() - 2) stringBuilder.deleteCharAt(j + 1);
//                }
//            }
//            // буквата останала накрая
//            System.out.println(stringBuilder);
//            System.out.println();
//        }



//        int zavoiCounter = 0;
//        int counter;
//        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
//        for (int i = 0; i < 100000000; i++) {
//            int[][] board = new int[6][6];
//            int posX = 0;
//            int posY = 0;
//            board[0][0] = 1;
//            counter = 0;
//            boolean changedX = false;
//            boolean changedY = false;
//            // true = vertikalno, false = horizont
//            int posoka = 0;
//            ArrayList<Integer> currentPath = new ArrayList<>();
//            outer:
//            while (board[5][4] == 0) {
//                int[] isStuck = new int[4];
//                while (true) {
//                    // 0 - levo - 1 - desno - 2 - gore - 3 - dolu (iztok, zapad, sever, ug)
//                    int kurami = ThreadLocalRandom.current().nextInt(0, 4);
//                    if (kurami == 0) {
//                        if (posX > 0 && board[posY][posX - 1] == 0) {
//                            posX -= 1;
//                            board[posY][posX] = 1;
//                            changedX = true;
//                            currentPath.add(kurami);
//                            posoka = 2;
//                            break;
//                        }
//                    } else if (kurami == 1) {
//                        if (posX < 5 && board[posY][posX + 1] == 0) {
//                            posX += 1;
//                            board[posY][posX] = 1;
//                            changedX = true;
//                            currentPath.add(kurami);
//                            posoka = 2;
//                            break;
//                        }
//
//                    } else if (kurami == 2) {
//                        if (posY > 0 && board[posY - 1][posX] == 0) {
//                            posY -= 1;
//                            board[posY][posX] = 1;
//                            changedY = true;
//                            currentPath.add(kurami);
//                            posoka = 1;
//                            break;
//                        }
//
//                    } else if (kurami == 3) {
//                        if (posY < 5 && board[posY + 1][posX] == 0) {
//                            posY += 1;
//                            board[posY][posX] = 1;
//                            changedY = true;
//                            currentPath.add(kurami);
//                            posoka = 1;
//                            break;
//                        }
//                    }
//                    isStuck[kurami]++;
//                    if (isStuck[0] > 1 && isStuck[1] > 1 && isStuck[2] > 1 && isStuck[3] > 1) {
//                        break outer;
//                    }
//                }
//
//                if (changedX && changedY) {
//                    counter++;
//                    if (posoka == 1) {
//                        changedX = false;
//                    } else {
//                        changedY = false;
//                    }
//                }
//            }
//            boolean hasNoZeros = true;
//            for (int[] l : board) {
//                for (int k : l) {
//                    if (k == 0) {
//                        hasNoZeros = false;
//                        break;
//                    }
//                }
//            }
//            if (counter == 17 && !path.contains(currentPath) && hasNoZeros) {
//                path.add(currentPath);
//                zavoiCounter++;
//                System.out.println(currentPath);
//            }
//        }
//        System.out.println(zavoiCounter);
    }
}
