package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

    void insert(Orders orders);

    void deleteById(Integer id);

    void updateById(Orders orders);

    @Select("select * from tables where id = #{id}")
    Orders selectById(Integer id);

    List<Orders> selectAll(@Param("userName") String userName, @Param("userId") Integer userId);

}
