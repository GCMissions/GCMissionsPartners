package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MVipOrderEntity;

public interface MVipDao extends JpaRepository<MVipOrderEntity, Long>,
    JpaSpecificationExecutor<MVipOrderEntity>{

    List<MVipOrderEntity> findByMemberIdAndStatusOrderByBuyDateDesc(Long memberId, String status);
    
    List<MVipOrderEntity> findByOrderId(String orderId);
    
    List<MVipOrderEntity> findByOrderMainId(String orderMainId);
    
    @Query(value = " select image_url,product_name,note,org_name,sale_count,share_count,feedback_num from ( "
            + " select p.IMAGE image_url,p.PRODUCT_NAME product_name,p.NOTE note,o.ORG_NAME org_name, "
            + " p.CREATE_DATE create_date,ifnull(ap.SALES,0) sale_count,ifnull(ap.SHARE_COUNT,0) share_count, "
            + " (select count(mof.ORDER_ID) from m_order_feedback mof where mof.PRODUCT_ID = p.PRODUCT_ID) feedback_num,"
            + " p.product_id product_id,'1' type "
            + " from p_product p left join s_org o on p.ORG_ID = o.ORG_ID "
            + " left join product_sort_param ap on p.product_id = ap.PRODUCT_ID "
            + " left join p_shief ps on p.product_id = ps.product_id where p.vip = '1' and ps.sale_flag = '1' "
            + " union all "
            + " select kp.IMG_URL image_url,kp.PNAME product_name,kp.QUICK_DESC note,'吾儿乐园' org_name, "
            + " kp.CREATE_DATE create_date,ifnull(ap.SALES,0) sale_count,ifnull(ap.SHARE_COUNT,0) share_count,"
            + " 0 feedback_num,kp.id product_id,'2' type "
            + " from kd_p_product kp left join app_product_sort_param ap on kp.id = ap.PRODUCT_ID and ap.`TYPE` = '1' "
            + " left join kd_p_sale ps on kp.id = ps.product_id where kp.vip = '1' and ps.sale_status = '2' "
            + " ) a order by a.create_date desc ", nativeQuery = true)
    List<Object[]> findVipProduct();
    
    @Query(value = " select p.IMAGE image_url,p.PRODUCT_NAME product_name,p.NOTE note,o.ORG_NAME org_name, "
            + " p.CREATE_DATE create_date,ifnull(ap.SALES,0) sale_count,ifnull(ap.SHARE_COUNT,0) share_count, "
            + " (select count(mof.ORDER_ID) from m_order_feedback mof where mof.PRODUCT_ID = p.PRODUCT_ID) feedback_num,"
            + " p.product_id product_id,'1' type "
            + " from p_product p left join s_org o on p.ORG_ID = o.ORG_ID "
            + " left join product_sort_param ap on p.product_id = ap.PRODUCT_ID "
            + " left join p_shief ps on p.product_id = ps.product_id where p.vip = '1' and ps.sale_flag = '1' "
            + " order by p.create_date desc ", nativeQuery = true)
    List<Object[]> findLyVipProduct();
}
