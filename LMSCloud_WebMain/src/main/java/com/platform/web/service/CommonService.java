package com.platform.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.web.mapper.CommonMapper;

@Service
@Transactional
public class CommonService {

	@Autowired
    private CommonMapper commonMapper;
	
	public String getExsistsTableYn(Map<Object, Object> vo) {
		return commonMapper.selectExsistsTableYn(vo);
	}
	public List<Map<String, Object>> getTableDataList(Map<String, Object> vo) {
		return commonMapper.selectTableDataList(vo);
	}
	public List<Map<String, Object>> getQuery(Map<String, Object> vo) {
		return commonMapper.selectQuery(vo);
	}
}
