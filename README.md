# University Department Database Implementation

- [University Department Database Implementation](#university-department-database-implementation)
    - [Description ](#description-)
    - [Database Structure ](#database-structure-)
    - [Data Privacy ](#data-privacy-)
    - [Course Management ](#course-management-)
    - [Grading Rules ](#grading-rules-)
    - [ER Diagram ](#er-diagram-)
    - [Setup ](#setup-)
  - [Functionality Implementation ](#functionality-implementation-)
    - [1. Data Management (PostgreSQL Functions) ](#1-data-management-postgresql-functions-)
    - [2. Data Retrieval and Calculation Functions ](#2-data-retrieval-and-calculation-functions-)
    - [3. Triggers ](#3-triggers-)
    - [4. Views ](#4-views-)
  - [Java Application Integration with PostgreSQL Database via JDBC:](#java-application-integration-with-postgresql-database-via-jdbc)
  - [Construction of Updatable Views:](#construction-of-updatable-views)
  - [Performance Study and Physical Design:](#performance-study-and-physical-design)
    - [Using different index types:](#using-different-index-types)
    - [Using clustering:](#using-clustering)
    - [Increasing student data (by 2000):](#increasing-student-data-by-2000)
    - [Conclusion:](#conclusion)


### Description <a name="description"></a>
This project involves the implementation of a database for a university 
department as part of the Databases course at the Technical University of Crete. 
The database will contain all data related to the department and its 
operations, following the specified requirements. The provided resources are 
an Entity-Relationship (ER) diagram, and an initial dataset backup in 
PostgreSQL, including sample data.

The project's objective is to create a comprehensive database for the university 
department. It will include information about fields of study, laboratories, 
courses, as well as professors, staff, and students. Personal information for 
all department members, such as professors, lab staff, and students, is crucial 
and includes unique identifiers (AMKA), names, surnames, father's names, and 
email addresses. Students additionally require details like enrollment number 
and registration date.

### Database Structure <a name="db-structure"></a>
The database consists of several main components:

- **Domains**: Represent research areas with unique codes (e.g., COMP, AIS, 
ACE), titles,and descriptions.
- **Laboratories**: Correspond to labs supporting educational activities, each 
associated with a domain and having a unique code, title, and description with 
professors and labstaff members assigned to them. Every lab is overseen by a 
professor, who must be of thehighest rank and cover one or more knowledge 
domains.
- **Courses**: Encompass semester-based courses with unique codes, titles, 
descriptions, teaching units, weekly teaching hours, tutorial support lab work, 
and indicative semester of execution (typical academic year - winter or spring). 
Thedepartment decides which courses will be taught in each academic semester.
- **Profesors**: Includes teaching staff categorized by ranks (regular, 
associate, assistant, lecturer).
- **Laboratory Staff**: Comprises personnel categorized by levels (A, B, C, D).
- **Students**: Store personal data for students, including unique 
identification numbers, names, surnames, emails, registration dates, and 
semester information.

### Data Privacy <a name="data-privacy"></a>
All personal data of department members (faculty, staff, and students) is 
carefully maintained in compliance with data protection regulations. This data 
includes unique identification numbers (AMKA), names, surnames, father's names, 
email addresses, and more.

### Course Management <a name="course-management"></a>
The department organizes courses on a semester basis. Each course has a unique 
code and holds information regarding its title, description, teaching units, 
weekly teaching hours, lab requirements, and indicative semester of execution 
(typical academic year - winter, spring).

### Grading Rules <a name="grading-rules"></a>
Each semester course has defined grading rules that determine the final grade 
for each student. These rules are established by the instructors and contribute 
to the student's overall academic performance.
The written examination's participation percentage contributes to the final 
grade. For non-laboratory courses, the participation percentage is 100%. If a 
course is a laboratory course and requires a laboratory grade above a minimum 
threshold, this threshold is recorded. If there's no threshold, it's set to 
zero. Similarly, if a laboratory course requires a minimum written examination 
grade,the minimum is recorded. If there's no minimum, it's set to zero.
Final Grade Calculation:
- For non-laboratory courses, the final grade is the same as the written 
examination grade (weighted 100%).
- If the laboratory grade is strictly lower than the threshold, the final grade 
is zero.
- If the written examination grade is strictly lower than the threshold, the 
final grade is the written examination grade.
- For all other cases, a combination of the laboratory and written examination 
grades is calculated based on participation percentages and used to determine 
the final grade.

### ER Diagram <a name="er-diagram"></a>
The ER diagram outlines the relationships between different entities in the 
database, such as domains, labs, courses, faculty, and students. It serves as a 
visual representation of the database structure.
![explanation image](https://github.com/etheodoraki/Database-Uni/blob/main/images/uniDB_ER.png)

### Setup <a name="setup"></a>
To set up the project, follow these steps:

Restore the provided database backup file using the PostgreSQL restore command.
Update the database connection details in the configuration file.

## Functionality Implementation <a name="functionality"></a>
To fulfill the project requirements, the following functionalities were 
implemented:

### 1. Data Management (PostgreSQL Functions) <a name="data-management"></a>
1.1. Data Insertion Functions
Functions are created to insert personal data records for professors, lab staff, 
and students. These functions accept the number of records to generate and 
create data using randomly selected Greek names and surnames. For student data, 
enrollment dates are considered. Student enrollment numbers follow a specific 
format (EEEEEAAAAAA, where EEEEE is the enrollment year, and AAAAAA is a unique 
incrementing number). The ranks of professors, the levels of lab staff, and 
the lab code are generated randomly from the possible range of values.

1.2. Grade Entry Function
A function is implemented to insert grades for enrolled students in courses of a 
specified semester. Random integer values from 1 to 10 are used for exam grades. 
If the course includes lab work, lab grades are also inserted. If grades already 
exist for certain students, the function updates them accordingly.

1.3. Proposed Records Generation
A function generates proposed course registrations for all students based on 
their current semester, considering prerequisites and past courses taken. The 
number of proposed registrations per student is capped at six, prioritizing 
courses from earlier semesters.

1.4. Future Course Creation
Another function creates semester-based course offerings for a specified 
future semester. Course information, instructors, lab staff, and lab 
assignments are copied from the most recent semester for each course.

### 2. Data Retrieval and Calculation Functions <a name="data-retrieval"></a>
2.1. Retrieval of Faculty and Lab Staff
This function retrieves names and AMKA of professors and lab staff members 
belonging to laboratories within a specified research area.

2.2. Retrieval of Student Grades
Retrieves course information and grades (based on desired category - exam, lab, 
or final) for a specific student and semester.

2.3. Retrieval of Enrollment Data
Retrieves student enrollment numbers and registration years for students 
enrolled in selected mandatory courses of the current semester.

2.4. Retrieval of Course Completion
Retrieves course codes of all mandatory courses and indicates whether a specific 
student has passed each course or not.

2.5. Retrieval of Unscheduled Mandatory Courses
Retrieves course codes and titles of mandatory courses that are supposed to be 
taught in the current semester but aren't scheduled.

2.6. Instructor with Most Courses Taught
Finds the professor(s) with the highest number of courses taught, considering 
only completed semesters.

2.7. Top Achievers by Course
Displays the percentage of students who achieved a grade of 8.5 or higher in a 
specified course and semester.

2.8. Student Workload Calculation
Calculates the total workload (lecture, tutorial, and lab hours) for a student 
in the current semester.

2.9. Dependency Analysis
Identifies courses that a student shouldn't attend based on prerequisites or 
recommended courses. It helps students avoid registering for courses they 
can't or shouldn't take.

2.10. Successful Completion of All Mandatory Courses (Optional)
An optional function finds students who have successfully completed all 
mandatory courses offered in the current semester.

### 3. Triggers <a name="triggers"></a>
3.1. Automatic Validity Checks for Semesters:
Ensure that semester start and end dates do not overlap with existing semesters.
Enforce chronological consistency of semester states (past, present, future).
Prevent multiple semesters with the "present" state.

3.2. Automatic Calculation of Final Grades:
Automatically calculate the final grade and pass/fail status for student records 
based on individual component grades.
Apply the grading rules described in section I.

3.3. Auto-compute Academic Year and Season:
Calculate the generated attributes 'academic_year' and 'academic_season' when 
inserting or updating records in the Semester table.

3.4. Automatic Enrollment Control:
Automatically check the enrollment of a student in a semester course based on 
prerequisite course restrictions and total credit hours.
Control is triggered when inserting new "requested" records or updating records 
from "proposed" to "requested".
Approve the enrollment if conditions are met, otherwise set the status to 
"rejected".
Prevent insertion or update of records in the "approved" or "rejected" status 
directly.

### 4. Views <a name="views"></a>

4.1. Presentation of Supervisors and Committee for Ungraduated Students' Theses:
Create a view that presents the supervisor and committee members for the theses 
of students who haven't graduated yet.
The view includes columns for student ID (ΑΜΚΑ) and committee members' names.

4.2. Retrieving the Number of Students per Enrollment Year:
Create a view that retrieves the count of students per enrollment year for the 
last 10 years.
Count students who meet graduation requirements and haven't graduated yet.
Result includes columns for year and count.


## Java Application Integration with PostgreSQL Database via JDBC:
A Java application using Eclipse and the JDBC guide provided with PostgreSQL 11.

The application should offer a menu for the following options:
1. Enter connection details for a specific PostgreSQL database (IP address, 
database name, username, password).
2. Validate Current Transaction / Start New.
3. Cancel Current Transaction.
4. Display all labs (code, title, field).
5. Display all knowledge areas (code, title).
6. Change lab name using specific code entered by the user.
7. Change knowledge area title using specific code entered by the user.
8. Enter grades for a specific course code in the current semester.
9. Revert a specific number of recent grade entries.

## Construction of Updatable Views:
An updatable view for a relevant database table and test its functionality.

1. Build a view displaying all department labs' information, including 
director's full name, contact email, title, knowledge areas, and sector.
2. Make this complex view updatable by implementing suitable INSTEAD OF 
triggers.
3. Updates to the view should correspondingly affect fields in the Lab and 
Professor tables, triggering changes in Covers table.

## Performance Study and Physical Design:

- Query task: Find students with entry dates after 1/9/2010 and before 1/9/2012, 
who have passed a course with a grade greater than 9, and the course instructor 
has the same name as the student.

<!-- - Start with a relatively small number of students.
- Use EXPLAIN ANALYSE to analyze query performance, recording results and 
observations.
- Sequentially create appropriate indexes to accelerate query execution. 
- Document index choices and their impact on execution plans.
- Minimize index usage to avoid update performance degradation.
- Study different index types and note differences observed.
- Optimize further by utilizing clustering for acceleration. 
- Record observations.
- Increase student data significantly (as demonstrated in Lab Exercise 7), 
including corresponding course records and grades.
- Delete previously created indexes.
- Reevaluate execution plans before and after index creation, documenting 
observations for each step in the report. -->

In order to have a comprehensive picture of the performance of the query, we 
first ran the above query on the relatively small database that was given to us 
initially. 
![q1](https://github.com/etheodoraki/Database-Uni/blob/main/images/Q1.png)

### Using different index types:
One of the ways to improve the execution time of a query is to use indexes. By 
implementing indexes on the appropriate fields, serial reading of an array can 
be avoided, since the rows that match the conditions are selected more quickly.
Hash indexes are only suitable for equality queries, while b-tree indexes can 
handle equality but also range queries, as is the question given to us. 
Both were applied to observe the differences.

B-Tree
![q2](https://github.com/etheodoraki/Database-Uni/blob/main/images/Q2.png)
The PostegreSQL query planner will use the B-tree index criterion, where the 
indexed column is involved in a comparison relation using the following 
operators: < , <= ,= ,>=,>. It's not always the fastest way compared to a simple 
scan and sort but it can prove to be helpful in some cases.

Hash
![q3](https://github.com/etheodoraki/Database-Uni/blob/main/images/Q3.png)
The Hash indexes can only handle simple equality relations and point queries 
where we are interested in a value or a very small set of values. The optimizer 
will use a Hash criterion whenever an indexed column is involved in relation to 
the equality operator '='. It is of no use at all for range queries

### Using clustering:
![q4](https://github.com/etheodoraki/Database-Uni/blob/main/images/Q4.png)

### Increasing student data (by 2000):
Without indexes:
![incr-noindex](https://github.com/etheodoraki/Database-Uni/blob/main/images/incr-noindex.png)

Clustering with B-tree:
![incr-clust](https://github.com/etheodoraki/Database-Uni/blob/main/images/incr-clust.png)

### Conclusion:
In the case of few data the use of hash index can improve the speed, but in the 
case of a large amount of data the use of Clustering with B-tree index is more 
efficient.
