-- 1. Position.DeptID ¡ú Department.DeptID
ALTER TABLE Position
ADD CONSTRAINT FK_Position_Department
FOREIGN KEY (DeptID) REFERENCES Department(DeptID);

-- 2. Employee.DeptID ¡ú Department.DeptID
ALTER TABLE Employee
ADD CONSTRAINT FK_Employee_Department
FOREIGN KEY (DeptID) REFERENCES Department(DeptID);

-- 3. Employee.PositionID ¡ú Position.PositionID
ALTER TABLE Employee
ADD CONSTRAINT FK_Employee_Position
FOREIGN KEY (PositionID) REFERENCES Position(PositionID);

-- 4. Employee.TitleID ¡ú Title.TitleID
ALTER TABLE Employee
ADD CONSTRAINT FK_Employee_Title
FOREIGN KEY (TitleID) REFERENCES Title(TitleID);

-- 5. Education.EmpID ¡ú Employee.EmpID
ALTER TABLE Education
ADD CONSTRAINT FK_Education_Employee
FOREIGN KEY (EmpID) REFERENCES Employee(EmpID);

-- 6. JobHistory.EmpID ¡ú Employee.EmpID
ALTER TABLE JobHistory
ADD CONSTRAINT FK_JobHistory_Employee
FOREIGN KEY (EmpID) REFERENCES Employee(EmpID);

-- 7. RewardPunish.EmpID ¡ú Employee.EmpID
ALTER TABLE RewardPunish
ADD CONSTRAINT FK_RewardPunish_Employee
FOREIGN KEY (EmpID) REFERENCES Employee(EmpID);
