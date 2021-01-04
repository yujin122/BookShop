create table all_qna(
    aq_num number(8) primary key,				-- 번호
    aq_category varchar2(400) not null,			-- 카테고리(회원/판매/취소&반품&교환/주문&결제/배송)
    aq_title varchar2(1000) not null,			-- 제목
    aq_content varchar2(3000) not null,			-- 내용
    aq_writer varchar2(50) not null,			-- 작성자
    aq_date date,								-- 날짜
    aq_hit number(8) default 0,					-- 조회수
    aq_group number(8),							
    aq_step number(8) default 0,
    aq_indent number(8) default 0,
    aq_pwd varchar2(30),
    aq_lock varchar2(1) default 0 not null
);