package com.platform.authentication.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.platform.authentication.authorization.PlatformGrantedAuthority;
import com.platform.authentication.model.ManagementLoginDTO;

@Repository
@Mapper
public interface ManageMemberMapper {
	ManagementLoginDTO selectUserMstMng(String userId);
	List<PlatformGrantedAuthority> selectUserMstMngAuthority(String userId);
}
