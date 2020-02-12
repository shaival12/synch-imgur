INSERT INTO 
	TBL_USERS (use_name, password, first_name, last_name, email, active, roles) 
VALUES
  	('mike11','sw212dd22','mike', 'nick', 'mike@gmail.com',1,'admin'),
  	('rocky123','de2d12121','rock', 'rock', 'rock@gmail.com',1,'user');
INSERT INTO 
	TBL_IMAGES (file_name, file_path, img_type, delete_hash_code, user_id) 
VALUES
  	('logo', '/opt/logo.png', 'images/png', 'adsds123d', 1),
  	('blog', '/opt/blog.jpeg', 'images/jpeg', '23ddsdsd', 2);  	
  	