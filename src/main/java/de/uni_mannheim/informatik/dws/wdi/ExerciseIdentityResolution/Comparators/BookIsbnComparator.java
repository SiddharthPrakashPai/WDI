package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;


import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBPedia_Zenodo_Book;


public class BookIsbnComparator implements Comparator<DBPedia_Zenodo_Book, Attribute> {

	private static final long serialVersionUID = 1L;
	private EqualsSimilarity<String> sim = new EqualsSimilarity<String>();
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			DBPedia_Zenodo_Book record1,
			DBPedia_Zenodo_Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {
		
    	String s1 = record1.getIsbn();
		String s2 = record2.getIsbn();
    	
    	double similarity = sim.calculate(s1, s2);
    	
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}
		return similarity;
	}


	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}
