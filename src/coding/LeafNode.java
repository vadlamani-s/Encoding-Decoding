package coding;

import java.util.Map;

/**
 * The leaf node class represents the leaf node of the prefix tree. The leaf node keeps track of
 * each of the characters in a string.
 */
class LeafNode implements INode {

  private final String character;

  /**
   * The constructor is used for instantiating the character of a string.
   *
   * @param character the character of the string.
   */
  public LeafNode(String character) {
    this.character = character;
  }

  @Override
  public String getCharacter() {
    return character;
  }

  @Override
  public void treeBuilder(String encode, int lenOfEncoding, String symbol) {
    throw new IllegalArgumentException("This method should not be called");
  }

  @Override
  public Map<String, INode> getMap() {
    throw new IllegalArgumentException("This method should not be called");
  }

}
