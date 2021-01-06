package coding;

/**
 * The Compare class is used for keeping track of the character and the frequency obtained from the
 * map. Each of the class instances is used to keep track of the frequency and the combined
 * character.
 */
class Compare implements Comparable<Compare> {

  private final String combineCharacter;
  private final int count;

  /**
   * The constructor instantiates the instances of the class, basically the frequency and the
   * characters.
   *
   * @param combineCharacter the combined character
   * @param count            the frequency of the character
   */
  public Compare(String combineCharacter, int count) {
    this.count = count;
    this.combineCharacter = combineCharacter;
  }


  @Override
  public int compareTo(Compare o) {
    if (this.count > o.count) {
      return 1;
    } else if (this.count < o.count) {
      return -1;
    } else {
      return this.combineCharacter.compareTo(o.combineCharacter);
    }
  }

  /**
   * Gets the character.
   *
   * @return the character
   */
  String getCombineCharacter() {
    return combineCharacter;
  }

  /**
   * Gets the frequency of the character..
   *
   * @return the frequency
   */
  int getCount() {
    return count;
  }

}
