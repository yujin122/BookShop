-- 도서 카테고리 테이블

create table book_category(
	cate_name number(20) not null,			    -- 카테고리 이름
	cate_code varchar2(30) primary key,		    -- 카테고리 중분류
	cate_code_ref varchar2(30),			        -- 카테고리 대분류(FK book_category cate_code)
	foreign key(cate_code_ref) references book_category(cate_code)
);

-- 카테고리 데이터

insert into book_category (cate_name, cate_code) values ('소설', 100);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('추리/공포/스릴러', 101, 100);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('판타지', 102, 100);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('소설/시/희곡', 103, 100);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('로맨스', 104, 100);

insert into book_category (cate_name, cate_code) values ('에세이/여행', 200);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('국내여행', 201, 200);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('해외여행', 202, 200);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('명상/치유 에세이', 203, 200);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('일기/편지글 에세이', 204, 200);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('그림 에세이', 205, 200);


insert into book_category (cate_name, cate_code) values ('유아', '300');
insert into book_category (cate_name, cate_code, cate_code_ref) values ('유아 그림책', 301, 300);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('유아 학습', 302, 300);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('유아 전집', 303, 300);

insert into book_category (cate_name, cate_code) values ('청소년', '400');
insert into book_category (cate_name, cate_code, cate_code_ref) values ('공부법', 401, 400);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('중학생', 402, 400);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('고등학생', 403, 400);

insert into book_category (cate_name, cate_code) values ('수험서/자격증/취업', 500);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('공무원', 501, 500);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('컴퓨터 수험서', 502, 500);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('전문직', 503, 500);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('외국어', 504, 500);


insert into book_category (cate_name, cate_code) values ('역사/철학', 600);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('한국사', 601, 600);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('동양사/동양문학', 602, 600);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('서양사/서양문학', 603, 600);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('철학', 604, 600);
insert into book_category (cate_name, cate_code, cate_code_ref) values ('서양철학', 605, 600);
