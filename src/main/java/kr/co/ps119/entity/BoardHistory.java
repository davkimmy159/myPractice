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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import kr.co.ps119.utils.Utility;

@Entity
public class BoardHistory implements Serializable {
	
	private transient static final long serialVersionUID = -940182745244633418L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Long id;
	
	@Column
	@NotBlank(message = "content of board history is empty")
	@Length(max = 300,
			message = "board name must be between 0 ~ 300")
	private String content;
	
	@Column
	@NotNull(message = "date is empty")
	private LocalDateTime createDate;

	// for view
	@Transient
	private String memberUsername;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",
				nullable = true)
	@JsonManagedReference
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id",
				nullable = true)
	@JsonManagedReference
	private Board board;

	public BoardHistory() {
	}
	
	public BoardHistory(Long id, String content, LocalDateTime createDate) {
		this.id = id;
		this.content = content;
		this.createDate = createDate;
	}

	
	// ------------------------------------------------------------------
	
	
	// Sets default values
	@PrePersist
	void setDefaultValues() {
		setCreateDate(LocalDateTime.now());
	}
	
	public String getFormattedCreateDateString() {
		return createDate.format(Utility.getDateTimeFormatter());
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
		
		if(!(member.getHistories().contains(this))) {
			member.getHistories().add(this);
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
		
		if(!(board.getHistories().contains(this))) {
			board.getHistories().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
		result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
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
		if (!(obj instanceof BoardHistory)) {
			return false;
		}
		BoardHistory other = (BoardHistory) obj;
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
		return true;
	}
}
