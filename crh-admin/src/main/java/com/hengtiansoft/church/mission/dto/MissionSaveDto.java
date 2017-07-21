package com.hengtiansoft.church.mission.dto;

import java.io.Serializable;

public class MissionSaveDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String content;
    
    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
