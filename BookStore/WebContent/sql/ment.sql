--댓글 테이블
create table ment (
	c_num_fk number(30),
	ment_num number(30) primary key,
	ment_group number(30),
	ment_step number(30),
	ment_indent number(30),
	ment_cont varchar2(100),
	ment_id_nickname varchar2(100) not null,
	ment_pwd varchar2(100) not null,
	ment_date date
	--constraint c_num_fk_con foreign key references commu(c_num) on delete cascade(나중에 넣을 제약조건)
);