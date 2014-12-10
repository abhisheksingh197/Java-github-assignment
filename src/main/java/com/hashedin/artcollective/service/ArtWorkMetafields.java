package com.hashedin.artcollective.service;

import java.util.List;

public class ArtWorkMetafields {
	private List<MetaField> metafields;
	private MetaField metafield;

	public List<MetaField> getMetafields() {
		return metafields;
	}

	public void setMetafields(List<MetaField> metafields) {
		this.metafields = metafields;
	}

	public MetaField getMetafield() {
		return metafield;
	}

	public void setMetafield(MetaField metafield) {
		this.metafield = metafield;
	}
	
}
