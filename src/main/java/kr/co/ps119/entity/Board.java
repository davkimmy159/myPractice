package kr.co.ps119.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Board implements Serializable {

	private static final long serialVersionUID = -4939299278182413724L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;
	
	@Column
	@NotBlank(message = "title is empty")
	private String title;
	
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
	@JoinColumn(name = "member_id",
				nullable = true)
	@JsonManagedReference
	private Member member;
	
	// child
	@OneToMany(mappedBy = "board",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<Comment> comments = new ArrayList<>();
	
	public Board() {
	}

	public Board(String title, String content, Date createDate) {
		this.title = title;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		
		if(!(member.getBoards().contains(this))) {
			member.getBoards().add(this);
		}
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		
		if(comment.getBoard() != this) {
			comment.setBoard(this);
		}
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", content=" + content + ", createDate=" + createDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTitle() == null) ? 0 : title.hashCode());
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
//		if (getClass() != obj.getClass())
//			return false;
		Board other = (Board) obj;
		if (getTitle() == null) {
			if (other.title != null)
				return false;
		} else if (!getTitle().equals(other.title))
			return false;
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