# Project Documentation - Department Database Implementation

## Table of Contents
- [General Description](#general-description)
- [Project Overview](#overview)
- [ER Diagram](#erdiagram)
- [Setup](#setup)
- [Database Structure](#dbstructure)
- [Data Privacy](#dataprivac`y)
- [Course Management](#cooursemanagement)
- [Grading Rules](#grading)
- [Functionality Implementation](#functionality)
  
### 1. General Description <a name="general-description"></a>
This project involves the implementation of a database for a university 
department as part of the Databases course at the Technical University of Crete. 
The database will encompass all data related to the department and its 
operations, following the specified requirements. The provided resources include 
an Entity-Relationship (ER) diagram, and an initial dataset backup in 
PostgreSQL, including sample data.

### 1. Project Overview <a name="project-overview"></a>
The project's objective is to create a comprehensive database for the university 
department. It will include information about fields of study, laboratories, 
courses, as well as faculty, staff, and students. Personal information for all 
department members, such as professors, lab staff, and students, is crucial and 
includes unique identifiers (AMKA), names, surnames, father's names, and email 
addresses. Students additionally require details like enrollment number and 
registration date.
Professors are classified into ranks (regular, associate, assistant, lecturer), 
while lab staff is categorized into levels (A, B, C, D). The department is 
divided into research areas characterized by codes, titles, and descriptions. 
Laboratories support educational activities and are assigned to specific 
research areas. Each lab is identified by a unique code, title, and description, 
with professors and lab staff members assigned to them. Every lab is overseen by 
a professor, who must be of the highest rank and cover one or more knowledge 
domains denoted by three-letter codes (e.g., CS, EDU, AI).
All courses are semester-based. Each course has a unique code and contains 
information about its title, description, teaching units, weekly lecture hours, 
tutorial support, lab work, and indicative semester of execution (typical 
academic year - winter or spring). The department decides which courses will be 
taught in each academic semester.

1. ER Diagram <a name="er-diagram"></a>
The ER diagram outlines the relationships between different entities in the d
atabase, such as domains, labs, courses, faculty, and students. It serves as a 
visual representation of the database structure.
![explanation image](https://github.com/etheodoraki/Database-Uni/blob/main/images/uniDB_ER.png)# Database-Uni

1. Setup <a name="setup"></a>
To set up the project, follow these steps:

Install PostgreSQL on your local machine.
Restore the provided database backup file using the PostgreSQL restore command.
Update the database connection details in the configuration file.

5. Database Structure <a name="database-structure"></a>
The database consists of several main components:

- Domains: Represent research areas with unique codes, titles, and descriptions.
- Labs: Correspond to labs supporting educational activities, each associated 
with a domain and having a unique code, title, and description.
- Faculty: Includes teaching staff categorized by ranks (regular, associate, 
assistant, lecturer).
- Laboratory Staff: Comprises personnel categorized by grades (A, B, C, D).
- Courses: Encompass semester-based courses with unique codes, titles, 
descriptions, teaching units, weekly teaching hours, and lab requirements.
- Students: Store personal data for students, including unique identification 
numbers, names, surnames, emails, registration dates, and semester information.

1. Data Privacy <a name="data-privacy"></a>
All personal data of department members (faculty, staff, and students) is 
carefully maintained in compliance with data protection regulations. This data 
includes unique identification numbers (AMKA), names, surnames, father's names, 
email addresses, and more.

1. Course Management <a name="course-management"></a>
The department organizes courses on a semester basis. Each course has a unique 
code and holds information regarding its title, description, teaching units, 
weekly teaching hours, lab requirements, and indicative semester of execution 
(typical academic year - winter, spring).

1. Grading Rules <a name="grading-rules"></a>
Each semester course has defined grading rules that determine the final grade 
for each student. These rules are established by the instructors and contribute 
to the student's overall academic performance.

### 11. Functionality Implementation <a name="functionality"></a>
To fulfill the project requirements, the following functionalities were implemented:

1. Data Management (PostgreSQL Functions)
1.1. Data Insertion Functions
Functions are created to insert personal data records for professors, lab staff, 
and students. These functions accept the number of records to generate and 
create data using randomly selected Greek names and surnames. For student data, 
enrollment dates are considered. Student enrollment numbers follow a specific 
format (EEEEEAAAAAA, where EEEEE is the enrollment year, and AAAAAA is a unique 
incrementing number).

1.2. Grade Entry Function
A function is implemented to insert grades for enrolled students in courses of a 
specified semester. Random integer values from 1 to 10 are used for exam grades. 
If the course includes lab work, lab grades are also inserted. If grades already 
exist for certain students, the function updates them accordingly.

1.3. Proposed Records Generation (Optional)
A function (optional) generates proposed course registrations for all students 
based on their current semester, considering prerequisites and past courses 
taken. The number of proposed registrations per student is capped at six, 
prioritizing courses from earlier semesters.

1.4. Future Course Creation (Optional)
Another optional function creates semester-based course offerings for a 
specified future semester. Course information, instructors, lab staff, and lab 
assignments are copied from the most recent semester for each course.

2. Data Retrieval and Calculation Functions
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
Finds the instructor(s) with the highest number of courses taught, considering 
only completed semesters.

2.7. Top Achievers by Course
Displays the percentage of students who achieved a grade of 8.5 or higher in a 
specified course and semester.

2.8. Student Workload Calculation
Calculates the total workload (lecture, tutorial, and lab hours) for a student 
in the current semester.

2.9. Dependency Analysis (Optional)
An optional function identifies courses that a student shouldn't attend based on 
prerequisites or recommended courses. It helps students avoid registering for 
courses they can't or shouldn't take.

2.10. Successful Completion of All Mandatory Courses (Optional)
An optional function finds students who have successfully completed all 
mandatory courses offered in the current semester.

Triggers
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

Views

4.1. Presentation of Supervisors and Committee for Ungraduated Students' Theses:

Create a view that presents the supervisor and committee members for the theses 
of students who haven't graduated yet.
The view includes columns for student ID (ΑΜΚΑ) and committee members' names.

4.2. Retrieving the Number of Students per Enrollment Year:

Create a view that retrieves the count of students per enrollment year for the 
last 10 years.
Count students who meet graduation requirements and haven't graduated yet.
Result includes columns for year and count.
