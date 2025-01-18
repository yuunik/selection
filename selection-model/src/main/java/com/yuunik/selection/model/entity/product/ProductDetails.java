package com.yuunik.selection.model.entity.product;

import com.yuunik.selection.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}