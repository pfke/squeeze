package de.pfke.squeeze.testing

object StringHelper {
  /**
   * Append string with some noise.
   */
  def appendWithNoise(content: String): String = new StringBuilder()
    .append(content)
    .append(RandomHelper.rndString(1024))
    .result()

  /**
   * Prepend string with some noise.
   */
  def prependWithNoise(content: String): String = new StringBuilder()
    .append(RandomHelper.rndString(1024))
    .append(content)
    .result()

  /**
   * Surround a string with random noise strings.
   */
  def surroundWithNoise(content: String): String = new StringBuilder()
    .append(RandomHelper.rndString(1024))
    .append(content)
    .append(RandomHelper.rndString(1024))
    .result()
}
