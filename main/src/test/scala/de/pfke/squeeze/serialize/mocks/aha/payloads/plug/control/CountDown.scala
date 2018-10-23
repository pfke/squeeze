package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.AhaFunction.AhaFunction
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control.ControlOperator.ControlOperator

trait CountDown
  extends BaseControl

case class CountDown_00022001(
        function: AhaFunction,
        operator: ControlOperator,
   functionValue: Int,
      timerValue: Int,
  resultFunction: AhaFunction,
     resultValue: Int
  )
  extends CountDown
