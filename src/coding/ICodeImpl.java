package coding;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * The ICodeImpl class implements the encode and decode methods. The class has methods for
 * generating a symbol->code map given a string and symbol code or use the map given during the
 * creation of the object.
 */
public class ICodeImpl implements ICoding {

  private final Map<String, String> map;

  /**
   * The constructor of the class takes text and code symbols as parameters and generates a map and
   * instantiates the ICodeImpl class.
   *
   * @param text        the text for generating a map
   * @param codeSymbols the code symbols required for encoding
   */
  public ICodeImpl(String text, String codeSymbols) {
    if (text.isEmpty()) {
      throw new IllegalArgumentException("Text entered is empty");
    }
    if (codeSymbols.isEmpty() || codeSymbols.length() == 1) {
      throw new IllegalArgumentException("CodeSymbols entered is empty");
    }
    for (int i = 0; i < codeSymbols.length(); i++) {
      if (codeSymbols.charAt(i) == '\0') {
        throw new IllegalArgumentException("CodeSymbols entered is null");
      }
    }

    this.map = generateMapping(text, codeSymbols);
  }

  /**
   * The constructor of the class takes map as a parameter and instantiates the ICodeImpl class.
   *
   * @param map the map for symbol and code
   */
  public ICodeImpl(Map<String, String> map) {
    if (map.size() == 0) {
      throw new IllegalArgumentException("Map entered is empty");
    }
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (entry.getValue() == null || entry.getValue().isEmpty() || entry.getKey() == null
              || entry.getKey().isEmpty()) {
        throw new IllegalArgumentException("key or value is empty or null in the map");
      }
    }
    this.map = map;
  }


  @Override
  public String encode(String text) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("text to be encoded cannot be null or empty");
    }

    StringBuilder encode = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      String temp = Character.toString(text.charAt(i));
      String code = map.get(temp);
      if (code == null) {
        throw new IllegalArgumentException("key not in map");
      }
      encode.append(code);
    }
    return encode.toString();
  }

  @Override
  public String decode(String encodedString) {
    if (encodedString == null || encodedString.isEmpty()) {
      throw new IllegalArgumentException("text to be decoded cannot be null or empty");
    }

    INode root = new IntermediateNode();
    for (String leafValue : map.keySet()) {
      String coding = map.get(leafValue);
      root.treeBuilder(coding, coding.length(), leafValue);
    }
    INode reference = root;
    StringBuilder decoded = new StringBuilder();
    for (int i = 0; i < encodedString.length(); i++) {
      String code = Character.toString(encodedString.charAt(i));
      if (!reference.getMap().containsKey(code)) {
        throw new IllegalArgumentException("encoding not in format");
      }
      if (reference.getMap().get(code).getCharacter() != null) {
        decoded.append(reference.getMap().get(code).getCharacter());
        reference = root;
      } else {
        reference = reference.getMap().get(code);
      }
    }
    if (reference != root) {
      throw new IllegalArgumentException("malformed encoded string");
    }

    return decoded.toString();
  }

  @Override
  public Map<String, String> getPrefixEncoding() {
    return this.map;
  }

  /**
   * Generate prefix encoding given text and code symbols.
   *
   * @param text        the text for generating prefix encoding
   * @param codeSymbols the code symbols that can be used
   * @return the prefix encoding
   */
  public static Map<String, String> generateMapping(String text, String codeSymbols) {
    Map<Integer, String> encodingMap = new HashMap<>();
    for (int i = 0; i < text.length(); i++) {
      encodingMap.put(charToAscii(text.charAt(i)), "");
    }
    PriorityQueue<Compare> queue = loadPriorityQueue(freqCharMap(text));

    if (queue.size() == 1) {
      encodingMap.put(charToAscii(queue.poll().getCombineCharacter().charAt(0)),
              Character.toString(codeSymbols.charAt(0)));
    } else {
      while (queue.size() != 1) {
        int totalFrequency = 0;
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < codeSymbols.length(); i++) {
          if (queue.peek() == null) {
            break;
          } else {
            Compare obj = queue.poll();
            String pop = obj.getCombineCharacter();
            String code = Character.toString(codeSymbols.charAt(i));
            for (int j = 0; j < pop.length(); j++) {
              int ascii = charToAscii(pop.charAt(j));
              encodingMap.put(ascii, code + encodingMap.get(ascii));
            }
            newString.append(pop);
            totalFrequency = totalFrequency + obj.getCount();
          }
        }
        Compare compare = new Compare(newString.toString(), totalFrequency);
        queue.add(compare);
      }
    }
    return asciiToMap(encodingMap);
  }


  private static PriorityQueue<Compare> loadPriorityQueue(Map<Integer, Integer> freqCharMap) {
    PriorityQueue<Compare> queue = new PriorityQueue<>();
    for (Integer ascii : freqCharMap.keySet()) {
      String s = asciiToChar(ascii);
      Compare compare = new Compare(s, freqCharMap.get(ascii));
      queue.add(compare);
    }
    return queue;
  }


  private static Map<Integer, Integer> freqCharMap(String text) {
    Map<Integer, Integer> freqCharMap = new HashMap<>();
    for (int i = 0; i < text.length(); i++) {
      char character = text.charAt(i);
      if (!freqCharMap.containsKey(((int) character))) {
        int count = (int) text.chars().filter(ch -> ch == character).count();
        freqCharMap.put((int) character, count);
      }
    }
    return freqCharMap;
  }

  private static int charToAscii(char c) {
    return c;
  }

  private static String asciiToChar(int ascii) {
    return Character.toString(ascii);
  }

  private static Map<String, String> asciiToMap(Map<Integer, String> encodingMap) {
    Map<String, String> asciiMap = new HashMap<>();
    for (Integer ascii : encodingMap.keySet()) {
      asciiMap.put(asciiToChar(ascii), encodingMap.get(ascii));
    }
    return asciiMap;
  }
}
