package de.pfke.squeeze.zlib.io.simpleFile.dataProvider

trait DataProvider {
  /**
    * Reads all data and return as string list.
    */
  def read(): List[String]

  /**
    * Write the given data
    * @param content is the data to be written
    * @param force write now or at your own timed schedule
    */
  def write(content: String, force: Boolean = false)
}
