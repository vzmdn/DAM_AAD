package exUnit4.entity;
// Generated 25 oct 2024, 9:14:26 by Hibernate Tools 4.3.6.Final

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Books generated by hbm2java
 */
@Entity
@Table(name = "books", catalog = "biblio")
public class Books implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codauthor")
	private Authors authors;
	
	@Column(name = "title", length = 60)
	private String title;

	public Books() {
	}

	public Books(int id) {
		this.id = id;
	}

	public Books(int id, Authors authors, String title) {
		this.id = id;
		this.authors = authors;
		this.title = title;
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Authors getAuthors() {
		return this.authors;
	}

	public void setAuthors(Authors authors) {
		this.authors = authors;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
