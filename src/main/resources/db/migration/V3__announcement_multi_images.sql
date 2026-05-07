CREATE TABLE announcement_images
(
    announcement_id BIGINT   NOT NULL,
    image_url       LONGTEXT NOT NULL,
    image_order     INT      NOT NULL,
    PRIMARY KEY (announcement_id, image_order),
    FOREIGN KEY (announcement_id) REFERENCES announcements (id) ON DELETE CASCADE
);

INSERT INTO announcement_images (announcement_id, image_url, image_order)
SELECT id, image, 0
FROM announcements
WHERE image IS NOT NULL
  AND image != '';

ALTER TABLE announcements
    DROP COLUMN image;
