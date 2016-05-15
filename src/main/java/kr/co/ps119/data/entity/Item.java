package kr.co.ps119.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	Item() {
	}
	
	public Item(String title, String content) {
		this(null, title, content);
	}

	public Item(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
