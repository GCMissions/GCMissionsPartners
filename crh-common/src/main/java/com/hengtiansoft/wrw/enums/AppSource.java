package com.hengtiansoft.wrw.enums;

public enum AppSource {
	android("1", "android"), ios("2", "ios");
	private String code;
	private String source;

	private AppSource(String code, String source) {
		this.code = code;
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public String getSource() {
		return source;
	}
	
	public static String getCode(String source){
		for(AppSource app : values()){
			if(app.getSource().equals(source)){
				return app.getCode();
			}
		}
		return null;
	}
	
	 public static String getValue(String key) {
        for (AppSource appSource : values()) {
            if (appSource.getCode().equals(key)) {
                return appSource.getSource();
            }
        }
        return null;
    }
}
