package model

import anorm.{~, RowParser}
import anorm.SqlParser._


trait TodoItem {

  val todoItemData: TodoItemData

  // Id, unique identifier for each item. This should be created by the server on creation
  def id: Long = todoItemData.id

  // Integer from 1 to 5 with the level of priority, where higher number means higher priority
  def priority: Int = todoItemData.priority

  // Description of the task, it shouldn't be empty
  def description: String = todoItemData.description

  // Boolean to mark a task as it has been done
  def isDone: Boolean = todoItemData.isDone

}

case class TodoItemData(id: Long, priority: Int, description: String, isDone: Boolean)

object TodoItemData {

  def apply(priority: Int, description: String) =
    TodoItemData(0l, priority, description, isDone = false)

  val parser: RowParser[TodoItemData] = {
      get[Long]("id") ~
      get[Int]("priority") ~
      get[String]("description") ~
      get[Boolean]("isdone") map {
        case id ~
          priority ~
          description ~
          isDone =>
            TodoItemData(id, priority, description, isDone)
    }
  }
}