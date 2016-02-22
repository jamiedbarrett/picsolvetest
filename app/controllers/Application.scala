package controllers

import model.TodoItemData
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.Future

class Application extends Controller {

  //Just use a form for now
  val userForm = Form(
    mapping(
      "id" -> ignored(0l),
      "priority" -> number(1,5),
      "description" -> text,
      "isdone" -> ignored(false)
    )(TodoItemData.apply)(TodoItemData.unapply)
  )

  def createTodoItem() = Action.async { request =>
    userForm.bindFromRequest.fold(
      error => Future.successful(BadRequest),
      formData => Future {
        //Save to dao here
        Created
      }
    )
  }

  def listAllTodoItems = Action.async {
    Future{???}
  }

}