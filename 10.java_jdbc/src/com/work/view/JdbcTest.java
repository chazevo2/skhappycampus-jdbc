package com.work.view;

import java.util.ArrayList;

import com.work.dto.Member;
import com.work.model.MemberDao;

public class JdbcTest {

	public static void main(String[] args) {
		// 회원 테이블에 대한 DAO 클래스를 이용해서 CRUD 호출 사용
		MemberDao dao = new MemberDao();
		
		int delcnt = dao.deleteAll();
		if(delcnt > 0) {
			System.out.println(delcnt + "명의 회원이 삭제되었습니다.");
		}
		boolean result = dao.insertMember(new Member("user05", "password05", "이예은"));
		if(result) {
			System.out.println("회원가입이 성공되었습니다.");
		}
		if(dao.updateMemberPw("user01", "123456", "password01")) {
			System.out.println("비밀번호 변경이 완료되었습니다.");
		} else {
			System.out.println("비밀번호 변경을 실패하였습니다.");
		}
		System.out.println();
		ArrayList<Member> list = dao.selectAll();
		for (Member member : list) {
			System.out.println(member);
		}
		System.out.println();
		Member member = dao.selectById("user04");
		if(member != null) {
			System.out.println(member);
		} else {
			System.out.println("해당 회원이 없습니다.");
		}
	}

}
