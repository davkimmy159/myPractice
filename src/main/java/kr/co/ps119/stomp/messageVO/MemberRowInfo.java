package kr.co.ps119.stomp.messageVO;

import java.util.ArrayList;
import java.util.List;

import kr.co.ps119.entity.Member;

public class MemberRowInfo {

	private String memberInfoRowData;
	private String username;
	
	public MemberRowInfo() {
	}
	
	public MemberRowInfo(String memberInfoRowData, String username) {
		this.memberInfoRowData = memberInfoRowData;
		this.username = username;
	}

	public String getMemberInfoRowData() {
		return memberInfoRowData;
	}

	public void setMemberInfoRowData(String memberInfoRowData) {
		this.memberInfoRowData = memberInfoRowData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}