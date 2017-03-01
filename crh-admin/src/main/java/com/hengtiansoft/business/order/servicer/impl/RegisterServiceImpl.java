package com.hengtiansoft.business.order.servicer.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.order.dto.RegisterRequestDto;
import com.hengtiansoft.business.order.servicer.RegisterService;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class RegisterServiceImpl implements RegisterService{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MMemberDao          memberDao;
    
    @Override
    @Transactional
    public MMemberEntity register(RegisterRequestDto requestDto) {
        // 参数验证
        if(requestDto == null || requestDto.getMobile() == null){
            throw new RuntimeException("参数错误");
        }
        
        // 查询手机号是否已注册
        String mobile = requestDto.getMobile();
        
        // 默认昵称
        String nickName = mobile;
        
        // 保存进数据库
        List<MMemberEntity> members = memberDao.findByLoginIdAndStatus(mobile, StatusEnum.NORMAL.getCode());
        MMemberEntity currentMember = null;
        String currentOpenID = requestDto.getOpenId();
        if(members != null && !members.isEmpty()){
            currentMember = members.get(0);
            
            // 如果存在则判断是否是更当前微信用户绑定，是则不再绑定
            if(currentOpenID != null && currentOpenID.equals(currentMember.getOpenId())){
                logger.error("手机号{}已绑定OPEN_ID:{}", mobile, requestDto.getOpenId());
                return currentMember;
            }
            
            // 清除以前绑定过该微信号的用户的绑定关系
            if(currentOpenID != null){
                memberDao.clearOpenID(currentOpenID); 
            }

            // 如果存在且不是当前用户，则绑定当前用户
            currentMember.setOpenId(requestDto.getOpenId());
            currentMember.setStatus(StatusEnum.NORMAL.getCode());
            return memberDao.save(currentMember);
        }else{
            // 清除以前绑定过该微信号的用户的绑定关系
            if(currentOpenID != null){
                memberDao.clearOpenID(currentOpenID);
            }
            
            // 手机号未注册则重新保存进系统
            currentMember = new MMemberEntity();
            currentMember.setLoginId(mobile);
            currentMember.setPassword(EncryptUtil.encryptMd5(mobile)); // 密码其实不需要，还是给个默认的吧
            currentMember.setOpenId(requestDto.getOpenId());
            currentMember.setMemberName(mobile);
            currentMember.setCustName(nickName);
            currentMember.setStatus(StatusEnum.NORMAL.getCode());
            currentMember.setCreateDate(new Date());
            currentMember.setModifyDate(new Date());
            return memberDao.save(currentMember);
        }
    
    }
}
