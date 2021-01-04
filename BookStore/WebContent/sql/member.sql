-- 회원 정보 테이블
create table member (
   mem_name varchar2(20)    			-- 이름
   mem_age varchar2(20),   				-- 연령대
   mem_nickname varchar2(20), 			-- 별명
   mem_gender varchar2(2),   			--성별 (F, M, U(unknown))중 하나만 들어감.
   mem_birth varchar2(20),   			-- 생년월일(MM-DD)
   mem_id varchar2(20) primary key, 	-- 아이디
   mem_pwd varchar2(20),      			-- 비밀번호
   mem_number varchar2(20),   			-- 전화번호
   mem_addr varchar2(200), 				-- 주소
   mem_email varchar2(100),   			-- 이메일
   mem_regdate date               		-- 가입일
   --추가 컬럼
);