# Toy Language Interpreter

A fully functional interpreter for a custom toy programming language, built in Java with multithreading support and an interactive GUI for real-time execution visualization.

This project was developed as part of the **Advanced Programming Methods** course at Babeș-Bolyai University.

---

## Features

- **Step-by-step execution** — programs are interpreted instruction by instruction, making the runtime state fully visible at each step
- **Multithreading** — supports forked execution (`fork` statement), allowing concurrent threads with independent execution stacks
- **Synchronization primitives** — semaphores, barriers, and latches for coordinating concurrent threads
- **Dynamic memory management** — a shared heap with garbage collection, accessible via reference types
- **File I/O** — open, read, and close files from within a toy language program
- **Interactive GUI** — real-time visualization of the execution stack, symbol table, heap, output buffer, and file table for all running threads

---

## Language Overview

The toy language supports:

| Construct | Example |
|-----------|---------|
| Variable declaration | `int v; bool b;` |
| Assignment | `v = 10;` |
| Arithmetic & relational expressions | `v + 2 * 3`, `v > 5` |
| Conditional | `if (exp) then s1 else s2` |
| While loop | `while (exp) s` |
| Print | `print(exp)` |
| File operations | `openRFile(var, "file.txt")`, `readFile(var, readVar)`, `closeRFile(var)` |
| Heap operations | `new(v, exp)`, `rH(v)`, `wH(v, exp)` |
| Fork | `fork(s)` — spawns a new thread |
| Synchronization | `createSemaphore`, `acquire`, `release`, `newBarrier`, `awaitBarrier`, etc. |

---

## Architecture

The interpreter follows a clean, layered architecture:

```
src/
├── model/
│   ├── statement/        # All language statements (IStatement implementations)
│   ├── expression/       # Arithmetic, relational, logical expressions
│   ├── type/             # Type system (int, bool, string, ref)
│   ├── value/            # Runtime values
│   └── adt/              # Execution stack, symbol table, heap, file table, output list
├── controller/           # ProgramState management and execution logic
├── repository/           # State persistence (log files)
└── view/
    ├── gui/              # JavaFX GUI
    └── textui/           # Text-based runner
```

Key design decisions:
- All data structures are backed by interfaces (`IStack`, `IHeap`, `IDictionary`, etc.) for testability and replaceability
- Each thread has its own `ProgramState` with its own execution stack and symbol table, sharing the heap and output
- Garbage collection runs after each execution step, collecting heap addresses no longer reachable from any symbol table

---

## Getting Started

### Prerequisites
- Java 17+
- JavaFX SDK (for the GUI)
- IntelliJ IDEA (recommended)

### Running the GUI

1. Clone the repository:
   ```bash
   git clone https://github.com/AndreeaMariaBuruiana/ToyLanguageInterpreter.git
   ```
2. Open the project in IntelliJ IDEA
3. Add the JavaFX SDK to your module dependencies
4. Run `GUIController.java` (or the main GUI entry point)

### Running in text mode

Run `Main.java` (or the text-based runner) — it will execute a predefined set of example programs and write execution logs to `log*.txt` files.

---

## Example Program

```
int v; int w; ref int a;
v = 10;
new(a, 20);
fork(
  wH(a, 30);
  v = 32;
  print(v);
  print(rH(a))
);
print(v);
print(rH(a))
```

**Expected output (one possible interleaving):**
```
10
30
32
30
```

---

## Technologies

`Java` `JavaFX` `OOP` `Multithreading` `Concurrency` `Design Patterns` `Git`

---

## Author

**Andreea-Maria Buruiana** — [GitHub](https://github.com/AndreeaMariaBuruiana) · [LinkedIn](https://www.linkedin.com/in/andreea-maria-buruiana) · [buruiana.andreea07@gmail.com](mailto:buruiana.andreea07@gmail.com)
