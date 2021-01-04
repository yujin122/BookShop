create table faq(
    f_num number(8) primary key,				-- 번호
    f_category varchar2(400) not null,			-- 카테고리
    f_question varchar2(1000) not null,			-- 질문
    f_answer varchar2(3000) not null			-- 답변
    f_date date
);