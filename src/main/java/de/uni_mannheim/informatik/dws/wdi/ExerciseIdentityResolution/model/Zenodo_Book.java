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
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Zenodo_Book implements Matchable {

	/*
	 <book id="B00001">
		<title>The Hunger Games</title>
		<author>Suzanne Collins</author>
		<rating>4.33</rating>
		<description>WINNING MEANS FAME AND FORTUNE.LOSING MEANS CERTAIN DEATH.THE HUNGER GAMES HAVE BEGUN. . . .In the ruins of a place once known as North America lies the nation of Panem, a shining Capitol surrounded by twelve outlying districts. The Capitol is harsh and cruel and keeps the districts in line by forcing them all to send one boy and once girl between the ages of twelve and eighteen to participate in the annual Hunger Games, a fight to the death on live TV.Sixteen-year-old Katniss Everdeen regards it as a death sentence when she steps forward to take her sister's place in the Games. But Katniss has been close to dead before—and survival, for her, is second nature. Without really meaning to, she becomes a contender. But if she is to win, she will have to start making choices that weight survival against humanity and life against love.</description>
		<language>English</language>
		<isbn>9780439023481</isbn>
		<genres>['Young Adult', 'Fiction', 'Dystopia', 'Fantasy', 'Science Fiction', 'Romance', 'Adventure', 'Teen', 'Post Apocalyptic', 'Action']</genres>
		<pages>374</pages>
		<publisher>Scholastic Press</publisher>
		<publishDate>09/14/08</publishDate>
		<numRatings>6376780</numRatings>
		<coverImg>https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1586722975l/2767052.jpg</coverImg>
	</book>
	 */

	protected String id;
	protected String provenance;
	
	private String title;
	private String author;
	private float rating;
	private String description;
	private String language;
	private String isbn;
	private List<String> genres;
	private int pages;
	private String publisher;
	private LocalDateTime publishDate;
	private String coverImg;
	

	public Zenodo_Book(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> gnr) {
		this.genres = gnr;
	}
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
	}
	
	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	@Override
	public String toString() {
		return String.format("[Book %s: %s / %s / %s / %s / %s]", getIdentifier(), getTitle(), getAuthor(), getIsbn(), getPublisher(), getPublishDate().toString());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Zenodo_Book){
			return this.getIdentifier().equals(((Zenodo_Book) obj).getIdentifier());
		}else
			return false;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}	
}
