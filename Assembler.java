import java.io.*;
import java.util.Scanner;

public class Assembler {
    String removedR;
    String fileLine;
    String[] fileLines;
    Scanner scanner;
    int binaryNumber;
    String binaryNumberString;
    public void fileLineReader(String fileName) {
        fileLine = "";
        try {
            scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                fileLine = scanner.nextLine();
                lineSplitter(fileLine);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void lineSplitter(String lineToSplit){
        fileLines = lineToSplit.split("[ ,./]+");
        switch (commandChecker(fileLines[0])){
            case 0 -> convertCommandToBinaryFirstFive(fileLines);
            case 1 -> {
                //TODO: Burak burdan devam et.
            }
            case 4 -> convertPushorPop(fileLines);

        }
    }
    public int commandChecker(String command){
        return switch (command) {
            case "ADD", "SUB", "AND", "OR", "XOR" -> 0;
            case "ADDI", "SUBI", "ANDI", "ORI", "XORI" -> 1;
            case "LD", "ST" -> 2;
            case "JUMP" -> 3;
            case "PUSH", "POP" -> 4;
            case "BE", "BNE" -> 5;
            default -> -1;
        };
    }
    public void convertPushorPop(String[] list){
        switch (list[0]) {
            case "PUSH" -> binaryNumberString = "01101";
            case "POP" -> binaryNumberString = "01110";
        }
        removedR = list[1].replace("R", "");
        int removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt) + "00000000000";
        bitsToHexConversion(binaryNumberString);
    }
    public void convertCommandToBinaryFirstFive(String[] list){
        switch (list[0]) {
            case "SUB" -> binaryNumberString = "00000";
            case "ADD" -> binaryNumberString = "00001";
            case "AND" -> binaryNumberString = "00010";
            case "OR" -> binaryNumberString = "00011";
            case "XOR" -> binaryNumberString = "00100";
        }
        removedR = list[1].replace("R", "");
        int removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt);
        removedR = list[2].replace("R", "");
        removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt);
        removedR = list[3].replace("R", "");
        removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt) + "000";
        System.out.println(binaryNumberString);
        bitsToHexConversion(binaryNumberString);
    }
   public String RegToBinary(int number){
       return switch (number){
           case 0 -> "0000";
           case 1 -> "0001";
           case 2 -> "0010";
           case 3 -> "0011";
           case 4 -> "0100";
           case 5 -> "0101";
           case 6 -> "0110";
           case 7 -> "0111";
           case 8 -> "1000";
           case 9 -> "1001";
           case 10 -> "1010";
           case 11 -> "1011";
           case 12 -> "1100";
           case 13 -> "1101";
           case 14 -> "1110";
           case 15 -> "1111";
           default -> throw new IllegalStateException("Unexpected value: " + number);
       };
    }
    public void bitsToHexConversion(String bitStream){
        int byteLength = 4;
        int bitStartPos = 0, bitPos = 0;
        String hexString = "";
        int sum = 0;
        // pad '0' to make input bit stream multiple of 4

        if(bitStream.length()%4 !=0){
            int tempCnt = 0;
            int tempBit = bitStream.length() % 4;
            while(tempCnt < (byteLength - tempBit)){
                bitStream = "0" + bitStream;
                tempCnt++;
            }
        }

        // Group 4 bits, and find Hex equivalent

        while(bitStartPos < bitStream.length()){
            while(bitPos < byteLength){
                sum = (int) (sum + Integer.parseInt("" + bitStream.charAt(bitStream.length()- bitStartPos -1)) * Math.pow(2, bitPos)) ;
                bitPos++;
                bitStartPos++;
            }
            if(sum < 10)
                hexString = Integer.toString(sum) + hexString;
            else
                hexString = (char) (sum + 55) + hexString;

            bitPos = 0;
            sum = 0;
        }
        System.out.println(hexString);
    }
    public static void main(String[] args) {
        String fileName = "test.txt";
        Assembler assembler = new Assembler();
        assembler.fileLineReader(fileName);

    }



}
