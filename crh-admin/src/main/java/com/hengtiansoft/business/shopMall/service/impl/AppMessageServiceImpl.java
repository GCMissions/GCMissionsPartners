package com.hengtiansoft.business.shopMall.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.shopMall.dto.AppMessageDto;
import com.hengtiansoft.business.shopMall.dto.AppMessagePageDto;
import com.hengtiansoft.business.shopMall.service.AppMessageService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.jpush.bean.JpushBean;
import com.hengtiansoft.common.jpush.enums.JpushExtraKeyEnum;
import com.hengtiansoft.common.jpush.enums.JpushTagEnum;
import com.hengtiansoft.common.jpush.util.JpushUtil;
import com.hengtiansoft.common.util.BizUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MMessageDao;
import com.hengtiansoft.wrw.entity.MMessageEntity;
import com.hengtiansoft.wrw.enums.MMessageReadStatusEnum;
import com.hengtiansoft.wrw.enums.MMessageTypeEnum;

/**
 * Class Name: AppMessageServiceImpl
 * Description: APP推送消息Service接口实现
 * 
 * @author jiekaihu
 */

@Service
public class AppMessageServiceImpl implements AppMessageService {

    @Autowired
    private MMessageDao messageDao;

    @Override
    public void search(final AppMessagePageDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<MMessageEntity> page = messageDao.findAll(new Specification<MMessageEntity>() {

            @Override
            public Predicate toPredicate(Root<MMessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.<String> get("type"), MMessageTypeEnum.SYSTEM.getKey()));
                
                Predicate p1 = null;
                if (StringUtils.isNotBlank(dto.getTitle())) {
                    p1 = cb.like(root.<String> get("title"), BizUtil.filterString(dto.getTitle()));
                    predicate = cb.and(p1);
                }
                if (dto.getStartDate() != null && dto.getEndDate() != null) {
                    Predicate p2 = cb.lessThan(root.<Date> get("createDate"), dto.getEndDate());
                    Predicate p3 = cb.greaterThan(root.<Date> get("createDate"), dto.getStartDate());
                    predicate = cb.and(p1, cb.and(p2, p3));
                }
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildMessageDto(page.getContent()));

    }

    private List<AppMessageDto> buildMessageDto(List<MMessageEntity> entities) {
        List<AppMessageDto> dtos = new ArrayList<AppMessageDto>();
        for (MMessageEntity one : entities) {
            dtos.add(ConverterService.convert(one, AppMessageDto.class));
        }
        return dtos;
    }

    @Override
    public AppMessageDto view(Long messageId) {
        MMessageEntity entity = messageDao.findOne(messageId);
        if (entity != null) {
            return ConverterService.convert(entity, AppMessageDto.class);
        }
        return null;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(AppMessageDto dto) throws UnsupportedEncodingException {
        // 标题检查
        if (WRWUtil.isBlank(dto.getTitle())) {
            throw new WRWException("标题不能为空");
        }
        if (dto.getTitle().length() > 50) {
            throw new WRWException("标题不能超过50个字符");
        }
        MMessageEntity oldMassage = messageDao.findByTitle(dto.getTitle());
        if (oldMassage != null && !oldMassage.getMessageId().equals(dto.getMessageId())) {
            throw new WRWException("推送标题已存在");
        }
        
        // 消息内容检查
        if (WRWUtil.isBlank(dto.getContent())) {
            throw new WRWException("消息内容不能为空");
        }
        if (dto.getContent().length() > 2000) {
            throw new WRWException("消息不能超过2000个字符");
        }

        // 存储消息
        MMessageEntity entity = ConverterService.convert(dto, MMessageEntity.class);
        entity.setContent(dto.getContent());
        //entity.setContent(URLEncoder.encode(dto.getContent(), "UTF-8"));
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setReadStatus(MMessageReadStatusEnum.UNREAD.getKey());
        entity.setType(MMessageTypeEnum.SYSTEM.getKey());
        messageDao.save(entity);

        // 推送push
        JpushBean jpushBean = new JpushBean();
        jpushBean.setTitle(dto.getTitle());
        jpushBean.setAlert(stripHTML(dto.getContent()));
        jpushBean.getTagValues().add(JpushTagEnum.MEMBER.getValue());
        // 设置交互数据
        jpushBean.getExtras().put(JpushExtraKeyEnum.MESSAGE_ID.getValue(), entity.getMessageId().toString());
        jpushBean.getExtras().put(JpushExtraKeyEnum.MESSAGE_TYPE.getValue(), MMessageTypeEnum.SYSTEM.getKey());
        JpushUtil.push(jpushBean);

        return ResultDtoFactory.toAck("消息保存成功并已推送");
    }

    /**
     * Description: 去除消息内容中的html标签
     *
     * @param htmlStr
     * @return
     */
    private String stripHTML(String htmlStr) {
        // 定义script的正则表达式
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        // 定义style的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        // 定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";
        // 定义空格回车换行符
        String regEx_space = "\\s*|\t|\r|\n";

        // 过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        // 过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        // 过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");

        return htmlStr.trim();
    }
}
