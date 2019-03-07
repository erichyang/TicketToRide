package unit_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import core.graph.Graph;
import core.graph.Rail;

public class GraphTester {
	public static void main(String[ ]args) throws FileNotFoundException {
		//Graph g = new Graph(new File("game_files\\cities\\graph.in"));
		//Graph simpleGraph = new Graph(new File("game_files\\cities\\testGraphs.in"));
		//simpleGraph.EdgeList();
		Scanner sc = new Scanner(new File("game_files\\cities\\testGraphs.in"));
		
		
		while (sc.hasNextLine()) {
			int num = sc.nextInt();
			sc.nextLine();
			Graph simpleGraph = new Graph();
			for(int i=0; i<num; i++) {
				String[] input = sc.nextLine().split(" ");
				simpleGraph.add(input[0],
						new Rail(input[0], input[1], Integer.parseInt(input[2]), Boolean.parseBoolean(input[3]), input[4]));
			}
			System.out.println(simpleGraph.DFS("A"));
		}
		//
	}
}
