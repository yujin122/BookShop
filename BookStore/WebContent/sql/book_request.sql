-- 도서 요청 테이블

create table book_request(
    r_num number(8) primary key,            -- 도서 요청 글번호
    r_member varchar2(20) not null,         -- 작성자(member mem_id)
    r_book_title varchar2(500) not null,    -- 도서명
    r_pub_company varchar2(100),            -- 출판사
    r_author varchar2(100),                 -- 저자
    r_price varchar2(50) default 0 not null,   -- 희망가격
    r_contact varchar2(100) not null,       -- 연락처
    r_title varchar2(100) not null,         -- 글 제목
    r_contents varchar2(1000) not null,     -- 글 내용
    r_date date,                            -- 작성일
    r_hit number(8) default 0				-- 조회수
);