package com.platform.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommonMapper {
	String selectExsistsTableYn(Map<Object, Object> vo);
	List<Map<String, Object>> selectTableDataList(Map<String, Object> vo);
	List<Map<String, Object>> selectQuery(Map<String, Object> vo);
}