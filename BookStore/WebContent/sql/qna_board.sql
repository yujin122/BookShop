-- 중고도서 문의글 테이블

create table qna_board (
    q_num number(8) primary key,        -- 글번호
    q_mem varchar2(20) not null,        -- 작성자 아이디(member mem_id)
    q_sale_num number(8) not null,      -- 문의 상품 번호(book_sale s_num)
    q_contents varchar(2000) not null,  -- 문의 내용
    q_date date,                        -- 문의 작성일
    q_hit number(8),                    -- 조회수
    q_group number(8),
    q_step number(8),
    q_indent number(8)
);