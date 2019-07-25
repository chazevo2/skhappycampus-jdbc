package com.work.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.work.dto.Member;

/*
 * ## JDBC(Java Data Base Connectivity)
 * -- Java와 Database를 연결하기 위한 표준
 * -- java.sql.*
 * -- javax.sql.*
 * 
 * ## JDBC 프로그래밍을 위한 준비
 * 1. DBMS 설치 : 오라클
 * 2. JDBC API : rt.jar(Java SE 표준api 포함되어 있음)
 * 3. JDBC Driver : DBMS 벤더가 JDBC API를 구현해서 제공하는 클래스의 묶음
 * 		=> 오라클 : ojdbc6.jar => JDK 버전을 의미함(JDK 1.6)
 * 
 * ## JDC Driver 위치
 * 1. %JAVA_HOME%\jre\lib\ext > ojbc6.jar 복사
 * 		=> javac/java 명령어가 class 검색하는 경로이므로 모든 프로젝트에 자동 반영됨
 * 2. 프로젝트에 classpath에 라이브러리 추가
 * 		=> 프로젝트마다 classpath에 라이브러리 추가 설정해야함
 * 
 * ## JDBC 프로그래밍 순서
 * 1. jdbc driver 로딩 : 생성자
 * 2. db server 연결 : Connection
 * 3. sql 통로 개설 : Statement, PreparedStatement, CallableStatement
 * 4. sql 수행 요청
 * 5. sql 수행 결과 처리
 * 6. 자원 해제
 * 
 * ## JDBC 사용위한 property
 * 1. String driver = "oracle.jdbc.driver.OracleDriver";
 * 2. String url = "jdbc:orcle:thin:@localhost:1521:XE";
 * 3. String user = "test";
 * 4. String password = "hi123456";
 * 
 */
public class MemberDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "test";
	private String password = "hi123456";

	public MemberDao() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Error : jdbc driver 로딩 오류발생");
			e.printStackTrace();
		}
	}
	
	// 회원 가입
	public boolean insertMember(Member m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			// sql 통로 개설
			String sql = "insert into members_simple values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			// sql 수행요청
			int result = pstmt.executeUpdate();
			// sql 수행 결과 처리
			if(result == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error : 레코드 추가시 오류 발생");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	// 전체회원 조회
	public ArrayList<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "select * from members_simple";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Member(rs.getString("member_id"), rs.getString("member_pw"), rs.getString("member_name")));
			}		
		} catch (SQLException e) {
			System.out.println("Error : 전체 조회시 오류");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 내정보조회
	public Member selectById(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "select * from members_simple where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Member(rs.getString("member_id"), rs.getString("member_pw"), rs.getString("member_name"));
			}
		} catch (SQLException e) {
			System.out.println("Error : 정보 조회시 오류");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 암호변경
	public boolean updateMemberPw(String memberId, String memberPw, String newMemberPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			// sql 통로 개설
			String sql = "update members_simple set member_pw=? where member_id=? and member_pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newMemberPw);
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			// sql 수행요청
			int result = pstmt.executeUpdate();
			// sql 수행 결과 처리
			if(result == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error : 레코드 추가시 오류 발생");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	// 전체 회원 삭제
	public int deleteAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "delete from members_simple";
			pstmt = conn.prepareStatement(sql);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 삭제시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 회원 탈퇴
	public int deleteMember(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "delete from members_simple where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 삭제시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
}
