CREATE TABLE `Cancellation` (
  `modulecode` VARCHAR(7) NOT NULL,
  `date` DATE NOT NULL,
  `description` LONGTEXT NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `Practical` (
  `modulecode` VARCHAR(7) NOT NULL,
  `semester` VARCHAR(1) NOT NULL,
  `weekday` INTEGER NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(20) NOT NULL,
  `startdate` DATE NOT NULL,
  `enddate` DATE NOT NULL,
  PRIMARY KEY (`modulecode`, `semester`, `weekday`, `time`)
);

CREATE TABLE `PracticalCancellation` (
  `modulecode` VARCHAR(7) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `TakesModule` (
  `uid` INTEGER NOT NULL,
  `modulecode` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`uid`, `modulecode`)
);

CREATE TABLE `Admin` (
  `uid` INTEGER PRIMARY KEY,
  `adminid` INTEGER NOT NULL
);

CREATE TABLE `Course` (
  `id` INTEGER PRIMARY KEY,
  `name` VARCHAR(20) NOT NULL,
  `department` VARCHAR(50) NOT NULL,
  `year` INTEGER NOT NULL
);

CREATE TABLE `Group` (
  `groupid` INTEGER PRIMARY KEY,
  `groupname` VARCHAR(25) NOT NULL,
  `type` VARCHAR(25) NOT NULL
);

CREATE TABLE `GroupTakesCourse` (
  `gid` INTEGER NOT NULL,
  `coursecode` INTEGER NOT NULL,
  PRIMARY KEY (`gid`, `coursecode`)
);

CREATE TABLE `GroupTakesModule` (
  `gid` INTEGER NOT NULL,
  `modulecode` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`gid`, `modulecode`)
);

CREATE TABLE `HasMeeting` (
  `uid` INTEGER NOT NULL,
  `mid` INTEGER NOT NULL,
  PRIMARY KEY (`uid`, `mid`)
);

CREATE TABLE `InGroup` (
  `uid` INTEGER NOT NULL,
  `gid` INTEGER NOT NULL,
  PRIMARY KEY (`uid`, `gid`)
);

CREATE TABLE `Lecture` (
  `modulecode` VARCHAR(7) NOT NULL,
  `semester` INTEGER NOT NULL,
  `weekday` INTEGER NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(20) NOT NULL,
  `startdate` DATE NOT NULL,
  `enddate` DATE NOT NULL,
  PRIMARY KEY (`modulecode`, `semester`, `weekday`, `time`)
);

CREATE TABLE `Lecturer` (
  `uid` INTEGER PRIMARY KEY,
  `lecturerid` INTEGER NOT NULL,
  `title` VARCHAR(10) NOT NULL
);

CREATE TABLE `Meeting` (
  `meetingid` INTEGER PRIMARY KEY,
  `date` DATE NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(20) NOT NULL,
  `description` LONGTEXT NOT NULL,
  `priority` INTEGER NOT NULL,
  `organiser` VARCHAR(100) NOT NULL
);

CREATE TABLE `Module` (
  `modulecode` VARCHAR(7) PRIMARY KEY,
  `description` LONGTEXT NOT NULL
);

CREATE TABLE `ModuleInCourse` (
  `modulecode` VARCHAR(7) NOT NULL,
  `courseid` INTEGER NOT NULL,
  PRIMARY KEY (`modulecode`, `courseid`)
);

CREATE TABLE `RoomChange` (
  `modulecode` VARCHAR(7) NOT NULL,
  `date` DATE NOT NULL,
  `room` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `Student` (
  `uid` INTEGER PRIMARY KEY,
  `studentid` INTEGER NOT NULL
);

CREATE TABLE `TeachesModule` (
  `lecturerid` INTEGER NOT NULL,
  `modulecode` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`lecturerid`, `modulecode`)
);

CREATE TABLE `User` (
  `userid` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `passwordhash` VARCHAR(100) NOT NULL,
  `email` VARCHAR(70) NOT NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(50) NOT NULL
)
