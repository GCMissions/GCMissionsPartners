package com.hengtiansoft.business.setting.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.business.setting.dto.ParaSettingDto;
import com.hengtiansoft.business.setting.service.ParaSettingService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.FtpUtil;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.dao.SAppVersionDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.SAppVersionEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.enums.AppSource;

@Service
public class ParaSettingServiceImpl implements ParaSettingService {
    private static final String ANDROID_APP_STUFF = ".apk";

    private static final Logger log               = LoggerFactory.getLogger(ParaSettingServiceImpl.class);

    private static Long         fileSize          = 0L;                                                    // 存储文件大小，因为功能是admin用，不用考虑并发

    @Value("${common.file.server}")
    private String              serverUrl;

    @Autowired
    SAppVersionDao              appVersionDao;

    @Autowired
    SBasicParaDao               paraDao;

    @Override
    @Cacheable(value = CacheType.DEFAULT, key = "'Para_Data'")
    public ParaSettingDto getParaSetting() {
        // 找到参数设置下的所有数据
        List<SBasicParaEntity> paraList = paraDao.findByTypeId(5);
        List<String> value1List = new ArrayList<String>();
        ParaSettingDto dto = new ParaSettingDto();
        for (SBasicParaEntity one : paraList) {
            value1List.add(one.getParaValue1());
        }
        dto.setC_hotline(value1List.get(0));
        dto.setC_orderTimeStart(value1List.get(1));
        dto.setC_orderTimeEnd(paraList.get(1).getParaValue2());
        dto.setC_timeSpace(value1List.get(2));
        dto.setC_orderDay(value1List.get(3));
        dto.setRemindTime(value1List.get(4));
        dto.setC_serviceQQ(value1List.get(5));
        dto.setOverTimeOrderInterval(value1List.get(6));
        dto.setZ_newOrderInterval(value1List.get(7));
        dto.setC_authServiceHint(value1List.get(8));
        dto.setOverTimeOrderTwiceInterval(value1List.get(9));
        dto.setOverTimePunishAmount(value1List.get(10));
        dto.setAppAdCountDown(value1List.get(11));
        dto.setAppAdSkipFlag(value1List.get(12));
        return dto;
    }

    @Override
    public void setAppParam(ParaSettingDto dto) {
        SAppVersionEntity sAppVersionEntity = appVersionDao.findLatestAppVersion();
        if (sAppVersionEntity != null) {
            dto.setAppType_android(sAppVersionEntity.getAppType());
            dto.setAppDownLoadUrl_android(sAppVersionEntity.getUrl());
            dto.setAppForceUpdate_android(sAppVersionEntity.getForceUpdate());
            dto.setAppVersion_android(sAppVersionEntity.getVersionNumber());
            dto.setAppUpdateDesc_android(sAppVersionEntity.getUpdateDesc());
        }
    }

