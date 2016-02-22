package dao

import anorm._
import com.google.inject.{ImplementedBy, Inject}
import model.TodoItemData
import play.api.db._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[TodoItemDaoImpl])
trait TodoItemDao {
  def create(todoItemData: TodoItemData): Future[TodoItemData]
  def listAll: Future[List[TodoItemData]]
}

class TodoItemDaoImpl @Inject()(@NamedDatabase("default") val db: Database) extends TodoItemDao with TodoItemSql {

  def create(todoItemData: TodoItemData): Future[TodoItemData] =
    Future {
      val generatedId: Option[Long] = db.withConnection { implicit connection =>
        insertTodoItem(todoItemData).executeInsert()
      }
      generatedId
        .map(identity => todoItemData.copy(id=identity))
        .getOrElse(throw new Exception)
    }

 def listAll: Future[List[TodoItemData]] =
    Future {
      db.withConnection { implicit connection =>
        selectTodoItems.as(TodoItemData.parser.*)
      }
    }
}

trait TodoItemSql {

  def insertTodoItem(todoItemData: TodoItemData) = {
    SQL(
      """
         | INSERT INTO todoitem (priority, description, isDone)
         | VALUES({priority}, {description}, {isdone})
         | """.stripMargin)
    .on('priority -> todoItemData.priority,
        'description -> todoItemData.description,
        'isdone -> todoItemData.isDone)
  }

  def selectTodoItems = {
    SQL("SELECT * FROM todoitem")
  }
}
