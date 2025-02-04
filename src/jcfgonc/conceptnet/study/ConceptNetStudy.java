package jcfgonc.conceptnet.study;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497b;

import graph.GraphAlgorithms;
import graph.GraphReadWrite;
import graph.StringEdge;
import graph.StringGraph;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import stream.StreamProcessor;
import stream.StreamService;
import structures.MapOfSet;
import structures.ObjectCount;
import structures.ObjectCounter;
import utils.RawConsoleInput;
import utils.VariousUtils;

@SuppressWarnings("unused")
public class ConceptNetStudy {
	public static void main(String[] args) throws Exception {
		String path = "D:\\My Source Code\\Java - PhD\\MapperMO\\data\\mykb.csv";
		StringGraph graph = new StringGraph();
		GraphReadWrite.readCSV(path, graph);

//		 propagateSuperInferences(graph);

		// painter to file transfer protocol
//		GraphAlgorithms.reachableUsingISA(graph, "painter", "file transfer protocol");
//		removeProfanity(graph);
		GraphAlgorithms.propagateRelationsThroughInheritance(graph);
	//	GraphReadWrite.writeCSV(path, graph);

//		HashSet<String> rootsIsa = findRootIsaConcepts(graph);
//		MapOfSet<String, String> synonyms = findSynonyms(graph);
		System.exit(0);
//		
//		removeConceptsWithDegreeBelow(graph, "settlement", 4);

		// to manually remove concepts
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		while (true) {
//			String source = reader.readLine();
//			if (source.equals("quit"))
//				break;
//			graph.removeVertex(source);
//		}
//		reader.close();

//		GraphAlgorithms.removeSmallerComponents(graph);

		String label = "isa";

		studyTargetDegree(graph, label);
		System.exit(0);
//		removeEdgesConsole(graph);

//		ArrayList<String> fields = VariousUtils.readFileRows("F:\\Desktop\\concept fields to be ISAs.txt");
//		correctFieldTargetToIsa(graph, fields);
//		GraphReadWrite.writeCSV(path, graph);
//		for (String field : fields) {
//			Set<StringEdge> out = graph.outgoingEdgesOf(field, "isa");
//			System.out.println(field + "\t" + out);
//		}

//		GraphAlgorithms.shortestPathSearch(graph, "mollusca", "crater");
//		System.out.println(GraphAlgorithms.getDistance(graph, "mollusca", "crater", 1000));
//		System.exit(0);

//		ObjectIndex<String> vertexLabels = new ObjectIndex<>();
//		ObjectIndex<String> relationLabels = new ObjectIndex<>();
//		GraphAlgorithms.convertStringGraph2IntDirectedMultiGraph(graph, vertexLabels, relationLabels);
		// GraphReadWrite.readCSV_highPerformance(path, graph);
		// graph.showStructureSizes();
//		System.out.println(ticker.getTimeDeltaLastCall());

//		System.out.println("vertices\t" + graph.getVertexSet().size());
		// System.out.println("edges \t" + graph.edgeSet().size());
		// showRelationCount(graph);
//		System.out.println("-------");
//		showConceptsDegree(graph);
//		System.exit(0);
//		getDirectedConceptsOfRelation(graph, "hassubevent", false, 2);

//		ArrayList<String> concepts = new ArrayList<>();
//
//		for (String concept : graph.getVertexSet()) {
//			// if (concept.startsWith("your_"))
//			if (concept.endsWith("_in"))
//				concepts.add(concept);
//		}
//
//		concepts.sort(new Comparator<String>() {
//
//			@Override
//			public int compare(String o1, String o2) {
//				int nwords1 = VariousUtils.fastSplit(o1, '_').length;
//				int nwords2 = VariousUtils.fastSplit(o2, '_').length;
//				return Integer.compare(nwords1, nwords2);
//			}
//		});
//
//		for (String concept : concepts) {
//			System.out.println(concept);
//		}

		// generaliseTransitivity(graph, "createdby");

//		String target = "television show";
//		String label = "isa";
//	//	studyDegree(graph, label, target);
//	//	System.exit(0);
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		while (true) {
//			String source = reader.readLine();
//			if (source.equals("quit"))
//				break;
//			StringEdge e = new StringEdge(source, target, label);
//			graph.removeEdge(e);
//		}
//		reader.close();

//		generaliseTransitivity(graph, "partof");

		GraphReadWrite.writeCSV(path, graph);
//		showRelationCount(graph);
//		System.out.println("-------");
//		studyDegree(graph);
		{
			// graph.renameVertex("vertebrate", "vertebrata");
			// generalizeGraph(graph);
			// correctRelations("edgesToEdit1.csv", graph);
			// prepareGraph(graph);
			// studyEdges(graph);
			// graph.removeEdges(GraphReadWrite.loadEdgesFromFile("edgesToRemove2.csv"));
			// removeSmaller2ents(graph);
			// removeDisconnectedEdges(graph);
			// removeRelationsWithLabel(graph, "isa");
			// HashSet<String> part = extractRandomPart(graph);
			// PatternFinderSwiProlog.findPatterns(graph);
		}

		// ticker.getTimeDeltaLastCall();
		// GraphReadWrite.writeCSV("output.csv", graph);
		// System.out.println(ticker.getTimeDeltaLastCall());
	}

	private static void removeProfanity(StringGraph graph) {
		ArrayList<String> profaneList = VariousUtils
				.readFileRows("D:/My Source Code/Java - PhD/MapperMO/data/english profanity.txt");
		ArrayList<List<String>> profaneSentenceWords = new ArrayList<List<String>>();
		for (String profaneSentence : profaneList) {
			List<String> profaneWords = Arrays.asList(profaneSentence.split(" "));
			profaneSentenceWords.add(profaneWords);
		}

		ArrayList<String> conceptsToRemove = new ArrayList<String>();

		Set<String> concepts = graph.getVertexSet();
		for (String concept : concepts) {
			if (isProfane(concept, profaneSentenceWords)) {
				conceptsToRemove.add(concept);
			}
		}

		graph.removeVertices(conceptsToRemove);
	}

	private static boolean isProfane(String concept, ArrayList<List<String>> profaneSentenceWords) {
		List<String> conceptWords = Arrays.asList(concept.split(" "));
		// see if any of the profane word sequence is contained in the concept
		for (List<String> profaneWords : profaneSentenceWords) {
			int i = Collections.indexOfSubList(conceptWords, profaneWords);
			if (i >= 0) {
				System.out.printf("\"%s\" is profane because it contains %s\n", concept, profaneWords);
				return true;
			}
		}
		return false;
	}

	/**
	 * propagates relations from ancestors (via ISA relations) to their children
	 * 
	 * old version, works bottom up and incompletely!
	 * 
	 * @param graph
	 */
	private static void propagateSuperInferences(StringGraph graph) {
//		graph.addEdge("predator","life form","desires");

		// propagate super inferences
		ArrayList<StringEdge> edgesToAdd = new ArrayList<StringEdge>();

		MapOfSet<String, String> synonyms = new MapOfSet<String, String>();

		// for (String x : graph.getVertexSet()) {
		String x = "person";
		{
			Set<StringEdge> out = graph.outgoingEdgesOf(x, "isa");
			Set<StringEdge> synedges = graph.outgoingEdgesOf(x, "synonym");
			for (StringEdge syned : synedges) {
				synonyms.add(x, syned.getOppositeOf(x));
			}
			out.addAll(synedges);

			for (StringEdge x_y : out) {
				String y = x_y.getTarget();
				for (StringEdge superEdge : graph.edgesOf(y)) {
					if (superEdge.equals(x_y))
						continue;
					String source = superEdge.getSource();
					String target = superEdge.getTarget();
					String relation = superEdge.getLabel();
					// inferences valid for the relation form y,relation,z
					if (source.equals(y)) {
						switch (relation) {
						case "isa":
						case "capableof":
						case "partof":
						case "causes":
						case "desires":
						case "atlocation":
						case "usedfor":
						case "madeof":
						case "requires":
						case "hasproperty":
						case "definedas":
						case "notdesires":
						case "createdby":
						case "hasprerequisite":
							StringEdge inference = new StringEdge(x, target, relation);
							System.out.println(inference);
							edgesToAdd.add(inference);
							break;
						case "synonym":
						case "antonym":
						case "receivesaction":
							break;
						default:
							System.out.println("missing relation: " + superEdge.getLabel() + "\t" + superEdge);
							break;
						}
					}
					// inferences valid for the relation form z,relation,y
					if (target.equals(y)) {
						switch (relation) {
						case "partof":
							StringEdge inference = new StringEdge(source, x, relation);
							System.out.println(inference);
							edgesToAdd.add(inference);
							break;
						default:
							System.out.println("missing relation: " + superEdge.getLabel() + "\t" + superEdge);
							break;
						}
					}

					System.lineSeparator();
				}
			}
		}
	}

	private static void removeEdgesConsole(StringGraph graph) throws IOException {
//		ArrayList<StringEdge> toRemove = new ArrayList<StringEdge>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = reader.readLine().trim();
			if (line.isEmpty())
				continue;
			if (line.equals("quit"))
				break;
			StringEdge e = new StringEdge(line, StringEdge.CSV_ORDER_SOURCE_LABEL_TARGET);
			graph.removeEdge(e);
		}
		reader.close();
	}

	/**
	 * renames all the edges which have "field" as a label and the given "field" as
	 * a target to "isa" field target.
	 * 
	 * @param graph
	 * @param fields
	 */
	private static void correctFieldTargetToIsa(StringGraph graph, ArrayList<String> fields) {
		for (String field : fields) {
			correctFieldTargetToIsa(graph, field);
		}
	}

	private static void correctFieldTargetToIsa(StringGraph graph, String field) {
		Set<StringEdge> edgesToChange = graph.incomingEdgesOf(field, "field");
		graph.removeEdges(edgesToChange);
		for (StringEdge newEdge : edgesToChange) {
			newEdge = newEdge.replaceLabel("isa");
			graph.addEdge(newEdge);
		}
	}

	private static void generaliseTransitivity(StringGraph graph, String relation) {
		HashSet<StringEdge> edgesToRemove = new HashSet<>();
		for (StringEdge edge0 : graph.edgeSet(relation)) { // X,isa,Y
			String conceptX = edge0.getSource();
			String conceptY = edge0.getTarget();
			Set<StringEdge> edges1 = graph.outgoingEdgesOf(conceptY, relation);
			if (!edges1.isEmpty()) {
				for (StringEdge edge1 : edges1) {
					String conceptZ = edge1.getTarget();
					StringEdge edge2 = new StringEdge(conceptX, conceptZ, relation);
					if (graph.containsEdge(edge2)) {
						edgesToRemove.add(edge2);
						System.out.println("removing\t" + edge2 + "\t::\t" + edge1);
					}
				}
			}

//			System.out.println(edge0 + "\t" + conceptX + "\t" + graph.getInDegree(conceptX) + "\t" + graph.getOutDegree(conceptX));
		}

		// graph.removeEdges(edgesToRemove);
		System.out.printf("removed %d edges\n", edgesToRemove.size());
	}

	private static HashSet<String> extractRandomPart(StringGraph graph, int minNewConceptsTrigger,
			int minTotalConceptsTrigger) {
		// just get a vertex
		RandomGenerator random = new Well44497b();
		String firstVertex = VariousUtils.getRandomElementFromCollection(graph.getVertexSet(), random);
		HashSet<String> closedSet = new HashSet<>();
		HashSet<String> openSet = new HashSet<>();
		// start in a given vertex
		openSet.add(firstVertex);
		// ---
		while (openSet.size() > 0) {
			// do a radial expansion
			Set<String> newVertices = GraphAlgorithms.expandFromOpenSetOneLevel(openSet, closedSet, graph, null);
			if (newVertices.isEmpty())
				break;

			if (closedSet.size() > minTotalConceptsTrigger) {
				break;
			}

			if (newVertices.size() > minNewConceptsTrigger) {
				newVertices = VariousUtils.randomSubSet(newVertices, minNewConceptsTrigger, random);
			}

			openSet.addAll(newVertices);
			openSet.removeAll(closedSet);
		}
		return closedSet;
	}

	private static <T> void displayCollection(Collection<T> c) {
		for (T e : c) {
			System.out.println(e);
		}
	}

	private static void manualOperation(StringGraph graph) throws IOException {
		ArrayList<StringEdge> toAdd = new ArrayList<>();
		ArrayList<StringEdge> toRemove = new ArrayList<>();
		boolean skipToEnd = false;

		for (StringEdge edge : graph.edgeSet()) {
			String source = edge.getSource();
			String target = edge.getTarget();
			String relation = edge.getLabel();

			if (target.startsWith("in_") && relation.equals("/r/IsA")) {
				System.out.printf("%s\tReplace/Delete/Ignore? ", edge);
				boolean validAnswer;
				do {
					validAnswer = true;
					char read = (char) RawConsoleInput.read(false);
					switch (read) {
					case 'r':
						String targetNew = target.substring(3 + target.indexOf("in_"));
						toRemove.add(edge);
						StringEdge newEdge = new StringEdge(source, targetNew, "/r/AtLocation");
						toAdd.add(newEdge);
						System.out.println("\t->\t" + newEdge);
						break;
					case 'd':
						toRemove.add(edge);
						break;
					case 'i':
						break;
					case 's':
						skipToEnd = true;
					default:
						validAnswer = false;
						break;
					}
				} while (!validAnswer);
			}
			if (skipToEnd) {
				break;
			}
		}

		graph.removeEdges(toRemove);
		graph.addEdges(toAdd);

	}

	private static void prepareGraph(StringGraph graph)
			throws InterruptedException, FileNotFoundException, IOException {
		// String vertex0 = "eukaryote"; //
		// String vertex1 = "specie";
		// displayCollection(graph.outgoingEdgesOf(vertex0, "isa"));
		// displayCollection(graph.edgesOf(vertex0, "synonym"));
		// displayCollection(graph.edgesOf(vertex0, "isa"));

		// generalizeGraph(graph);
		// getConceptsTargetOfISA(graph);
		// graph.removeVertex("band");
		// graph.removeVertex("single");
		// graph.removeVertex("book");
		// graph.removeVertex("write_work");
		// graph.removeVertex("name");
		// graph.removeVertex("tv_show");

		// removeSmallerComponents(graph);

		generalizeGraph(graph);

	}

	private static void studyIsaConceptCovariance(StringGraph graph, String referenceConcept) {
		ObjectCounter<String> isaTargets = new ObjectCounter<>();
		for (StringEdge incEdge : graph.incomingEdgesOf(referenceConcept, "isa")) {
			String source = incEdge.getSource();
			Set<StringEdge> outIsa = graph.outgoingEdgesOf(source, "isa");
			Collection<String> edgesTargets = GraphAlgorithms.getEdgesTargets(outIsa);
			isaTargets.addObjects(edgesTargets);
		}
		isaTargets.toSystemOut();
	}

	private static void replaceEdges(StringGraph graph, String labelToReplace, String replacementLabel) {
		ArrayList<StringEdge> toRemove = new ArrayList<>();
		ArrayList<StringEdge> toAdd = new ArrayList<>();
		for (StringEdge edge : graph.edgeSet()) {
			if (edge.getLabel().equals(labelToReplace)) {
				toRemove.add(edge);
				StringEdge newEdge = edge.replaceLabel(labelToReplace, replacementLabel);
				toAdd.add(newEdge);
			}
		}
		graph.addEdges(toAdd);
		graph.removeEdges(toRemove);
	}

	private static void studyEdgeSourcesAndTargets(StringGraph graph) {
		ObjectCounter<String> sourceIsa = new ObjectCounter<>();
		ObjectCounter<String> targetIsa = new ObjectCounter<>();
		for (StringEdge edge : graph.edgeSet()) {
			if (edge.getLabel().equals("memberof")) {

				String edgeSource = edge.getSource();
				Set<StringEdge> outSource = graph.outgoingEdgesOf(edgeSource, "isa");
				Collection<String> sources = GraphAlgorithms.getEdgesTargets(outSource);

				String edgeTarget = edge.getTarget();
				Set<StringEdge> outTarget = graph.outgoingEdgesOf(edgeTarget, "isa");
				Collection<String> targets = GraphAlgorithms.getEdgesTargets(outTarget);

				if (sources.contains("specie") && targets.contains("specie")) {

					sourceIsa.addObjects(sources);
					targetIsa.addObjects(targets);
				}
			}
		}
		sourceIsa.toSystemOut();
		targetIsa.toSystemOut();
	}

	private static void generalizeGraphShorter(StringGraph graph)
			throws InterruptedException, FileNotFoundException, IOException {
		List<String> isaConcepts = GraphReadWrite.loadConceptsFromFile("isaConcepts.txt");
		// HashSet<String> isaConcepts = getConceptsTargetOfISA(graph);

		for (String isaConcept : isaConcepts) {
			ArrayList<ArrayList<String>> sequences = sequenceDetector(graph, isaConcept, 0.2);
			for (ArrayList<String> sequence : sequences) {
				generalizeFromSequence(graph, sequence, false, false);
			}
		}
	}

	private static void generalizeGraph(StringGraph graph) throws InterruptedException {
		// TODO: [biomolecule, -isa, X, +isa, enzyme]
		// [protein_molecule, -isa, X, +isa, biomolecule]

		// ArrayList<String> sequence = new ArrayList<>();
		// sequence.add("reptile");
		// sequence.add("-isa");
		// sequence.add("X");
		// sequence.add("+isa");
		// sequence.add("specie");
		// generalizeFromSequence(graph, sequence, false, false);
		// System.exit(0);

		// ArrayList<ArrayList<String>> sequences = sequenceDetector(graph,
		// "specie", 0.2);
		// for (ArrayList<String> sequence : sequences) {
		// generalizeFromSequence(graph, sequence);
		// }
		// System.exit(0);

		Vector<ArrayList<String>> sequences = new Vector<>();
		ArrayList<String> concepts = new ArrayList<String>(graph.getVertexSet());

		StreamService te = new StreamService(4);
		// find sequences of relations
		{
			StreamProcessor sp = new StreamProcessor() {

				@Override
				public void run(int processorId, int rangeL, int rangeH, int streamSize) {
					ArrayList<ArrayList<String>> curSequences = new ArrayList<>();
					for (int i = rangeL; i <= rangeH; i++) {
						String concept = concepts.get(i);
						int conceptDegree = graph.degreeOf(concept);
						if (conceptDegree < 100)
							continue;
						// System.out.printf("pid\t%d \t i\t%d \t %s \t deg\t%d
						// \n",
						// processorId, i, concept, conceptDegree);
						ArrayList<ArrayList<String>> seq = sequenceDetector(graph, concept, 0.05);
						curSequences.addAll(seq);
					}
					sequences.addAll(curSequences);
				}
			};
			te.invoke(concepts.size(), sp);
		}

		// System.exit(0);
		// generalize the sequences

		for (ArrayList<String> sequence : sequences) {
			generalizeFromSequence(graph, sequence, false, false);
		}

		// {
		// StreamProcessor sp = new StreamProcessor() {
		//
		// @Override
		// public void run(int processorId, int rangeL, int rangeH, int
		// streamSize) {
		// for (int i = rangeL; i <= rangeH; i++) {
		// ArrayList<String> sequence = sequences.get(i);
		// generalizeFromSequence(graph, sequence, false);
		// // System.out.printf("pid\t%d \t i\t%d \t %s \t deg\t%d \n",
		// processorId, i, concept, conceptDegree);
		// }
		// }
		// };
		// te.invoke(sequences.size(), sp);
		// }

		te.shutdown();
	}

	/**
	 * lower score is better
	 * 
	 * @param path
	 * @return
	 */
	private static int scorePath(ArrayList<StringEdge> path) {
		if (path.isEmpty()) {
			return Integer.MAX_VALUE;
		}
		int sum = 0;
		for (StringEdge edge : path) {
			String label = edge.getLabel();
			switch (label) {
			case "isa":
				sum += 1;
				break;
			case "synonym":
				sum += 2;
				break;
			case "derivedfrom":
				sum += 3;
				break;
			default:
				break;
			}
		}
		return sum;
	}

	private static void generalizeFromSequence(StringGraph graph, ArrayList<String> sequence, boolean fullGeneralizer,
			boolean useInferencer) {
		String concept0 = sequence.get(0);
		String rel0 = sequence.get(1);
		String rel1 = sequence.get(3);
		String concept1 = sequence.get(4);
		System.out.println(sequence);
		if (rel0.equals("-isa") && rel1.equals("+isa")) {

			StringEdge genTest = new StringEdge(concept0, concept1, "notgeneralize");
			StringEdge genTestR = genTest.reverse();
			if (graph.containsEdge(genTest) || graph.containsEdge(genTestR)) {
				System.out.println("->\tnot generalizing");
				return;
			}

			// [specie, -isa, X, +isa, eukaryote]
			// ---------
			// add specie,isa,eukaryote
			// delete X,isa,eukaryote
			//
			// decide if swap reptile and specie in the above example
			// generalizeFromSequenceLock.readLock().lock();
			ArrayList<StringEdge> path01 = null;
			ArrayList<StringEdge> path10 = null;

			if (useInferencer) {
				// generalizeFromSequenceLock.readLock().unlock();
				path01 = GraphAlgorithms.shortestIsaPath(graph, concept0, concept1, false, true);
				path10 = GraphAlgorithms.shortestIsaPath(graph, concept1, concept0, false, true);

			} else { // direct ISA inference
				path01 = GraphAlgorithms.shortestIsaPath(graph, concept0, concept1, false, false);
				path10 = GraphAlgorithms.shortestIsaPath(graph, concept1, concept0, false, false);

				// //this commented code tests the ISA relation directly on both
				// concepts
				// StringEdge isa01 = new StringEdge(concept0, concept1, "isa");
				// StringEdge isa10 = new StringEdge(concept1, concept0, "isa");
				// path10 = new ArrayList<>(1);
				// path01 = new ArrayList<>(1);
				// boolean test01 = graph.containsEdge(isa01);
				// boolean test10 = graph.containsEdge(isa10);
				// if (test01) {
				// path01.add(isa01);
				// }
				// if (test10) {
				// path10.add(isa10);
				// }
			}

			int score01 = scorePath(path01);
			int score10 = scorePath(path10);

			System.out.println(score01 + ": " + path01);
			System.out.println(score10 + ": " + path10);
			// left <-> right scores may be equal/similar
			if (Math.abs(score01 - score10) < 1) {
				// HashMap<String, ArrayList<StringEdge>> ancestors =
				// GraphAlgorithms.lowestCommonAncestorIsa(graph, concept0,
				// concept1, false, true);
				return; // eh, better abort
			}

			String newConcept, oldConcept;
			if (score01 < score10) {
				newConcept = concept0;
				oldConcept = concept1;
			} else {
				newConcept = concept1;
				oldConcept = concept0;
			}

			StringEdge reversedIsa = new StringEdge(newConcept, oldConcept, "isa");
			if (!graph.containsEdge(reversedIsa.reverse())) {
				graph.addEdge(reversedIsa);
			}
			System.out.println("->\t" + reversedIsa);

			// all of those X who were specie will be now eukaryote
			// generalizeFromSequenceLock.readLock().lock();
			// delete only those X which were both specie and eukaryote OR all
			// X?
			Collection<StringEdge> toChange;
			Set<StringEdge> incomingOld = graph.incomingEdgesOf(oldConcept, "isa");
			if (fullGeneralizer) {
				toChange = incomingOld;
			} else {
				Set<StringEdge> incomingNew = graph.incomingEdgesOf(newConcept, "isa");
				toChange = GraphAlgorithms.getEdgesWithSources(incomingOld,
						GraphAlgorithms.getEdgesSourcesAsSet(incomingNew));
			}
			// generalizeFromSequenceLock.readLock().unlock();

			// generalizeFromSequenceLock.writeLock().lock();
			for (StringEdge edge : toChange) {
				StringEdge newEdge = edge.replaceSourceOrTarget(oldConcept, newConcept);
				if (newEdge.isLoop())
					continue;
				if (graph.containsEdge(newEdge.reverse()))
					continue;
				graph.removeEdge(edge);
				graph.addEdge(newEdge);
			}
			// generalizeFromSequenceLock.writeLock().unlock();
		}
		// TODO: atlocation
		// else if (rel0.equals("-atlocation") && rel1.equals("+isa")) {
		// [moon, -atlocation, berzelius, +isa, crater]
		// add crater,atlocation,moon
		// delete X,atlocation,moon
		// graph.addEdge(target, "atlocation", source);
		// Set<StringEdge> toDelete = graph.incomingEdgesOf(target, "isa");
		// graph.removeEdges(toDelete);
		// }
	}

	private static void correctSymmetricRelations(String filename, StringGraph graph) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while (br.ready()) {
			String line = br.readLine().trim();
			if (!line.isEmpty()) {
				String[] tokens = line.split("\t+");
				String source = tokens[0];
				String relation = tokens[1];
				String target = tokens[2];
				String operation = tokens[3];

				StringEdge se = new StringEdge(source, target, relation);
				StringEdge se_reversed = se.reverse();

				// only continue if the graph contains both relations
				if (graph.containsEdge(se) && graph.containsEdge(se_reversed)) {
					switch (operation) {
					case "a":
						// remove both
						graph.removeEdge(se);
						graph.removeEdge(se_reversed);
						break;
					case "n":
						// remove the specified, keep the reversed
						graph.removeEdge(se);
						break;
					case "o":
						// specified Ok, remove the other
						graph.removeEdge(se_reversed);
						break;
					case "s":
						// remove both, replace by a synonym relation
						graph.removeEdge(se);
						graph.removeEdge(se_reversed);
						graph.addEdge(new StringEdge(source, target, "synonym"));
						break;
					}
				}
			}
		}
		br.close();
	}

