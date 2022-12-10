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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void lineSplitter(String lineToSplit) throws IOException {
        fileLines = lineToSplit.split("[ ,./]+");
        switch (commandChecker(fileLines[0])){
            case 0 -> convertCommandToBinaryFirstFive(fileLines);
            case 1 -> {
                ConvertCommandToBinaryImm(fileLines[0]);
                binaryNumberString += RegToBinary(Integer.parseInt(fileLines[1].substring(1)));
                binaryNumberString += RegToBinary(Integer.parseInt(fileLines[2].substring(1)));
                binaryNumberString += ConvertImmToBinary(fileLines[3]);
                System.out.print(binaryNumberString + "---");
                bitsToHexConversion(binaryNumberString);
            }
            case 2 -> convertLDorST(fileLines);
            case 3 -> convertJump(fileLines);
            case 4 -> convertPushorPop(fileLines);
            case 5 -> {
                convertCommandToBnBne(fileLines[0]);
                binaryNumberString += RegToBinary(Integer.parseInt(fileLines[1].substring(1)));
                binaryNumberString += RegToBinary(Integer.parseInt(fileLines[2].substring(1)));
                binaryNumberString += ConvertAdrToBinary(fileLines[3]);
                System.out.print(binaryNumberString + "---");
                bitsToHexConversion(binaryNumberString);
            }

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
    public void convertCommandToBnBne(String command) {
        switch (command) {
            case "BE" -> binaryNumberString = "01111";
            case "BNE" -> binaryNumberString = "10000";
        }
    }
    public String ConvertAdrToBinary(String addr) {
        String s = Integer.toBinaryString(Integer.parseInt(addr));
        if (Integer.parseInt(addr) < 0) {
            return s.substring(s.length() - 7);
        } else {
            while (s.length() < 7) {
                s = "0" + s;
            }
            return s;
        }
    }
    public void convertJump(String[] addr) throws IOException {
        binaryNumberString = "01100";
        String binaryAddr = addrToBinaryJump(addr[1]);
        binaryNumberString = binaryNumberString + binaryAddr;
        bitsToHexConversion(binaryNumberString);
    }
    public void convertPushorPop(String[] list) throws IOException {
        switch (list[0]) {
            case "PUSH" -> binaryNumberString = "01101";
            case "POP" -> binaryNumberString = "01110";
        }
        removedR = list[1].replace("R", "");
        int removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt) + "00000000000";
        bitsToHexConversion(binaryNumberString);
    }
    public void convertLDorST(String[] list) throws IOException {
        switch (list[0]){
            case "LD" -> binaryNumberString = "01010";
            case "ST" -> binaryNumberString = "01011";
        }
        removedR = list[1].replace("R", "");
        int removedRInt = Integer.parseInt(removedR);
        binaryNumberString = binaryNumberString + RegToBinary(removedRInt);
        String addrString = list[2];
        String binaryAddr = addrToBinaryLDST(addrString);
        binaryNumberString = binaryNumberString + binaryAddr;
        bitsToHexConversion(binaryNumberString);

    }
    public String addrToBinaryJump(String number){
        StringBuilder s = new StringBuilder(Integer.toBinaryString(Integer.parseInt(number)));
        if (Integer.parseInt(number) < 0){
            return s.substring(s.length() - 14);
        }
        else {
            while (s.length() < 14){
                s.insert(0, "0");
            }
            return s.toString();
        }
    }
    public void convertCommandToBinaryFirstFive(String[] list) throws IOException {
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
        bitsToHexConversion(binaryNumberString);
    }
    public String ConvertImmToBinary(String imm) { // sadece -64 ve 63 arası
        String s = Integer.toBinaryString(Integer.parseInt(imm));
        if (Integer.parseInt(imm) < 0) {
            return s.substring(s.length() - 7);
        } else {
            switch (s.length()) {
                case 1:
                    return "000000" + s;
                case 2:
                    return "00000" + s;
                case 3:
                    return "0000" + s;
                case 4:
                    return "000" + s;
                case 5:
                    return "00" + s;
                case 6:
                    return "0" + s;
                default:
                    return s;
            }
        }
    }
    public void ConvertCommandToBinaryImm(String command) {
        switch (command) {
            case "SUBI" -> binaryNumberString = "00110";
            case "ADDI" -> binaryNumberString = "00101";
            case "ANDI" -> binaryNumberString = "00111";
            case "ORI" -> binaryNumberString = "01000";
            case "XORI" -> binaryNumberString = "01001";
        }
    }
    public String addrToBinaryLDST(String number){
        StringBuilder s = new StringBuilder(Integer.toBinaryString(Integer.parseInt(number)));
        if (Integer.parseInt(number) < 0){
             return s.substring(s.length() - 10);
        }
        else {
            while (s.length() < 10){
                s.insert(0, "0");
            }
            return s.toString();
        }
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
    public void bitsToHexConversion(String bitStream) throws IOException {
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
        FileWriter fw = new FileWriter("output.txt", true);
        BufferedWriter out = new BufferedWriter(fw);
        out.write(hexString);
        out.newLine();
        out.close();
    }
    public static void main(String[] args) {
        String fileName = "test.txt";
        Assembler assembler = new Assembler();
        assembler.fileLineReader(fileName);
    }



}
