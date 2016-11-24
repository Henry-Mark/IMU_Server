-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.14 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 正在导出表  hh.friend 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
REPLACE INTO `friend` (`userId`, `friendId`, `beFriendTimeMillis`, `splitTimeMillis`, `lable`) VALUES
	(100000010, 100001, 0, 0, '');
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;

-- 正在导出表  hh.message 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
REPLACE INTO `message` (`MessageId`, `messageType`, `state`, `fromUserId`, `toUserId`, `SendTimeMillis`, `LastAccessTimeMillis`, `Reservedfiled1`, `Reservedfiled2`) VALUES
	(1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- 正在导出表  hh.user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`userId`, `account`, `nickname`, `username`, `password`, `type`, `age`, `phone`, `sex`, `avatarUrl`, `signature`, `address`, `RegistrationTimeMillis`, `LastAccessTimeMillis`, `Reservedfiled1`, `Reservedfiled2`, `state`) VALUES
	(100001, 'hhs', 'henry', '', '123456', 0, 0, '', 0, '', '', '', 1479977809636, 1479977809636, '', '', 1),
	(100002, 'hhh', 'henry', '', '123456', 0, 0, '', 0, '', '', '', 1479976346634, 1479976346634, '', '', 1),
	(100000010, 'hhhs', 'henry', '', '123456', 0, 0, '', 0, '', '', '', 1479978073096, 1479978073096, '', '', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
