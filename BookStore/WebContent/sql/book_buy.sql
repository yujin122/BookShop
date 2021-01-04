-- 구매 테이블

create table book_buy(
    buy_num number(8) primary key,                      -- 구매 번호
    buy_snum number(8) not null,                        -- 구매 중고도서 번호(book_sale s_num)
    buy_member varchar2(20) not null,                   -- 구매자(member mem_id)
    buy_name varchar2(20) not null,                     -- 입금자명
    buy_qty number(8) not null,                         -- 구매 수량
    buy_trans varchar2(20) not null,                    -- 거래 방식 (direct, delivery)
    buy_phone varchar2(50) not null,                    -- 연락처
    buy_request varchar2(1000),                         -- 요청사항
    buy_state varchar2(10) default '신청' not null,      -- 구매 상태 (신청, 완료, 취소)
    buy_date date                                      	-- 구매 날짜
);