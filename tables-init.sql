-- ============================================
-- Create Schema
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'card')
BEGIN
    EXEC('CREATE SCHEMA card')
END
GO


-- ============================================
-- CUSTOMER
-- ============================================
IF OBJECT_ID('card.customer', 'U') IS NULL
BEGIN
CREATE TABLE card.customer (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,

    name NVARCHAR(255),
    email NVARCHAR(255),
    phone NVARCHAR(50),

    created_time DATETIMEOFFSET,
    created_by NVARCHAR(255),

    modified_time DATETIMEOFFSET,
    modified_by NVARCHAR(255)
)
END
GO


-- ============================================
-- CARD
-- ============================================
IF OBJECT_ID('card.card', 'U') IS NULL
BEGIN
CREATE TABLE card.card (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,

    card_number NVARCHAR(50),

    customer_id UNIQUEIDENTIFIER,

    card_type NVARCHAR(20),   -- DEBIT / CREDIT
    status NVARCHAR(20),      -- ACTIVE / BLOCKED / EXPIRED

    expiry_time DATETIMEOFFSET,

    created_time DATETIMEOFFSET,
    created_by NVARCHAR(255),

    modified_time DATETIMEOFFSET,
    modified_by NVARCHAR(255),

    CONSTRAINT fk_card_customer
        FOREIGN KEY (customer_id)
        REFERENCES card.customer(id)
        ON DELETE CASCADE
)
END
GO


-- ============================================
-- CARD LIMIT
-- ============================================
IF OBJECT_ID('card.card_limit', 'U') IS NULL
BEGIN
CREATE TABLE card.card_limit (
    card_id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,

    daily_limit int,
    daily_limit_currency NVARCHAR(10),

    used_today int,

    last_reset_time DATETIMEOFFSET,

    CONSTRAINT fk_card_limit_card
        FOREIGN KEY (card_id)
        REFERENCES card.card(id)
        ON DELETE CASCADE
)
END
GO