import java.io.*;
import java.util.Scanner;
import java.util.

public class Assembler {
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
            case 0 -> {
                //TODO: Do Something
            }
            case 1 -> {
                //TODO: Burak burdan devam et.
            }
        }
        if (commandChecker(fileLines[0]) == 0) {
            ConvertCommandToBinary(fileLines);
        }
        if (commandChecker(fileLines[0]) == 1){

        }
    }
    public int commandChecker(String command){
        return switch (command) {
            case "ADD", "SUB", "AND", "OR", "XOR" -> 0;
            case "ADDI", "SUBI", "ANDI", "ORI", "XORI" -> 1;
            default -> -1;
        };
    }
    public void ConvertCommandToBinary(String[] list){
        switch(list[0]){
            case "SUB":
                binaryNumberString = "00000";
                break;
            case "ADD":
                binaryNumberString = "00001";
                break;
            case "AND":
                binaryNumberString = "00010";
                break;
            case "OR":
                binaryNumberString = "00011";
                break;
            case "XOR":
                binaryNumberString = "00100";
                break;
        }
    }
    public static void main(String[] args) {
        String fileName = "test.txt";
        String test = "ADD R3,R5,R7";
        String[] test2;
        Assembler assembler = new Assembler();
        assembler.fileLineReader(fileName);
    }



}
