package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex.asType

import de.pfke.squeeze.serialize.SerializerBuildException
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.ByReflectionAssembler
import de.pfke.squeeze.squeezer.mocks.asType.NotAllHaveIface
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class NotAllHaveIfaceSerializerSpec
  extends BaseSpec {
  "testing serializer for NotAllHaveIface type" when {
    "having a sub class extending this iface, but does not habe the typeForIface annot" should {
      "should throw an exception" in {
        an[SerializerBuildException] shouldBe thrownBy(ByReflectionAssembler().assemble[NotAllHaveIface]())
      }
    }
  }
}
