# picsolvetest

An MVP that records todo items and display them in a list using Futures.

Execute `activator run` to launch the application.

To create a todo item `POST` request to `/todoitem/create` with body:
`priority` = Integer between 1 and 5
`description` = Some text

To view list of all todo items `GET` request to `/todoitem/list` to receive a response in JSON as follows:

```json
{
  "result": [
    {
      "id": 1,
      "priority": 3,
      "description": "Another thing to do",
      "isDone": false
    },
    {
      "id": 2,
      "priority": 5,
      "description": "Do some stuff",
      "isDone": false
    }
  ]
}
```
Note: updating items would be done as a `PUT` request since to a particular state can be idempotent.
