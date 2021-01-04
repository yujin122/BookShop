-- 도서 판매 정보 테이블

create table book_sale(
    s_num number(8) primary key,				-- 판매 번호
    s_member varchar2(20) not null,				-- 판매자(member mem_id)
    s_bnum number(8) not null,					-- 판매 도서(book_info b_num)
    s_qty number(10) default 1 not null,		-- 재고량
    s_state varchar2(20) not null,				-- 판매 상태(판매중, 판매완료)
    s_quality varchar2(10) not null,			-- 도서 상태(최상, 상, 중)
    s_price number(8) default 0 not null,		-- 판매 가격
    s_charge number(10) default 0 not null,		-- 배송비
    s_direct varchar2(10) not null,				-- 직거래 가능(가능, 불가능)
    s_contents varchar2(2000),					-- 판매 도서 관련 내용(판매자 작성)
    s_image varchar2(500),						-- 판매 도서 관련 이미지(판매자 첨부)
    s_date date,								-- 판매 등록 날짜
    s_hit number(5)	default 0                 	-- 조회수
);