-- 도서 정보 테이블

create table book_info(
    b_num number(8) primary key,			-- 도서 번호
    b_cate_fk varchar2(20) not null,		-- 카테고리번호(book_category cate_code)
    b_isbn varchar2(20),					-- isbn
    b_name varchar2(200) not null,			-- 책 이름
    b_author varchar2(500) not null,		-- 작가
    b_translator varchar2(500),				-- 옮긴이
    b_pub_company varchar2(100) not null,	-- 출판사	
    b_pub_date date not null,				-- 출간일
    b_image varchar2(500),					-- 이미지
    b_price number(8) default 0,			-- 원가
    b_contents varchar2(2000)				-- 책 설명
);
