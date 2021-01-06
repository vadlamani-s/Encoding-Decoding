import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import coding.ICodeImpl;
import coding.ICoding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * A JUnit test class for the ICodeImplTest class.
 */
public class ICodeImplTest {

  ICoding iCoding;

  @Before
  public void setUp() throws Exception {
    String text = "she sells sea shells";
    String codeSymbols = "01";
    iCoding = new ICodeImpl(text, codeSymbols);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail() {
    iCoding = new ICodeImpl("", "01");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail1() {
    iCoding = new ICodeImpl("she", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail2() {
    iCoding = new ICodeImpl("she", "0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail5() {
    iCoding = new ICodeImpl("she", "0\0");
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail3() {
    Map<String, String> map = new HashMap<>();
    iCoding = new ICodeImpl(map);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail4() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "001");
    map.put("b", null);
    iCoding = new ICodeImpl(map);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTestFail6() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "001");
    map.put(null, "011");
    iCoding = new ICodeImpl(map);
  }


  @Test
  public void encodeTest() {
    Map<String, String> map = iCoding.getPrefixEncoding();
    String toBeEncoded = "she";
    String encoded = "";
    for (int i = 0; i < toBeEncoded.length(); i++) {
      encoded += map.get(Character.toString(toBeEncoded.charAt(i)));
    }
    String encodedActual = iCoding.encode("she");
    assertEquals(encoded, encodedActual);
  }

  @Test
  public void mapWithOneCharacter() {
    String text = "sssss";
    String codeSymbols = "01";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    Map<String, String> map = iCoding1.getPrefixEncoding();
    assertEquals(map.size(), 1);
  }

  @Test
  public void encodeTestOneCharacter() {
    String text = "sssss";
    String codeSymbols = "01";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    Map<String, String> map = iCoding1.getPrefixEncoding();
    String toBeEncoded = "s";
    String encoded = "";
    for (int i = 0; i < toBeEncoded.length(); i++) {
      encoded += map.get(Character.toString(toBeEncoded.charAt(i)));
    }
    String encodedActual = iCoding1.encode(toBeEncoded);
    assertEquals(encoded, encodedActual);
  }


  @Test(expected = IllegalArgumentException.class)
  public void encodeCharacterNotInMap() {
    Map<String, String> map = iCoding.getPrefixEncoding();
    String toBeEncoded = "apple";
    String encoded = "";
    for (int i = 0; i < toBeEncoded.length(); i++) {
      encoded += map.get(Character.toString(toBeEncoded.charAt(i)));
    }
    String encodedActual = iCoding.encode("apple");
    assertEquals(encoded, encodedActual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeStringNull() {
    Map<String, String> map = iCoding.getPrefixEncoding();
    String toBeEncoded = null;
    String encoded = "";
    String encodedActual = iCoding.encode(toBeEncoded);
    System.out.println(encodedActual);
    assertEquals(encoded, encodedActual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeStringEmpty() {
    Map<String, String> map = iCoding.getPrefixEncoding();
    String toBeEncoded = "";
    String encoded = "";
    String encodedActual = iCoding.encode(toBeEncoded);
    System.out.println(encodedActual);
    assertEquals(encoded, encodedActual);
  }

  @Test
  public void encodeCaseSensitiveTest() {
    Map<String, String> map = iCoding.getPrefixEncoding();
    String toBeEncoded = "She";
    String encoded = "";
    for (int i = 0; i < toBeEncoded.length(); i++) {
      encoded += map.get(Character.toString(toBeEncoded.charAt(i)));
    }
    String encodedActual = iCoding.encode("she");
    assertNotEquals(encoded, encodedActual);
  }

  @Test
  public void decodeTest() {
    String text = "she";
    String encoded = iCoding.encode(text);
    assertEquals(text, iCoding.decode(encoded));
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeDifferentEncoding() {
    String text = "she sells sea shells";
    String codeSymbols = "01234567";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    String toBeEncoded = "she";
    String encoded = iCoding1.encode(toBeEncoded);
    assertEquals(toBeEncoded, iCoding.decode(encoded));
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeCharacterNotInMap() {
    String text = "she";
    String codeSymbols = "01234567";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    assertEquals(text, iCoding1.decode("12345"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeException() {
    assertEquals("a", iCoding.decode("10100"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeException1() {
    assertEquals("a", iCoding.decode(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeException2() {
    assertEquals("a", iCoding.decode(null));
  }

  @Test
  public void punctuationTest() {
    String text = "she shells __?><11234 , || ?? ()!@#$%^&**(,,,,,.....";
    String codeSymbols = "01234567";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    System.out.println(iCoding1.getPrefixEncoding());
    assertEquals(26, iCoding1.getPrefixEncoding().size());
  }

  @Test
  public void codeSymbolTest() {
    String text = "she shells __?><11234 , || ?? ()!@#$%^&**(,,,,,.....";
    String codeSymbols = "0123456789ABCDEFGHIJKL";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    Map<String, String> map = iCoding1.getPrefixEncoding();
    String toBeEncoded = "she shells";
    String encoded = "";
    for (int i = 0; i < toBeEncoded.length(); i++) {
      encoded += map.get(Character.toString(toBeEncoded.charAt(i)));
    }
    assertEquals(encoded, iCoding1.encode(toBeEncoded));
  }

  @Test
  public void punctuationTest1() {
    String text = "she shells __?><11234 , || ?? ()!@#$%^&**(,,,,,.....";
    String codeSymbols = "0123456789ABCDEFGHIJKL";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    String toBeEncoded = "<11234 , || ??";
    String encoded = iCoding1.encode(toBeEncoded);
    assertEquals(toBeEncoded, iCoding1.decode(encoded));
  }

  @Test
  public void PrefixEncodingTest() {
    String map = "{a=100,b=00,c=01,d=11,e=101}";
    Map<String, String> symbolCode = new HashMap<>();
    map = map.replace("{", "");
    map = map.replace("}", "");
    String[] parts = map.split(",");
    for (String part : parts) {
      String[] keyValue = part.split("=");
      symbolCode.put(keyValue[0], keyValue[1]);
    }
    ICoding iCoding = new ICodeImpl(symbolCode);
    String encoded = iCoding.encode("abc");
    assertEquals("abc", iCoding.decode(encoded));
  }

  @Test
  public void punctuationTest2() {
    String text = "she shells __?><11234 , || ?? ()!@#$%^&**(,,,,,.....";
    String codeSymbols = "0123456789@#$%$^&";
    ICoding iCoding1 = new ICodeImpl(text, codeSymbols);
    String toBeEncoded = "<11234 , || ??";
    String encoded = iCoding1.encode(toBeEncoded);
    assertEquals(toBeEncoded, iCoding1.decode(encoded));
  }


}