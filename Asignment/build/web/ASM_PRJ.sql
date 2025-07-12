USE [master]
GO
DROP DATABASE [PRJ301_ASS]
GO
CREATE DATABASE [PRJ301_ASS] 
GO
USE [PRJ301_ASS]
GO

-- Food Ordering System Database
-- Created based on the provided relation model
-- Modified to combine User and User_Detail tables
-- Updated with proper Unicode support for Vietnamese characters

-- 1. Combined User Table
CREATE TABLE [User] (
    User_ID INT IDENTITY(1,1) PRIMARY KEY,
    User_name VARCHAR(255),
    Password VARCHAR(255),
    Role VARCHAR(50), -- Admin/Manager/Customer
    User_fullName NVARCHAR(255) COLLATE Vietnamese_CI_AS,
    Phone VARCHAR(20),
    Address NVARCHAR(500) COLLATE Vietnamese_CI_AS,
    Gender NVARCHAR(10) COLLATE Vietnamese_CI_AS
);

-- 4. Promotion Table
CREATE TABLE [Promotion] (
    Promote_ID INT PRIMARY KEY,
    Promote_code VARCHAR(50),
    Value INT,
    Time_valid INT,
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID)
);

-- 2. Cart Table
CREATE TABLE [Cart] (
    Cart_ID INT IDENTITY(1,1) PRIMARY KEY,
    Cart_status VARCHAR(50), -- valid/not valid
    Promote_ID INT,
    User_ID INT,
    FOREIGN KEY (Promote_ID) REFERENCES [Promotion](Promote_ID),
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID)
);

-- 5. Notification Table
CREATE TABLE [Notification] (
    Notification_ID INT PRIMARY KEY,
    Title NVARCHAR(255) COLLATE Vietnamese_CI_AS,
    Content NTEXT COLLATE Vietnamese_CI_AS,
    Type VARCHAR(50),
    Notification_status VARCHAR(50), -- checked/not checked
    DateReceive DATE,
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID)
);

-- 13. Payment Table
CREATE TABLE [Payment] (
    Payment_ID INT IDENTITY(1,1) PRIMARY KEY,
    Payment_code VARCHAR(255),
    Payment_date DATE,
    Payment_status VARCHAR(50), -- Success/Fail
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID)
);

-- 7. Order Table
CREATE TABLE [Order] (
    Order_ID INT IDENTITY(1,1) PRIMARY KEY,
    Order_time DATE,
    User_ID INT,
    Payment_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID),
    FOREIGN KEY (Payment_ID) REFERENCES [Payment](Payment_ID)
);

-- 15. Category Table
CREATE TABLE [Category] (
    Category_ID INT PRIMARY KEY,
    Category_name NVARCHAR(100) COLLATE Vietnamese_CI_AS -- Cơm/Đồ ăn kèm/Canh/Nước
);

-- 9. Menu Table
CREATE TABLE [Menu] (
    Menu_ID INT PRIMARY KEY,
    Food NVARCHAR(255) COLLATE Vietnamese_CI_AS,
    Image VARCHAR(255),
    Price DECIMAL(10,2),
    Food_description NTEXT COLLATE Vietnamese_CI_AS,
    Food_status VARCHAR(50), -- Ready to serve/Preparing/Out of Stock
    Category_ID INT,
    FOREIGN KEY (Category_ID) REFERENCES [Category](Category_ID)
);

-- 8. Order_Item Table
CREATE TABLE [Order_Item] (
    Order_ID INT,
    Menu_ID INT,
    Order_quantity INT,
    Order_price_time DATE,
    PRIMARY KEY (Order_ID, Menu_ID),
    FOREIGN KEY (Order_ID) REFERENCES [Order](Order_ID),
    FOREIGN KEY (Menu_ID) REFERENCES [Menu](Menu_ID)
);

