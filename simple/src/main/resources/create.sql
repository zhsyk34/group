CREATE TABLE IF NOT EXISTS gateway_version (
  id           BIGINT UNSIGNED NOT NULL    AUTO_INCREMENT,
  major        VARCHAR(30)     NOT NULL,
  minor        VARCHAR(10)     NOT NULL,
  info         VARCHAR(10)     NOT NULL,
  download_url VARCHAR(100)    NOT NULL,
  create_time  TIMESTAMP       NOT NULL,
  update_time  TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS remote_record (
  id           BIGINT UNSIGNED   NOT NULL    AUTO_INCREMENT,
  gateway_id   BIGINT UNSIGNED   NOT NULL    NOT NULL,
  device_index SMALLINT UNSIGNED NOT NULL    NOT NULL,
  gateway_sn   VARCHAR(255)      NOT NULL,
  gateway_name VARCHAR(255)      NOT NULL,
  lock_id      BIGINT UNSIGNED   NOT NULL    NOT NULL,
  lock_uuid    VARCHAR(255)      NOT NULL,
  lock_name    VARCHAR(255)      NOT NULL,
  push_type    TINYINT UNSIGNED  NOT NULL,
  create_time  TIMESTAMP         NOT NULL,
  update_time  TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS gateway (
  id                 BIGINT UNSIGNED NOT NULL    AUTO_INCREMENT,
  sn                 VARCHAR(30)     NOT NULL,
  udid               VARCHAR(40)     NOT NULL,
  app_id             VARCHAR(60)     NOT NULL,
  name               VARCHAR(30)     NOT NULL,
  major              VARCHAR(30)     NOT NULL
  COMMENT '网关主版本号',
  gateway_version_id BIGINT UNSIGNED NOT NULL,
  create_time        TIMESTAMP       NOT NULL,
  update_time        TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8
  COMMENT '网关';


CREATE TABLE IF NOT EXISTS tenant (
  id          BIGINT UNSIGNED  NOT NULL    AUTO_INCREMENT,
  idcard      VARCHAR(18)      NOT NULL,
  name        VARCHAR(20)      NOT NULL,
  phone       VARCHAR(20)      NOT NULL,
  gender      TINYINT UNSIGNED NOT NULL,
  email       VARCHAR(255),
  create_time TIMESTAMP        NOT NULL,
  update_time TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS tenant_password (
  id          BIGINT UNSIGNED NOT NULL    AUTO_INCREMENT,
  lock_id     BIGINT UNSIGNED NOT NULL,
  ordinal     INT UNSIGNED    NOT NULL
  COMMENT '租客密码编号,2-98',
  value       VARCHAR(255),
  remark      VARCHAR(255),
  create_time TIMESTAMP       NOT NULL,
  update_time TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS door_lock (
  id             BIGINT UNSIGNED   NOT NULL    AUTO_INCREMENT,
  uuid           VARCHAR(36)       NOT NULL,
  gateway_id     BIGINT UNSIGNED   NOT NULL    NOT NULL,
  device_index   SMALLINT UNSIGNED NOT NULL    NOT NULL,
  name           VARCHAR(30)       NOT NULL,
  is_available   TINYINT UNSIGNED  NOT NULL,
  super_password VARCHAR(10)       NOT NULL,
  temp_password  VARCHAR(10)       NOT NULL,
  locked         TINYINT UNSIGNED  NOT NULL,
  up_locked      TINYINT UNSIGNED  NOT NULL,
  back_locked    TINYINT UNSIGNED  NOT NULL,
  online         TINYINT UNSIGNED  NOT NULL,
  voltage        INT UNSIGNED      NOT NULL    NOT NULL,
  push_time      TIMESTAMP,
  create_time    TIMESTAMP         NOT NULL,
  update_time    TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS request (
  id            BIGINT UNSIGNED  NOT NULL    AUTO_INCREMENT,
  ack_no        VARCHAR(36)      NOT NULL
  COMMENT '回执编号,用以异步查询',
  device_no     VARCHAR(36)      NOT NULL
  COMMENT '锁序列号(lock-uuid)或(入网时)网关序列号(gateway-udid)',
  content       VARCHAR(255)     NOT NULL
  COMMENT '请求内容',
  app_id        VARCHAR(255)     NOT NULL
  COMMENT '确认请求者身份',
  callback_url  VARCHAR(255),
  accept_time   TIMESTAMP        NOT NULL,
  submit_time   TIMESTAMP,
  finish_time   TIMESTAMP,
  callback_time TIMESTAMP,
  status        TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;


CREATE TABLE IF NOT EXISTS local_record (
  id           BIGINT UNSIGNED   NOT NULL    AUTO_INCREMENT,
  gateway_id   BIGINT UNSIGNED   NOT NULL    NOT NULL,
  device_index SMALLINT UNSIGNED NOT NULL    NOT NULL,
  gateway_sn   VARCHAR(255)      NOT NULL,
  gateway_name VARCHAR(255)      NOT NULL,
  lock_id      BIGINT UNSIGNED   NOT NULL    NOT NULL,
  lock_uuid    VARCHAR(255)      NOT NULL,
  lock_name    VARCHAR(255)      NOT NULL,
  push_type    TINYINT UNSIGNED  NOT NULL,
  create_time  TIMESTAMP         NOT NULL,
  update_time  TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = UTF8;

ALTER TABLE gateway
  ADD CONSTRAINT uk_sn UNIQUE INDEX (sn),
  ADD CONSTRAINT uk_udid UNIQUE INDEX (udid);

ALTER TABLE door_lock
  ADD CONSTRAINT uk_uuid UNIQUE INDEX (uuid),
  ADD CONSTRAINT uk_gateway_id_index UNIQUE INDEX (gateway_id, device_index),
  ADD CONSTRAINT uk_gateway_id_name UNIQUE INDEX (gateway_id, name);

ALTER TABLE remote_record
  ADD INDEX idx_lock_id(lock_id),
  ADD INDEX idx_lock_name(lock_name),
  ADD INDEX idx_lock_uuid(lock_uuid);

ALTER TABLE request
  ADD CONSTRAINT uk_ack_no UNIQUE INDEX (ack_no),
  ADD INDEX idx_device_no(device_no);





