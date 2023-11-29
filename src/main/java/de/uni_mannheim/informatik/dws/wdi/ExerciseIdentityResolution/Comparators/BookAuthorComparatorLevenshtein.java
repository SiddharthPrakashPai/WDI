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
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBPedia_Zenodo_Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookAuthorComparatorLevenshtein implements Comparator<DBPedia_Zenodo_Book, Attribute> {

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			DBPedia_Zenodo_Book record1,
			DBPedia_Zenodo_Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		String s1 = record1.getAuthor();
		String s2 = record2.getAuthor();
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
		}

		// 1. Preprocessing
		// 1.a Replace underscores for spaces (for Dbpedia authors)
		// 1.b Eliminate info between parenthesis (for Goodread's authors)
		List<Double> similarities = new ArrayList<>();
		double similarity;
		if (s1 != null && s2 != null) {
			s1 = s1.replace("_", " ").replaceAll("\\([^)]*\\)", "").toLowerCase();
			s2 = s2.replace("_", " ").replaceAll("\\([^)]*\\)", "").toLowerCase();
			String[] authors_record1 = s1.split(",");
			String[] authors_record2 = s2.split(",");
			// 2. Calculate similarity between each pair of authors
			for (String author1 : authors_record1) {
				for (String author2 : authors_record2) {
					similarity = sim.calculate(author1.trim(), author2.trim());
					similarities.add(similarity);
				}
			}
		}

		// 2.a Obtain the final similarity
		if (similarities.isEmpty()) {
			similarity = sim.calculate(s1, s2);
		} else {
			similarity = Collections.max(similarities);
		}
    	
		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(s1);
			this.comparisonLog.setRecord2PreprocessedValue(s2);
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