-- 10. Purchase Table
CREATE TABLE [Purchase] (
    User_ID INT,
    Menu_ID INT,
    PRIMARY KEY (User_ID, Menu_ID),
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID),
    FOREIGN KEY (Menu_ID) REFERENCES [Menu](Menu_ID)
);

-- 11. Cart_Item Table
CREATE TABLE [Cart_Item] (
    Cart_ID INT,
    Menu_ID INT,
    Food_quantity INT,
    Cart_price_time DATE,
    PRIMARY KEY (Cart_ID, Menu_ID),
    FOREIGN KEY (Cart_ID) REFERENCES [Cart](Cart_ID),
    FOREIGN KEY (Menu_ID) REFERENCES [Menu](Menu_ID)
);

-- 12. Feedback Table
CREATE TABLE [Feedback] (
    Feedback_ID INT IDENTITY(1,1) PRIMARY KEY,
    Rating INT,
    Comment NTEXT COLLATE Vietnamese_CI_AS,
    User_ID INT,
    Menu_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID),
    FOREIGN KEY (Menu_ID) REFERENCES [Menu](Menu_ID)
);

-- 14. Payment_Method Table
CREATE TABLE [Payment_Method] (
    Method_ID INT PRIMARY KEY,
    Select_method VARCHAR(50), -- Momo/Napas/Paypal
    Payment_ID INT,
    FOREIGN KEY (Payment_ID) REFERENCES [Payment](Payment_ID)
);

-- 6. Ship Table
CREATE TABLE [Ship] (
    Ship_ID INT IDENTITY(1,1) PRIMARY KEY,
    Ship_time DATE,
    Ship_status VARCHAR(50), -- arrived/not arrived
    User_ID INT,
    Order_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [User](User_ID),
    FOREIGN KEY (Order_ID) REFERENCES [Order](Order_ID)
);

-- ==========================================
-- SAMPLE DATA INSERTION
-- ==========================================

-- Insert Categories
INSERT INTO [Category] (Category_ID, Category_name) VALUES
(1, N'Cơm'),
(2, N'Đồ ăn kèm'),
(3, N'Canh'),
(4, N'Nước');

-- Insert Menu Items
INSERT INTO [Menu] (Menu_ID, Food, Image, Price, Food_description, Food_status, Category_ID) VALUES
-- Cơm (Rice dishes)
(1, N'Cơm trắng', '', 15000.00, N'Cơm trắng thơm ngon, hạt dẻo', 'Ready to serve', 1),
(2, N'Cơm chiên', '', 25000.00, N'Cơm chiên trứng thơm ngon với hành lá', 'Ready to serve', 1),
(3, N'Bún', '', 20000.00, N'Bún tươi dai ngon, ăn kèm rau sống', 'Ready to serve', 1),

-- Đồ ăn kèm (Side dishes)
(4, N'Sườn non', '', 45000.00, N'Sườn non nướng thơm lừng, mềm ngọt', 'Ready to serve', 2),
(5, N'Sườn mềm', '', 40000.00, N'Sườn mềm ướp gia vị đậm đà', 'Ready to serve', 2),
(6, N'Xiên thịt', '', 35000.00, N'Xiên thịt nướng than hoa thơm ngon', 'Ready to serve', 2),
(7, N'Lạp xưởng', '', 30000.00, N'Lạp xưởng Trung Quốc ngọt thanh', 'Ready to serve', 2),
(8, N'Thịt ba rọi', '', 38000.00, N'Thịt ba rọi nướng vàng ươm', 'Ready to serve', 2),
(9, N'Gà nướng', '', 50000.00, N'Gà nướng lá chuối thơm phức', 'Ready to serve', 2),
(10, N'Bì', '', 25000.00, N'Bì heo trộn dừa nạo giòn tan', 'Ready to serve', 2),
(11, N'Chả', '', 28000.00, N'Chả cá thác lác dai ngon', 'Ready to serve', 2),
(12, N'Trứng', '', 8000.00, N'Trứng ốp la vàng ươm', 'Ready to serve', 2),
(13, N'Top mỡ hành', '', 12000.00, N'Mỡ hành thơm ngon béo ngậy', 'Ready to serve', 2),
(14, N'Đồ chua', '', 15000.00, N'Đồ chua cà rốt, củ cải chua ngọt', 'Ready to serve', 2),

