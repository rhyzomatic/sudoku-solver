# sudoku-solver
A sudoku based on a simple depth-first search algorithm written in Java.

## Usage

Compile the java code:

```
$ javac SudokuSolver.java
```

To run the solver, do:

```
$ java SudokuSolver
```

Input is space separated values of the sudoku puzzle from left to right.

## Included Puzzles

I've included one example puzzle, the 11-star puzzle designed by Arto Inkala. See [this Telegraph article](http://www.telegraph.co.uk/news/science/science-news/9359579/Worlds-hardest-sudoku-can-you-crack-it.html).

To input this test puzzle, do:

```
$ java SudokuSolver < puzzles/11star.txt
```
