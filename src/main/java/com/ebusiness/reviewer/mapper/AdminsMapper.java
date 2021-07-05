package com.ebusiness.reviewer.mapper;

import com.ebusiness.reviewer.model.Admins;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminsMapper {
    /**
     * 对于多个参数来说，每个参数之前都要加上@Param注解，
     * 要不然会找不到对应的参数进而报错
     */
    @Select("select * from admins where phone = #{phone} and password = #{password}")
    Admins adminLogin(@Param("phone") String phone, @Param("password") String password);
    @Select("select * from admins where id = #{id}")
    Admins getUserInfo(Integer id);
    @Select("update admins set username = #{username}, email = #{email},address = #{address},password = #{password} where id = #{id}")
    int changeMassage(Admins admins);
}
