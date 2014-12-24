ALTER TABLE art_work ADD deleted tinyint(1) NOT NULL DEFAULT '0';
ALTER TABLE frame_variant ADD deleted tinyint(1) NOT NULL DEFAULT '0';

/* SQL Queries to create and set featuedImageId field for already existing artworks */
ALTER TABLE art_work ADD featured_image_id bigint(20);

select concat('update art_work set featured_image_id = ', min(i.id), ' where id = ', a.id, ';')
from art_work a, art_work_images ai, image i 
where a.id = ai.art_work_id and ai.images_id = i.id group by a.id;

select a1.handle, i1.width, i1.height, rs.featured_image
from art_work a1,  
(select a.id as art_work_id, min(i.id) as featured_image from art_work a, art_work_images ai, image i 
where a.id = ai.art_work_id and ai.images_id = i.id group by a.id) rs,
image i1
where a1.id = rs.art_work_id and rs.featured_image = i1.id
