package coding;

import java.util.Map;

/**
 * The interface Node represents two kinds of nodes. The interface contains all the methods needed
 * for the nodes. Each of the node are part of the tree.
 */
interface INode {

  /**
   * Gets the character in the node.
   *
   * @return the character
   */
  String getCharacter();

  /**
   * Tree builder method builds a tree method based on the symbol encoding map.
   *
   * @param encode        the encoded string
   * @param lenOfEncoding the lenOfEncoding is the length of the encoding
   * @param symbol        the symbol is the each character after decoding
   */
  void treeBuilder(String encode, int lenOfEncoding, String symbol);

  /**
   * Gets the symbol encoding map with key as the encoding character and value as INode type
   * object.
   *
   * @return the hash map
   */
  Map<String, INode> getMap();


}
