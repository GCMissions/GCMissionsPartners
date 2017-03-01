package com.hengtiansoft.common.authority.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.authority.service.AuthorityService;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.BizUtil;
import com.hengtiansoft.wrw.dao.MAddressDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.MAddressEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.AddressType;

public class ShiroUtil {
    /**
     * Description: 从cookie或者header里获得token
     *
     * @param request
     * @return
     */
    public static String getToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            token = httpServletRequest.getHeader("token");
        } else {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    public static UserInfo getUserByToken(HttpServletRequest request) {
        String token = getToken(request);
        if (BizUtil.isEmpty(token)) {
            return null;
        } else {
            AuthorityService authorityService = ApplicationContextUtil.getBean(AuthorityService.class);
            return authorityService.findUserInfoByToken(token);
        }

    }

    /**
     * 
    * Description: 获取页面上的regionId
    *
    * @param request
    * @return
     */
    public static SRegionEntity getRegionByRequest(HttpServletRequest request) {
        return getCityFromCookies(request);
    }
    /**
     * 
    * Description: 从cookies里获取城市
    *
    * @param request
    * @return
     */
    private static SRegionEntity getCityFromCookies(HttpServletRequest request){
        Integer cityId = 330100;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("regionId".equals(cookie.getName())) {
                    cityId = Integer.parseInt(cookie.getValue());
                }
            }
        }
        SRegionDao regionDao = ApplicationContextUtil.getBean(SRegionDao.class);
        return regionDao.findOne(cityId);
    }
    

    /**
     * 获取用户所在市的id
     * 
     * @return
     */
    public static SRegionEntity getMemberCurrentRegion(Long memberId) {
        // 如果用户已登录，且存在默认地址，则返回该用户所在的城市
        MAddressDao addressDao = ApplicationContextUtil.getBean(MAddressDao.class);
        MAddressEntity address = addressDao.findDefAddress(memberId, AddressType.DEFAULT.getCode());
        if (address != null) {
            SRegionDao regionDao = ApplicationContextUtil.getBean(SRegionDao.class);
            SRegionEntity regionEntity = regionDao.findOne(address.getRegionId());
            if (regionEntity != null) {
                return regionDao.findOne(regionEntity.getParentId());
            }
        }
//        throw new WLYException("默认地址不存在");
        return null;
    }

}
