package com.laotek.intelligentdraw.dataserver.common;

public class DiagResultDto {
    private long id;
    private String uniqueCode;

    public long getId() {
	return id;
    }

    public String getUniqueCode() {
	return uniqueCode;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setUniqueCode(String uniqueCode) {
	this.uniqueCode = uniqueCode;
    }
}
