package de.pfke.squeeze.zlib.crypto

import de.pfke.squeeze.zlib.crypto
import de.pfke.squeeze.zlib.crypto.CipherAlgorithm.CipherAlgorithm

object CipherAlgorithm
  extends Enumeration {
  type CipherAlgorithm = Value

  val AES_CBC_NoPadding: crypto.CipherAlgorithm.Value = Value("AES/CBC/NoPadding")
  val AES_CBC_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("AES/CBC/PKCS5Padding")
  val AES_ECB_NoPadding: crypto.CipherAlgorithm.Value = Value("AES/ECB/NoPadding")
  val AES_ECB_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("AES/ECB/PKCS5Padding")
  val DES_CBC_NoPadding: crypto.CipherAlgorithm.Value = Value("DES/CBC/NoPadding")
  val DES_CBC_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("DES/CBC/PKCS5Padding")
  val DES_ECB_NoPadding: crypto.CipherAlgorithm.Value = Value("DES/ECB/NoPadding")
  val DES_ECB_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("DES/ECB/PKCS5Padding")
  val DESede_CBC_NoPadding: crypto.CipherAlgorithm.Value = Value("DESede/CBC/NoPadding")
  val DESede_CBC_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("DESede/CBC/PKCS5Padding")
  val DESede_ECB_NoPadding: crypto.CipherAlgorithm.Value = Value("DESede/ECB/NoPadding")
  val DESede_ECB_PKCS5Padding: crypto.CipherAlgorithm.Value = Value("DESede/ECB/PKCS5Padding")
  val RSA_ECB_PKCS1Padding: crypto.CipherAlgorithm.Value = Value("RSA/ECB/PKCS1Padding")
  val RSA_ECB_OAEPWithSHA_1AndMGF1Padding: crypto.CipherAlgorithm.Value = Value("RSA/ECB/OAEPWithSHA-1AndMGF1Padding")
  val RSA_ECB_OAEPWithSHA_256AndMGF1Padding: crypto.CipherAlgorithm.Value = Value("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
}

object CipherHandler {

}

class CipherHandler(
  algorithm: CipherAlgorithm
) {
  def decrypt(): Unit = {

  }

  def encrypt(): Unit = {

  }
}
