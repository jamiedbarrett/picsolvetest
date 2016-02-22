package controllers

import com.google.inject.Inject
import model.TodoItemData
import play.api.libs.json.Json
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import service.TodoItemService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class TodoList(result: List[TodoItemData])

class Application @Inject()(val todoItemService: TodoItemService) extends Controller {

  implicit val todoListFormat =  Json.format[TodoList]

  //Just use a form for now
  val userForm = Form(
    mapping(
      "id" -> ignored(0l),
      "priority" -> number(1,5),
      "description" -> text,
      "isdone" -> ignored(false)
    )(TodoItemData.apply)(TodoItemData.unapply)
  )

  def createTodoItem() = Action.async { implicit request =>
    System.out.println("Yeah")
    userForm.bindFromRequest.fold(
      error => Future.successful(BadRequest),
      formData =>
       todoItemService.createTodoItem(formData).map { creationResult =>
         creationResult.fold(
           error => InternalServerError,
           success => Created)
       }
    )
  }

  def listAllTodoItems = Action.async {
    todoItemService.listAllTodoItems.map { itemList =>
      val todoList = TodoList(itemList.map(_.todoItemData))
      Ok(Json.toJson(todoList))
    }
  }
}