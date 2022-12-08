import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assembler {
    String fileLine;
    String[] fileLines;
    Scanner scanner;
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
        if (fileLines[0] == "ADD");
    }
    public void commandChecker(String command){
        switch (command){
            case "ADD":
                ;;
            case "SUB":
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
