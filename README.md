# Advent of Code — Java Solutions

This repository contains my solutions to the **[Advent of Code](https://adventofcode.com/)** programming puzzles,
implemented in **Java** and organized by **year** and **day**.

The project is intentionally structured to scale across multiple AoC editions, encourage clean problem-solving, and
support automation via Maven and CI.

I hope it serves as a useful reference for others looking to learn Java or improve their problem-solving skills.

---

## Overview

- Language: **Java**
- Build Tool: **Maven**
- Focus: correctness, readability, and reuse
- Organization: one package per year and per day
- Inputs handled via resources (no hard-coded data)

---

## Goals

- Practice structured problem-solving
- Explore modern Java features and language evolution
- Maintain clean, readable, and well-isolated solutions
- Serve as a long-term reference for Advent of Code challenges

---

## Project Structure

### Source Code

- [`src/main/java/`](src/main/java)
    - [`com.adventofcode`](src/main/java/com/adventofcode) – root package for all code
        - [`common`](src/main/java/com/adventofcode/common) – shared utilities and abstractions used across puzzles
        - `yearYYYY.dayDD` – individual puzzle solutions, grouped by year and day

### Resources

- [`src/main/resources/`](src/main/resources)
    - `yearYYYY/dayDD/`
        - `example.txt` – optional example input from the problem description
        - `input.txt` – the real puzzle input for that day

### Tests

- [`src/test/java/`](src/test/java)
    - [`com.adventofcode`](src/test/java/com/adventofcode) – root package for all tests
        - [`common`](src/test/java/com/adventofcode/common) – unit tests for shared utilities
        - `yearYYYY.dayDD` – unit tests for individual puzzle solutions

---

## Architecture Notes

- Each puzzle lives in its own package `yearYYYY.dayDD`
- Puzzle metadata and discovery are handled via [`@DayPuzzle`](src/main/java/com/adventofcode/DayPuzzle.java)
- Common execution logic is centralized in [`AbstractPuzzle`](src/main/java/com/adventofcode/AbstractPuzzle.java)
- Common test logic is centralized in [`AbstractPuzzleTest`](src/test/java/com/adventofcode/AbstractPuzzleTest.java)
- Input handling is standardized to keep solutions deterministic and testable
- The structure allows adding new years without touching existing code

---

## Running

### IDE

Run the corresponding main puzzle class directly from your IDE, e.g.
[ReportRepair](src/main/java/com/adventofcode/year2020/day01/ReportRepair.java).

### Maven

```sh
mvn clean install
```

### Special Requirements

The solution for [`year2025.day10.Factory`](src/main/java/com/adventofcode/year2025/day10/Factory.java) requires
the [Z3 Theorem Prover](https://github.com/Z3Prover/z3) developed by Microsoft. Before running this puzzle, you must
run the provided [`download-z3.sh`](download-z3.sh) script to download Z3.

```sh
bash download-z3.sh
```

This will ensure the Z3 solver is available for the puzzle to work correctly.

---

## Inputs

Each puzzle automatically loads its input from `src/main/resources/yearYYYY/dayDD/input.txt` when executed.

If present, `example.txt` can be used for local validation and debugging.

---

## CI

- GitHub Actions workflow included
- Builds and runs tests on every push
- Ensures solutions stay compilable across changes

---

## Requirements

- Java 25
- Maven 3.9+

---

## License

This project is for educational purposes and is not affiliated with Advent of Code.
