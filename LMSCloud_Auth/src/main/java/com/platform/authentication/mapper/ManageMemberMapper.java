package com.platform.authentication.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ManageMemberMapper {
	List<Map<String, Object>> selectTableDataList(Map<String, Object> vo);
}
