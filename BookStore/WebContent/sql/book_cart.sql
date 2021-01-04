-- 장바구니 테이블

create table book_cart(
    c_num number(8) primary key,        -- 장바구니 번호
    c_sale_num number(8) not null,      -- 중고도서 판매 번호(book_sale s_num)
    c_member varchar2(20) not null,     -- 회원 번호(member mem_id)
    c_qty number(10) not null,          -- 도서 수량
    c_date date                         -- 날짜
);