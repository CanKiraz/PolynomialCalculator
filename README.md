# Polynomial Calculator
This project calculates the transactions given in the input.txt file and prints their results line-by-line into the output.txt file.
The transactions are subtraction (-), addition (+), and lastly, multiplication (*).
## How To Use
In the first line of the input.txt file, the number of given transactions should be written.
Given transaction should be written line-by-line and should be in the given format:

**\+ 4x3y6z9+3z*2x 2x3y+x3y2z5**  --->  **(transaction sign) (term1) (term2)**

After setting the input file, just start the main class. The results will be written into the output file line-by-line.

The example for input:
> 4\
> \+ x 3\
> \- 5x3y2+3x3y2*9x 2x2y4+2x\
> \+ 2xyz+4y 2xyz-4y\
> \* x3+3x2y+3xy2+y3 x4+4x3y+6x2y2+4xy3+y4

The expected result in output:
> x+3\
> 8x3y2-2x2y4+7x\
> 4xyz\
> x7+4x6y+6x5y2+4x4y3+x3y4
