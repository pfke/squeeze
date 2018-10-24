package de.pfke.squeeze.zlib.crypto

import java.io.{InputStream, OutputStream}
import java.nio.ByteBuffer
import java.nio.channels.Channels
import javax.crypto.{Cipher, SecretKey}

import scala.language.implicitConversions

object AESTools {
  /**
    * Implicit convert from byte array into string.
    */
  implicit def toString(in: Array[Byte]): String = new String(in)

  private val CIPHER_METHOD = "AES/ECB/PKCS5Padding"

  /**
    * Calculated the maximum out put length.
    *
    * @param method either Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
    * @param key AES key
    * @param len len to de- or encrypt
    * @return max calculated length
    */
  private def calcOutputSize(
    method: Int,
    key: SecretKey,
    len: Int
  ) = {
    val aes = Cipher.getInstance(CIPHER_METHOD)

    aes.init(method, key)
    aes.getOutputSize(len)

  }
  private def calcDecryptOutputSize(key: SecretKey, len: Int) = calcOutputSize(Cipher.DECRYPT_MODE, key, len)
  private def calcEncryptOutputSize(key: SecretKey, len: Int) = calcOutputSize(Cipher.ENCRYPT_MODE, key, len)

  /**
    * Decrypt or encrypt the given byte buffer.
    *
    * @param method either Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
    * @param key AES key
    * @param in in byte buffer
    * @return decrypted or encrypted output
    */
  def doCipher(
    method: Int,
    key: SecretKey,
    in: ByteBuffer
  ): ByteBuffer = doCipher(method, key, in, ByteBuffer.allocateDirect(calcOutputSize(method, key, in.capacity())))

  /**
    * Decrypt or encrypt the given byte buffer.
    *
    * @param method either Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
    * @param key AES key
    * @param in in byte buffer
    * @param out out byte buffer
    * @return decrypted or encrypted output
    */
  def doCipher(
    method: Int,
    key: SecretKey,
    in: ByteBuffer,
    out: ByteBuffer
  ): ByteBuffer = {
    val aes = Cipher.getInstance(CIPHER_METHOD)

    aes.init(method, key)
    aes.doFinal(in, out)
    in.flip()
    out.flip()
    out
  }

  /**
    * Decrypt or encrypt the given byte array.
    *
    * @param method either Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
    * @param key AES key
    * @param cipherBytes in array
    * @return decrypted or encrypted output
    */
  def doCipher(
    method: Int,
    key: SecretKey,
    cipherBytes: Array[Byte]
  ): Array[Byte] = {
    val bb = doCipher(method, key, ByteBuffer.wrap(cipherBytes))

    val out = new Array[Byte](bb.remaining())
    bb.get(out)
    bb.flip()
    out
  }

  /**
    * Encrypt given clear text with the given key.
    *
    * @param key AES key
    * @param inStream VFS file to encrypt
    * @param outStream file to write encrypted content into
    */
  def doCipher(
    method: Int,
    key: SecretKey,
    inStream: InputStream,
    outStream: OutputStream
  ): Unit = {
    val inChannel = Channels.newChannel(inStream)
    val outChannel = Channels.newChannel(outStream)

    // copy bit wise
    val inBuffer = ByteBuffer.allocateDirect(1024 * 1024)

    while(inChannel.read(inBuffer) > 0) {
      outChannel.write(
        doCipher(method, key, inBuffer)
      )
    }

    inChannel.close()
    outChannel.close()
  }
}
