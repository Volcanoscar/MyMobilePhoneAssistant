package com.cbooy.mmpa.model;

public class UpdateVersionInfo {
	private String version;
	private String desc;
	private String update_url;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	@Override
	public String toString() {
		return "UpdateVersionInfo [version=" + version + ", desc=" + desc
				+ ", update_url=" + update_url + "]";
	}
}
