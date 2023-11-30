# Parallel Search of Articulation Points
This is the program, written in Java, that searches for articulation points in the graph using Bread-First-Search (BFS) and brute-force algorithms. It makes a use of Apache Spark to parallelize execution of data-involved tasks among remote computing nodes during the searching process. It can also be used with the single local computing node, when this local comput.

<div class="img-with-text">
    <p>Initial graph:</p>
    <img src="https://github.com/A-Rakhmatullaev/Parallel-Articulation-Points/blob/main/readme/initial.png" alt="initial" width="420" height="300"/>
</div>

## Theory of articulation points
Articulation points are the nodes of the graph such that, when removed, cause a fission of the graph into two or more unconnected, non-linked graphs.

<div class="img-with-text">
    <p>Articulation points in the graph (marked with green color):</p>
    <img src="https://github.com/A-Rakhmatullaev/Parallel-Articulation-Points/blob/main/readme/initial_points.png" alt="initial_points" width="420" height="300"/>
</div>

<div class="img-with-text">
    <p>Example if node 'B' is removed:</p>
    <img src="https://github.com/A-Rakhmatullaev/Parallel-Articulation-Points/blob/main/readme/b_removed.png" alt="b_removed" width="420" height="300"/>
</div>

As you can see from this example, the initial graph gets divided into three unconnected graphs.

<div class="img-with-text">
    <p>Example if node 'D' is removed:</p>
    <img src="https://github.com/A-Rakhmatullaev/Parallel-Articulation-Points/blob/main/readme/d_removed.png" alt="d_removed" width="420" height="300"/>
</div>

As you can see from this example, the initial graph gets divided into two unconnected graphs.

What are articulation points and where are they used?


## Theory of brute-force algorithm

## Explanation of general algorithm

## Results and benchmarks

## License