	private static void correctRelations(String filename, StringGraph graph) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while (br.ready()) {
			String line = br.readLine().trim();
			if (!line.isEmpty()) {
				String[] tokens = line.split("\t+");
				String source = tokens[0];
				String relation = tokens[1];
				String target = tokens[2];
				String operation = tokens[3];

				StringEdge se = new StringEdge(source, target, relation);

				switch (operation) {
				case "r": // revert
					graph.removeEdge(se);
					graph.addEdge(se.reverse());
					break;
				case "n": // Not
				case "d": // Delete
					// remove the specified, keep the reversed
					graph.removeEdge(se);
					break;
				case "o": // OK
				case "k":
					// do nothing
					break;
				case "s": // Synonym
					// remove, replace by a synonym relation
					graph.removeEdge(se);
					graph.addEdge(new StringEdge(source, target, "synonym"));
					break;
				default:
					System.err.println("unrecognized option " + operation);
					break;
				}
			}
		}
		br.close();
	}

	private static ArrayList<ArrayList<String>> sequenceDetector(StringGraph graph, String refConcept,
			double generalizationThreshold) {
		ArrayList<ArrayList<String>> sequencesToGeneralize = new ArrayList<>();
		ArrayList<ObjectCount<ArrayList<String>>> sortedCount;
		// do the sequence counting
		{
			ObjectCounter<ArrayList<String>> sequenceCounter = new ObjectCounter<>();
			for (StringEdge edge0 : graph.edgesOf(refConcept)) {
				String relation0 = edge0.getLabel();

				String concept01 = edge0.getOppositeOf(refConcept);
				String dir0 = "+";
				if (edge0.incomesTo(refConcept)) {
					dir0 = "-";
				} else {
				}

				for (StringEdge edge1 : graph.edgesOf(concept01)) {
					String relation1 = edge1.getLabel();
					String concept10 = edge1.getOppositeOf(concept01);
					if (concept10.equals(refConcept)) { // no back loops
						continue;
					}

					String dir1 = "+";
					if (edge1.incomesTo(concept01)) {
						dir1 = "-";
					} else {
					}
					ArrayList<String> sequence = new ArrayList<>(5);
					sequence.add(refConcept); // first
					sequence.add(dir0 + relation0);
					sequence.add("X"); // sequence.add(concept01); // opposing
										// first
					sequence.add(dir1 + relation1);
					sequence.add(concept10); // last
					sequenceCounter.addObject(sequence);
				}
			}
			sortedCount = sequenceCounter.getSortedCount();
		}

		// normalize sequence counts
		{
			for (ObjectCount<ArrayList<String>> sequence : sortedCount) {
				// [build, -isa, X, +isa, architectural_structure]
				ArrayList<String> array = sequence.getId();
				int count = sequence.getCount();
				int degree0;
				String relationCode = array.get(1);
				String rel = relationCode.substring(1);
				if (relationCode.charAt(0) == '+') {
					Set<StringEdge> oe = graph.outgoingEdgesOf(refConcept, rel);
					degree0 = oe.size();
				} else {
					Set<StringEdge> ie = graph.incomingEdgesOf(refConcept, rel);
					degree0 = ie.size();
				}
				double normalizedCount = (double) count / degree0;
				if (normalizedCount < generalizationThreshold) {
					break;
				}
				sequencesToGeneralize.add(array);
				// System.out.println(sequence + "\t" + degree0 + "\t" +
				// normalizedCount);
			}
		}

		return sequencesToGeneralize;
	}

	public static void toSystemOut(ArrayList<ObjectCount<ArrayList<String>>> counts) {
		for (ObjectCount<ArrayList<String>> count : counts) {
			// if (count.getCount() < lowLimit)
			// break;
			System.out.println(count);
		}
	}

	public static int getSum(ArrayList<ObjectCount<ArrayList<String>>> counts) {
		int sum = 0;
		for (ObjectCount<ArrayList<String>> count : counts) {
			sum += count.getCount();
		}
		return sum;
	}

	private static void studyDegree(StringGraph graph) {
		ObjectCounter<Integer> degreeCounter = new ObjectCounter<>();
		for (String concept : graph.getVertexSet()) {
			int degree = graph.degreeOf(concept);
			degreeCounter.addObject(degree);
		}
		degreeCounter.toSystemOut();
	}

	private static void studyTargetDegree(StringGraph graph, String label, String target) {
		Set<StringEdge> in = graph.incomingEdgesOf(target, label);
		ObjectCounter<String> degreeCounter = new ObjectCounter<>();
		for (StringEdge edge : in) {
			String source = edge.getSource();
			if (degreeCounter.containsObject(source))
				continue;
			int degree = graph.degreeOf(source);
			degreeCounter.setCount(source, degree);
		}
		degreeCounter.toSystemOut();
	}

	private static void studyTargetDegree(StringGraph graph, String label) {
		Set<StringEdge> in = graph.edgeSet(label);
		ObjectCounter<String> degreeCounter = new ObjectCounter<>();
		for (StringEdge edge : in) {
			String target = edge.getTarget();
			if (degreeCounter.containsObject(target))
				continue;
			int degree = graph.degreeOf(target);
			degreeCounter.setCount(target, degree);
		}
		degreeCounter.toSystemOut();
	}

	private static HashSet<String> getDirectedConceptsOfRelation(StringGraph graph, String relation, boolean outgoing,
			int threshold) {
		ObjectCounter<String> conceptDegree = new ObjectCounter<>();
		ObjectOpenHashSet<String> closedSet = new ObjectOpenHashSet<>();
		// find a concept that
		for (String concept : graph.getVertexSet()) {
			if (closedSet.contains(concept)) {
				continue;
			}
			// must be a target/source of the relation
			Set<StringEdge> ie;
			if (outgoing) {
				ie = graph.outgoingEdgesOf(concept, relation);
			} else {
				ie = graph.incomingEdgesOf(concept, relation);
			}
			if (ie.isEmpty()) {
				continue;
			}
			int degreeOf = ie.size();
			conceptDegree.addObject(concept, degreeOf);
			closedSet.add(concept);
		}
		ArrayList<ObjectCount<String>> sortedCount = conceptDegree.getSortedCount();
		HashSet<String> concepts = new HashSet<>();
		for (ObjectCount<String> count : sortedCount) {
			String concept = count.getId();
			int degree = count.getCount();
			if (degree < threshold)
				break;
			System.out.printf("%s\t%d\n", concept, degree);
			concepts.add(concept);
		}
		return concepts;
	}

	private static void showRelationCount(StringGraph graph) {
		System.out.println("relation count:");
		ObjectCounter<String> relationCounter = getRelationCount(graph);
		relationCounter.toSystemOut();
	}

	private static ObjectCounter<String> getRelationCount(StringGraph graph) {
		ObjectCounter<String> relationCounter = new ObjectCounter<>();
		for (StringEdge edge : graph.edgeSet()) {
			String label = edge.getLabel();
			String source = edge.getSource();
			String target = edge.getTarget();
			// if (source.contains("//")) {
			relationCounter.addObject(label);
			// }
		}
		return relationCounter;
	}

	private static void reverseEdges(StringGraph graph) {
		// rela��es �X,/r/hasa,Y� para �Y,/r/partof,X�.
		ArrayList<StringEdge> toRemove = new ArrayList<>();
		ArrayList<StringEdge> toAdd = new ArrayList<>();
		for (StringEdge edge : graph.edgeSet()) {
			String label = edge.getLabel();
			String source = edge.getSource();
			String target = edge.getTarget();
			if (label.equals("/r/dbpedia/influenced")) {
				toRemove.add(edge);
				StringEdge ne = new StringEdge(target, source, "/r/dbpedia/influencedby");
				toAdd.add(ne);
			}
		}
		graph.removeEdges(toRemove);
		graph.addEdges(toAdd);
	}

	private static void removeRelationsWithLabel(StringGraph graph, String label) {
		ArrayList<StringEdge> toRemove = new ArrayList<>();
		for (StringEdge edge : graph.edgeSet()) {
			if (edge.getLabel().equals(label)) {
				toRemove.add(edge);
			}
		}
		graph.removeEdges(toRemove);
	}

	/**
	 * remove edges which connect two exclusive concepts
	 * 
	 * @param graph
	 */

	private static void removeDisconnectedEdges(StringGraph graph) {
		ArrayList<StringEdge> toRemove = new ArrayList<>();
		for (StringEdge edge : graph.edgeSet()) {
			String source = edge.getSource();
			String target = edge.getTarget();
			int tdegree = graph.degreeOf(target);
			int sdegree = graph.degreeOf(source);
			if (tdegree <= 1 && sdegree <= 1) {
				// System.out.println(edge);
				toRemove.add(edge);
			}
		}
		graph.removeEdges(toRemove);
	}

	private static void removeConceptsWithDegreeBelow(StringGraph graph, String typeOfConcept, int threshold) {

		ArrayList<String> conceptsToRemove = new ArrayList<>();
		Set<StringEdge> isaConcept = graph.incomingEdgesOf(typeOfConcept, "isa");
		for (StringEdge edge : isaConcept) {
			String source = edge.getSource();
			// String target = edge.getTarget();

			int degree = graph.degreeOf(source);

			if (degree == 2) {
				// System.out.printf("%s\t%d\t%s\n", source, degree,
				// graph.edgesOf(source));

			}

			if (degree < threshold) {
				Set<StringEdge> edgesOf = graph.edgesOf(source);
				System.out.printf("%s\t%d\t%s\n", source, degree, edgesOf);
				//
				// try {
				// Thread.sleep(250);
				// } catch (InterruptedException e) {
				// }

				conceptsToRemove.add(source);
			} else {
			}

		}
		System.out.print("removing " + conceptsToRemove.size() + " " + typeOfConcept + " concepts...");
		graph.removeVertices(conceptsToRemove);

		System.out.println(" done.");
	}

	public static void studyDegree(StringGraph graph, Collection<String> concepts) {
		System.out.println("sorting...");

		ArrayList<String> orderedConcepts = new ArrayList<>(concepts);
		orderedConcepts.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int degree1 = graph.degreeOf(o1);
				int degree2 = graph.degreeOf(o2);
				return -Integer.compare(degree1, degree2);
			}
		});

		for (int i = 0; i < 256; i++) {
			String concepti = orderedConcepts.get(i);
			System.out.printf("%s %d\n", concepti, graph.degreeOf(concepti));
			// studyEdges(graph, concepti);
		}
	}

	private static void studyEdgeLabels(StringGraph graph) {
		studyEdgeLabels(graph, null);
	}

	private static void studyEdgeLabels(StringGraph graph, String concepti) {
		// count number of edges of each label connected to the given concept
		ObjectCounter<String> labelCounter = new ObjectCounter<>();
		Set<StringEdge> edges;
		if (concepti != null)
			edges = graph.edgesOf(concepti);
		else
			edges = graph.edgeSet();
		for (StringEdge edge : edges) {
			String label = edge.getLabel();
			labelCounter.addObject(label);
		}

		labelCounter.toSystemOut();
	}

	private static void showConceptsDegree(StringGraph graph) {
		ObjectCounter<String> degreeCounter = new ObjectCounter<>();
		for (String concept : graph.getVertexSet()) {
			degreeCounter.addObject(concept, graph.degreeOf(concept));
		}
		ArrayList<ObjectCount<String>> sortedCount = degreeCounter.getSortedCount();
		for (ObjectCount<String> oc : sortedCount) {
			String concept = oc.getId();
			int inDegree = graph.getInDegree(concept);
			int outDegree = graph.getOutDegree(concept);
			int degree = oc.getCount();
			System.out.printf("%s\t%d\t%d\t%d\n", concept, degree, inDegree, outDegree);
		}
	}

	public static void memoryUsage() {
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
		System.out.println("memory usage: " + usedMB);
	}

}
