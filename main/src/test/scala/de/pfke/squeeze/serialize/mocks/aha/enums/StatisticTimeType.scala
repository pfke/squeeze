package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object StatisticTimeType
  extends Enumeration {
  type StatisticTimeType = Value

  val _unknown       = Value(0)
  val _1_sec         = Value(1)
  val _5_sec         = Value(5)
  val _10_sec        = Value(10)
  val _20_sec        = Value(20)
  val _30_sec        = Value(30)
  val _1_min         = Value(60)
  val _2_min         = Value(120)
  val _5_min         = Value(300)
  val _10_min        = Value(600)
  val _15_min        = Value(900)
  val _20_min        = Value(1200)
  val _30_min        = Value(1800)
  val _1_hour        = Value(3600)
  val _3_hour        = Value(10800)
  val _6_hour        = Value(21600)
  val _12_hour       = Value(43200)
  val _1_day         = Value(86400)
  val _2_day         = Value(172800)
  val _1_week        = Value(604800)
  val _1_month       = Value(2678400)
  val _3_month       = Value(8035200)
  val _6_month       = Value(16070400)
  val _1_year        = Value(31536000)
  val _single_events = Value(86400000)
}

