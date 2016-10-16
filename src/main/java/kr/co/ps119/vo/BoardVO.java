package kr.co.ps119.vo;

import java.util.Date;

import kr.co.ps119.entity.Member;

public class BoardVO {

	private final Long id;
	
	private final String title;
	
	private final String content;
	
	private final Date createDate;
	
	private final Date lastUpdateDate;
	
	private final Long updateCount;

	private final Member member;

	public BoardVO(Long id, String title, String content, Date createDate, Date lastUpdateDate, Long updateCount, Member member) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.updateCount = updateCount;
		this.member = member;
	}

	public Long getId() {
		return id.longValue();
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return new Date(createDate.getTime());
	}

	public Date getLastUpdateDate() {
		return new Date(lastUpdateDate.getTime());
	}

	public Long getUpdateCount() {
		return updateCount;
	}

	public Member getMember() {
		return member;
	}
}
