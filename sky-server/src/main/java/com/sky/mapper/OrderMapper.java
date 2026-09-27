package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);
    void update(Orders orders);
    Orders getByNumber(String number);
    Orders getById(Long id);
    Page<OrderVO> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
    Integer countByStatus(Integer status);
    
}