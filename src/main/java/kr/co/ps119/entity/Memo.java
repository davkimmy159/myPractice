package kr.co.ps119.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Memo implements Serializable {
	
	private static final long serialVersionUID = 5199824822705432663L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memo_id")
	private Long id;
	
	@Column
	@NotBlank(message = "title is empty")
	@Length(max = 600,
			message = "memo title must be between 0 ~ 600")
	private String title;
	
	@Column
	@NotBlank(message = "memo content is empty")
	@Lob
	private String content;
	
	@Column
	@NotNull(message = "date is empty")
	private LocalDateTime createDate;
	
	@Column
	@NotNull(message = "date is empty")
	private LocalDateTime lastUpdateDate;

	// for view
	@Transient
	private String memberUsername;
	
	// foreign key 1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",
				nullable = true)
	@JsonManagedReference
	private Member member;
	
	// foreign key 2
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id",
				nullable = true)
	@JsonManagedReference
	private Board board;
	
	public Memo() {
	}

	public Memo(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}
	
	
	// ------------------------------------------------------------------
	
	
	// Sets default values
	@PrePersist
	void setDefaultValues() {
//		setContent("This is memo");
		setCreateDate(LocalDateTime.now());
		setLastUpdateDate(LocalDateTime.now());
	}
	
	
	// ------------------------------------------------------------------
	
	
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public String getMemberUsername() {
		return memberUsername;
	}

	public void setMemberUsername(String memberUsername) {
		this.memberUsername = memberUsername;
	}
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		
		if(!(member.getMemos().contains(this))) {
			member.getMemos().add(this);
		}
	}
	
	// for view
	public void setMemberNull() {
		this.member = null;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
		
		if(!(board.getMemos().contains(this))) {
			board.getMemos().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
		result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
		result = prime * result + ((getLastUpdateDate() == null) ? 0 : getLastUpdateDate().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Memo)) {
			return false;
		}
		Memo other = (Memo) obj;
		if (getTitle() == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!getTitle().equals(other.title)) {
			return false;
		}
		if (getContent() == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!getContent().equals(other.content)) {
			return false;
		}
		if (getCreateDate() == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!getCreateDate().equals(other.createDate)) {
			return false;
		}
		if (getLastUpdateDate() == null) {
			if (other.lastUpdateDate != null) {
				return false;
			}
		} else if (!getLastUpdateDate().equals(other.lastUpdateDate)) {
			return false;
		}
		return true;
	}
}