package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.mapper.CompetitionMapper;
import com.ebusiness.reviewer.mapper.UsersMapper;
import com.ebusiness.reviewer.mapper.WorksMapper;
import com.ebusiness.reviewer.model.Competition;
import com.ebusiness.reviewer.model.Users;
import com.ebusiness.reviewer.model.Works;
import com.ebusiness.reviewer.pojo.vo.CompetitionResultVO;
import com.ebusiness.reviewer.utils.RespBean;
import com.ebusiness.reviewer.utils.RespPageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsersService {
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    private WorksMapper worksMapper;

    public int register(Users users) {
        System.out.println(users.getPhone());
        if (usersMapper.findPhone(users.getPhone()) >= 1) {
            return 0;
        } else {
            return usersMapper.register(users);
        }
    }

    public int checkUser(String phone, String password) {
        return usersMapper.checkUser(phone, password);
    }

    public Users userLogin(String phone, String password) {
        return usersMapper.userLogin(phone, password);
    }

    public void joinCompetition(int userId, int competitionId) {
        usersMapper.joinCompetition(userId, competitionId);
    }

    public int check(int userId, int competitionId) {
        return usersMapper.check(userId, competitionId);
    }

    public RespBean getMyJoinCompetition(int id) {
        List<Competition> myJoinCompetition = competitionMapper.getMyJoinCompetition(id);
        if (myJoinCompetition.isEmpty()) {
            return RespBean.error("暂无数据");
        }
        System.out.println(myJoinCompetition.get(0).toString());
        List<CompetitionResultVO> list = new ArrayList<>();
        myJoinCompetition.forEach(e -> {
            CompetitionResultVO competitionResultVO = new CompetitionResultVO();
            BeanUtils.copyProperties(e, competitionResultVO);
            final Works works = worksMapper.getWorkByUserIdAndComId(id, e.getId());
            if (Objects.nonNull(works) && works.getAnnexUrl() != null) {
                competitionResultVO.setHaveAnnex(true);
            }
            list.add(competitionResultVO);
        });
        return RespBean.ok(list);
    }

    public RespPageBean getUsersList(Integer page, Integer size, String keyWords) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Users> data = usersMapper.getUsersListByPage(page, size, keyWords);
        Integer total = usersMapper.getUsersTotal(keyWords);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public int changeInEnable(int id) {
        final Users userInfo = usersMapper.getUserInfo(id);
        return usersMapper.changeInEnable(id, userInfo.getIsenable());
    }

    public Users getUserMessageByPhone(String phone) {
        return usersMapper.getUserMessageByPhone(phone);
    }

    public int checkQuestion(Users users) {
        return usersMapper.checkQuestion(users);
    }

    public int updatePassword(Users users) {
        return usersMapper.updatePassword(users);
    }

    public Users getUserInfo(Integer id) {
        return usersMapper.getUserInfo(id);
    }

    public int changeMassage(Users users) {
        return usersMapper.changeMassage(users);
    }
}
