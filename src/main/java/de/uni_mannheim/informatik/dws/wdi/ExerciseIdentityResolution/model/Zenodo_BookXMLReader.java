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
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class Zenodo_BookXMLReader extends XMLMatchableReader<Zenodo_Book, Attribute> {

	@Override
	public Zenodo_Book createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Zenodo_Book book = new Zenodo_Book(id, provenanceInfo);

		// fill the attributes
		book.setTitle(getValueFromChildElement(node, "title"));
		book.setAuthor(getValueFromChildElement(node, "author"));
		
		String rt = getValueFromChildElement(node, "rating");
		Float r = Float.valueOf(rt);
		book.setRating(r);
		
		book.setDescription(getValueFromChildElement(node, "description"));
		book.setLanguage(getValueFromChildElement(node, "language"));
		book.setIsbn(getValueFromChildElement(node, "isbn"));
		
		String s = getValueFromChildElement(node, "genres");
		String[] strings = s.substring(1, s.length() - 1).split(",");
		List<String> gnr = Arrays.asList(strings);
		book.setGenres(gnr);
		
		String pg = getValueFromChildElement(node, "pages");
		Integer p = Integer.valueOf(pg);
		book.setPages(p);
		
		book.setTitle(getValueFromChildElement(node, "publisher"));
		

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "publishDate");
			if (date != null) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				        .appendPattern("yyyy-MM-dd")
				        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				        .toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				book.setPublishDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		book.setTitle(getValueFromChildElement(node, "numRatings"));
		book.setTitle(getValueFromChildElement(node, "coverImg"));
		
		return book;
	}


}
