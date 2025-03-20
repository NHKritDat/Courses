USE master
GO

CREATE DATABASE WatercolorsPainting2024DB
GO

USE WatercolorsPainting2024DB
GO

CREATE TABLE UserAccount(
  UserAccountID int primary key,
  UserPassword nvarchar(50) not null,
  UserFullName nvarchar(70) not null,
  UserEmail nvarchar(90) unique, 
  Role int
)
GO

INSERT INTO UserAccount VALUES(100 ,N'@5', N'Administrator', 'admin@WatercolorsPainting.info', 1);
INSERT INTO UserAccount VALUES(101 ,N'@5', N'Staff', 'staff@WatercolorsPainting.info', 2);
INSERT INTO UserAccount VALUES(157 ,N'@5', N'Manager', 'manager@WatercolorsPainting.info', 3);
INSERT INTO UserAccount VALUES(158 ,N'@5', N'Customer', 'customer@WatercolorsPainting.info', 4);
GO


CREATE TABLE Style(
  StyleId nvarchar(30) primary key,
  StyleName nvarchar(100) not null,
  StyleDescription nvarchar(250) not null, 
  OriginalCountry nvarchar(160)
)
GO
INSERT INTO Style VALUES(N'SS00112', N'Wet-on-Wet Technique', N'In this style, wet paint is applied to a wet surface, allowing the colors to blend and bleed into one another. This technique is often used to create soft, seamless transitions between colors and to capture atmospheric effects ', N'Netherlands')
GO
INSERT INTO Style VALUES(N'SS00155', N'Dry Brush Technique', N'The dry brush technique involves using minimal water on the brush, resulting in a drier application of paint. This creates textured and rough brushstrokes, often used to depict details, textures, and intricate elements in a painting. ', N'France')
GO
INSERT INTO Style VALUES(N'SS00118', N'Glazing Technique', N'Glazing involves layering transparent washes of color on top of each other to create depth and richness in the painting. Each layer of color adds complexity to the overall composition, resulting in luminous and vibrant effects ', N'Japan')
GO
INSERT INTO Style VALUES(N'SS00342', N'Negative Painting', N'In negative painting, artists create shapes and forms by painting around the subject, allowing the surrounding areas to define the intended subject. ', N'United States')
GO
INSERT INTO Style VALUES(N'SS00662', N'Impressionistic Style', N'Inspired by the Impressionist movement, this style focuses on capturing the fleeting effects of light, color, and atmosphere. ', N'India')
GO




CREATE TABLE WatercolorsPainting(
 PaintingId nvarchar(45) primary key,
 PaintingName nvarchar(100) not null,
 PaintingDescription nvarchar(250),
 PaintingAuthor nvarchar(120),
 Price decimal,
 PublishYear int, 
 CreatedDate Datetime,
 StyleId nvarchar(30) references Style(StyleId) on delete cascade on update cascade
)
GO

INSERT INTO WatercolorsPainting VALUES(N'WP00111', N'The Starry Night', N'This iconic painting depicts a dramatic, swirling night sky over a tranquil village, showcasing van Goghs distinctive style and use of bold, expressive brushwork.', N'Vincent van Gogh', 1099, 1889, CAST(N'2024-2-16' AS DateTime), 'SS00112')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00112', N'Water Lilies series', N'This series of water lily paintings capture the play of light and color on the surface of the water, reflecting the artists fascination with capturing the ephemeral qualities of natural light.', N'Claude Monet', 1299, 1916, CAST(N'2024-2-16' AS DateTime), 'SS00155')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00113', N'The Great Wave off Kanagawa', N'This ukiyo-e woodblock print portrays a powerful wave towering over boats with Japans Mount Fuji in the background, showcasing Hokusais mastery of line, movement, and perspective', N'Katsushika Hokusai', 1199, 1831, CAST(N'2024-2-16' AS DateTime), 'SS00155')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00114', N'Sunflowers', N'This vibrant still life of sunflowers showcases his use of bold colors and distinctive brushwork, capturing the beauty and vitality of the flowers', N'Vincent van Gogh', 1249, 1888, CAST(N'2024-2-16' AS DateTime), 'SS00118')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00115', N'The Blue Boat', N'This painting depicts a solitary boat on calm blue waters, showcasing the artists skill in capturing the atmospheric effects of light and water in a tranquil seascape', N'Winslow Homer', 1349, 1895, CAST(N'2024-2-16' AS DateTime), 'SS00662')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00116', N'Japanese Bridge', N'This painting features a Japanese footbridge over a water lily pond in his Giverny garden, emphasizing the interplay of light, color, and nature in an enchanting and serene setting', N'Claude Monet', 1049, 1899, CAST(N'2024-2-16' AS DateTime), 'SS00155')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00117', N'Koi Fish', N'This watercolor painting captures the graceful movement and vibrant colors of koi fish in a pond, showcasing his mastery of capturing the fluidity and energy of aquatic life', N'Sargent Claude', 1699, 1919, CAST(N'2024-2-16' AS DateTime), 'SS00662')
GO
INSERT INTO WatercolorsPainting VALUES(N'WP00118', N'Church at Auvers', N'This painting depicts the church in Auvers-sur-Oise with expressive brushwork, capturing the architectural details and the surrounding landscape with emotive and vibrant colors', N'Vincent van Gogh', 1449, 1890, CAST(N'2024-2-16' AS DateTime), 'SS00155')
GO

