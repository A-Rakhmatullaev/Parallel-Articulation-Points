// Spark Dependencies
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import scala.Tuple2;

// General Dependencies
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ArticulationPoint {

    public static void main(String[] args) {

        // Get input file
        String inputFile = args[0];

        // Configure Spark
        SparkConf sparkConf = new SparkConf().setAppName("AR - Articulation Points");
        // Start a Java Spark Context (JSC)
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        // Obtain information from input file, and put it as a String type value to Java Resilient Distributed Dataset (RDD)
        JavaRDD <String> lines = jsc.textFile(inputFile).cache();
        lines.count();

        JavaPairRDD <Integer, List <Integer> > graph = lines.zipWithIndex().mapToPair(tuple -> {
            // where 1st element in tuple is line of type String
            // 2nd element is its index
            
            // split string to integers
            // put each integer into the list inside of new Tuple2, and tuple._2() as index
            // return new Tuple2
            List <Integer> edges = stringToIntList(tuple._1());
            return new Tuple2 <> (tuple._2().intValue(), edges);
        }).repartition(10);

        int graphLength = graph.countByKey().size();

        int [][] graphAsArray = new int [graphLength][graphLength];
        
        for(Tuple2 <Integer, List <Integer> > tuple : graph.collect()) {
            for(int i = 0; i < graphLength; i++) {
                graphAsArray[tuple._1()][i] = tuple._2().get(i);
            }
        }

        // Start a timer
        long startTime = System.currentTimeMillis();

        // Find articulation points using BFS
        List <Integer> articulationPoints = articulationPoints(graph, graphAsArray, graphLength);

        System.out.println("+++++++++++++++++++++++++++++++++++++ Articulation Points: " + articulationPoints);


        // End a timer
        long endTime = System.currentTimeMillis();
        // Overall elapsed time 
        long elapsedTime = endTime - startTime;

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Elapsed Time: " +  elapsedTime);

        jsc.stop();
    }

    // Return articulation points as a list
    public static List <Integer> articulationPoints(JavaPairRDD <Integer, List <Integer> > graph, int [][] graphAsArray, int graphLength) {
        // Call bfs function for each target vertex we are calling while mapping
        // Here, visitedNetwork is represented by a pair, where the key is vertexID, and values are IDs of all visited nodes when this vertex was removed
        //JavaPairRDD <Integer, Boolean> visitedNetwork = 
        return graph.mapToPair(targetVertex -> {
            return new Tuple2 <> (targetVertex._1(), bfs(graphAsArray, graphLength, targetVertex._1()));
        }).filter(vertexAndVisited -> vertexAndVisited._2()).keys().collect();
    }

    public static boolean bfs(int [][] graphAsArray, int graphLength, Integer startVertex) {
        Deque <Integer> queue = new ArrayDeque <> ();
        Set <Integer> visited = new HashSet <> ();

        queue.addLast((startVertex + 1) % graphLength);
        visited.add((startVertex + 1) % graphLength);

        while (!queue.isEmpty()) {
            int vertex = queue.removeFirst();
            for (int neighbor = 0; neighbor < graphLength; neighbor++) {
                if (neighbor != startVertex && graphAsArray[vertex][neighbor] > 0 && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.addLast(neighbor);
                }
            }
        }

        return visited.size() != graphLength - 1 ? true : false;
    }

    public static List <Integer> stringToIntList(String input) {
        String[] strArray = input.split(" ");
        List <Integer> intList = new ArrayList <Integer> ();
        for(int i = 0; i < strArray.length; i++) {
            intList.add(Integer.parseInt(strArray[i]));
        }
        return intList;
    }
    
}