package com.ebusiness.reviewer.controller;

import com.ebusiness.reviewer.model.Image;
import com.ebusiness.reviewer.model.Works;
import com.ebusiness.reviewer.service.CompetitionService;
import com.ebusiness.reviewer.service.WorksService;
import com.ebusiness.reviewer.utils.RespBean;
import com.ebusiness.reviewer.utils.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/works")
public class WorksController {
    @Autowired
    WorksService worksService;

    @Autowired
    CompetitionService competitionService;

    @PostMapping("/upload")
    @Transactional
    public synchronized RespBean updateImage(HttpServletRequest req, MultipartHttpServletRequest multiReq) {
//        判断该学生是否以及提交过作品
        int userId = Integer.parseInt(req.getParameter("userId"));
        int competitionId = Integer.parseInt(req.getParameter("competitionId"));
        String worksname = req.getParameter("worksname");
        String IndustryAnalysis = req.getParameter("IndustryAnalysis");

        Works works = new Works();
        works.setUserId(userId);
        works.setCompetitionId(competitionId);
        works.setWorksName(worksname);
        works.setIndustryAnalysis(IndustryAnalysis);
        int worksId = 0;
        Works check = worksService.check(userId, competitionId);
        if (Objects.isNull(check)) {
            worksService.insertWorks(works);
        }
        //否则就 直接查出当前id
        worksId = worksService.getWorksId(works);
        //works表已经有该作品了,直接存到image表中
        MultipartFile file = multiReq.getFile("file");
        if (file.isEmpty()) { //若文件选择为空,就上传失败
            return RespBean.error("图片为空！上传失败");
        }
        String filePath = null;
        try {
            filePath = ResourceUtils.getURL("classpath:").getPath() + "static/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File dir = new File(filePath);
        if (!dir.exists()) {  //若路径不存在,则创建一个这样的文件夹
            dir.mkdir();
        }
        String oldName = file.getOriginalFilename();//获取文件上传的文件名
        String newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        try {
            File file1 = new File(filePath, newName);
            //创建了一个File对象，名字是file1 ，路径是filePath，名字是fileName。
            //调用这个对象的相关方法完成文件创建，删除，读取，写入等操作
            file.transferTo(file1);    //将上传的文件写入创建好的文件中
//            将图片上传到图片表中;
            Image image = new Image();
            image.setPhoto(newName);
            image.setUserId(userId);
            image.setWorksId(worksId);
            int result = worksService.uploadImage(image);
            if (result == 1) {
                return RespBean.ok("上传成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.error("上传图片失败，未知错误");
    }

    @GetMapping("/")
    public RespPageBean getWorksListByPage(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size, String keyWords) {

        RespPageBean worksListByPage = worksService.getWorksListByPage(page, size, keyWords);
        return worksListByPage;
    }

    //    获取成绩榜单 ,这里的keyWords是学生的姓名
    @GetMapping("/scoreList/")
    public RespPageBean getScoreListByPage(String keyWords) {
        return worksService.getScoreListByPage(keyWords);
    }

    @PutMapping("/updateGroup/")
    public RespBean updateGroup(int groupId, int worksId) {
        int result = worksService.updateGroup(groupId, worksId);
        if (result == 1) {
            return RespBean.ok("修改小组成功");
        }
        return RespBean.error("修改小组失败");
    }

    @PutMapping("/changeStartCheck/")
    public RespBean changeStartCheck(int id) {
        int result = worksService.changeStartCheck(id);
        if (result == 1) {
            return RespBean.ok("提交审核成功");
        }
        return RespBean.error("提交审核失败");
    }

    @GetMapping("/getCheckWorks/")
    public RespBean getCheckWorksByPage(
            String keyWords, Integer groupId) {
        if (groupId == null) {
            return RespBean.error("您还没有小组，请联系管理员分配");
        }
        return RespBean.ok(worksService.getCheckWorksByPage(keyWords, groupId));
    }

    @GetMapping("/detail/")
    public RespBean getWorksDetail(int id) {
        Works works = worksService.getWorksDetail(id);
        if (works != null) {
            return RespBean.ok(works);
        }
        return RespBean.error("获取作品详情失败！");
    }

    @GetMapping("/scoring/")
    public RespBean scoring(Integer workId, Integer totalScore, Integer reviewerId, Integer competitionId) {
        System.out.println(workId);
        //判断裁判是否打过分数
        //根据workId评审id和比赛id进行校验
        int result1 = competitionService.checkScoring(workId, reviewerId, competitionId);
        if (result1 != 0) {
            return RespBean.error("您已经评审过该作品了");
        }

        int result = worksService.scoring(workId, totalScore, reviewerId, competitionId);
        if (result == 1) {
            return RespBean.ok("打分成功");
        }
        return RespBean.error("打分失败，该作品可能已经被删除");
    }

    @PutMapping("/end")
    public RespBean endCompetitionById(Integer competitionId) {
        int result = worksService.endCompetitionById(competitionId);
        if (result != 0) {
            return RespBean.ok("共有参赛作品【" + result + "】份,请于【作品管理】界面查看最终成绩");
        }
        return RespBean.error("没有参赛作品");
    }

    @GetMapping("/myworks/")
    public RespBean getMyWorks(Integer userId) {
        List<Works> list = worksService.getMyWorks(userId);
        if (list != null) {
            return RespBean.ok("获取到比赛成绩", list);
        }
        return RespBean.error("未查询到比赛结果");
    }
}
