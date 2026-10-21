package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    Double sumByMap(Map map);
    Integer getOrderCount(Map map);
    Integer getUserCount(Map map);
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
    
}