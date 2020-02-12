# synch-imgur
This is spring-boot application to access Imgur API to get, upload and delete images.

1.ImageController
   
2.UserController


# H2 DB schema :

```shell
DROP TABLE IF EXISTS TBL_USERS;
 
CREATE TABLE TBL_USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(150) NOT NULL,
  password  VARCHAR(150) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL,
  active INT default 1,
  roles VARCHAR(250) DEFAULT 'user'
);

DROP TABLE IF EXISTS TBL_IMAGES;
 
CREATE TABLE TBL_IMAGES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  file_name VARCHAR(250),
  file_path VARCHAR(250),
  img_type VARCHAR(250),
  delete_hash_code VARCHAR(100),
  user_id INT
);
```


