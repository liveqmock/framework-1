package com.mopon.entity.logs;

import java.util.Date;

public class Logging {
	
    private Long logId;//ID

    private Long logUid;//用户ID

    private String logName;//用户名

    private String logMobile;//手机

    private String logIp;//登陆IP

    private Date logDate;//日期

    private Integer logSiteId;//业务系统ID

    private String logSiteName;//业务系统名称

    private Integer logClientType;//类型
    
    private String logInfo;//信息
	
    //查询属性
    private String startDate;
	
	private String endDate;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogUid() {
        return logUid;
    }

    public void setLogUid(Long logUid) {
        this.logUid = logUid;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName == null ? null : logName.trim();
    }

    public String getLogMobile() {
        return logMobile;
    }

    public void setLogMobile(String logMobile) {
        this.logMobile = logMobile == null ? null : logMobile.trim();
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp == null ? null : logIp.trim();
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Integer getLogSiteId() {
        return logSiteId;
    }

    public void setLogSiteId(Integer logSiteId) {
        this.logSiteId = logSiteId;
    }

    public String getLogSiteName() {
        return logSiteName;
    }

    public void setLogSiteName(String logSiteName) {
        this.logSiteName = logSiteName == null ? null : logSiteName.trim();
    }

    public Integer getLogClientType() {
        return logClientType;
    }

    public void setLogClientType(Integer logClientType) {
        this.logClientType = logClientType;
    }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
}