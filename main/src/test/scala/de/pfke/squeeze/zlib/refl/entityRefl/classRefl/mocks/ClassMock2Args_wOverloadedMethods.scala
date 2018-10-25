package de.pfke.squeeze.zlib.refl.entityRefl.classRefl.mocks

class ClassMock2Args_wOverloadedMethods(
  val arg1: String,
  val arg2: Int
) {
  def method1() = "method1"
  def method2(var1: String) = s"method2(String): $var1"
  def method2(var1: String, var2: Int) = s"method2(String, Int): $var1, $var2"
  def method2(var1: Byte, var2: String, var3: Long) = s"method2(Byte, String): $var1, $var2"
}
