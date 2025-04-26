CREATE TABLE `sale` (
    `id_sala` CHAR(36),
    `nome_sala` VARCHAR(255),
    `posti` INT,
    `imax` TINYINT(1),
    PRIMARY KEY (`id_sala`)
);

CREATE TABLE `programmazione` (
    `id_programmazioni` CHAR(36),
    `data_ora` DATETIME,
    `sala` CHAR(36),
    PRIMARY KEY (`id_programmazioni`),
    FOREIGN KEY (`sala`) REFERENCES `sale`(`id_sala`) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE `film` (
    `id_film` CHAR(36),
    `nome_film` VARCHAR(255),
    `data_uscita` DATE,
    PRIMARY KEY (`id_film`)
);

CREATE TABLE `film_programmazioni` (
    `film` CHAR(36),
    `programmazione` CHAR(36),
    PRIMARY KEY (`film`, `programmazione`),
    FOREIGN KEY (`film`) REFERENCES `film`(`id_film`) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (`programmazione`) references `programmazione`(`id_programmazioni`) ON UPDATE CASCADE ON DELETE RESTRICT
);