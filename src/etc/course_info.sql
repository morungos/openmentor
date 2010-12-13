alter table tbl_course_tutors drop foreign key FKD3855E0575ED7BF;
alter table tbl_course_students drop foreign key FK4C6DEBBC276819F;

drop table if exists tbl_course;
drop table if exists tbl_tutor;
drop table if exists tbl_course_tutors;
drop table if exists tbl_student;
drop table if exists tbl_course_students;

create table tbl_course (
   id varchar(16) not null,
   course_title varchar(255),
   primary key (id)
);
create table tbl_tutor (
   org_id varchar(255) not null,
   first_name varchar(255) not null,
   last_name varchar(255) not null,
   primary key (org_id)
);
create table tbl_course_tutors (
   course_id varchar(16) not null,
   tutor_id varchar(255) not null,
   primary key (course_id, tutor_id)
);
create table tbl_student (
   org_id varchar(16) not null,
   first_name varchar(255) not null,
   last_name varchar(255) not null,
   primary key (org_id)
);
create table tbl_course_students (
   course_id varchar(16) not null,
   student_id varchar(16) not null,
   primary key (course_id, student_id)
);
alter table tbl_course_tutors add index FKD3855E0575ED7BF (course_id), add constraint FKD3855E0575ED7BF foreign key (course_id) references tbl_course (id);
alter table tbl_course_students add index FK4C6DEBBC276819F (student_id), add constraint FK4C6DEBBC276819F foreign key (student_id) references tbl_student (org_id);

insert into tbl_course (id, course_title) values ('CM2006', 'Interface Design');
insert into tbl_course (id, course_title) values ('CMM520', 'Human Interface Design');
insert into tbl_course (id, course_title) values ('CMM007', 'Intranet Systems Development');

insert into tbl_tutor (org_id, first_name, last_name) values ('00900001', 'Stuart', 'Watt');
insert into tbl_tutor (org_id, first_name, last_name) values ('00900002', 'Denise', 'Whitelock');
insert into tbl_tutor (org_id, first_name, last_name) values ('00900003', 'Hassan', 'Sheikh');
insert into tbl_tutor (org_id, first_name, last_name) values ('00900004', 'Aggelos', 'Liapis');

insert into tbl_student (org_id, first_name, last_name) values ('M0000001', 'Sam', 'Smith');
insert into tbl_student (org_id, first_name, last_name) values ('M0000002', 'Pat', 'Parker');
insert into tbl_student (org_id, first_name, last_name) values ('M0000003', 'Sandy', 'Jones');

insert into tbl_course_tutors (course_id, tutor_id) values ('CM2006', '00900001');
insert into tbl_course_tutors (course_id, tutor_id) values ('CM2006', '00900002');
insert into tbl_course_tutors (course_id, tutor_id) values ('CM2006', '00900003');
insert into tbl_course_tutors (course_id, tutor_id) values ('CM2006', '00900004');
insert into tbl_course_tutors (course_id, tutor_id) values ('CMM520', '00900001');
insert into tbl_course_tutors (course_id, tutor_id) values ('CMM007', '00900002');

insert into tbl_course_students (course_id, student_id) values ('CM2006', 'M0000001');
insert into tbl_course_students (course_id, student_id) values ('CM2006', 'M0000002');
insert into tbl_course_students (course_id, student_id) values ('CM2006', 'M0000003');
insert into tbl_course_students (course_id, student_id) values ('CMM520', 'M0000001');
insert into tbl_course_students (course_id, student_id) values ('CMM007', 'M0000002');


