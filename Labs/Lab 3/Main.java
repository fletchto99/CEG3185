public class Main {
    public static void main(String... args) {

        byte verticalMax = 9;
        byte horizontalMax = 19;

        //Sample 1
        System.out.println();
        System.out.println();
        for (int i = 1; i <= verticalMax; i++) {
            for (int k = 1; k <= i; k++) {
                System.out.print(i);
            }
            System.out.println();
        }

        //Sample 2
        System.out.println();
        System.out.println();
        for (int i = 1; i <= verticalMax; i++) {
            for (int k = 1; k <= horizontalMax; k++) {
                System.out.print(i);
            }
            System.out.println();
        }

        //Part 1
        System.out.println();
        System.out.println();
        for (int i = 1; i <= verticalMax; i++) {
            for(int j=0; j < horizontalMax; j++) {
                if ((i-1)%8==0) {
                    System.out.print(i);
                } else {
                    System.out.print(j % 9 == 0 ? i : ".");
                }
            }
            System.out.println();
        }

        //Part 2
        System.out.println();
        System.out.println();
        for (int i = 1; i <= verticalMax; i++) {
            for(int j=0; j < horizontalMax; j++) {
                if ((i-1) % 4 == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(j % 9 == 0 ? i : ".");
                }
            }
            System.out.println();
        }

        //Part 3
        System.out.println();
        System.out.println();
        for (int i = 0; i < verticalMax; i++) {
            for(int j=0; j < horizontalMax; j++) {
                if (i % 4 == 0) {
                    System.out.print("1");
                } else {
                    if (j % 9 == 0) {
                        System.out.print("1");
                    } else if (j % 3 == 0) {
                        System.out.print("x");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }

        //Part 1
        System.out.println();
        System.out.println();
        for (int i = 1; i <= verticalMax; i++) {

            int numNumbers = i * 2 - 1;
            int numDots = (horizontalMax - numNumbers) / 2;

            System.out.println(
                    new String(new char[numDots]).replace("\0", ".") +
                    new String(new char[numNumbers]).replace("\0", Integer.toString(i)) +
                    new String(new char[numDots]).replace("\0", ".")
            );
        }
    }
}