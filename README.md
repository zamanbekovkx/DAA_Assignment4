# DAA Assignment 4 — Dynamic Programming and String Algorithms

**Course:** Design and Analysis of Algorithms  
**Assignment:** 4 — Dynamic Programming and String Algorithms  
**Student:** Olzhas Zamanbekov  
**Group:** SE-2516  
**Final tag:** `v1.0`

## What is implemented

- Edit Distance:
  - recursive version without memory;
  - bottom-up full-table DP;
  - two-row DP with O(m) extra memory.
- String search:
  - Naive search;
  - KMP search;
  - public `lps(String p)` method.
- Counters measured inside the algorithms.
- Fixed-seed input generator for reproducibility.
- JUnit 5 tests including reference values, 100 random short edit-distance cases, 100 random search cases, overlap cases and edge cases.
- Experiment runner producing `results/results.csv`.
- Three PNG plots in `results/plots/`.
- Report in `REPORT.md`.

## Project layout

```text
src/main/java/daa/       Java source code
src/test/java/daa/       JUnit 5 tests
results/results.csv      all experiment runs
results/plots/            PNG charts
REPORT.md                 2–4 page-style report
README.md                 build/run instructions
```

## Requirements

- Java 17 or newer
- Maven 3.9+ for the normal build/test command

## Build and test

```bash
mvn clean test
```

## Run the reference checks without Maven

```bash
javac -d out src/main/java/daa/*.java
java -cp out daa.ReferenceCheck
```

## Run the experiment without Maven

```bash
javac -d out src/main/java/daa/*.java
java -cp out daa.ExperimentRunner
```

The experiment writes `results/results.csv`.

## Reproducibility

The experiment uses fixed seeds `2516` and `2517`. Running the same experiment command generates the same input strings and the same logical test cases. Wall-clock times can differ between computers, but the measured counters and generated inputs are reproducible.

## Git commands

```bash
git init
git add .
git commit -m "feat: complete DAA assignment 4"
git branch -M main
git tag v1.0
```

If using GitHub, create a repository and push the `main` branch and `v1.0` tag.

## AI-use acknowledgement

AI assistance was used for drafting and reviewing parts of the implementation, experiment structure, documentation and test coverage. The final code, generated results, plots and conclusions were checked against the assignment requirements and reference values.
