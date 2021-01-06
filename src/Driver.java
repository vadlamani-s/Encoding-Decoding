import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import coding.ICodeImpl;
import coding.ICoding;


/**
 * The driver class is used to generate encoding for a string passed as a parameter and decoding for
 * an encoded string. The class can read and write from file or console both the encoded and decoded
 * messages.
 */
public class Driver {

  static final String path = Paths.get("").toAbsolutePath().toString() + "\\" + "res\\";

  private static Map<String, String> mapBuilder(String map) {
    Map<String, String> symbolCode = new HashMap<>();
    map = map.replace("{", "");
    map = map.replace("}", "");
    String[] parts = map.split(", ");
    for (String part : parts) {
      String[] keyValue = part.split("=");
      symbolCode.put(keyValue[0], keyValue[1]);
    }
    return symbolCode;
  }

  private static String fileToString(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(Driver.path + fileName
              + ".txt"));
      System.out.println("String read from file Successfully !!!!");
      return reader.readLine();
    } catch (IOException e) {
      throw new IllegalStateException("Error opening the file");
    }
  }

  private static void stringToFile(String text, String fileName) throws IOException {
    FileWriter writer = null;
    try {
      writer = new FileWriter(Driver.path + fileName + ".txt");
      writer.write(text);
      writer.flush();
      System.out.println("String written to file Successfully !!!!");
    } catch (IOException e) {
      throw new IllegalStateException("Error opening the file");
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }

  private static void fileOrConsoleChoice(Scanner scanner, int choice, String encodedString)
          throws IOException {
    if (choice == 1) {
      System.out.println("Please enter the file name");
      String fileName = scanner.nextLine();
      Driver.stringToFile(encodedString, fileName);
    } else {
      System.out.println(encodedString);
    }

  }

  private static String mapBuildingChoice(Scanner scanner) {
    System.out.println("Please enter 1 if the string is taken from a file or else 2 for console");
    int choice1 = scanner.nextInt();
    scanner.nextLine();
    String codeString = "";
    switch (choice1) {
      case 1: {
        System.out.println("Please enter the file name");
        String fileName = scanner.next();
        codeString = fileToString(fileName);
        break;
      }
      case 2: {
        System.out.println("Please enter the string for building the prefix encoding map");
        codeString = scanner.nextLine();
        break;
      }
      default: {
        System.out.println("The entered option is incorrect");
      }
    }
    return codeString;
  }

  private static void fileOrConsole(Scanner scanner, Map<String, String> map) throws IOException {
    System.out.println("Enter 1 if prefix encoding has to be stored in a file else enter 2 for "
            + "console");
    int prefixChoice = scanner.nextInt();
    scanner.nextLine();
    if (prefixChoice <= 0 || prefixChoice >= 3) {
      return;
    }
    if (prefixChoice == 1) {
      System.out.println("Enter the file name");
      String fileName = scanner.next();
      stringToFile(map.toString(), fileName);
    } else {
      System.out.println(map.toString());
    }
  }


  /**
   * The main is used to run the driver class for encoding and decoding a string.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) throws IOException {

    ICoding iCoding = null;
    Scanner scanner = new Scanner(System.in);


    System.out.println("Please enter 1 if you have a prefix encoding map, 2 if you want to generate"
            + "a map for encoding and decoding, 3 for generating only a map");
    int choice = scanner.nextInt();
    scanner.nextLine();
    try {
      switch (choice) {
        case 1: {
          System.out.println("Enter 1 if prefix encoding to be read from file else 2 for console");
          int prefixCodingChoice = scanner.nextInt();
          scanner.nextLine();
          if (prefixCodingChoice <= 0 || prefixCodingChoice >= 3) {
            System.out.println("The entered option is incorrect");
            return;
          }

          if (prefixCodingChoice == 1) {
            System.out.println("Please enter the file name");
            String prefixFileName = scanner.next();
            String mapInString = fileToString(prefixFileName);
            Map<String, String> symbolCode = mapBuilder(mapInString);
            iCoding = new ICodeImpl(symbolCode);
          } else {

            System.out.println("Please enter the map");
            String mapInString = scanner.nextLine();
            if (mapInString.isEmpty()) {
              throw new IllegalArgumentException("prefix encoding map cannot be empty or null");
            }
            Map<String, String> symbolCode = mapBuilder(mapInString);
            iCoding = new ICodeImpl(symbolCode);
            fileOrConsole(scanner, iCoding.getPrefixEncoding());
          }
          break;
        }
        case 2: {
          String codeString = mapBuildingChoice(scanner);
          System.out.println("Please enter the string for coding Scheme");
          String codingScheme = scanner.next();
          iCoding = new ICodeImpl(codeString, codingScheme);
          fileOrConsole(scanner, iCoding.getPrefixEncoding());
          break;
        }
        case 3: {
          String codeString = mapBuildingChoice(scanner);
          System.out.println("Please enter the string for coding Scheme");
          String codingScheme = scanner.next();
          Map<String, String> map = ICodeImpl.generateMapping(codeString, codingScheme);
          fileOrConsole(scanner, map);
          return;
        }
        default:
          System.out.println("The entered option is incorrect");
          return;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }


    System.out.println("Enter 1 to encode or 2 for decode or anything else for exiting");
    int option = scanner.nextInt();
    if (option <= 0 || option >= 3) {
      return;
    }
    scanner.nextLine();
    while (option == 1 || option == 2) {

      if (option == 1) {

        System.out.println("Please enter 1 if the string to be encoded is taken from a file else"
                + " enter 2");
        choice = scanner.nextInt();
        scanner.nextLine();
        String toBeEncoded = "";
        switch (choice) {
          case 1: {
            System.out.println("Please enter the file name");
            String fileName = scanner.nextLine();
            toBeEncoded = Driver.fileToString(fileName);
            break;
          }
          case 2: {
            System.out.println("Please enter the string to be encoded");
            toBeEncoded = scanner.nextLine();
            break;
          }
          default: {
            System.out.println("The entered option is incorrect");
            return;
          }
        }

        System.out.println("Please enter 1 if the string encoded is to be saved to a file else"
                + " enter 2 for console");
        choice = scanner.nextInt();
        scanner.nextLine();
        String encodedString = iCoding.encode(toBeEncoded);
        if (!(choice <= 0 || choice >= 3)) {
          fileOrConsoleChoice(scanner, choice, encodedString);
        } else {
          return;
        }
      }

      if (option == 2) {
        System.out.println("Please enter 1 if the string to be decoded is taken from a file else"
                + " enter 2 for console");
        choice = scanner.nextInt();
        scanner.nextLine();
        String toBeDecoded = "";
        switch (choice) {
          case 1: {
            System.out.println("Please enter the file name");
            String fileName = scanner.nextLine();
            toBeDecoded = fileToString(fileName);
            break;
          }
          case 2: {
            System.out.println("Please enter the string to be decoded");
            toBeDecoded = scanner.nextLine();
            break;
          }
          default: {
            System.out.println("The entered option is incorrect");
            return;
          }
        }

        System.out.println("Please enter 1 if the string decoded is to be saved to a file else"
                + " enter 2 for console");
        int choice2 = scanner.nextInt();
        scanner.nextLine();
        String decodedString = iCoding.decode(toBeDecoded);
        if (!(choice2 <= 0 || choice2 >= 3)) {
          fileOrConsoleChoice(scanner, choice2, decodedString);
        } else {
          return;
        }
      }
      System.out.println("Enter 1 of encode or 2 for decode or anything else for exiting");
      option = scanner.nextInt();
      scanner.nextLine();
    }
  }
}