-- Canh (Soup)
(15, N'Canh bí', '', 18000.00, N'Canh bí đỏ ngọt thanh mát', 'Ready to serve', 3),
(16, N'Canh chua', '', 22000.00, N'Canh chua cà chua thơm ngon', 'Ready to serve', 3),
(17, N'Canh rong biển', '', 20000.00, N'Canh rong biển bổ dưỡng', 'Ready to serve', 3),

-- Nước (Drinks)
(18, N'Trà đá', '', 5000.00, N'Trà đá mát lạnh giải khát', 'Ready to serve', 4),
(19, N'Nước lọc', '', 8000.00, N'Nước lọc tinh khiết', 'Ready to serve', 4),
(20, N'Pepsi', '', 12000.00, N'Pepsi cola mát lạnh', 'Ready to serve', 4),
(21, N'Cocacola', '', 12000.00, N'Cocacola nguyên chất', 'Ready to serve', 4),
(22, N'Trà chanh', '', 15000.00, N'Trà chanh tươi mát', 'Ready to serve', 4);

-- Insert Combined User Data
INSERT INTO [User] (User_name, Password, Role, User_fullName, Phone, Address, Gender) VALUES
('admin', 'admin123', 'Admin', N'Nguyễn Văn Admin', '0901234567', N'123 Đường Lê Lợi, Quận 1, TP.HCM', N'Male'),
('manager1', 'manager123', 'Manager', N'Trần Thị Manager', '0912345678', N'456 Đường Nguyễn Huệ, Quận 1, TP.HCM', N'Female'),
('customer1', 'customer123', 'Customer', N'Lê Văn Khách', '0923456789', N'789 Đường Trần Hưng Đạo, Quận 5, TP.HCM', N'Male'),
('customer2', 'customer456', 'Customer', N'Phạm Thị Lan', '0934567890', N'101 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM', N'Female'),
('customer3', 'customer789', 'Customer', N'Hoàng Minh Tuấn', '0945678901', N'202 Đường Võ Văn Tần, Quận 3, TP.HCM', N'Male');

-- Insert Promotions
INSERT INTO [Promotion] (Promote_ID, Promote_code, Value, Time_valid, User_ID) VALUES
(1, 'WELCOME10', 10, 30, 3),
(2, 'BIRTHDAY20', 20, 7, 4),
(3, 'LOYALTY15', 15, 60, 5),
(4, 'NEWUSER25', 25, 14, 3);

-- Insert Carts
INSERT INTO [Cart] (Cart_status, Promote_ID, User_ID) VALUES
('valid', 1, 3),
('valid', 2, 4),
('valid', 3, 5),
('not valid', 3, 3);

-- Insert Cart Items
INSERT INTO [Cart_Item] (Cart_ID, Menu_ID, Food_quantity, Cart_price_time) VALUES
(1, 1, 1, '2025-07-09'),
(1, 4, 1, '2025-07-09'),
(1, 18, 1, '2025-07-09'),
(2, 2, 2, '2025-07-09'),
(2, 9, 1, '2025-07-09'),
(2, 21, 2, '2025-07-09'),
(3, 3, 1, '2025-07-09'),
(3, 6, 2, '2025-07-09'),
(3, 15, 1, '2025-07-09');

-- Insert Payments
INSERT INTO [Payment] (Payment_code, Payment_date, Payment_status, User_ID) VALUES
('PAY001', '2025-07-08', 'Success', 3),
('PAY002', '2025-07-08', 'Success', 4),
('PAY003', '2025-07-09', 'Success', 5),
('PAY004', '2025-07-09', 'Fail', 3);

