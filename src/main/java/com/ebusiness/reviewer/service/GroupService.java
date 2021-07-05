package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.mapper.GroupMapper;
import com.ebusiness.reviewer.mapper.UsersMapper;
import com.ebusiness.reviewer.mapper.WorksMapper;
import com.ebusiness.reviewer.model.Group;
import com.ebusiness.reviewer.model.Users;
import com.ebusiness.reviewer.model.Works;
import com.ebusiness.reviewer.pojo.vo.GroupVO;
import com.ebusiness.reviewer.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    @Autowired
    private WorksMapper worksMapper;

    @Autowired
    private UsersMapper usersMapper;

    public List<Group> getGroupList() {
        return groupMapper.getGroupList();
    }

    public void createGroup(Group group) {
        groupMapper.createGroup(group);
    }

    /**
     * 获取小组内的作品列表
     *
     * @return
     */
    public RespBean getWorksByGroupId(Integer id) {
        List<Works> worksList = worksMapper.getWorksByGroupId(id);
        if (CollectionUtils.isEmpty(worksList)) {
            return RespBean.error("暂无数据");
        }
        List<GroupVO> list = new ArrayList<>();
        worksList.forEach(e -> {
            GroupVO groupVO = new GroupVO();
            groupVO.setWorksName(e.getWorksName());
            groupVO.setCreateTime(e.getCreateTime());
            groupVO.setStartCheck("开始".equals(e.getStartCheck()) ? e.getStartCheck() : "未开始");
            Users userInfo = usersMapper.getUserInfo(e.getUserId());
            groupVO.setUsername(userInfo.getUsername());
            list.add(groupVO);
        });

        return RespBean.ok(list);
    }
}
