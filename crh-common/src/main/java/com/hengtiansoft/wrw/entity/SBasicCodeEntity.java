package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_BASIC_CODE")
public class SBasicCodeEntity implements Serializable {

      private static final long serialVersionUID = 4789726667420962710L;

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(name = "CODE_ID")
      private Long codeId;

      @Column(name = "QR_CODE")
      private String qrCode;

      @Column(name = "PREFIX_CODE")
      private String prefixCode;

      @Column(name = "PACK_CODE")
      private String packCode;

      @Column(name = "GOOD_ID")
      private Long goodId;

      @Column(name = "URL")
      private String url;

      @Column(name = "CODE_URL")
      private String codeUrl;

      @Column(name = "STATUS")
      private String status;

      @Column(name = "CREATE_ID")
      private Long createId;

      @Column(name = "CREATE_DATE")
      private Date createDate;

      @Column(name = "QUERY_COUNT")
      private Long queryCount;

      public Long getCodeId() {
            return codeId;
      }

      public void setCodeId(Long codeId) {
            this.codeId = codeId;
      }

      public String getQrCode() {
            return qrCode;
      }

      public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
      }

      public String getPrefixCode() {
            return prefixCode;
      }

      public void setPrefixCode(String prefixCode) {
            this.prefixCode = prefixCode;
      }

      public String getPackCode() {
            return packCode;
      }

      public void setPackCode(String packCode) {
            this.packCode = packCode;
      }

      public Long getGoodId() {
            return goodId;
      }

      public void setGoodId(Long goodId) {
            this.goodId = goodId;
      }

      public String getUrl() {
            return url;
      }

      public void setUrl(String url) {
            this.url = url;
      }

      public Long getCreateId() {
            return createId;
      }

      public void setCreateId(Long createId) {
            this.createId = createId;
      }

      public Date getCreateDate() {
            return createDate;
      }

      public void setCreateDate(Date createDate) {
            this.createDate = createDate;
      }

      public String getStatus() {
            return status;
      }

      public void setStatus(String status) {
            this.status = status;
      }

      public String getCodeUrl() {
            return codeUrl;
      }

      public void setCodeUrl(String codeUrl) {
            this.codeUrl = codeUrl;
      }

      public Long getQueryCount() {
            return queryCount;
      }

      public void setQueryCount(Long queryCount) {
            this.queryCount = queryCount;
      }

}