-- Insert Payment Methods
INSERT INTO [Payment_Method] (Method_ID, Select_method, Payment_ID) VALUES
(1, 'Momo', 1),
(2, 'Paypal', 2),
(3, 'Napas', 3),
(4, 'Momo', 4);

-- Insert Orders
INSERT INTO [Order] (Order_time, User_ID, Payment_ID) VALUES
('2025-07-08', 3, 1),
('2025-07-08', 4, 2),
('2025-07-09', 5, 3);

-- Insert Order Items
INSERT INTO [Order_Item] (Order_ID, Menu_ID, Order_quantity, Order_price_time) VALUES
(1, 1, 1, '2025-07-08'),
(1, 4, 1, '2025-07-08'),
(1, 18, 1, '2025-07-08'),
(2, 2, 2, '2025-07-08'),
(2, 9, 1, '2025-07-08'),
(2, 21, 2, '2025-07-08'),
(3, 3, 1, '2025-07-09'),
(3, 6, 2, '2025-07-09'),
(3, 15, 1, '2025-07-09');

-- Insert Shipping
INSERT INTO [Ship] (Ship_time, Ship_status, User_ID, Order_ID) VALUES
('2025-07-09', 'arrived', 3, 1),
('2025-07-09', 'arrived', 4, 2),
('2025-07-10', 'not arrived', 5, 3);

-- Insert Notifications
INSERT INTO [Notification] (Notification_ID, Title, Content, Type, Notification_status, DateReceive, User_ID) VALUES
(1, N'Đơn hàng đã được xác nhận', N'Đơn hàng #1 của bạn đã được xác nhận và đang chuẩn bị', 'Order', 'checked', '2025-07-08', 3),
(2, N'Đơn hàng đã giao thành công', N'Đơn hàng #1 đã được giao thành công', 'Delivery', 'checked', '2025-07-09', 3),
(3, N'Khuyến mãi mới', N'Bạn có mã giảm giá 20% cho sinh nhật', 'Promotion', 'not checked', '2025-07-09', 4),
(4, N'Đơn hàng đã xác nhận', N'Đơn hàng #2 của bạn đã được xác nhận', 'Order', 'checked', '2025-07-08', 4),
(5, N'Đơn hàng đang giao', N'Đơn hàng #3 đang trên đường giao đến bạn', 'Delivery', 'not checked', '2025-07-09', 5);

-- Insert Feedback
INSERT INTO [Feedback] (Rating, Comment, User_ID, Menu_ID) VALUES
(5, N'Sườn non rất ngon, nướng vừa tới', 3, 4),
(4, N'Cơm chiên thơm ngon, nhưng hơi mặn', 4, 2),
(5, N'Gà nướng tuyệt vời, sẽ đặt lại', 4, 9),
(3, N'Xiên thịt bình thường, không đặc biệt', 5, 6),
(5, N'Trà đá mát lạnh, giá rẻ', 3, 18);

-- Insert Purchase History
INSERT INTO [Purchase] (User_ID, Menu_ID) VALUES
(3, 1),
(3, 4),
(3, 18),
(4, 2),
(4, 9),
(4, 21),
(5, 3),
(5, 6),
(5, 15);

ALTER TABLE [User]
ADD Email VARCHAR(255);

-- Update existing users with sample email addresses
UPDATE [User] 
SET Email = 'admin@foodorder.com'
WHERE User_name = 'admin';

UPDATE [User] 
SET Email = 'manager1@foodorder.com'
WHERE User_name = 'manager1';

UPDATE [User] 
SET Email = 'customer1@email.com'
WHERE User_name = 'customer1';

UPDATE [User] 
SET Email = 'customer2@email.com'
WHERE User_name = 'customer2';

UPDATE [User] 
SET Email = 'customer3@email.com'
WHERE User_name = 'customer3';