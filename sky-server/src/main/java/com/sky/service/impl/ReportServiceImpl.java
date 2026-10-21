package com.sky.service.impl;

import com.sky.dto.DataOverViewQueryDTO;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.utils.DateUtil;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 营业额统计
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDateTime begin, LocalDateTime end) {
        List<LocalDate> dateList = DateUtil.getDateList(begin.toLocalDate(), end.toLocalDate());
        List<Double> turnoverList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED); 
            Double turnover = reportMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(org.apache.commons.lang.StringUtils.join(dateList, ","))
                .turnoverList(org.apache.commons.lang.StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计
     */
    @Override
    public UserReportVO getUserStatistics(LocalDateTime begin, LocalDateTime end) {
        List<LocalDate> dateList = DateUtil.getDateList(begin.toLocalDate(), end.toLocalDate());
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            // 总用户量：截止当天的总用户数
            Integer totalUser = reportMapper.getUserCount(map);

            map.put("begin", beginTime);
            // 新增用户量：当天新增的用户数
            Integer newUser = reportMapper.getUserCount(map);

            totalUserList.add(totalUser == null ? 0 : totalUser);
            newUserList.add(newUser == null ? 0 : newUser);
        }

        return UserReportVO.builder()
                .dateList(org.apache.commons.lang.StringUtils.join(dateList, ","))
                .newUserList(org.apache.commons.lang.StringUtils.join(newUserList, ","))
                .totalUserList(org.apache.commons.lang.StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     */
    @Override
    public OrderReportVO getOrderStatistics(LocalDateTime begin, LocalDateTime end) {
        List<LocalDate> dateList = DateUtil.getDateList(begin.toLocalDate(), end.toLocalDate());
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 查询每天的订单总数
            Integer orderCount = getOrderCount(beginTime, endTime, null);
            // 查询每天的有效订单数（已完成）
            Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        // 计算时间区间内的订单总数和有效订单数
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).orElse(0);
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).orElse(0);
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(org.apache.commons.lang.StringUtils.join(dateList, ","))
                .orderCountList(org.apache.commons.lang.StringUtils.join(orderCountList, ","))
                .validOrderCountList(org.apache.commons.lang.StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return reportMapper.getOrderCount(map);
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDateTime begin, LocalDateTime end) {
        List<GoodsSalesDTO> salesTop10 = reportMapper.getSalesTop10(begin, end);
        
        List<String> names = salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numbers = salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        return SalesTop10ReportVO.builder()
                .nameList(org.apache.commons.lang.StringUtils.join(names, ","))
                .numberList(org.apache.commons.lang.StringUtils.join(numbers, ","))
                .build();
    }

    @Override
    public void exportBusinessData(HttpServletResponse response, DataOverViewQueryDTO dto) {
        LocalDateTime beginTime = dto.getBegin();
        LocalDateTime endTime = dto.getEnd();
        if (beginTime == null || endTime == null) {
            endTime = LocalDateTime.now();
            beginTime = endTime.minusDays(29).with(LocalTime.MIN);
        }

        try {
            BusinessDataVO businessData = getBusinessData(beginTime, endTime);

            XSSFWorkbook excel = new XSSFWorkbook();
            XSSFSheet sheet = excel.createSheet("运营数据");
            
            int rowIndex = 0;

            XSSFRow row0 = sheet.createRow(rowIndex++);
            row0.createCell(0).setCellValue("时间：" + beginTime.toLocalDate() + " 至 " + endTime.toLocalDate());

            XSSFRow row1 = sheet.createRow(rowIndex++);
            row1.createCell(0).setCellValue("营业额");
            row1.createCell(1).setCellValue("有效订单");
            row1.createCell(2).setCellValue("订单完成率");
            row1.createCell(3).setCellValue("平均客单价");
            row1.createCell(4).setCellValue("新增用户");

            XSSFRow row2 = sheet.createRow(rowIndex++);
            row2.createCell(0).setCellValue(businessData.getTurnover());
            row2.createCell(1).setCellValue(businessData.getValidOrderCount());
            row2.createCell(2).setCellValue(businessData.getOrderCompletionRate() * 100 + "%");
            row2.createCell(3).setCellValue(businessData.getUnitPrice());
            row2.createCell(4).setCellValue(businessData.getNewUsers());

            rowIndex++;

            XSSFRow headerRow = sheet.createRow(rowIndex++);
            headerRow.createCell(0).setCellValue("日期");
            headerRow.createCell(1).setCellValue("营业额");
            headerRow.createCell(2).setCellValue("有效订单");
            headerRow.createCell(3).setCellValue("订单完成率");
            headerRow.createCell(4).setCellValue("平均客单价");
            headerRow.createCell(5).setCellValue("新增用户");

            List<LocalDate> dateList = DateUtil.getDateList(beginTime.toLocalDate(), endTime.toLocalDate());
            
            for (LocalDate date : dateList) {
                LocalDateTime dayBegin = LocalDateTime.of(date, LocalTime.MIN);
                LocalDateTime dayEnd = LocalDateTime.of(date, LocalTime.MAX);
                
                BusinessDataVO dayData = getBusinessData(dayBegin, dayEnd);

                XSSFRow row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(date.toString());
                row.createCell(1).setCellValue(dayData.getTurnover());
                row.createCell(2).setCellValue(dayData.getValidOrderCount());
                row.createCell(3).setCellValue(dayData.getOrderCompletionRate() * 100 + "%");
                row.createCell(4).setCellValue(dayData.getUnitPrice());
                row.createCell(5).setCellValue(dayData.getNewUsers());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = java.net.URLEncoder.encode("运营数据报表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            out.flush();
            out.close();
            excel.close();

        } catch (Exception e) {
            log.error("导出报表失败", e);
        }
    }

    private BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

        map.put("status", Orders.COMPLETED);
        Double turnover = reportMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;

        Integer validOrderCount = reportMapper.getOrderCount(map);
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        map.remove("status");
        Integer totalOrderCount = reportMapper.getOrderCount(map);

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        Double unitPrice = 0.0;
        if (validOrderCount != 0) {
            unitPrice = turnover / validOrderCount;
        }

        Integer newUsers = reportMapper.getUserCount(map);
        newUsers = newUsers == null ? 0 : newUsers;

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }
}