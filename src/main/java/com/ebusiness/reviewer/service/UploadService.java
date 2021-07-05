package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.mapper.WorksMapper;
import com.ebusiness.reviewer.model.Works;
import com.ebusiness.reviewer.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author husheng
 * @date 2021/5/30 - 18:54
 */
@Service
public class UploadService {

    @Autowired
    private WorksMapper worksMapper;

    public RespBean uploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws FileNotFoundException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int competitionId = Integer.parseInt(req.getParameter("competitionId"));

        Works works = new Works();
        works.setUserId(userId);
        works.setCompetitionId(competitionId);
        Works workByUserIdAndComId = worksMapper.getWorkByUserIdAndComId(userId, competitionId);
        if (Objects.isNull(workByUserIdAndComId)) {
            worksMapper.insertWorks(works);
        }
        Works worksById = worksMapper.getWorksById(works);
        if (Objects.isNull(worksById)) {
            return RespBean.error("作品信息已经不存在");
        }
        MultipartFile file = multiReq.getFile("file");
        if (file.isEmpty()) {
            return RespBean.error("文件为空！上传失败");
        }
        String filePath = ResourceUtils.getURL("classpath:").getPath()+"static/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String oldName = file.getOriginalFilename();//获取文件上传的文件名
        String newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        try {
            File file1 = new File(filePath, newName);
            //创建了一个File对象，名字是file1 ，路径是filePath，名字是fileName。
            //调用这个对象的相关方法完成文件创建，删除，读取，写入等操作
            file.transferTo(file1);
            works.setId(worksById.getId());
            works.setAnnexUrl(newName);
            worksMapper.uploadAnnex(works);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.ok("上传文件成功");
    }
}
