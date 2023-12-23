# Parallel Search of Articulation Points
This is the program, written in Java, that searches for articulation points in the graph using Bread-First-Search (BFS) and brute-force algorithms. It makes a use of Apache Spark to parallelize execution of data-involved tasks among remote computing nodes during the searching process. It can also be used with the single local computing node, when this local comput.

<div class="img-with-text">
    <p>Initial graph:</p>
    <img src="https://github.com/A-Rakhmatullaev/Parallel-Articulation-Points/blob/main/readme/initial.png" alt="initial" width="420" height="300"/>
</div>

## Theory of articulation points
Articulation points are the vertices of the graph such that, when removed, cause a fission of the graph into two or more unconnected, non-linked graphs.

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
Brute-force algorithm suggests that, in order to find an articulation point, program should pick each vertex in the graph, remove it from the graph, and check if the graph would still stay connected. Brute-force algorithm is more resource and time consuming, compared to Tarjan's algorithm, and has an overall complexity of O(V * (V + E)).

Explain brute-force algorithm to find articulation points!

## Explanation of general algorithm
The general algorithm of the program is quite straightforward. First, the program gets the input file and map its data to the proper usable form. In this case, this program just sorts and then stores edge data in form of JavaPairRDD <Tuple2 <Integer, List <Integer>>>, where the key in the tuple is vertex ID, and the list contains information as either 0 or 1, to indicate if this vertex is connected to other vertex with id matching index in the list.

For example, if there is 1 in 36th element of the list, it means, that the vertex in the key, has an edge with vertex which has an ID of 36.

After that, the program maps each element from initial JavaPairRDD to another new JavaPairRDD of the same type parameter of Tuple2 <Integer, List <Integer> >. However, now list of integers will include the list of IDs of all vertices that have been visited. This is the part, where program implements both BFS and brute-force algorithms. Inside of 'map' function, program makes a list of visited vertices using 'bfs' function and considers that the current tuple's key, which is vertex ID, will not be taken into account for the BFS. It ensures, that it can get list of visited nodes when this vertex is removed from the graph. This is needed, when after mapping all tuples, it can check, if the list size of visited vertices is not equal to number of all vertices - 1. If yes, then it can say that this vertex is an articulation point. 

## Results and benchmarks
Here is the table that demonstrates execution time of the program running on 4 computing nodes with 1000, 2000 and 4000 vertices:
| Number of Vertices | 1000    | 2000    | 4000    |
| :---:              | :---:   | :---:   | :---:   |
| Seconds            | 1.106   | 7.566   | 22.637  |

Considering that program operates with edge information of the each vertex with all other vertices, it forms a matrix, meaning that program actually handles not just 1000 vertices, but 1 000 000 objects in case of 1000x1000 matrix.

## License
This code is distributed under GPLv3

> Copyright (C) 2023-2024 Akbarbek Rakhmatullaev [[Linkedin](https://www.linkedin.com/in/a-rakhmatullaev/)].
> 
> This program is a free software.
> You can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation.
> For more information on this, and how to apply and follow the GNU GPL, see <https://www.gnu.org/licenses/>.
