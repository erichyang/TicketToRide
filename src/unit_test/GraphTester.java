package unit_test;

import java.io.File;
import java.io.FileNotFoundException;

import core.graph.Graph;

public class GraphTester {
	public static void main(String[ ]args) throws FileNotFoundException {
		Graph g = new Graph(new File("game_files\\cities\\graph.in"));
	}
}
