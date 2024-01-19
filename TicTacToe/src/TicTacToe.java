
public class TicTacToe {
    /**
     * HelferMethode mit den Gewinnbedingungen
     */
    public static boolean HelperMethod(int[][] k, int a) {

        for (int i = 0; i < 3; i++) {    //Schleife zum Sparen ein Paar Argumente

            if ((a >= 4) && // a >= 4, weil niemand vor seinem dritten X oder O gewinnen k
                    (((k[0][i] == k[1][i]) && (k[1][i] == k[2][i])) ||    //Spalten XXX oder OOO
                            ((k[i][0] == k[i][1]) && (k[i][1] == k[i][2])) || //Zeilen XXX oder OOO
                            ((k[0][0] == k[1][1]) && (k[1][1] == k[2][2])) || //Erster Diagonal
                            ((k[2][0] == k[1][1]) && (k[1][1] == k[0][2]))))  //Zweiter Diagonal
            {
                return true;
            }
        }
        return false;
    }
/**
 * Main Methode
*/
    public static void main(String[] args) {
        int [][] k = new int [3][3];    //Array, die den Spielboard repraesentiert
        int p;
        int q;
        int l = 0;
        String s = "P1 wins ";
        outer:
        for (int a=0; a<args.length; a++){ // a probiert welche Wert den args[i] hat
        for (int i=0; i<args.length; i++){ // i  repraesentiert den Zug
            if (Integer.parseInt(args[i]) == a) {
                l = i;
                p = a / 3;
                q = a % 3;
                if (i % 2 == 0) {
                    k [p][q] = 'X';
                }
                else{
                    s = "P2 wins ";
                    k [p][q] = 'O';
                }
                if (HelperMethod(k, i)){
                    System.out.println(s + i);
                    break outer;
                }
            }
            }
        }
        for (int a=0; a<args.length; a++){
            while (l < args.length){
                if (Integer.parseInt(args[l]) == a) {
                    p = a / 3;
                    q = a % 3;
                    if (l % 2 == 0) {
                        k [p][q] = 'X';
                    }
                    else{
                        k [p][q] = 'O';
                    }
                }
                l++;
                if (l == args.length) return;
            }
        }
        System.out.println("draw");
    }
}










        /*while (a <= 8) {   //Schleife fuer die Position des Integer Wertes im eingegebenen String.
                switch (Integer.parseInt(args[a])) {    //Erster Spieler (Zug)
                    case 0:
                        k[0][0] = 'X';
                        if (HelperMethod(k, a)) {   //Gewinnbedingungen ausprobieren
                            System.out.println(s + a);  //Ausgabe: P1 wins + Zug Nummer
                        }
                    case 1:
                        k[1][0] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 2:
                        k[2][0] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 3:
                        k[0][1] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 4:
                        k[1][1] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 5:
                        k[2][1] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 6:
                        k[0][2] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 7:
                        k[1][2] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                    case 8:
                        k[2][2] = 'X';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + a);
                        }
                }
            if (a < 8) { //Damit es nicht nach dem Wert vom 18 Character sucht, die in der Eingabe nicht existiert

                s = "P2 wins ";
                switch (Integer.parseInt(args[a+1])) {    //Zweiter Spieler (Zug)
                    case 0:
                        k[0][0] = 'O';
                        if (HelperMethod(k, a)) {   //Gewinnbedingungen ausprobieren
                            System.out.println(s + (a + 1));    //Ausgabe: P1 wins + Zug Nummer
                        }
                    case 1:
                        k[1][0] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 2:
                        k[2][0] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 3:
                        k[0][1] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 4:
                        k[1][1] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 5:
                        k[2][1] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 6:
                        k[0][2] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 7:
                        k[1][2] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                    case 8:
                        k[2][2] = 'O';
                        if (HelperMethod(k, a)) {
                            System.out.println(s + (a + 1));
                        }
                }
            }
            a++;
        }
         */

