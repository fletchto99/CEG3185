import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Template {



    public static void main(String args[]) throws FileNotFoundException {

        if (args.length != 1) {
            System.out.println("Please pass a file path to the first argument.");
            return;
        }

        PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(args[0])));
        System.setOut(ps);



        //sample codes
        shape1(9);
        System.out.println();
        shape2(9);
        System.out.println();


        //open a file in write mode "c:\\lab3.txt"

        //append following  patterns in a file e.g. "c:\\lab3.txt" one by one

        shape3(9);
        System.out.println();
        shape4(9);
        System.out.println();
        shape5(9);
        System.out.println();
        shape6(9);

        ps.flush();
        ps.close();

        //read the pattern File e.g. "c://lab3.txt" and display to the screen


    }

    //sample pattern printing to the screen
    public static void shape1(int j) {
        for (int i = 1; i <= j; i++) {
            for (int k = 1; k <= i; k++) {
                System.out.print(i);
            }
            System.out.println();
        }

    }


    //sample pattern printing to the screen
    public static void shape2(int j) {
        for (int i = 1; i <= j; i++) {
            for (int k = 1; k <= (j * 2 + 1); k++) {
                System.out.print(i);
            }
            System.out.println();
        }

    }
	
	/*
	 * 
1111111111111111111
2........2........2
3........3........3
4........4........4
5........5........5
6........6........6
7........7........7
8........8........8
9999999999999999999
	
	* 
	*/


    //save the pattern in a file
    //need to handle the exceptions
    public static void shape3(int j) {
        for (int i = 1; i <= j; i++) {
            for(int k=0; k <= j*2; k++) {
                if ((i-1)%8==0) {
                    System.out.print(i);
                } else {
                    System.out.print(k % 9 == 0 ? i : ".");
                }
            }
            System.out.println();
        }
    }
	
	/*
	 * 
1111111111111111111
2........2........2
3........3........3
4........4........4
5555555555555555555
6........6........6
7........7........7
8........8........8
9999999999999999999
	
	* 
	*/

    //save the pattern in a file
    //need to handle the exceptions
    public static void shape4(int j) {
        for (int i = 1; i <= j; i++) {
            for(int k=0; k <= j*2; k++) {
                if ((i-1) % 4 == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(k % 9 == 0 ? i : ".");
                }
            }
            System.out.println();
        }
    }
	
	/*
	 * 
1111111111111111111
1..x..x..1..x..x..1
1..x..x..1..x..x..1
1..x..x..1..x..x..1
1111111111111111111
1..x..x..1..x..x..1
1..x..x..1..x..x..1
1..x..x..1..x..x..1
1111111111111111111
	
	* 
	*/

    //save the pattern in a file
    //need to handle the exceptions
    public static void shape5(int j) {
        for (int i = 0; i < j; i++) {
            for(int k=0; k <= j*2; k++) {
                if (i % 4 == 0) {
                    System.out.print("1");
                } else {
                    if (k % 9 == 0) {
                        System.out.print("1");
                    } else if (k % 3 == 0) {
                        System.out.print("x");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
    }

	/*
	 * 
.........1.........
........222........
.......33333.......
......4444444......
.....555555555.....
....66666666666....
...7777777777777...
..888888888888888..
.99999999999999999.
	
	* 
	*/

    //save the pattern in a file
    //need to handle the exceptions
    public static void shape6(int j) {
        for (int i = 1; i <= j; i++) {

            int numNumbers = i * 2 - 1;
            int numDots = ((j+1)*2 - numNumbers) / 2;

            System.out.println(
                    new String(new char[numDots]).replace("\0", ".") +
                    new String(new char[numNumbers]).replace("\0", Integer.toString(i)) +
                    new String(new char[numDots]).replace("\0", ".")
            );
        }
    }



}