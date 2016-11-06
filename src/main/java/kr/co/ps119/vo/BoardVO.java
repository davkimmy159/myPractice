package kr.co.ps119.vo;

import java.time.LocalDateTime;
import java.util.Date;

import kr.co.ps119.utils.Utility;

public class BoardVO {

	private final Long id;
	
	private final String title;
	
	private final String content;
	
	private final LocalDateTime createDate;
	
	private final LocalDateTime lastUpdateDate;
	
	private final Long updateCount;

	private final Long hitCount;
	
	private final String username;

	public BoardVO(Long id, String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long updateCount, Long hitCount) {
		this(id, title, content, createDate, lastUpdateDate, updateCount, hitCount, "");
	}
	
	public BoardVO(Long id, String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long updateCount, Long hitCount, String username) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.updateCount = updateCount;
		this.hitCount = hitCount;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public String getFormattedCreateDate() {
		return createDate.format(Utility.getDateTimeFormatter());
	}

	public String getFormattedLastUpdateDate() {
		return lastUpdateDate.format(Utility.getDateTimeFormatter());
	}

	public Long getUpdateCount() {
		return updateCount;
	}
	
	public Long getHitCount() {
		return hitCount;
	}

	public String getUsername() {
		return username;
	}
}
