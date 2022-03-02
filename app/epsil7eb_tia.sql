-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  mar. 16 avr. 2019 à 14:28
-- Version du serveur :  5.5.61-38.13-log
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `epsil7eb_tia`
--

-- --------------------------------------------------------

--
-- Structure de la table `Affectations`
--

CREATE TABLE `Affectations` (
  `idAffectation` varchar(50) NOT NULL,
  `createdAt` datetime DEFAULT NULL,
  `dateDebut` datetime DEFAULT NULL,
  `dateFin` datetime DEFAULT NULL,
  `Sites_idSites` varchar(50) NOT NULL,
  `Agents_NumeroMat` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Affectations`
--

INSERT INTO `Affectations` (`idAffectation`, `createdAt`, `dateDebut`, `dateFin`, `Sites_idSites`, `Agents_NumeroMat`) VALUES
('JHH4224', '2019-03-27 11:50:44', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'GEHH3456', '234567'),
('234567', '2019-03-20 00:00:00', '2019-03-26 00:00:00', '2019-03-30 00:00:00', '234567', '234567'),
('101bcb6ae347e40a3dd67b0f4d421961', '2019-03-27 12:03:37', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'GEHH3456', '234567fefe'),
('f9fcd4113fd908443d2fd86c0b806931', '2019-03-27 12:04:51', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'GEHH3456', '234567fefe'),
('c2639c97575d3ad14553ea703f1a875b', '2019-03-29 02:04:26', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'GEHH3456', '234567fefe');

-- --------------------------------------------------------

--
-- Structure de la table `Agents`
--

CREATE TABLE `Agents` (
  `NumeroMat` varchar(50) NOT NULL,
  `Nom` varchar(45) NOT NULL,
  `Postnom` varchar(45) DEFAULT NULL,
  `Prenom` varchar(45) NOT NULL,
  `Sex` varchar(15) NOT NULL,
  `Tache` varchar(50) NOT NULL,
  `image` text,
  `NumeroCard` varchar(45) NOT NULL,
  `fingerData` text,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Agents`
--

INSERT INTO `Agents` (`NumeroMat`, `Nom`, `Postnom`, `Prenom`, `Sex`, `Tache`, `image`, `NumeroCard`, `fingerData`, `createdAt`, `updatedAt`) VALUES
('234567', 'sambaj', 'sambaj', 'maureen', 'F', 'CEO', NULL, '9876', NULL, '2019-03-18 00:00:00', NULL),
('OIU5678BG', 'nzolo', 'ngwe', 'aime', 'M', '', NULL, '1554607237', NULL, '2019-04-07 03:20:37', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Contrats`
--

CREATE TABLE `Contrats` (
  `idContrats` varchar(50) NOT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `Agents_NumeroMat` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Controller`
--

CREATE TABLE `Controller` (
  `idController` int(11) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `Sites_idSites` varchar(50) NOT NULL,
  `Agents_NumeroMat` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Horaires`
--

CREATE TABLE `Horaires` (
  `idHoraires` varchar(50) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `heureDebut` time DEFAULT NULL,
  `heureFin` time DEFAULT NULL,
  `Agents_NumeroMat` varchar(50) NOT NULL,
  `Affectations_idAffectation` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Horaires`
--

INSERT INTO `Horaires` (`idHoraires`, `type`, `dateDebut`, `dateFin`, `heureDebut`, `heureFin`, `Agents_NumeroMat`, `Affectations_idAffectation`) VALUES
('c2639c97575d3ad14553ea703f1a875b', '2', '2019-01-01', '2019-01-01', '08:00:00', '16:00:00', '234567fefe', 'c2639c97575d3ad14553ea703f1a875b'),
('7c45eefd7c7c49490cf59ccef99387ef', '2', '2019-01-02', '2019-01-02', '08:00:00', '16:00:00', '234567fefe', 'c2639c97575d3ad14553ea703f1a875b'),
('e60869797aae1e5a0c9fc3a731635bd1', '2', '2019-01-03', '2019-01-03', '08:00:00', '16:00:00', '234567fefe', 'c2639c97575d3ad14553ea703f1a875b');

-- --------------------------------------------------------

--
-- Structure de la table `Presences`
--

CREATE TABLE `Presences` (
  `idPresences` varchar(50) NOT NULL,
  `debut` datetime DEFAULT NULL,
  `fin` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `Agents_NumeroMat` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Presences`
--

INSERT INTO `Presences` (`idPresences`, `debut`, `fin`, `createdAt`, `Agents_NumeroMat`) VALUES
('234567', '2019-03-27 06:00:00', '2019-03-27 17:00:00', '2019-03-26 06:00:00', '234567');

-- --------------------------------------------------------

--
-- Structure de la table `Sites`
--

CREATE TABLE `Sites` (
  `idSites` varchar(50) NOT NULL,
  `nom` varchar(250) DEFAULT NULL,
  `location` text,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Sites`
--

INSERT INTO `Sites` (`idSites`, `nom`, `location`, `createdAt`, `updatedAt`) VALUES
('234567', 'Epsilon', 'ni skndks ksdk d', '2019-03-25 00:00:00', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Affectations`
--
ALTER TABLE `Affectations`
  ADD PRIMARY KEY (`idAffectation`,`Sites_idSites`,`Agents_NumeroMat`),
  ADD KEY `fk_Affectation_Sites1_idx` (`Sites_idSites`),
  ADD KEY `fk_Affectations_Agents1_idx` (`Agents_NumeroMat`);

--
-- Index pour la table `Agents`
--
ALTER TABLE `Agents`
  ADD PRIMARY KEY (`NumeroMat`),
  ADD UNIQUE KEY `NumeraMat_UNIQUE` (`NumeroMat`);

--
-- Index pour la table `Contrats`
--
ALTER TABLE `Contrats`
  ADD PRIMARY KEY (`idContrats`,`Agents_NumeroMat`),
  ADD KEY `fk_Contrats_Agents1_idx` (`Agents_NumeroMat`);

--
-- Index pour la table `Controller`
--
ALTER TABLE `Controller`
  ADD PRIMARY KEY (`idController`,`Sites_idSites`,`Agents_NumeroMat`),
  ADD KEY `fk_Controller_Agents1_idx` (`Agents_NumeroMat`),
  ADD KEY `fk_Controller_Sites1_idx` (`Sites_idSites`);

--
-- Index pour la table `Horaires`
--
ALTER TABLE `Horaires`
  ADD PRIMARY KEY (`idHoraires`,`Agents_NumeroMat`,`Affectations_idAffectation`),
  ADD KEY `fk_Horaires_Affectations1_idx` (`Affectations_idAffectation`),
  ADD KEY `fk_Horaires_Agents1_idx` (`Agents_NumeroMat`);

--
-- Index pour la table `Presences`
--
ALTER TABLE `Presences`
  ADD PRIMARY KEY (`idPresences`,`Agents_NumeroMat`),
  ADD KEY `fk_Presences_Agents1_idx` (`Agents_NumeroMat`);

--
-- Index pour la table `Sites`
--
ALTER TABLE `Sites`
  ADD PRIMARY KEY (`idSites`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Controller`
--
ALTER TABLE `Controller`
  MODIFY `idController` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
