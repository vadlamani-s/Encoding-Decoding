package coding;

import java.util.HashMap;
import java.util.Map;

/**
 * The Intermediate node class represents the intermediate node of the prefix tree. The intermediate
 * node are all nodes apart from the leaf node in a prefix tree. The node keeps track of the child
 * nodes the current node is pointing to.
 */
class IntermediateNode implements INode {

  private final Map<String, INode> childMap;

  /**
   * The constructor is used to instantiate the hashmap used for keeping track of all the child
   * nodes the prefix tree.
   */
  public IntermediateNode() {
    this.childMap = new HashMap<>();
  }

  @Override
  public Map<String, INode> getMap() {
    return childMap;
  }


  private INode add(String code, INode node) {
    if (!this.childMap.containsKey(code)) {
      childMap.put(code, node);
      return node;
    } else {
      return childMap.get(code);
    }
  }

  @Override
  public void treeBuilder(String encode, int lenOfEncoding, String symbol) {
    String code = Character.toString(encode.charAt(encode.length() - lenOfEncoding));
    if (lenOfEncoding <= 1) {
      INode nextNode = this.add(code, new LeafNode(symbol));
      return;
    }
    INode nextNode = this.add(code, new IntermediateNode());
    nextNode.treeBuilder(encode, lenOfEncoding - 1, symbol);
  }


  @Override
  public String getCharacter() {
    return null;
  }


}
