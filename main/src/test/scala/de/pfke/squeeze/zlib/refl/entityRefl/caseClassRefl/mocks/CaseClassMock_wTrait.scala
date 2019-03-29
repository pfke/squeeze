package de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks

sealed trait trait_CaseClassMock_wTrait {
  def arg1: Option[String]
  def arg2: Int
  def arg3: Option[Boolean]
}

case class CaseClassMock_wTrait(
  arg1: Option[String] = None,
  arg2: Int = 0,
  arg3: Option[Boolean] = Some(true)
) extends trait_CaseClassMock_wTrait
