Nikita Bizyuk
TCSS 483
02-01-2026

Regular Expressions:
- No Extra credit was attempted on this assignment.
- Minimal code was included to assist with validating input. Specifically, for question 5 of the assignment
which is for the date. I wrote a helper function to ensure that user input is a real calendar date. The helper method
Checks which month has 30 days vs 31 days as well as whether or not it is a leap year. It then compares the input date
to make sure that it is within bounds of valid calendar dates.

Running the assignment:
- The assignment can be compiled regularly using an IDE.
- The main class does not provide any outputs.
- The MainRegexUnitTests.java was written with the assistance of LLM.
It has 192 unit tests. At least 16 unit tests for each category. The results
are outputted to the console. 
- A .txt file is attached to this folder which provides a visual of the unit test
output.

Issues:
- All 192 unit test passed.
- validateAddress() function street type such as blvd, rd, ave is fixed therefore 
certain addresses that i could not think of will return false.