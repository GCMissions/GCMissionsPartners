package com.hengtiansoft.common.constant;

import java.util.ArrayList;
import java.util.List;

import com.hengtiansoft.common.dto.ImageDto;
import com.hengtiansoft.common.enumeration.ImagePathEnum;

public final class ImageListConstant {
		
	public static List<ImageDto> getList(String type){
		List<ImageDto> list =new ArrayList<ImageDto>();
		
		if(type.equals(ImagePathEnum.ADMIN_PRODUCT.getKey())){
			list.add(new ImageDto("-large.",800,800));
			list.add(new ImageDto("-medium.",300,300));
			list.add(new ImageDto("-thumbnail.",170,170));
		}else if(type.equals(ImagePathEnum.ADMIN_AD.getKey())){
			list.add(new ImageDto("-large.",990,460));
			list.add(new ImageDto("-medium.",720,300));
		}else if(type.equals(ImagePathEnum.ADMIN_BRAND.getKey())){
			list.add(new ImageDto("-large.",300,300));
		}else if(type.equals(ImagePathEnum.PFLOOR_PRODUCT.getKey())){
			list.add(new ImageDto("-large.",235,615));
			list.add(new ImageDto("-medium.",230,300));
		}else if(type.equals(ImagePathEnum.PRODUCT_DETAIL.getKey())){
		    list.add(new ImageDto("-large.",990,1280));
		    list.add(new ImageDto("-medium.",750,750));
		}else if(type.equals(ImagePathEnum.APP_IMAGE.getKey())){
		    list.add(new ImageDto("-medium.",645,375));
		}else if(type.equals(ImagePathEnum.ADMIN_TEAM.getKey())){
		    list.add(new ImageDto("-medium.",465,740));
		}
		return list;
	}
}
