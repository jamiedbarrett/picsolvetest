package testhelp

import model.TodoItemData
import play.api.db.evolutions.Evolutions
import play.api.db.{Databases, Database}

object TestHelpers {

  val testTodoData1 = TodoItemData(0l, 5, "Buy mac and cheese for goldfish", false)
  val testTodoData2 = TodoItemData(0l, 2, "Serialise the contents of my fridge to JSON", false)
  val testTodoData3 = TodoItemData(0l, 3, "Cook mac and cheese for goldfish", false)

  def withTestDatabase[T](block: Database => T) = {

    Databases.withInMemory(
      name = "default",
      urlOptions = Map(
        "MODE" -> "PostgreSQL"
      ),
      config = Map(
        "logStatements" -> true
      )
    ) { database =>
          Evolutions.applyEvolutions(database)
          block(database)
          Evolutions.cleanupEvolutions(database)
    }
  }
}
