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