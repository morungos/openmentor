drop table if exists tbl_course;
drop table if exists tbl_tutor;
drop table if exists tbl_course_tutors;
drop table if exists tbl_student;
drop table if exists tbl_course_students;

drop table if exists tbl_assignment;
drop table if exists tbl_comment;
drop table if exists tbl_comment_classes;
drop table if exists tbl_submission;
drop table if exists tbl_submission_students;
drop table if exists tbl_submission_tutors;

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

create table tbl_assignment (
   id integer not null auto_increment,
   course_id varchar(16) not null,
   assignment_title varchar(255) not null,
   primary key (id)
);

create table tbl_comment (
   id integer not null auto_increment,
   text longtext,
   submission_id integer,
   primary key (id)
);

create table tbl_comment_classes (
   comment_id integer not null,
   category_name varchar(8)
);

create table tbl_submission (
   id integer not null auto_increment,
   body longblob,
   filename varchar(255),
   type varchar(255),
   grade varchar(255),
   assignment_id integer,
   primary key (id)
);

create table tbl_submission_students (
   submission_id integer not null,
   student_id varchar(16)
);

create table tbl_submission_tutors (
   submission_id integer not null,
   tutor_id varchar(16)
);

insert into tbl_assignment (id, course_id, assignment_title) values (1, 'CM2006', 'Ians one');
insert into tbl_assignment (id, course_id, assignment_title) values (2, 'CM2006', 'New one');
insert into tbl_assignment (id, course_id, assignment_title) values (3, 'CM2006', 'Assignment3');
insert into tbl_assignment (id, course_id, assignment_title) values (4, 'CM2006', 'Which');

insert into tbl_comment_classes (comment_id, category_name) values (1, 'C1');
insert into tbl_comment_classes (comment_id, category_name) values (2, 'B1');
insert into tbl_comment_classes (comment_id, category_name) values (2, 'B5');
insert into tbl_comment_classes (comment_id, category_name) values (3, 'A1');
insert into tbl_comment_classes (comment_id, category_name) values (5, 'C1');
insert into tbl_comment_classes (comment_id, category_name) values (6, 'C1');
insert into tbl_comment_classes (comment_id, category_name) values (6, 'B5');
insert into tbl_comment_classes (comment_id, category_name) values (7, 'C2');
insert into tbl_comment_classes (comment_id, category_name) values (7, 'C1');
insert into tbl_comment_classes (comment_id, category_name) values (8, 'B1');
insert into tbl_comment_classes (comment_id, category_name) values (8, 'A1');
insert into tbl_comment_classes (comment_id, category_name) values (8, 'B5');
insert into tbl_comment_classes (comment_id, category_name) values (9, 'D1');
insert into tbl_comment_classes (comment_id, category_name) values (9, 'B1');
insert into tbl_comment_classes (comment_id, category_name) values (9, 'B5');
insert into tbl_comment_classes (comment_id, category_name) values (10, 'B5');
insert into tbl_comment_classes (comment_id, category_name) values (11, 'B1');
insert into tbl_comment_classes (comment_id, category_name) values (11, 'C1');
insert into tbl_comment_classes (comment_id, category_name) values (11, 'B5');

insert into tbl_comment (id, submission_id, text) values (1, 1, 'Would they be close enough geographically?  You haven\'t established that this would just be on offer only for those in the area of the university. From your description of your learners, a meeting as an effort to counter isolation and encourage interaction with known individuals would probably be welcome.  There is a strong argument against using these sessions to introduce learners to the technology.  Some activities done on the computer they will be using, with synchronous telephone and e-mail support, is a more authentic learning environment.');
insert into tbl_comment (id, submission_id, text) values (2, 1, 'Including assessment which, from your description, will probably be important to your learners.');
insert into tbl_comment (id, submission_id, text) values (3, 1, 'Its good to see you found this resource useful.');
insert into tbl_comment (id, submission_id, text) values (4, 1, 'Well argued.');
insert into tbl_comment (id, submission_id, text) values (5, 1, 'Do you mean ?state\' here, that is, funded by public money rather than private?');
insert into tbl_comment (id, submission_id, text) values (6, 1, 'On this subject or something similar?  It would be good to know what lessons you have learned from these experiences.');
insert into tbl_comment (id, submission_id, text) values (7, 1, 'If the exam structure for students is still the same, will the teachers be encouraged to implement this new learning into their classroom practice?');
insert into tbl_comment (id, submission_id, text) values (8, 1, 'This is a good summary.  You also need to note down which areas you will need to explore further.');
insert into tbl_comment (id, submission_id, text) values (9, 1, 'Not a word wasted here! Good introduction, concise but with all relevance information included. It would also be good to add here a statement about the source of your knowledge about your learners. I realise that some of this is woven into your discussion.');
insert into tbl_comment (id, submission_id, text) values (10, 1, 'Pre-assessment of what they can actually do will be important.');
insert into tbl_comment (id, submission_id, text) values (11, 1, 'So they could probably tell you what constitutes a good experiment but would not have designed and carried one out themselves?  And while they may not have been encouraged to reflect on their practice, in the right environment or with appropriate rewards they might adapt quite easily to a constructivist learning environment. This would be interesting to test out.');

insert into tbl_submission (id, filename, grade, assignment_id) values (1, 'comments.doc', '1', 1);

insert into tbl_submission_students (submission_id, student_id) values (1, 'M0000003');

insert into tbl_submission_tutors (submission_id, tutor_id) values (1, '00900004');

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


