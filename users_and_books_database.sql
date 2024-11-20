drop database if exists OOP_Library;

create database OOP_Library;
use OOP_Library;

drop table if exists users;
create table users (
	user_id int not null auto_increment,
    user_name varchar(255),
    user_phone varchar(255),
    user_password varchar(255),
    user_admin boolean,
    primary key (user_id)
);


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oop_library`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--
drop table if exists books;
CREATE TABLE `books` (
  `book_id` varchar(255) NOT NULL,
  `book_name` varchar(255) DEFAULT NULL,
  `book_author` varchar(255) DEFAULT NULL,
  `book_publication_year` int(11) DEFAULT NULL,
  `borrowed_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `book_description` text DEFAULT NULL,
  `thumbnail` text DEFAULT NULL,
  `catagory` varchar(55) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`book_id`),
  ADD KEY `borrower` (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `borrower` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

insert into users (user_name, user_phone, user_password, user_admin)
values 
	('Huy', '0965425660', '27042005QuocHuy@@', true),
    ('Hoàng', '0835817188', '02052005Hoang@', true);

ALTER TABLE books CHANGE book_publication_year
book_publication_year VARCHAR(11) NULL DEFAULT NULL;

select * from users;