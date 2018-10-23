package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asType

import de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec
import de.pfke.squeeze.zlib.SerializerBuildException

class NotAllHaveIfaceSerializerSpec
  extends BaseSpec {
  "testing serializer for NotAllHaveIface type" when {
    "having a sub class extending this iface, but does not habe the typeForIface annot" should {
      "should throw an exception" in {
        an[SerializerBuildException] shouldBe thrownBy(BuildByReflection().build[NotAllHaveIface]())
      }
    }
  }
}
