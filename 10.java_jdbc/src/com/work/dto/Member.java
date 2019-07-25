package com.work.dto;

public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;

	public Member() {
	}

	public Member(String memberId, String memberPw, String memberName) {
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(memberId);
		builder.append(", ");
		builder.append(memberPw);
		builder.append(", ");
		builder.append(memberName);
		builder.append("]");
		return builder.toString();
	}

}
