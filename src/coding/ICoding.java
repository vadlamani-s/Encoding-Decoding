package coding;

import java.util.Map;

/**
 * The interface Coding is used for encoding and decoding of a String using trees. The interface has
 * two methods one for encoding where in the string to be encoded is taken as an argument and
 * decoding method where in a encoded message is taken as a parameter.
 */
public interface ICoding {

  /**
   * Encode method is used for encoding a text which is taken as a parameter.
   *
   * @param text the text to be encoded
   * @return the encoded string
   */
  String encode(String text);

  /**
   * Decode method is used for decoding an encoded string.
   *
   * @param encodedString the encodedString
   * @return the decoded string
   */
  String decode(String encodedString);

  /**
   * Gets prefix encoding or symbol->code map.
   *
   * @return the prefix encoding
   */
  Map<String, String> getPrefixEncoding();

}
