 **Number Series:**
 
* Fibonacci: `1,1,2,3,5,8,13,21,34,55,89... `
* Catalan: `1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796...`

This series has many appearances in combinatorics. Starting with C_0 = 1, we can calculate using the recurrence relation `C_(n+1) = ((2(2n+1))/(n+2))*C_n`, or the following formulas can be used:

<img src="/ProgrammingCompetition/Images/CatalanSeries.png" alt="Catalan Series Formula" width="40%">
     
* Lazy Caterer: `1, 2, 4, 7, 11, 16, 22, 29, 37, 46, 56, 67, 79, 92, 106, 121, 137...`

The maximum number of pieces of a circle that can be made with a given number of straight cuts. For example, three cuts across a circle will produce six pieces if the cuts all meet at a common point, but seven if they do not. Calculated using: `p = (n^2 + n + 2)/2 for n >= 0`

* Trianglar: `0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55...`

<img src="/ProgrammingCompetition/Images/TriangleNumberSeries.png" alt="Triangular Series Formula" width="45%">

<img src="/ProgrammingCompetition/Images/TriangleNumberSeriesVisualization.png" alt="Triangular Series Visualization" width="25%">

* Hexagonal: `1, 6, 15, 28, 45, 66, 91, 120, 153, 190, 231...`

<img src="/ProgrammingCompetition/Images/HexagonalNumberSeries.png" width="45%">

<img src="/ProgrammingCompetition/Images/HexagonalNumberSeriesVisualization.png" width="25%">

* Subfactorial: `0 1 2 9 44 265 1854 14833...`

Denoted !n, this represents a lot of common patterns, notably the number of ways elements can be arranged such that each element is not found in it's starting position.

<img src="/ProgrammingCompetition/Images/subfactorial.png" width="20%">
