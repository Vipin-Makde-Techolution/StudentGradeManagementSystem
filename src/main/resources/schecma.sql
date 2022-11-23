CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `semester` int NOT NULL,
PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `exams` (
  `id`  bigint NOT NULL AUTO_INCREMENT,
`marksObtained` int(50) NOT NULL,
`studentId`  bigint NOT NULL,
`paperId`  bigint NOT NULL,
`semester` int(11) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (studentId) REFERENCES student(id),
FOREIGN KEY (paperId) REFERENCES subject(paperId)
);

CREATE TABLE IF NOT EXISTS `subject` (
  `paperId`  bigint NOT NULL AUTO_INCREMENT,
  `typeOfExam` varchar(50) NOT NULL,
  `subjectName` varchar(100) NOT NULL,
`subjectId` int(11) NOT NULL,
`isMandatory` varchar(10) NOT NULL,
PRIMARY KEY (`paperId`),
UNIQUE KEY (`subjectId`)
);

CREATE TABLE IF NOT EXISTS `enrollment` (
  `id`  bigint NOT NULL AUTO_INCREMENT,
  `studentId`  bigint NOT NULL,
`subjectId` int(11) NOT NULL,
`semester` int(11) NOT NULL,
`approvalId` varchar(100) NOT NULL,
`currentlyEnrolled` varchar(100) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (studentId) REFERENCES student(id),
FOREIGN KEY (subjectId) REFERENCES subject(subjectId)
);


CREATE VIEW V_GRADES
AS
  SELECT m.*,
         CASE
         WHEN m.marks <= 30
         THEN 'Fail'
         WHEN m.marks > 30
         AND m.marks <= 45
         THEN '3rd division'
         WHEN m.marks > 45
         AND m.marks <= 60
         THEN '2nd division'
         WHEN m.marks > 60
         AND m.marks <= 85
         THEN '1st division'
         WHEN m.marks > 85
         THEN 'Distinction'
         END grade
  FROM (
    SELECT i.semester,
           i.studentId,
           i.subjectId,
           i.subjectName,
           sum(i.marksObtained) marks
    FROM (
      SELECT e.semester,
             e.studentId,
             s.subjectId,
             s.subjectName,
             s.paperId,
             e.marksObtained
      FROM exams e,
      subject s
      WHERE e.paperId = s.paperId
    ) i
    GROUP BY i.semester,
    i.studentId,
    i.subjectName
  ) m;


DROP PROCEDURE IF EXISTS studentgrade.getResultForEachClass;
DELIMITER $$
CREATE PROCEDURE studentgrade.`getResultForEachClass`(semester int)
BEGIN
SELECT m.semester,	st.name, m.subjectName, MAX(m.marks) marks
FROM v_grades m, student st
WHERE st.semester = semester
AND st.id = m.studentId
AND st.semester = m.semester
Group BY
m.semester,
st.name,
m.subjectName ;
END ;


--Topper for a particular subject
DROP PROCEDURE IF EXISTS studentgrade.getTopperForSubject;
DELIMITER $$
CREATE PROCEDURE studentgrade.`getTopperForSubject`( subjectData varchar(30) , semester int  )
BEGIN
WITH student_marks
AS (
SELECT m.semester,
m.studentId,
st.name,
m.subjectName,
marks
FROM v_grades m,
student st
WHERE st.semester = semester
AND m.subjectName = subjectData
AND st.id = m.studentId
AND st.semester = m.semester
)
SELECT semester,
name,
marks
FROM (
SELECT semester,
name,
marks,
ROW_NUMBER() OVER (
PARTITION BY semester ORDER BY marks DESC,
semester
) AS topStudent
FROM student_marks
) A
WHERE A.topStudent = 1;
END;




--Class Topper
DROP PROCEDURE IF EXISTS studentgrade.getClassTopper;
DELIMITER $$
CREATE PROCEDURE studentgrade.`getClassTopper`( semester int  )
BEGIN
WITH student_marks
AS (
SELECT m.semester,
st.name,
sum(m.marks) marks
FROM v_grades m,
student st
WHERE st.semester = semester
AND st.id = m.studentId
AND st.semester = m.semester
GROUP BY m.semester,
st.name
)
SELECT semester,
name,
marks
FROM (
SELECT semester,
name,
marks,
ROW_NUMBER() OVER (
PARTITION BY semester ORDER BY marks DESC,
semester
) AS topStudent
FROM student_marks
) A
WHERE A.topStudent = 1;
END;

--TopperOfEachGenderInClass
DROP PROCEDURE IF EXISTS studentgrade.getTopperOfEachGenderInClass;
DELIMITER $$
CREATE PROCEDURE studentgrade.`getTopperOfEachGenderInClass`( semester int  )
BEGIN
WITH student_marks
AS (
SELECT m.semester,
st.name,
st.gender,
sum(m.marks) marks
FROM v_grades m,
student st
WHERE st.semester = 4
AND st.id = m.studentId
AND st.semester = m.semester
GROUP BY m.semester,
st.name
)
SELECT semester,
name,
marks,
gender
FROM (
SELECT semester,
name,
marks,
gender,
ROW_NUMBER() OVER (
PARTITION BY semester, gender ORDER BY marks DESC,
semester
) AS topStudent
FROM student_marks
) A
WHERE A.topStudent = 1;
end;