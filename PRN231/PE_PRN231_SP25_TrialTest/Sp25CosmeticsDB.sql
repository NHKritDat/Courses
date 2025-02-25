USE master
GO

CREATE DATABASE Sp25CosmeticsDB
GO

USE Sp25CosmeticsDB
GO

CREATE TABLE SystemAccount (
  AccountID int primary key,
  AccountPassword nvarchar(100) not null,
  EmailAddress nvarchar(100) unique, 
  AccountNote nvarchar(240) not null,
  Role int
)
GO

INSERT INTO SystemAccount VALUES(551 ,N'@1','admin@CosmeticsDB.info', N'System Admin', 1);
INSERT INTO SystemAccount VALUES(553 ,N'@1','manager@CosmeticsDB.info', N'Manager', 2);
INSERT INTO SystemAccount VALUES(552 ,N'@1','staff@CosmeticsDB.info', N'Staff', 3);
INSERT INTO SystemAccount VALUES(554 ,N'@1','member1@CosmeticsDB.info', N'Member 1', 4);
GO


CREATE TABLE CosmeticCategory (
  CategoryID nvarchar(30) primary key,
  CategoryName nvarchar(120) not null,
  UsagePurpose nvarchar(250) not null, 
  FormulationType nvarchar(250) not null
)
GO

INSERT INTO CosmeticCategory VALUES(N'CAT0101011', N'Makeup', N'To enhance facial features and improve appearance for various occasions.', N'Includes products like foundations, concealers, eyeshadows, blushes, and lipsticks, often available in liquid, cream, powder, or stick forms.')
GO
INSERT INTO CosmeticCategory VALUES(N'CAT0101012', N'Skincare', N'To cleanse, moisturize, protect, and improve the health and appearance of the skin.', N'Comprises cleansers, toners, serums, moisturizers, and treatments, available in creams, gels, lotions, and oils.')
GO
INSERT INTO CosmeticCategory VALUES(N'CAT0101013', N'Body Care', N'To maintain and improve the health and appearance of the body skin, often focusing on hydration and protection.', N'Includes body lotions, creams, scrubs, and oils, typically found in creamy, gel, or liquid forms.')
GO
INSERT INTO CosmeticCategory VALUES(N'CAT0101014', N'Hair Care', N'To cleanse, condition, and style hair, promoting overall hair health and appearance.', N'Encompasses shampoos, conditioners, hair masks, and styling products, usually available in liquid or cream formulations.')
GO
INSERT INTO CosmeticCategory VALUES(N'CAT0101015', N'Fragrance', N'To provide a pleasant scent and enhance personal allure or mood.', N'Includes perfumes, colognes, and body sprays, typically available in liquid form with varying concentrations (e.g., Eau de Parfum, Eau de Toilette)')
GO

/*
Cosmetic Name: The name of the cosmetic product.
Ingredients: List of ingredients used in the cosmetic product.
Skin Type: Suitable skin types (e.g., oily, dry, sensitive, combination).
Size: Size or volume of the product (e.g., 30ml, 15g).
Price: Retail price of the product.
ExpirationDate: Shelf life or expiration date.

Category Name: The name of the category (e.g., skincare, makeup, haircare).
Subcategories: Specific types within the main category (e.g., under makeup: foundation, lipstick, eyeshadow).
Usage Purpose: Intended use or function (e.g., moisturizing, coverage, enhancing).
Formulation Type: How the product is formulated (e.g., liquid, cream, powder, gel).
*/


CREATE TABLE CosmeticInformation (
  CosmeticID nvarchar(30) PRIMARY KEY,
  CosmeticName nvarchar(160) not null,
  SkinType nvarchar(200) not null, 
  ExpirationDate nvarchar(160) not null,
  CosmeticSize nvarchar(400) not null, 
  DollarPrice decimal not null, 
  CategoryID nvarchar(30) FOREIGN KEY references CosmeticCategory(CategoryID) on delete cascade on update cascade,
)
GO


INSERT INTO CosmeticInformation VALUES(N'PL009601', N'Maybelline Fit Me Matte + Poreless Foundation', N'Oily/Combination', N'12 months after opening', N'30 ml', 7.99, N'CAT0101011')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009602', N'NARS Blush', N'All Skin Types', N'24 months after opening', N'4.8 g', 30, N'CAT0101011')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009603', N'CeraVe Hydrating Facial Cleanser', N'Normal/Dry', N'12 months after opening', N'355 ml', 15.99, N'CAT0101012')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009604', N'The Ordinary Niacinamide 10% + Zinc 1%', N'Oily/Acne-Prone', N'12 months after opening', N'30 ml', 6.5, N'CAT0101012')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009605', N'Vaseline Intensive Care Lotion', N'Dry Skin', N'24 months after opening', N'400 ml', 8, N'CAT0101013')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009606', N'Neutrogena Hydro Boost Water Gel', N'All Skin Types', N'12 months after opening', N'50 ml', 19.99, N'CAT0101013')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009607', N'Lush Sugar Plum Fairy Shower Gel', N'All Skin Types', N'12 months after opening', N'250 g', 14.95, N'CAT0101015')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009608', N'St. Ives Fresh Skin Apricot Scrub', N'All Skin Types', N'24 months after opening', N'170 g', 5.99, N'CAT0101015')
GO
INSERT INTO CosmeticInformation VALUES(N'PL009609', N'Eucerin Advanced Repair Cream', N'Dry Skin', N'24 months after opening', N'340 g', 14.99, N'CAT0101015')
GO



