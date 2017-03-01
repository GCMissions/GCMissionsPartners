package com.hengtiansoft.task.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.task.dto.SimpleMemberInfoDto;
import com.hengtiansoft.task.service.SynchronizationCacheService;
import com.hengtiansoft.task.service.SynchronizationMemberInfoService;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: SynchronizationMemberInfoServiceImpl Description: TODO
 * 
 * @author changchen
 *
 */
@Service
public class SynchronizationMemberInfoServiceImpl implements SynchronizationMemberInfoService {

    private static Logger logger = LoggerFactory.getLogger(SynchronizationMemberInfoService.class);
    
    private static final String DATA_SOURCE = "dataSource_member";
    
    private static final int NUM = 100;

    @Autowired
    private MMemberDao memberDao;
    @Autowired
    private SynchronizationCacheService synchronizationCacheService;
    @Autowired
    private DataSource dataSource_member;
    
    
    @Override
    public List<SimpleMemberInfoDto> findSourceData(long begin) {
        // SQL
        String sql = "SELECT ID, PHONE, OPENID FROM eventvote_apply WHERE PHONE IS NOT NULL AND OPENID IS NOT NULL AND ID > " + begin;
        
        Connection conn = null;
        try {
            conn = dataSource_member.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        // 执行SQL
        List<SimpleMemberInfoDto> list = new ArrayList<SimpleMemberInfoDto>();
        try {
            // 解析结果集
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                String phone = rs.getString("PHONE");
                String openId = rs.getString("OPENID");
                Long id = rs.getLong("ID");
                if(phone != null && openId != null){
                    list.add(new SimpleMemberInfoDto(id, phone, openId));
                }
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }

    
    @Override
    @Transactional
    public int saveMemberInfo(List<SimpleMemberInfoDto> list) {
        // 结果记录
        int success = 0;
        int exist_phone = 0;
        int exist_openid = 0;

        // 参数判空
        if (CollectionUtils.isEmpty(list)) {
            logger.error("没有需要同步的数据！！！");
            return 0;
        }

        // 遍历保存信息
        List<MMemberEntity> memberList = new ArrayList<MMemberEntity>();
        MMemberEntity member = null;
        Date currentTs = new Date();
        Long maxId = 0L;
        for (SimpleMemberInfoDto sourceInfo : list) {
            // 基本数据
            String phone = sourceInfo.getPhone();
            String openId = sourceInfo.getOpenId();
            
            // 最大ID
            Long id = sourceInfo.getId();
            if(id > maxId){
                maxId = id;
            }

            // 手机号是否已注册
            List<MMemberEntity> checkPhoneList = memberDao.findByLoginIdAndStatus(phone, StatusEnum.NORMAL.getCode());
            if (CollectionUtils.isNotEmpty(checkPhoneList)) {
                exist_phone++;
                continue;
            }
            
            // OPEN_ID已存在
            List<MMemberEntity> checkOpenIDList = memberDao.findByOpenId(openId);
            if(CollectionUtils.isNotEmpty(checkOpenIDList)){
                exist_openid++;
                continue;
            }
            
            // 完善必要信息保存数据库
            member = new MMemberEntity();
            member.setLoginId(phone);
            member.setPassword(EncryptUtil.encryptMd5(phone));
            member.setOpenId(openId);
            member.setMemberName(phone);
            member.setCustName(phone);
            member.setStatus(StatusEnum.NORMAL.getCode());
            member.setCreateDate(currentTs);
            memberList.add(member);
            
            if(memberList.size() % NUM == 0){
                List<MMemberEntity> resultList = memberDao.save(memberList);
                if(CollectionUtils.isNotEmpty(resultList)){
                    success += resultList.size();
                }
                memberList = new ArrayList<MMemberEntity>();
            }
        }
        
        // 数据持久化
        List<MMemberEntity> resultList =  memberDao.save(memberList);
        if(CollectionUtils.isNotEmpty(resultList)){
            success += resultList.size();
        }
        
        // 将最大ID放入缓存，以便下次从此开始
        synchronizationCacheService.rememberBegin(maxId);
        
        logger.error("同步用户信息结束：总共【{}】，成功【{}】，手机号已存在【{}】，OPENID已存在【{}】", list.size(), success, exist_phone, exist_openid);
        
        return success;
    }





    @Override
    public void synchronizationMember() {
        // 上次处理到第几条
        Long begin = synchronizationCacheService.getBegin();
        
        logger.error("开始同步数据：上次记录点为【{}】", begin);
        
        // 获取原始数据
        List<SimpleMemberInfoDto> list = this.findSourceData(begin);
        
        // 处理数据
        this.saveMemberInfo(list);
    }


}
