use demojpa;

create table demojpa.student (
	id bigint primary key,
    name varchar(255) not null
	);

create table demojpa.course (
	id bigint primary key,
    title varchar(255) not null
    );
    
create table demojpa.enrollment (
	id bigint primary key, 
    enrollmentDate DATE not null, 
    student_id bigint references student(id),
    course_id bigint references course(id)
    );
    
insert into student (id, name) values (1, 'Alice');
insert into student (id, name) values (2, 'Bob');
insert into student (id, name) values (3, 'Charlie');

insert into course (id, title) values (1, 'Mathematics');
insert into course (id, title) values (2, 'Physics');
insert into course (id, title) values (3, 'Chemistry');

-- alice 

insert into enrollment (id, enrollmentDate, student_id, course_id) values (1, '2023-10-10', 1, 1);
insert into enrollment (id, enrollmentDate, student_id, course_id) values (2, '2023-10-09', 1, 2);

-- bob

insert into enrollment (id, enrollmentDate, student_id, course_id) values (3, '2023-09-15', 2, 2);

-- charlie

insert into enrollment (id, enrollmentDate, student_id, course_id) values (4, '2023-10-09', 3, 3);


