package com.platform.authentication.authorization;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public class ManagementUser implements UserDetails {
	private static final long serialVersionUID = -1690504269499001239L;
	private String userIdx;
	private String userId;
	private String pwd;
	private String userNm;
	private String authCd;
	private String picPath;
	private String posCd;
	private String sex;
	private String birthDate;
	private String lunarFlag;
	private String marryFlag;
	private String marryDate;
	private String hp;
	private String smsFlag;
	private String smartFlag;
	private String homeTel;
	private String email;
	private String emailFlag;
	private String zipCd;
	private String addr1;
	private String addr2;
	private String stAddr1;
	private String lastDate;
	private String lastIp;
	private String userLoginNotAllow;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	private boolean isCredentialsNonExpired = true;
	private boolean isEnabled;
	
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	private List<PlatformGrantedAuthority> authorities;
	public void setAuthorities(List<PlatformGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(String userIdx) {
		this.userIdx = userIdx;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getAuthCd() {
		return authCd;
	}

	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPosCd() {
		return posCd;
	}

	public void setPosCd(String posCd) {
		this.posCd = posCd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLunarFlag() {
		return lunarFlag;
	}

	public void setLunarFlag(String lunarFlag) {
		this.lunarFlag = lunarFlag;
	}

	public String getMarryFlag() {
		return marryFlag;
	}

	public void setMarryFlag(String marryFlag) {
		this.marryFlag = marryFlag;
	}

	public String getMarryDate() {
		return marryDate;
	}

	public void setMarryDate(String marryDate) {
		this.marryDate = marryDate;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getSmartFlag() {
		return smartFlag;
	}

	public void setSmartFlag(String smartFlag) {
		this.smartFlag = smartFlag;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public String getZipCd() {
		return zipCd;
	}

	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getStAddr1() {
		return stAddr1;
	}

	public void setStAddr1(String stAddr1) {
		this.stAddr1 = stAddr1;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getUserLoginNotAllow() {
		return userLoginNotAllow;
	}

	public void setUserLoginNotAllow(String userLoginNotAllow) {
		this.userLoginNotAllow = userLoginNotAllow;
	}

	@Override
	public List<PlatformGrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return userId;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
