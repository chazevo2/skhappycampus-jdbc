/*
 * 회원 테이블 : members_simple
 * ## 속성
 * 1. 아이디	member_id	varchar2(20) 식별키
 * 2. 비밀번호	member_pw	varchar2(30) not null
 * 3. 이름	member_name	varchar2(20) not null
 */
drop table member cascade constraints purge;-- 쓰레기 파일 생성하지 않고 완전 삭제
purge recyclebin;-- 쓰레기 파일 제거

create table members_simple (
	member_id varchar2(20) primary key,
	member_pw varchar2(20) not null,
	member_name varchar2(20)
);


insert into members_simple(member_id, member_pw, member_name) values('user01', 'password01', '박준혁');
insert into members_simple values('user02', 'password02', '오지홍');

commit;

-- 전체 회원 조회
select * from members_simple;

-- 로그인
select * from members_simple where member_id='user01' and member_pw='password01';

-- 암호 변경
update members_simple set member_pw='123456' where member_id='user01' and member_pw='password01';

commit;
