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
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Book implements Matchable {

	/*
	 * Wiki
	<book id="wiki_books_1">
		<title>Animal Farm</title>
		<author>George Orwell</author>
		<description> Old Major, the old boar on the Manor Farm, calls the animals on the farm for a meeting, where he compares the humans to parasites and teaches the animals a revolutionary song, 'Beasts of England'. When Major dies, two young pigs, Snowball and Napoleon, assume command and turn his dream into a philosophy. The animals revolt and drive the drunken and irresponsible Mr Jones from the farm, renaming it "Animal Farm". They adopt Seven Commandments of Animal-ism, the most important of which is, "All animals are equal". Snowball attempts to teach the animals reading and writing; food is plentiful, and the farm runs smoothly. The pigs elevate themselves to positions of leadership and set aside special food items, ostensibly for their personal health. Napoleon takes the pups from the farm dogs and trains them privately. Napoleon and Snowball struggle for leadership. When Snowball announces his plans to build a windmill, Napoleon has his dogs chase Snowball away and declares himself leader. Napoleon enacts changes to the governance structure of the farm, replacing meetings with a committee of pigs, who will run the farm. Using a young pig named Squealer as a "mouthpiece", Napoleon claims credit for the windmill idea. The animals work harder with the promise of easier lives with the windmill. After a violent storm, the animals find the windmill annihilated. Napoleon and Squealer convince the animals that Snowball destroyed it, although the scorn of the neighbouring farmers suggests that its walls were too thin. Once Snowball becomes a scapegoat, Napoleon begins purging the farm with his dogs, killing animals he accuses of consorting with his old rival. He and the pigs abuse their power, imposing more control while reserving privileges for themselves and rewriting history, villainising Snowball and glorifying Napoleon. Squealer justifies every statement Napoleon makes, even the pigs' alteration of the Seven Commandments of Animalism to benefit themselves. 'Beasts of England' is replaced by an anthem glorifying Napoleon, who appears to be adopting the lifestyle of a man. The animals remain convinced that they are better off than they were when under Mr Jones. Squealer abuses the animals' poor memories and invents numbers to show their improvement. Mr Frederick, one of the neighbouring farmers, attacks the farm, using blasting powder to blow up the restored windmill. Though the animals win the battle, they do so at great cost, as many, including Boxer the workhorse, are wounded. Despite his injuries, Boxer continues working harder and harder, until he collapses while working on the windmill. Napoleon sends for a van to take Boxer to the veterinary surgeon's, explaining that better care can be given there. Benjamin, the cynical donkey, who "could read as well as any pig", notices that the van belongs to a knacker, and attempts to mount a rescue; but the animals' attempts are futile. Squealer reports that the van was purchased by the hospital and the writing from the previous owner had not been repainted. He recounts a tale of Boxer's death in the hands of the best medical care. Years pass, and the pigs learn to walk upright, carry whips and wear clothes. The Seven Commandments are reduced to a single phrase: "All animals are equal, but some animals are more equal than others". Napoleon holds a dinner party for the pigs and the humans of the area, who congratulate Napoleon on having the hardest-working but least fed animals in the country. Napoleon announces an alliance with the humans, against the labouring classes of both "worlds". He abolishes practices and traditions related to the Revolution, and changes the name of the farm to "The Manor Farm". The animals, overhearing the conversation, notice that the faces of the pigs have begun changing. During a poker match, an argument breaks out between Napoleon and Mr Pilkington, and the animals realise that the faces of the pigs look like the faces of humans, and no one can tell the difference between them. The pigs Snowball, Napoleon, and Squealer adapt Old Major's ideas into an actual philosophy, which they formally name Animalism. Soon after, Napoleon and Squealer indulge in the vices of humans (drinking alcohol, sleeping in beds, trading). Squealer is employed to alter the Seven Commandments to account for this humanisation, an allusion to the Soviet government's revising of history in order to exercise control of the people's beliefs about themselves and their society. The original commandments are: # Whatever goes upon two legs is an enemy. # Whatever goes upon four legs, or has wings, is a friend. # No animal shall wear clothes. # No animal shall sleep in a bed. # No animal shall drink alcohol. # No animal shall kill any other animal. # All animals are equal. Later, Napoleon and his pigs secretly revise some commandments to clear them of accusations of law-breaking (such as "No animal shall drink alcohol" having "to excess" appended to it and "No animal shall sleep in a bed" with "with sheets" added to it). The changed commandments are as follows, with the changes bolded: * 4 No animal shall sleep in a bed with sheets. * 5 No animal shall drink alcohol to excess. * 6 No animal shall kill any other animal without cause. Eventually these are replaced with the maxims, "All animals are equal, but some animals are more equal than others", and "Four legs good, two legs better!" as the pigs become more human. This is an ironic twist to the original purpose of the Seven Commandments, which were supposed to keep order within Animal Farm by uniting the animals together against the humans, and prevent animals from following the humans' evil habits. Through the revision of the commandments, Orwell demonstrates how simply political dogma can be turned into malleable propaganda.</description>
		<genres>'Roman à clef', 'Satire', "Children's literature", 'Speculative fiction', 'Fiction'</genres>
		<publishDate>1945-08-17</publishDate>
	</book>
	
	* Zenodo
	<book id="B00001">
		<title>The Hunger Games</title>
		<author>Suzanne Collins</author>
		<rating>4.33</rating>
		<description>WINNING MEANS FAME AND FORTUNE.LOSING MEANS CERTAIN DEATH.THE HUNGER GAMES HAVE BEGUN. . . .In the ruins of a place once known as North America lies the nation of Panem, a shining Capitol surrounded by twelve outlying districts. The Capitol is harsh and cruel and keeps the districts in line by forcing them all to send one boy and once girl between the ages of twelve and eighteen to participate in the annual Hunger Games, a fight to the death on live TV.Sixteen-year-old Katniss Everdeen regards it as a death sentence when she steps forward to take her sister's place in the Games. But Katniss has been close to dead before—and survival, for her, is second nature. Without really meaning to, she becomes a contender. But if she is to win, she will have to start making choices that weight survival against humanity and life against love.</description>
		<language>English</language>
		<isbn>9780439023481</isbn>
		<genres>'Young Adult', 'Fiction', 'Dystopia', 'Fantasy', 'Science Fiction', 'Romance', 'Adventure', 'Teen', 'Post Apocalyptic', 'Action'</genres>
		<pages>374</pages>
		<publisher>Scholastic Press</publisher>
		<publishDate>2008-09-14</publishDate>
		<numRatings>6376780</numRatings>
		<coverImg>https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1586722975l/2767052.jpg</coverImg>
	</book>
	 */

	protected String id;
	protected String provenance;
	private String title;
	private String author;
	private String description;
	private String language;
	private String isbn;
	private String genres;
	private int pages;
	private String publisher;
	private LocalDateTime publishDate;
	private String coverImg;

	public Book(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
	}

	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Override
	public String getProvenance() {
		return provenance;
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
	
	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
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
		if(obj instanceof Book){
			return this.getIdentifier().equals(((Book) obj).getIdentifier());
		}else
			return false;
	}	
	
}
