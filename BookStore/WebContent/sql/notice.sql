-- 공지사항 table
create table book_notice (
    n_num number(5) primary key,             -- 글번호
    n_title varchar2(500) not null,          -- 제목
    n_cont varchar2(2000) not null,          -- 내용      
    n_date date,                             -- 작성일
    n_hit number(8) default 0                -- 조회수
);

create sequence notice_seq
start with 1
increment by 1
nocache;