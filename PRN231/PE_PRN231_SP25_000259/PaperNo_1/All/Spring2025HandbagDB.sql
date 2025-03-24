USE master
GO

CREATE DATABASE Spring2025HandbagDB  
GO

USE Spring2025HandbagDB 
GO

CREATE TABLE SystemAccounts (
    AccountID INT PRIMARY KEY, 
    Username VARCHAR(100) NOT NULL, 
    Email VARCHAR(255) NOT NULL, 
	Password VARCHAR(255) NOT NULL, 
    Role int, 
    IsActive bit DEFAULT 1
);

INSERT INTO SystemAccounts (AccountID, Username, Email, Password, Role, IsActive) VALUES
(1, 'admin456', 'admin@handbagasian.org', '@1', 1, 1),
(2, 'steve', 'steve@handbagasian.org', '@1',  4, 1),
(3, 'modjose', 'jose@handbagasian.org', '@1', 2, 1),
(4, 'michael', 'machael@handbagasian.org', '@1', 4, 0),
(5, 'devops', 'dev@globalflower.com', '@1', 3, 1);

CREATE TABLE Brand (
    BrandID INT PRIMARY KEY,
    BrandName VARCHAR(255) NOT NULL,
    Country VARCHAR(100),
    FoundedYear INT,
    Website VARCHAR(255)
);

CREATE TABLE Handbag (
    HandbagID INT PRIMARY KEY,
    BrandID INT,
    ModelName VARCHAR(255) NOT NULL,
    Material VARCHAR(100),
    Color VARCHAR(50),
    Price DECIMAL(10, 2),
    Stock INT,
    ReleaseDate DATE,
    CONSTRAINT fk_handbag_brand FOREIGN KEY (BrandID) REFERENCES Brand(BrandID) ON DELETE CASCADE
);

INSERT INTO Brand (BrandID, BrandName, Country, FoundedYear, Website) VALUES
(1, 'Louis Vuitton', 'France', 1854, 'https://www.louisvuitton.com'),
(2, 'Gucci', 'Italy', 1921, 'https://www.gucci.com'),
(3, 'Chanel', 'France', 1910, 'https://www.chanel.com'),
(4, 'Prada', 'Italy', 1913, 'https://www.prada.com'),
(5, 'Hermes', 'France', 1837, 'https://www.hermes.com'),
(6, 'Dior', 'France', 1946, 'https://www.dior.com'),
(7, 'Burberry', 'UK', 1856, 'https://www.burberry.com'),
(8, 'Coach', 'USA', 1941, 'https://www.coach.com'),
(9, 'Michael Kors', 'USA', 1981, 'https://www.michaelkors.com'),
(10, 'Fendi', 'Italy', 1925, 'https://www.fendi.com');


INSERT INTO Handbag (HandbagID, BrandID, ModelName, Material, Color, Price, Stock, ReleaseDate) VALUES
(1, 1, 'Speedy 30', 'Canvas', 'Brown', 1550.00, 50, '2024-01-10'),
(2, 2, 'Marmont Mini', 'Leather', 'Black', 2100.00, 30, '2024-02-05'),
(3, 3, 'Classic Flap Bag', 'Leather', 'Black', 7500.00, 20, '2023-12-20'),
(4, 4, 'Galleria Bag', 'Leather', 'Beige', 3600.00, 15, '2023-11-15'),
(5, 5, 'Birkin 25', 'Leather', 'Orange', 15000.00, 5, '2023-10-01'),
(6, 6, 'Lady Dior', 'Leather', 'Red', 5200.00, 10, '2024-02-01'),
(7, 6, 'TB Bag', 'Leather', 'Beige', 2500.00, 25, '2024-01-20'),
(8, 6, 'Tabby Shoulder Bag', 'Leather', 'Green', 495.00, 100, '2024-03-01'),
(9, 9, 'Jet Set Tote', 'Leather', 'Black', 398.00, 120, '2024-02-10'),
(10, 10, 'Baguette Bag', 'Canvas', 'Brown', 3300.00, 35, '2023-12-10');
