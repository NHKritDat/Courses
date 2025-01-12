use master
go

create database CandidateManagement
go

use CandidateManagement
go

create table JobPosting(
  PostingID nvarchar(20) primary key,
  JobPostingTitle nvarchar(100) not null,
  Description nvarchar(250), 
  PostedDate Datetime
)
go

create table CandidateProfile(
 CandidateID nvarchar(20) primary key,
 Fullname nvarchar(100) not null,
 Birthday Datetime,
 ProfileShortDescription nvarchar(250),
 ProfileURL nvarchar(150),
 PostingID nvarchar(20) references JobPosting(PostingID) on delete cascade on update cascade
)
go

create table HRAccount
(
 Email nvarchar(20) PRIMARY KEY,
 Password nvarchar(40),
 FullName nvarchar(30), 
 MemberRole int
);
go

insert into HRAccount VALUES ('admin@hr.com.vn','123@','Admin HR', 1);

insert into HRAccount VALUES ('manager@hr.com.vn','123@','Manager HR', 2);

insert into HRAccount VALUES ('staff@hr.com.vn','123','Staff HR', 3);
go


insert into JobPosting values(N'P0001',N'System Administrator Specialist (MS Exchange, AD Voice))', N'As our company is growing and we offer more service to our teams, we are looking for a Senior System engineer with a broad set of expertise in Messaging, voice systems, mail gateways, encryption (smime), etc.', CAST(N'2022-07-01' AS DateTime))

insert into JobPosting values(N'P0002',N'IT Security Manager', N'Establish the Information Security Plan toward to updated ISO Framework as well as in alignment with IT Strategic Plan', CAST(N'2022-07-01' AS DateTime))

insert into JobPosting values(N'P0003',N'(Senior) IT Recruitment Consultant', N'Developing and maintaining loyal business relationship with them aligning with our company and team business strategies', CAST(N'2022-07-01' AS DateTime))

insert into JobPosting values(N'P0004',N'Senior Network Security Engineer', N'In this role you will provide Security Subject Matter technical and leadership, expertise, support and consultancy/advice within relevant service levels for several cyber security technology systems and services currently in use across NAB.', CAST(N'2022-07-01' AS DateTime))

insert into JobPosting values(N'P0005',N'Mobile Game Developer ', N'Responsible for QA phase of those large-scale projects (or multi projects) with 7-8 members. Responsibilities are to instruct the team on quality metrics and testing strategies for each project.', CAST(N'2022-07-01' AS DateTime))
go

insert into CandidateProfile values(N'CANDIDATE0001',N'Alan Clinton',CAST(N'1990-09-01' AS DateTime), N'Excellent communication skills with a commitment to understanding customer requirements as well as business objectives.',N'AlanClinton.PDF', N'P0002')

insert into CandidateProfile values(N'CANDIDATE0002',N'Ethelbert Harron',CAST(N'1993-04-16' AS DateTime), N'Critical thinking skills and an analytical mind with strong problem-solving abilitiess.',N'EthelbertHarron.PDF', N'P0002')

insert into CandidateProfile values(N'CANDIDATE0003',N'Kenneth Paul',CAST(N'1980-09-24' AS DateTime), N'Highly experienced with various operating systems and databases',N'KennethPaul.PDF', N'P0002')

insert into CandidateProfile values(N'CANDIDATE0004',N'Michael Leonard',CAST(N'1995-12-30' AS DateTime), N'Proven ability to troubleshoot hardware, software, and network issues quickly and effectively.',N'MichaelLeonard.PDF', N'P0002')

insert into CandidateProfile values(N'CANDIDATE0005',N'William Peter',CAST(N'1991-06-28' AS DateTime), N'Problem solving skills to revise existing systems and suggest improvements.',N'WilliamPeter.PDF', N'P0003')

insert into CandidateProfile values(N'CANDIDATE0006',N'Edward David Clinton',CAST(N'1990-07-13' AS DateTime), N'Patience and ability to train users in both new and existing IT systems.',N'EdwardDavidClinton.PDF', N'P0004')

insert into CandidateProfile values(N'CANDIDATE0007',N'Harvey Louis',CAST(N'1990-09-01' AS DateTime), N'Enthusiastic about working collaboratively.',N'Harvey Louis.PDF', N'P0005')
go




