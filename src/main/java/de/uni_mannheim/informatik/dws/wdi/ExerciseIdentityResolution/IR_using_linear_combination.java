package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
//import de.uni_mannheim.informatik.dws.winter.matching.algorithms.MaximumBipartiteMatchingAlgorithm;
//import de.uni_mannheim.informatik.dws.winter.matching.blockers.Blocker;
//import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
//import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");
	
    public static void main( String[] args ) throws Exception
    {
		String firstDsName = "DBPedia";
		String secondDsName = "Zenodo";

		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Book, Attribute> firstDs = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/" + firstDsName + "_books_schema.xml"),
				"/books/book", firstDs);
		HashedDataSet<Book, Attribute> secondDs = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/" + secondDsName + "_books_schema.xml"),
				"/books/book", secondDs);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/" + firstDsName + "_" + secondDsName + "_GS_test.csv"));

		// create a matching rule

		// Rule
		LinearCombinationMatchingRule<Book, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.8);
		// experiment with a matching rule with penalty
//		LinearCombinationMatchingRuleWithPenalty<Book, Attribute> matchingRuleWithPenalty = new LinearCombinationMatchingRuleWithPenalty<>(0.7);
		matchingRule.activateDebugReport("data/output/matching_rule/debugResultsMatchingRule.csv", 1000, gsTest);
		
		// add comparators
		// Rule 1: TitleJ&AuthorL th 0.8
		matchingRule.addComparator(new BookTitleComparatorJaccard(), 0.6);
		matchingRule.addComparator(new BookAuthorComparatorLevenshtein(), 0.4);
		// Rule 2: TitleJ&AuthorL&PublishDate&Isbn th 0.7
//		matchingRule.addComparator(new BookTitleComparatorJaccard(), 0.5);
//		matchingRule.addComparator(new BookAuthorComparatorLevenshtein(), 0.3);
//		matchingRule.addComparator(new BookPublishDateComparator2Years(), 0.2);
//		matchingRule.addComparator(new BookIsbnComparator(), 1);
		// Rule 3: TitleJ&AuthorL&Language&Isbn th 0.7
//		matchingRule.addComparator(new BookTitleComparatorJaccard(), 0.5);
//		matchingRule.addComparator(new BookAuthorComparatorLevenshtein(), 0.3);
//		matchingRule.addComparator(new BookLanguageComparatorJaccard(), 0.2);
//		matchingRule.addComparator(new BookIsbnComparator(), 1);
		// Rule Extra: TitleJNoPp&AuthorLNoPp th 0.7
//		matchingRule.addComparator(new BookTitleComparatorJaccardNoPp(), 0.6);
//		matchingRule.addComparator(new BookAuthorComparatorLevenshteinNoPp(), 0.4);


		// create a blocker (blocking strategy)
		StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(new BookBlockingKeyByTitleGenerator());
//		NoBlocker<Book, Attribute> blocker = new NoBlocker<>();
//		SortedNeighbourhoodBlocker<Book, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new BookBlockingKeyByYearGenerator(), 300);
		blocker.setMeasureBlockSizes(true);
		//Write debug results to file:
		blocker.collectBlockSizeData("data/output/blocking/debugResultsBlocking.csv", 100);
		
		// Initialize Matching Engine
		MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Book, Attribute>> correspondences = engine.runIdentityResolution(
				firstDs, secondDs, null, matchingRule,
				blocker);

		// Create a top-1 global matching
//		  correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.0);

//		 Alternative: Create a maximum-weight, bipartite matching
//		 MaximumBipartiteMatchingAlgorithm<Movie,Attribute> maxWeight = new MaximumBipartiteMatchingAlgorithm<>(correspondences);
//		 maxWeight.run();
//		 correspondences = maxWeight.getResult();

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/lc_" + firstDsName + "_" + secondDsName + "_correspondences.csv"),
				correspondences);
		
		logger.info("*\tEvaluating result\t*");
		// evaluate your result
		MatchingEvaluator<Book, Attribute> evaluator = new MatchingEvaluator<Book, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		logger.info(firstDsName + " <-> " + secondDsName);
		logger.info(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.4f",perfTest.getF1()));
    }
}
