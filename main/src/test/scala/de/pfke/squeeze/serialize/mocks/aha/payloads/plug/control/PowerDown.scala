package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.AhaFunction.AhaFunction
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control.ControlOperator.ControlOperator

case class PowerDown(
        function: AhaFunction,
        operator: ControlOperator,
   functionValue: Int,
      timerValue: Int,
  resultFunction: AhaFunction,
     resultValue: Int
) extends BaseControl
