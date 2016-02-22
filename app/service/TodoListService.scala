package service

import com.google.inject.{ImplementedBy, Inject}
import dao.TodoItemDao
import model.{TodoItemData, TodoItem}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[TodoItemServiceImpl])
trait TodoItemService {
  def listAllTodoItems: Future[List[TodoItem]]
  def createTodoItem(todoItemData: TodoItemData): Future[Either[Error, TodoItem]]
}

class TodoItemServiceImpl @Inject()(val todoItemDao: TodoItemDao) extends TodoItemService {

  def createTodoItem(todoItemData: TodoItemData): Future[Either[Error, TodoItem]] = {
    todoItemDao.create(todoItemData).map { item =>
      Right(
        new TodoItem {
          val todoItemData = item
        }
      )
    }.recover{ case _ => Left(new Error(s"Failed to insert $todoItemData")) }
  }

  def listAllTodoItems = {
    todoItemDao.listAll.map { list =>
      list.map { data =>
        new TodoItem {
          val todoItemData = data
        }
      }
    }.recover{ case _ => Nil }
  }
}


