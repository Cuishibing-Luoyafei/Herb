create database herb;
use herb;
create table t_user(
	userId varchar(32) primary key,
	userNickName varchar(32) not null,
	userPassword varchar(20) not null,
    userGender varchar(2),					
    userIntroduce varchar(255),				
    userCreateDate datetime default current_timestamp,
    userAddress varchar(20),				
    userTel varchar(20),
    isAdmin varchar(1)
)engine=innoDB default charset=utf8;

create table t_commodity(
	commodityId varchar(32) primary key,
	commodityName varchar(32) not null,		
	commodityCategary varchar(10),			
	commodityPicture varchar(255),			
	commodityDescribe varchar(255),			
	commodityCount int,					
	commodityOldNewLevel int,			
	commodityNewPrice varchar(32),				
	commodityOldPrice varchar(32),				
	commodityOwnerId varchar(32),		
	commodifyCreateDate datetime default current_timestamp,		
	wantShopCount int,
	isOrder varchar(1)
)engine=innoDB default charset=utf8

--用来记录订单
create table t_commodityOrder (
	orderId varchar(32) primary key,
	commodityId varchar(32) not null,							--物品Id
	unitPrice varchar(11),										--单价
	commodityCount int default 0,								--个数
	userId varchar(32) not null,
	sellerId varchar(32) not null,
	addr varchar(255) not null,									--地址
	status varchar(1) default "0",								--状态(0:订单未创建；1:订单成功；2:订单作废)
	orderDate datetime default current_timestamp			--创建时间
)engine=innoDB default charset=utf8;

--用户Id与商品Id对应表
create table t_userId_commodityId (
	upId int primary key auto_increment,
	userId varchar(32) not null,
	commodityId varchar(32) not null
)engine=innoDB default charset=utf8;

--买家给卖家商品留言
create table t_reply (
	replyId int primary key auto_increment,
	commodityId varchar(32) not null,
	replyUserId varchar(32) not null,
	replyContent varchar(255),
	Timestamp datetime default current_timestamp
)engine=innoDB default charset=utf8;