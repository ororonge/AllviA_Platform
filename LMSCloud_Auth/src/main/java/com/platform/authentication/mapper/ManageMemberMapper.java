package com.platform.authentication.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.platform.authentication.authorization.ManagementUser;

@Repository
@Mapper
public interface ManageMemberMapper {
	ManagementUser selectUserMstMng(String userId);
}
