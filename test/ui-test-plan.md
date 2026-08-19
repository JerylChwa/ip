# UI Test Plan

This file lists the UI test cases used by the `test-ui` skill. Each test case
is a full chatbot session: a sequence of commands fed to the program's
standard input (one per line, always ending with `bye`), and the exact
console output the program is expected to produce for that session.

The program's entry point is `src/main/java/PDD.java` (class `PDD`), with
supporting classes `Task.java`, `Todo.java`, `Deadline.java`, `Event.java`
in the same directory.

## How a test case is structured

- **Aim**: what behavior this test case is checking.
- **Input**: the exact lines sent to the program's stdin, in order.
- **Expected output**: the exact stdout the program must produce.

---

## TC1: Greet and exit

**Aim**: Verify the startup banner/greeting is shown, and that `bye` exits
with the goodbye message.

**Input**:
```
bye
```

**Expected output**:
```
____________________________________________________________
 ____  ____  ____  
|  _ \|  _ \|  _ \ 
| |_) | | | | | | |
|  __/| |_| | |_| |
|_|   |____/|____/ 

Hello! I'm PDD.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## TC2: Add a todo, deadline, and event; list them

**Aim**: Verify `todo`, `deadline`, and `event` commands add tasks with the
correct type tag and are echoed correctly, and that `list` shows them with
1-based numbering.

**Input**:
```
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

**Expected output**:
```
____________________________________________________________
 ____  ____  ____  
|  _ \|  _ \|  _ \ 
| |_) | | | | | | |
|  __/| |_| | |_| |
|_|   |____/|____/ 

Hello! I'm PDD.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## TC3: Mark and unmark a task

**Aim**: Verify `mark` sets a task's status icon to `[X]`, and `unmark`
reverts it back to `[ ]`, with the correct confirmation messages.

**Input**:
```
todo borrow book
mark 1
list
unmark 1
list
bye
```

**Expected output**:
```
____________________________________________________________
 ____  ____  ____  
|  _ \|  _ \|  _ \ 
| |_) | | | | | | |
|  __/| |_| | |_| |
|_|   |____/|____/ 

Hello! I'm PDD.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] borrow book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] borrow book
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] borrow book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## TC4: Deadline with an unusual date string

**Aim**: Verify that the text after `/by` is stored and echoed verbatim,
with no parsing/validation of the date/time string.

**Input**:
```
deadline do homework /by no idea :-p
list
bye
```

**Expected output**:
```
____________________________________________________________
 ____  ____  ____  
|  _ \|  _ \|  _ \ 
| |_) | | | | | | |
|  __/| |_| | |_| |
|_|   |____/|____/ 

Hello! I'm PDD.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] do homework (by: no idea :-p)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] do homework (by: no idea :-p)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Adding new test cases

Append a new `## TCn: <title>` section following the same structure (Aim,
Input, Expected output). The `test-ui` skill reads this file top to bottom
and treats each `## TC` heading as a new, independent session, run in a
fresh instance of the program.
