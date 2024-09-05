package controllers

import models.Rectangle
import org.apache.pekko.actor.{ActorSystem, Props}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import tasks.{ClearCacheArea, ClearCacheAreaActor}

import javax.inject.Inject
import scala.collection.mutable.ArrayBuffer

class RectangleController @Inject()(val controllerComponents: ControllerComponents
                                    //, clearCacheArea: ClearCacheArea
                                   ) extends BaseController {

  //кэш значений площади прямоугольника
  //ArrayBuffer[(сторона A, сторона B, площадь)]
  val cacheArea = ArrayBuffer[(BigDecimal, BigDecimal, BigDecimal)]()



  /*val actorSys = ActorSystem("ClearCache")
  val actorCacheClean = actorSys.actorOf(Props[ClearCacheAreaActor], "actorCacheClean")
  actorCacheClean ! cacheArea*/
  //new ClearCacheArea(actorSys, actorCacheClean)



  /**
   * Получение площади прямоугольника по указанным сторонам
   *
   * @param message текст-контекст
   * @param a       сторона A
   * @param b       сторона B
   */
  def area(message: String
           , a: BigDecimal
           , b: BigDecimal
          ): Action[AnyContent] = {

    /**
     * Получение площади прямоугольника с учётом кэша
     * @return Some[(nArea, bByCache)]
     */
    def getAreaWithCache(): Some[(BigDecimal, Boolean)] = {
      Some {
        cacheArea
          .find { case (nACache, nBCache, nAreaCache) =>
            (nACache == a && nBCache == b) ||
              (nBCache == a && nACache == b)
          }
          .map { case (nACache, nBCache, nAreaCache) => (nAreaCache, true) }
          .getOrElse {
            val nArea = Rectangle(a, b).getArea()
            cacheArea += ((a, b, nArea))

            (nArea, false)
          }
      }
    }

    Action { implicit request: Request[AnyContent] =>
      //буфер ошибок
      val bufErr = ArrayBuffer[String]()

      //Получение площади прямоугольника с указанными сторонами
      val dataArea = try {
        getAreaWithCache()
      } catch {
        //Ошибка
        case e: Throwable =>
          bufErr += e.getMessage
          None
      }

      dataArea match {
        case Some(data) =>
          val nArea = data._1
          val bByCache = data._2
          val sAdditionalText = {
            if (bByCache) "получено из кэша"
            else "посчитано"
          }

          //Результат
          Ok(views.html.getArea(s"$message ($sAdditionalText)", nArea))

        //Ошибка
        case None => BadRequest(s"Ошибка\n${bufErr.mkString("\n")}")
        case _ => BadRequest("Неизвестная ошибка.")
      }
    }
  }
}