    @Override
    public ResultDto<String> uploadApp(MultipartFile multipartFile, String appVersion) {
        /*
         * app 程序存放路径：
         * wrw-admin/static/upload/androidAppVersion
         */

        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {

                if (!(StringUtils.isNotBlank(multipartFile.getOriginalFilename()) && multipartFile.getOriginalFilename().toLowerCase()
                        .indexOf(ANDROID_APP_STUFF) > 0)) {
                    return ResultDtoFactory.toNack("上传文件格式不正确！");
                }

                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/"
                        + multipartFile.getOriginalFilename().replace(ANDROID_APP_STUFF, "_" + appVersion + ANDROID_APP_STUFF));

                if (tempFile.exists()) {
                    return ResultDtoFactory.toNack("该版本已经存在！");
                }

                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                multipartFile.transferTo(tempFile);

                try {

                    FtpUtil.upLoad(tempFile.getName(), new FileInputStream(tempFile), "wrw-admin/static/upload/", "androidAppVersion/");
                } finally {
                    FileUtils.deleteQuietly(tempFile);
                }
                fileSize = multipartFile.getSize();
                /*
                 * SAppVersionEntity
                 * curAppVersionEntityAndroid=appVersionDao.findByVersionNumberAndAppTypeAndUrl(appVersion,
                 * AppSource.android.getCode(),"/androidAppVersion/"+tempFile.getName());
                 * if(curAppVersionEntityAndroid!=null){
                 * curAppVersionEntityAndroid.setFileLength(String.valueOf(multipartFile.getSize()));
                 * appVersionDao.save(curAppVersionEntityAndroid);
                 * }
                 */

                return ResultDtoFactory.toAck(
                        "上传成功!",
                        serverUrl + "/androidAppVersion/"
                                + multipartFile.getOriginalFilename().replace(ANDROID_APP_STUFF, "_" + appVersion + ANDROID_APP_STUFF));
            } catch (Exception e) {
                log.error("msg", e);
            }
        }
        return ResultDtoFactory.toNack("上传失败！");
    }

    @Override
    @CacheEvict(value = CacheType.DEFAULT, key = "'Para_Data'")
    @Transactional
    public ResultDto<String> save(ParaSettingDto dto) {
        // 找到参数设置下的所有数据
        List<SBasicParaEntity> paraList = paraDao.findByTypeId(5);

        paraList.get(0).setParaValue1(dto.getC_hotline());
        paraList.get(1).setParaValue1(dto.getC_orderTimeStart());
        paraList.get(1).setParaValue2(dto.getC_orderTimeEnd());
        paraList.get(2).setParaValue1(dto.getC_timeSpace());
        paraList.get(3).setParaValue1(dto.getC_orderDay());
        paraList.get(4).setParaValue1(dto.getRemindTime());
        paraList.get(5).setParaValue1(dto.getC_serviceQQ());
        paraList.get(6).setParaValue1(dto.getOverTimeOrderInterval());
        paraList.get(7).setParaValue1(dto.getZ_newOrderInterval());
        paraList.get(8).setParaValue1(dto.getC_authServiceHint());
        paraList.get(9).setParaValue1(dto.getOverTimeOrderTwiceInterval());
        paraList.get(10).setParaValue1(dto.getOverTimePunishAmount());
        paraList.get(11).setParaValue1(dto.getAppAdCountDown());
        paraList.get(12).setParaValue1(dto.getAppAdSkipFlag());

        SAppVersionEntity curAppVersionEntityAndroid = appVersionDao.findByVersionNumberAndAppTypeAndUrl(dto.getAppVersion_android(),
                dto.getAppType_android(), dto.getAppDownLoadUrl_android());
        if (curAppVersionEntityAndroid == null) {
            if (StringUtils.isNotBlank(dto.getAppVersion_android()) && StringUtils.isNotBlank(dto.getAppDownLoadUrl_android())) {
                SAppVersionEntity appVersionEntity = new SAppVersionEntity();
                appVersionEntity.setAppType(dto.getAppType_android());
                String url = dto.getAppDownLoadUrl_android();
                appVersionEntity.setUrl(url);
                appVersionEntity.setFileName(url.substring(url.lastIndexOf("/") + 1, url.length()));
                appVersionEntity.setFileLength(String.valueOf(fileSize));
                appVersionEntity.setVersionNumber(dto.getAppVersion_android());
                appVersionEntity.setForceUpdate(dto.getAppForceUpdate_android());
                appVersionEntity.setCreateDate(new Date());
                appVersionEntity.setUpdateDesc(dto.getAppUpdateDesc_android());
                appVersionEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                appVersionEntity.setLatestVersion("1");
                appVersionDao.updateLatestVersion("0", AppSource.android.getCode());
                appVersionDao.save(appVersionEntity);
            }
        } else {
            curAppVersionEntityAndroid.setVersionNumber(dto.getAppVersion_android());
            curAppVersionEntityAndroid.setForceUpdate(dto.getAppForceUpdate_android());
            curAppVersionEntityAndroid.setUpdateDesc(dto.getAppUpdateDesc_android());
            appVersionDao.save(curAppVersionEntityAndroid);
        }

        paraDao.save(paraList);

        return ResultDtoFactory.toAck("参数设置保存成功");
    }

}
