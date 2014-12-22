CREATE INDEX REQ_UNIQUE_ID ON shopify_web_hook(request_unique_id);
CREATE INDEX ARTIST_USERNAME ON artist(username);
CREATE INDEX ARTIST_COLLECTION_ID ON artist(collection_id);
CREATE INDEX DEDUCTION_ARTIST_ID ON deduction(artist_id);
CREATE INDEX FRAME_VARIANT_PRODUCT_ID ON frame_variant(product_id);
CREATE INDEX ORDER_LINE_ITEM_ARTIST_ID ON order_line_item(artist_id);
CREATE INDEX TRANSACTION_ARTIST_ID ON transaction(artist_id);
CREATE INDEX SYNCHRONIZE_TYPE ON synchronize_log(synchronize_type);