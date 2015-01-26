CREATE TABLE `Cancleation` (
  `modulecode` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  `description` LONGTEXT NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `Lab` (
  `modulecode` VARCHAR(255) NOT NULL,
  `semester` VARCHAR(255) NOT NULL,
  `weekday` INTEGER NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(255) NOT NULL,
  `startdate` DATE NOT NULL,
  `enddate` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`modulecode`, `weekday`)
);

CREATE TABLE `LabCancellation` (
  `modulecode` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `TakesModule` (
  `uid` INTEGER NOT NULL,
  `modulecode` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`uid`, `modulecode`)
);

CREATE TABLE `admin` (
  `uid` INTEGER PRIMARY KEY,
  `adminid` INTEGER NOT NULL
);

CREATE TABLE `course` (
  `id` INTEGER PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `department` VARCHAR(255) NOT NULL,
  `year` INTEGER NOT NULL
);

CREATE TABLE `group` (
  `groupid` INTEGER PRIMARY KEY,
  `groupname` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL
);

CREATE TABLE `grouptakescourse` (
  `gid` INTEGER NOT NULL,
  `coursecode` INTEGER NOT NULL,
  PRIMARY KEY (`gid`, `coursecode`)
);

CREATE TABLE `grouptakesmodule` (
  `gid` INTEGER NOT NULL,
  `modulecode` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`gid`, `modulecode`)
);

CREATE TABLE `hasmeeting` (
  `uid` INTEGER NOT NULL,
  `mid` INTEGER NOT NULL,
  PRIMARY KEY (`uid`, `mid`)
);

CREATE TABLE `ingroup` (
  `uid` INTEGER NOT NULL,
  `gid` INTEGER NOT NULL,
  PRIMARY KEY (`uid`, `gid`)
);

CREATE TABLE `lecture` (
  `modulecode` VARCHAR(255) NOT NULL,
  `semester` INTEGER NOT NULL,
  `weekday` INTEGER NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(255) NOT NULL,
  `startdate` DATE NOT NULL,
  `enddate` DATE NOT NULL,
  PRIMARY KEY (`modulecode`, `semester`, `weekday`, `time`)
);

CREATE TABLE `lecturer` (
  `uid` INTEGER PRIMARY KEY,
  `lecturerid` INTEGER NOT NULL,
  `title` VARCHAR(255) NOT NULL
);

CREATE TABLE `meeting` (
  `meetingid` INTEGER PRIMARY KEY,
  `date` DATE NOT NULL,
  `time` INTEGER NOT NULL,
  `room` VARCHAR(255) NOT NULL,
  `description` LONGTEXT NOT NULL,
  `priority` INTEGER NOT NULL,
  `organiser` VARCHAR(255) NOT NULL
);

CREATE TABLE `module` (
  `modulecode` VARCHAR(255) PRIMARY KEY,
  `description` LONGTEXT NOT NULL
);

CREATE TABLE `moduleincourse` (
  `modulecode` VARCHAR(255) NOT NULL,
  `courseid` INTEGER NOT NULL,
  PRIMARY KEY (`modulecode`, `courseid`)
);

CREATE TABLE `roomchange` (
  `modulecode` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  `room` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`modulecode`, `date`)
);

CREATE TABLE `student` (
  `uid` INTEGER PRIMARY KEY,
  `studentid` INTEGER NOT NULL
);

CREATE TABLE `teachesmodule` (
  `lecturerid` INTEGER NOT NULL,
  `modulecode` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`lecturerid`, `modulecode`)
);

CREATE TABLE `user` (
  `userid` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `passwordhash` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL
)
