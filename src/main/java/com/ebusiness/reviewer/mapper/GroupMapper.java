package com.ebusiness.reviewer.mapper;

import com.ebusiness.reviewer.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 添加了@Mapper注解之后这个接口在编译时会生成相应的实现类
 *
 * 需要注意的是：这个接口中不可以定义同名的方法，因为会生成相同的id
 * 也就是说这个接口是不支持重载的
 */
@Mapper

public interface GroupMapper {

    List<Group> getGroupList();

    //查询小组id数组
    List<Group> findGroupId();

    @Select("insert INTO `group` (id,group_name,isenable,create_time) VALUES(DEFAULT,#{groupName},'可用',NOW())")
    void createGroup(Group group);
    @Select("select COUNT(*) from `group`")


    int findGroupNum();
}
