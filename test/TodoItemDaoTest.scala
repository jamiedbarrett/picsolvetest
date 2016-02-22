import dao.TodoItemDaoImpl
import model.TodoItemData

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global



class TodoItemDaoTest extends PlaySpec with MockitoSugar {

  import testhelp.TestHelpers._

  "TodoItemDao create" should {
    "create a todo item" in {
      withTestDatabase(database => {
        val testItemDao = new TodoItemDaoImpl(database)
        val creation = testItemDao.create(testTodoData1)

        val createdTodo = Await.result(creation, 5.seconds)
        createdTodo.id mustBe >(0l)
        createdTodo mustEqual testTodoData1.copy(id=createdTodo.id)
      })
    }
  }

  "TodoItemDao listAll" should {
    "list the created todo items" in {
      withTestDatabase(database => {
        val testItemDao = new TodoItemDaoImpl(database)
        val createdList = Future.sequence(List(testItemDao.create(testTodoData1),
                               testItemDao.create(testTodoData2),
                               testItemDao.create(testTodoData3)))

        Await.result(createdList, 5.seconds) must contain theSameElementsAs Await.result(testItemDao.listAll, 5.seconds)
      })
    }
  }
}








