/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBPedia_Zenodo_Book;

public class BookAuthorComparatorJaccard implements Comparator<DBPedia_Zenodo_Book, Attribute> {

	private static final long serialVersionUID = 1L;
	TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	private ComparatorLogger comparisonLog;
	
	@Override
	public double compare(
			DBPedia_Zenodo_Book record1,
			DBPedia_Zenodo_Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		// 1. Preprocessing
		String s1 = record1.getAuthor();
		String s2 = record2.getAuthor();
		// 1.a Eliminate commas
		// 1.b Replace underscores for spaces (for Dbpedia authors)
		// 1.c Eliminate info between parenthesis (for Goodread's authors)
		if (s1 != null) {
			s1 = s1.replace(",", "").replace("_", " ").replaceAll("\\([^)]*\\)", "").toLowerCase();
		}
		if (s2 != null) {
			s2 = s2.replace(",", "").replace("_", " ").replaceAll("\\([^)]*\\)", "").toLowerCase();
		}
		// 2. Calculate similarity
		double similarity = sim.calculate(s1, s2);

		// 3. Postprocessing
		int postSimilarity = 1;
		if (similarity <= 0.3) {
			postSimilarity = 0;
		}

		postSimilarity *= similarity;
		
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
    	
			this.comparisonLog.setSimilarity(Double.toString(similarity));
			this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
		}
		return postSimilarity;
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
