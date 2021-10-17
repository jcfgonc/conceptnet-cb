package jcfgonc.conceptnet.study;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import graph.GraphReadWrite;
import graph.StringGraph;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;

/**
 * This is SLOW AS HELL! (mostly unusable)
 * 
 * @author jcfgonc
 *
 */
public class InteractiveDatalog {

	public static void main(String[] args) throws NoSuchFileException, IOException, DatalogException {
		JatalogInterface ji = new JatalogInterface();

		String path = "kb/conceptnet5v45.csv";
		StringGraph kb = new StringGraph();
		GraphReadWrite.readCSV(path, kb);
	//	GraphReadWrite.writePRO("kb/out.pl", kb);
	//	System.exit(0);
		
		ji.addFacts(kb);

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String query_s = scanner.nextLine();
			StringGraph query_g = new StringGraph();
			GraphReadWrite.readPrologFromString(query_s, query_g);
			List<Expr> query = JatalogInterface.createQueryFromStringGraph(query_g);
			Collection<Map<String, String>> bindings = ji.query(query);
			System.out.println(bindings);
		}
		scanner.close();
	}

}
