package imeigenerator;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


        public class ImeiGenerator {

            public static int checksum(String string) {
                int[] digits = string.chars().map(Character::getNumericValue).toArray();
                int oddSum = Arrays.stream(digits).reduce((x, y) -> x + y).orElse(0);
                int evenSum = Arrays.stream(digits).map(x -> x * 2).map(x -> x % 10 + x / 10).reduce((x, y) -> x + y).orElse(0);
                return (oddSum + evenSum) % 10;
            }

            public static int generate(String string) {
                int cksum = checksum(string + "0");
                return (10 - cksum) % 10;
            }

            public static String appendLuhn(String string) {
                return string + Integer.toString(generate(string));
            }

            public static String generateImei(String subjectImei) {
                StringBuilder imeiDigits = new StringBuilder();
                Random rand = new Random();

                for (int i = 0; i < 14; i++) {
                    // Find missing digits and complete with randos
                    String digit = subjectImei.length() > i ? String.valueOf(subjectImei.charAt(i)) : Integer.toString(rand.nextInt(10));

                    // Add digits to IMEI
                    imeiDigits.append(digit);
                }

                // Append the Luhn checksum and return
                return appendLuhn(imeiDigits.toString());
            }

            public static void generateList(String tacCode) {
                System.out.println("TAC:  " + tacCode);
                System.out.println("IMEIs:");

                for (int i = 0; i < 20; i++) {
                    System.out.println(generateImei(tacCode));
                }
            }

            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

                while (true) {
                    try {
                        String tac = scanner.nextLine();
                        generateList(tac);
                        System.out.println("---------------------");
                    } catch (NumberFormatException e) {
                        System.out.println("Not a valid TAC code");
                    }
                }
            }
        }


