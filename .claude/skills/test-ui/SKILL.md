---
name: test-ui
description: Run this project's UI/console test suite for the PDD chatbot. Use when the user asks to test the UI, run UI tests, verify console output, or check that the chatbot's output matches the test plan. Reads test cases from test/ui-test-plan.md, runs the program for each, and compares actual vs. expected output.
---

# test-ui

Runs the console-based UI tests for this project's chatbot (`src/main/java/PDD.java`)
against the test cases recorded in `test/ui-test-plan.md`, and reports a full
transcript of the test session.

## Test case format

`test/ui-test-plan.md` contains one `## TCn: <title>` section per test case.
Each section has three parts:

- **Aim** — what the test case is checking.
- **Input** — a fenced code block with the exact lines to feed to the
  program's stdin, one command per line, always ending in `bye`.
- **Expected output** — a fenced code block with the exact stdout the
  program must produce for that input.

Each test case is an independent full session: run the program fresh for
each one (do not reuse state across test cases).

## Procedure

1. Read `test/ui-test-plan.md` and parse out each `## TCn` section's Aim,
   Input, and Expected output blocks, in file order.

2. Compile the program once, before running any test cases:
   ```
   javac -d <tmp-build-dir> src/main/java/*.java
   ```
   If compilation fails, stop immediately and report the compiler error —
   do not attempt to run any test cases.

3. For each test case, in order:
   a. Run the program, piping the test case's Input lines to stdin:
      ```
      java -cp <tmp-build-dir> PDD
      ```
   b. Capture the actual stdout produced.
   c. Compare the actual output to the Expected output **exactly**
      (including whitespace and line breaks).
   d. Record this test case's console session (the input lines interleaved
      with the actual output, as a human would see them typed into the
      running program) — keep this for the final transcript regardless of
      pass/fail.
   e. If the actual output does not match the expected output:
      - Stop running any further test cases immediately.
      - Report which test case failed (its number, title, and Aim).
      - Show the actual output and the expected output for that test case
        side by side (or clearly labeled), so the mismatch is easy to spot.
      - Do not proceed to remaining test cases.

4. After the loop ends (either all test cases passed, or one failed and
   testing was stopped):
   - Show the full recorded console session transcript for every test case
     that was run (input + actual output), so the user can see what
     actually happened during the test run.
   - Give a one-line summary: e.g. "3/4 test cases passed, stopped at TC4"
     or "All 4 test cases passed."

## Notes

- The program has no external dependencies or build tool config in this
  project (no `build.gradle` present as of this writing) — compile and run
  directly with `javac`/`java` as shown above. If a build tool is later
  added to the project, prefer its equivalent compile/run commands instead.
- Use a scratch/temp directory for compiled `.class` output so it doesn't
  pollute the working tree.
- Comparison must be exact — do not "fuzzy match" or ignore trailing
  whitespace differences, since the divider lines and indentation are part
  of the intended UI contract.
