package de.pfke.squeeze.zlib.data.byTypes

object OptionIncludes
  extends OptionIncludes

trait OptionIncludes {
  implicit def toOption[T](v: T): Option[T] = Option(v)

  implicit class evalOption[T](
    in: Option[T]
    ) {
    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").doOrDoNothing(println _ + "heiko")
     */
    @deprecated(message = "use 'matchToExec'", since = "3.0.81")
    def doOrDoNothing(
      op: (T) => Unit
      ) = {
      in match {
        case Some(x) => op(x)
        case None =>
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOrDefault(_ + "heiko", default = "nüscht")
     */
    @deprecated(message = "use 'matchTo'", since = "3.0.81")
    def gettyOrDefault[E](
      op: (T) => E,
      default: E
      ): E = {
      in match {
        case Some(x) => op(x)
        case None => default
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    @deprecated(message = "use 'matchToOption'", since = "3.0.81")
    def gettyOr[E](
      op: (T) => E
      ): Option[E] = {
      in match {
        case Some(x) => Some(op(x))
        case None => None
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    @deprecated(message = "use 'matchToException'", since = "3.0.81")
    def gettyOr[E](
      op: (T) => E,
      exp: Exception
      ): Option[E] = {
      in match {
        case Some(x) => Some(op(x))
        case None => throw exp
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    def getOrException(
      exp: Exception
      ): T = {
      in match {
        case Some(x) => x
        case None => throw exp
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    def matchTo[E](
      op: (T) => E,
      default: E
      ): E = {
      in match {
        case Some(x) => op(x)
        case None => default
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    def matchToException[E](
      op: (T) => E,
      exp: Exception
      ): E = {
      in match {
        case Some(x) => op(x)
        case None => throw exp
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    def matchToExec(
      op: (T) => Unit
      ) = {
      in match {
        case Some(x) => op(x)
        case None =>
      }
    }

    /**
     * Eval an option with a one-liner.
     *
     * Some("hello").gettyOr(_ + "heiko")
     */
    def matchToOption[E](
      op: (T) => E
      ): Option[E] = {
      in match {
        case Some(x) => Some(op(x))
        case None => None
      }
    }
  }

  implicit class stringOption(
    in: Option[String]
    ) {
    def orDefault: String = orDefault("???")
    def orDefault(default: String): String = in.getOrElse(default)
  }
}
