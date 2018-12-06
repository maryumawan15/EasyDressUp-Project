CREATE TABLE `users` (
  `email` varchar(225) DEFAULT NULL,
  `firstName` varchar(225) DEFAULT NULL,
  `lastName` varchar(225) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `groupName` varchar(45) DEFAULT NULL,
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ;

CREATE TABLE `user_groups` (
  `email` varchar(225) NOT NULL,
  `grouname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `groupuser` FOREIGN KEY (`email`) REFERENCES `users` (`email`)
);

CREATE TABLE `category` (
  `category` varchar(225) NOT NULL,
  `parentCategory` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`category`),
  KEY `parentcat_idx` (`parentCategory`),
  CONSTRAINT `parentcat` FOREIGN KEY (`parentCategory`) REFERENCES `category` (`category`)
);

CREATE TABLE `cloths` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(225) DEFAULT NULL,
  `contentType` varchar(45) DEFAULT NULL,
  `uploadedOn` datetime DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `imagePath` varchar(225) DEFAULT NULL,
  `category` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `clothsUser_idx` (`user`),
  KEY `category_idx` (`category`),
  CONSTRAINT `category` FOREIGN KEY (`category`) REFERENCES `category` (`category`),
  CONSTRAINT `clothsUser` FOREIGN KEY (`user`) REFERENCES `users` (`userid`)
);

CREATE TABLE `useroutfit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `outfituser_idx` (`user`),
  CONSTRAINT `outfituserId` FOREIGN KEY (`user`) REFERENCES `users` (`userid`)
) ;

CREATE TABLE `useroutfitcalender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cloth` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `outfit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `outfitcloths_idx` (`cloth`),
  KEY `outfituser_idx` (`user`),
  KEY `outfit_idx` (`outfit`),
  CONSTRAINT `outfit` FOREIGN KEY (`outfit`) REFERENCES `useroutfit` (`id`),
  CONSTRAINT `outfitcloths` FOREIGN KEY (`cloth`) REFERENCES `cloths` (`id`),
  CONSTRAINT `outfituser` FOREIGN KEY (`user`) REFERENCES `users` (`userid`)
);