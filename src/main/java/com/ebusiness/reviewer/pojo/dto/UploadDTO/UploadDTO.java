package com.ebusiness.reviewer.pojo.dto.UploadDTO;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author husheng
 * @date 2021/5/30 - 18:56
 */
public class UploadDTO {

    private Integer workId;

    private MultipartFile file;

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
