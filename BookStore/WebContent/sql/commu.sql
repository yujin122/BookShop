--게시판 테이블
create table commu (
	c_num number(30) primary key,
	c_group number(30),
	c_step number(30),
	c_indent number(30),
	c_title varchar2(30) not null,
	c_id_nickname varchar2(100) not null,
	c_pwd varchar2(100) not null,
	c_content clob,
	c_hit number(30),
	c_like number(30),
	c_date date
);
