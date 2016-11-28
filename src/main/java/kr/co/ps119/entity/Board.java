package kr.co.ps119.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
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
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import kr.co.ps119.utils.Utility;

@Entity
public class Board implements Serializable {

	private transient static final long serialVersionUID = -4939299278182413724L;
	private transient static final Board emptyBoard = new Board(0L, "", "", null, null, 0L, 0L);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;
	
	@Column
	@NotBlank(message = "title is empty")
	@Length(max = 600,
			message = "board name must be between 0 ~ 600")
	private String title;
	
	@Column
	@NotBlank(message = "board content is empty")
	@Lob
	private String content;
	
	@Column
	@NotNull(message = "date is empty")
	private LocalDateTime createDate;
	
	@Column
	@NotNull(message = "date is empty")
	private LocalDateTime lastUpdateDate;
	
	@Column
	@NotNull(message = "update count is empty")
	private Long updateCount;

	@Column
	@NotNull(message = "hitCount count is empty")
	private Long hitCount;
	
	// foreign key 1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",
				nullable = true)
	@JsonManagedReference
	private Member member;
	
	@OneToMany(mappedBy = "board",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<Memo> memos = new ArrayList<>();
	
	public Board() {
	}

	public Board(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long updateCount, Long hitCount) {
		this(0L, title, content, createDate, lastUpdateDate, updateCount, hitCount);
	}
	
	public Board(Long id, String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long updateCount, Long hitCount) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.updateCount = updateCount;
		this.hitCount = hitCount;
	}

	
	// ------------------------------------------------------------------
	
	
	// Sets default values
	@PrePersist
	void setDefaultValues() {
		setContent("Welcome to IMCOLLA!");
		setCreateDate(LocalDateTime.now());
		setLastUpdateDate(LocalDateTime.now());
		setUpdateCount(0L);
		setHitCount(0L);
	}
	
	public String getFormattedCreateDateString() {
		return createDate.format(Utility.getDateTimeFormatter());
	}
	
	public String getFormattedLastUpdateDateString() {
		return lastUpdateDate.format(Utility.getDateTimeFormatter());
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

	public Long getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Long updateCount) {
		this.updateCount = updateCount;
	}
	
	public void setHitCount(Long hitCount) {
		this.hitCount = hitCount;
	}
	
	public Long getHitCount() {
		return hitCount;
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

	public List<Memo> getMemos() {
		return memos;
	}

	public void addMemo(Memo memo) {
		memos.add(memo);
		
		if(memo.getBoard() != this) {
			memo.setBoard(this);
		}
	}

	public boolean isEmptyBoard() {
		return this.id <= 0L;
	}
	
	public static Board getEmptyBoard() {
		return emptyBoard;
	}
	
	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", content=" + content + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate + ", updateCount=" + updateCount + ", hitCount="
				+ hitCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updateCount == null) ? 0 : updateCount.hashCode());
		result = prime * result + ((hitCount == null) ? 0 : hitCount.hashCode());
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
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
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
		if (getTitle() == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!getTitle().equals(other.title)) {
			return false;
		}
		if (getUpdateCount() == null) {
			if (other.updateCount != null) {
				return false;
			}
		} else if (!getUpdateCount().equals(other.updateCount)) {
			return false;
		}
		if (getHitCount() == null) {
			if (other.hitCount != null) {
				return false;
			}
		} else if (!getHitCount().equals(other.hitCount)) {
			return false;
		}
		return true;
	}
}