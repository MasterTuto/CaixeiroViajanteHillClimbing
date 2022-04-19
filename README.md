# TSP Solver using Hill Climbing algorithm

It finds the local maximum of the function, e.g. returns when no other better adjacent solution is found. A solution is considered adjacent when switching two cities and configuration is valid. As the definition here of switching between two cities is very fragile, it searches for few configurations before stopping. Maybe switch a little more may a better approach.

As you can see on the demonstration below, it stops as soon as the current solution is worse than the other we had (as it is the local minimum).

![Demonstration gif](animation.gif)
