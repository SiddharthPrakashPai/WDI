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
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class BookXMLReader extends XMLMatchableReader<Book, Attribute>  {

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<Book, Attribute> dataset) {
		super.initialiseDataset(dataset);
		
	}
	
	@Override
	public Book createModelFromElement(Node node, String provenanceInfo) {
		String id = node.getAttributes().getNamedItem("id").getNodeValue();

		// create the object with id and provenance information
		Book book = new Book(id, provenanceInfo);

		// fill the attributes
		book.setTitle(getValueFromChildElement(node, "title"));
		book.setAuthor(getValueFromChildElement(node, "author"));
		
		// convert the date string into a DateTime object
		
		try {
			String date = getValueFromChildElement(node, "publishDate");
			if (date != null && !date.isEmpty()) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				        .appendPattern("yyyy-MM-dd")
				        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				        .toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				book.setPublishDate(dt);
			}
			else if(date == null || date.isEmpty()){
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
						.appendPattern("yyyy-MM-dd")
						.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
						.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
						.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
						.toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse("1970-01-01", formatter);
				book.setPublishDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		book.setDescription(getValueFromChildElement(node, "description"));
		book.setLanguage(getValueFromChildElement(node, "language"));
		book.setIsbn(getValueFromChildElement(node, "isbn"));
		book.setGenres(getValueFromChildElement(node, "genres"));
		
		try {
			String pg = getValueFromChildElement(node, "pages");
			if (pg != null && !pg.isEmpty()) {
				Integer p = Integer.valueOf(pg);
				book.setPages(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		book.setPublisher(getValueFromChildElement(node, "publisher"));
		book.setCoverImg(getValueFromChildElement(node, "coverImg"));

		return book;
	}

}
