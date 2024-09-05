package models

import scala.collection.mutable.ArrayBuffer

/** Класс прямогольника */
class Rectangle(a: BigDecimal, b: BigDecimal) {

  /** Получение площади прямоугольника */
  def getArea(): BigDecimal = a * b

  /** Валидация прямоугольника */
  def valid(): Unit = {
    val bufErr = ArrayBuffer[String]()

    if (a <= 0) bufErr += "Сторона A должна быть > 0"
    if (b <= 0) bufErr += "Сторона B должна быть > 0"

    if (bufErr.nonEmpty) throw new Exception(s"Ошибка прямоугольника:\n${bufErr.mkString("\n")}")
  }
}

object Rectangle {
  def apply(a: BigDecimal, b: BigDecimal, bNeedValid: Boolean = true): Rectangle = {
    val rectangle = new Rectangle(a, b)
    if (bNeedValid) rectangle.valid()
    rectangle
  }
}
