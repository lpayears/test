package com.lpa.test.service;


import com.lpa.test.entity.MidManage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MidManageService {
    List<MidManage> findAll();

    void saveMidManage(MidManage midManage);

    void saveMidManage(MidManage midManage, MultipartFile file)throws IOException;

    void savetecMidManage(MidManage midManage, MultipartFile file)throws IOException;

    MidManage findOne(Integer id);
}
