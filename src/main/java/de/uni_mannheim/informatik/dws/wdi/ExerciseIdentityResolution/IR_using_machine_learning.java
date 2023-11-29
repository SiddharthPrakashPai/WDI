package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookPublishDateComparator10Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookPublishDateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookAuthorComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookAuthorComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookAuthorComparatorLowerCaseJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookTitleComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookIsbnComparator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBPedia_Zenodo_BookXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBPedia_Zenodo_Book;
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

		String first_ds = "Zenodo";
		String second_ds = "Wikipedia";

		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<DBPedia_Zenodo_Book, Attribute> dataDBPedia = new HashedDataSet<>();
		new DBPedia_Zenodo_BookXMLReader().loadFromXML(new File("data/input/" + first_ds + "_books_schema.xml"), "/books/book",
				dataDBPedia);
		HashedDataSet<DBPedia_Zenodo_Book, Attribute> dataZenodo = new HashedDataSet<>();
		new DBPedia_Zenodo_BookXMLReader().loadFromXML(new File("data/input/" + second_ds + "_books_schema.xml"), "/books/book",
				dataZenodo);

		// load the training set
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/" + first_ds + "_" + second_ds + "_GS_train.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic"; // use a logistic regression
		WekaMatchingRule<DBPedia_Zenodo_Book, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule_" + first_ds + "_" + second_ds + ".csv",
				1000, gsTraining);

		// add comparators
		matchingRule.addComparator(new BookTitleComparatorEqual());
		matchingRule.addComparator(new BookTitleComparatorLevenshtein());
		matchingRule.addComparator(new BookTitleComparatorJaccard());
		matchingRule.addComparator(new BookPublishDateComparator2Years());
		matchingRule.addComparator(new BookPublishDateComparator10Years());
		matchingRule.addComparator(new BookAuthorComparatorJaccard());
		matchingRule.addComparator(new BookAuthorComparatorLevenshtein());
		matchingRule.addComparator(new BookIsbnComparator());

		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<DBPedia_Zenodo_Book, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataDBPedia, dataZenodo, null, matchingRule, gsTraining);
		logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		
		
		  // create a blocker (blocking strategy)
		  StandardRecordBlocker<DBPedia_Zenodo_Book, Attribute> blocker = new StandardRecordBlocker<DBPedia_Zenodo_Book, Attribute>(new BookBlockingKeyByTitleGenerator()); 
//		  SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByDecadeGenerator(), 1); 
		  blocker.collectBlockSizeData("data/output/debugResultsBlocking_" + first_ds + "_" + second_ds + ".csv", 100);
		 

		  // Initialize Matching Engine 
		  MatchingEngine<DBPedia_Zenodo_Book, Attribute> engine = new MatchingEngine<>();
		  
		  // Execute the matching 
		  logger.info("*\tRunning identity resolution\t*");
		  Processable<Correspondence<DBPedia_Zenodo_Book, Attribute>> correspondences =
		  engine.runIdentityResolution( dataDBPedia, dataZenodo, null, matchingRule,blocker);
		  
		  // write the correspondences to the output file 
		  new CSVCorrespondenceFormatter().writeCSV(new
		  File("data/output/" + first_ds + "_" + second_ds + "_correspondences.csv"), correspondences);
		  
		  // load the gold standard (test set)
		  logger.info("*\tLoading gold standard\t*"); MatchingGoldStandard gsTest = new
		  MatchingGoldStandard(); gsTest.loadFromCSVFile(new File(
		  "data/goldstandard/" + first_ds + "_" + second_ds + "_GS_test.csv"));
		  
		  // evaluate your result `
		  logger.info("*\tEvaluating result\t*");
		  MatchingEvaluator<DBPedia_Zenodo_Book, Attribute> evaluator = new
		  MatchingEvaluator<DBPedia_Zenodo_Book, Attribute>(); Performance perfTest =
		  evaluator.evaluateMatching(correspondences, gsTest);
		  
		  // print the evaluation result 
		  logger.info(first_ds + " <-> " + second_ds);
		  logger.info(String.format( "Precision: %.4f",perfTest.getPrecision()));
		  logger.info(String.format( "Recall: %.4f", perfTest.getRecall()));
		  logger.info(String.format( "F1: %.4f",perfTest.getF1()));
		 
		System.out.println("Done");
	}
}
