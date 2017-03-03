package com.hengtiansoft.church.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.SFunctionInfoDao;
import com.hengtiansoft.church.dao.SRoleInfoDao;
import com.hengtiansoft.church.dao.SUserDao;
import com.hengtiansoft.church.entity.SFunctionInfoEntity;
import com.hengtiansoft.church.entity.SUserEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.authority.domain.FunctionInfo;
import com.hengtiansoft.common.authority.domain.RoleInfo;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.authority.service.AuthorityService;
import com.hengtiansoft.common.util.EncryptUtil;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private SUserDao userDao;

    @Autowired
    private SRoleInfoDao roleInfoDao;

    @Autowired
    private SFunctionInfoDao functionInfoDao;

    @Override
    public UserInfo findUserInfoByToken(String token) {
        List<SUserEntity> userInfos = userDao.findByWebToken(token);
        if (!userInfos.isEmpty()) {
            return userInfos.get(0);
        }
        return null;
    }

    @Override
    public UserInfo authcUser(String loginId, String password) {
    	String encryptPwd = EncryptUtil.encryptMd5(password, loginId);
        List<SUserEntity> userInfos = userDao.findByLoginIdAndPasswordAndStatus(loginId, encryptPwd,
                StatusEnum.NORMAL.getCode());
        if (!userInfos.isEmpty()) {
            return userInfos.get(0);
        }
        return null;
    }

    @Override
    public String getToken(UserInfo userInfo) {
        SUserEntity entity = (SUserEntity) userInfo;
        return entity.getWebToken();
    }

    @Override
    public void setLoginInfo(UserInfo userInfo, String token) {
        SUserEntity entity = (SUserEntity) userInfo;
        entity.setWebToken(token);
        userDao.save(entity);
    }

    @Override
    public void clearToken(Long userId) {
        SUserEntity entity = userDao.findOne(userId);
        entity.setWebToken("");
    }

    @Override
    public List<RoleInfo> findRoleInfosByUserId(Long userId) {
        List<RoleInfo> infos = new ArrayList<RoleInfo>();
        infos.addAll(roleInfoDao.findByUserId(userId));
        return infos;
    }

    @Override
    public List<FunctionInfo> findFunctionsByRoleIds(Iterable<Long> roleIds) {
        List<FunctionInfo> infos = new ArrayList<FunctionInfo>();
        infos.addAll(functionInfoDao.findByRoleIds(roleIds));
        return infos;
    }

    @Override
    public Map<String, FunctionInfo> getFunctions() {
        Map<String, FunctionInfo> map = new HashMap<String, FunctionInfo>();
        List<SFunctionInfoEntity> entities = functionInfoDao.findAll();
        for (SFunctionInfoEntity entity : entities) {
            map.put(entity.getUrl(), entity);
        }
        return map;
    }

    @Override
    public void clearUserInfoCache(String token) {

    }

    @Override
    public void clearAuthcCache(String token) {

    }

    @Override
    public void clearAuthzCache(String token) {

    }

    @Override
    public String getStaticPath() {
        return null;
    }

    @Override
    public String getFtpPath() {
        return null;
    }

    @Override
    public String getQqPath() {
        return null;
    }

}
