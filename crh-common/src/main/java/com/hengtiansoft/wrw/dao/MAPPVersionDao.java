package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengtiansoft.wrw.entity.MAppVersionEntity;

public interface MAPPVersionDao extends JpaRepository<MAppVersionEntity, Long>{
	public MAppVersionEntity findByAppTypeAndIsLatestVersion(String appType, String isLatestVersion);
}
