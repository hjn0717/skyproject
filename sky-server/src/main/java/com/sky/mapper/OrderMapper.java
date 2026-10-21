package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);
    void update(Orders orders);
    Orders getByNumber(String number);
    Orders getById(Long id);
    Page<OrderVO> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
    Integer countByStatus(Integer status);
	
	@Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime orderTime);
}