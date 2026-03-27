-- 叶菜类
INSERT INTO products (name, price, stock, category, created_at, updated_at) VALUES
('生菜', 3.50, 100, 'LEAFY_GREEN', NOW(), NOW()),
('菠菜', 4.00, 80, 'LEAFY_GREEN', NOW(), NOW()),
('空心菜', 3.80, 90, 'LEAFY_GREEN', NOW(), NOW()),
('油麦菜', 3.20, 85, 'LEAFY_GREEN', NOW(), NOW());

-- 根茎类
INSERT INTO products (name, price, stock, category, created_at, updated_at) VALUES
('胡萝卜', 2.50, 150, 'ROOT_VEGETABLE', NOW(), NOW()),
('白萝卜', 2.00, 120, 'ROOT_VEGETABLE', NOW(), NOW()),
('土豆', 3.00, 200, 'ROOT_VEGETABLE', NOW(), NOW()),
('红薯', 3.50, 100, 'ROOT_VEGETABLE', NOW(), NOW());

-- 果菜类
INSERT INTO products (name, price, stock, category, created_at, updated_at) VALUES
('西红柿', 4.50, 80, 'FRUIT_VEGETABLE', NOW(), NOW()),
('黄瓜', 3.80, 100, 'FRUIT_VEGETABLE', NOW(), NOW()),
('茄子', 4.20, 70, 'FRUIT_VEGETABLE', NOW(), NOW()),
('青椒', 5.00, 60, 'FRUIT_VEGETABLE', NOW(), NOW());

-- 菌菇类
INSERT INTO products (name, price, stock, category, created_at, updated_at) VALUES
('香菇', 8.00, 50, 'MUSHROOM', NOW(), NOW()),
('平菇', 6.50, 60, 'MUSHROOM', NOW(), NOW()),
('金针菇', 5.80, 70, 'MUSHROOM', NOW(), NOW()),
('杏鲍菇', 7.50, 40, 'MUSHROOM', NOW(), NOW());

-- 豆类
INSERT INTO products (name, price, stock, category, created_at, updated_at) VALUES
('毛豆', 6.00, 80, 'BEAN', NOW(), NOW()),
('豌豆', 5.50, 70, 'BEAN', NOW(), NOW()),
('四季豆', 4.80, 90, 'BEAN', NOW(), NOW()),
('豇豆', 4.50, 85, 'BEAN', NOW(), NOW()); 