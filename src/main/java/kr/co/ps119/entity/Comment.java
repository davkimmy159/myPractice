package kr.co.ps119.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 5199824822705432663L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@Column
	@NotBlank(message = "content is empty")
	@Lob
	private String content;
	
	@Column
	@NotNull(message = "date is empty")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	// foreign key 1
	@ManyToOne(fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   optional = false)
	@JoinColumn(name = "board_id",
				nullable = true)
	@JsonManagedReference
	private Board board;
	
	public Comment() {
	}

	public Comment(String content, Date createDate) {
		this.content = content;
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	/*
	public void setId(Long id) {
		this.id = id;
	}
	*/

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
		
		if(!(board.getComments().contains(this))) {
			board.getComments().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getContent() == null) ? 0 : content.hashCode());
		result = prime * result + ((getCreateDate() == null) ? 0 : createDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(!(obj instanceof Board))
			return false;
		/*
		if (getClass() != obj.getClass())
			return false;
		*/
		Comment other = (Comment) obj;
		if (getContent() == null) {
			if (other.content != null)
				return false;
		} else if (!getContent().equals(other.content))
			return false;
		if (getCreateDate() == null) {
			if (other.createDate != null)
				return false;
		} else if (!getCreateDate().equals(other.createDate))
			return false;
		return true;
	}
	
	
}
