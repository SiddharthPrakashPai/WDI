package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_machine_learning {

	/*
	 * Logging Options: default: level INFO - console trace: level TRACE - console
	 * infoFile: level INFO - console/file traceFile: level TRACE - console/file
	 * 
	 * To set the log level to trace and write the log to winter.log and console,
	 * activate the "traceFile" logger as follows: private static final Logger
	 * logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main(String[] args) throws Exception {

		String firstDsName = "DBPedia";
		String secondDsName = "Zenodo";

		// loading data
		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Book, Attribute> firstDs = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/" + firstDsName + "_books_schema.xml"), "/books/book",
				firstDs);
		HashedDataSet<Book, Attribute> secondDs = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/" + secondDsName + "_books_schema.xml"), "/books/book",
				secondDs);

		// load the training set
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/" + firstDsName + "_" + secondDsName + "_GS_train.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic"; // use a logistic regression
		WekaMatchingRule<Book, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule.activateDebugReport("data/output/matching_rule/debugResultsMatchingRule_" + firstDsName + "_" + secondDsName + ".csv",
				1000, gsTraining);

		// add comparators
		matchingRule.addComparator(new BookTitleComparatorEqual());
		matchingRule.addComparator(new BookTitleComparatorLevenshtein());
		matchingRule.addComparator(new BookLanguageComparatorJaccard());
		matchingRule.addComparator(new BookTitleComparatorJaccard());
		matchingRule.addComparator(new BookPublishDateComparator2Years());
		matchingRule.addComparator(new BookPublishDateComparator10Years());
		matchingRule.addComparator(new BookAuthorComparatorJaccard());
		matchingRule.addComparator(new BookAuthorComparatorLevenshtein());
		matchingRule.addComparator(new BookIsbnComparator());

		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<Book, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(firstDs, secondDs, null, matchingRule, gsTraining);
		logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		
		
		  // create a blocker (blocking strategy)
		  // No blocker
//		  NoBlocker<Book, Attribute> blocker = new NoBlocker<>();
		  // Standard Blocker
		  StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(new BookBlockingKeyByTitleGenerator());
		  // Sorted Neighbourhood Blocker
//		   SortedNeighbourhoodBlocker<Book, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new BookBlockingKeyByYearGenerator(), 300);
		  blocker.collectBlockSizeData("data/output/blocking/debugResultsBlocking_" + firstDsName + "_" + secondDsName + ".csv", 100);


		  // Initialize Matching Engine
		  MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		  // Execute the matching
		  logger.info("*\tRunning identity resolution\t*");
		  Processable<Correspondence<Book, Attribute>> correspondences =
		  engine.runIdentityResolution( firstDs, secondDs, null, matchingRule,blocker);

		  // write the correspondences to the output file
		  new CSVCorrespondenceFormatter().writeCSV(new
		  File("data/output/correspondences/" + firstDsName + "_" + secondDsName + "_correspondences.csv"), correspondences);

		  // load the gold standard (test set)
		  logger.info("*\tLoading gold standard\t*"); MatchingGoldStandard gsTest = new
		  MatchingGoldStandard(); gsTest.loadFromCSVFile(new File(
		  "data/goldstandard/" + firstDsName + "_" + secondDsName + "_GS_test.csv"));

		  // evaluate your result `
		  logger.info("*\tEvaluating result\t*");
		  MatchingEvaluator<Book, Attribute> evaluator = new
		  MatchingEvaluator<Book, Attribute>(); Performance perfTest =
		  evaluator.evaluateMatching(correspondences, gsTest);

		  // print the evaluation result
		  logger.info(firstDsName + " <-> " + secondDsName);
		  logger.info(String.format( "Precision: %.4f",perfTest.getPrecision()));
		  logger.info(String.format( "Recall: %.4f", perfTest.getRecall()));
		  logger.info(String.format( "F1: %.4f",perfTest.getF1()));
		 
		System.out.println("Done");
	}
}
