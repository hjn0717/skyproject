package com.sky.service;

import com.sky.dto.DataOverViewQueryDTO;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public interface ReportService {
	
    TurnoverReportVO getTurnoverStatistics(LocalDateTime begin, LocalDateTime end);
    UserReportVO getUserStatistics(LocalDateTime begin, LocalDateTime end);
    OrderReportVO getOrderStatistics(LocalDateTime begin, LocalDateTime end);
    SalesTop10ReportVO getSalesTop10(LocalDateTime begin, LocalDateTime end);
    void exportBusinessData(HttpServletResponse response, DataOverViewQueryDTO dataOverViewQueryDTO);
    
}