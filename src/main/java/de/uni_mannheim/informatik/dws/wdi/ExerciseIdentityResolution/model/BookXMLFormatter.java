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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class BookXMLFormatter extends XMLFormatter<Book> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(Book record, Document doc) {
		Element book = doc.createElement("book");

		book.appendChild(createTextElement("id", record.getIdentifier(), doc));

		book.appendChild(createTextElement("title",
				record.getTitle(),
				doc));
		book.appendChild(createTextElement("author",
				record.getAuthor(),
				doc));
		book.appendChild(createTextElement("publishDate", record
				.getPublishDate().toString(), doc));

		// book.appendChild(createGenresElement(record, doc));

		return book;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	// protected Element createGenresElement(DBPedia_Zenodo_Book record, Document doc) {
	// 	Element actorRoot = actorFormatter.createRootElement(doc);

	// 	for (Zenodo_Book a : record.getGenres()) {
	// 		actorRoot.appendChild(actorFormatter
	// 				.createElementFromRecord(a, doc));
	// 	}

	// 	return actorRoot;
	// }

}
