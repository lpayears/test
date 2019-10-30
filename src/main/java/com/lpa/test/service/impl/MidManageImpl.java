package com.lpa.test.service.impl;



import com.lpa.test.entity.MidManage;
import com.lpa.test.entity.PostManage;
import com.lpa.test.repository.MidManageRepository;
import com.lpa.test.repository.PostManageRepository;
import com.lpa.test.service.MidManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MidManageImpl implements MidManageService {

    @Autowired
    private MidManageRepository midManageRepository;
    @Autowired
    private PostManageRepository postManageRepository;

    @Override
    public List<MidManage> findAll() {
        List<MidManage> midManages = midManageRepository.findAllWithDetails();
        return midManages;
    }

    @Override
    public void saveMidManage(MidManage midManage) {
        midManage.setCreate(LocalDateTime.now());
        midManage.setUpdate(LocalDateTime.now());
        midManageRepository.save(midManage);
        if (midManage.getState().equals("1")) {
            PostManage postManage = new PostManage();
            postManage.setState("0");
            postManage.setProject(midManage.getProject());
            postManage.setCreate(LocalDateTime.now());
            postManageRepository.save(postManage);
        }
    }

    @Override
    public void savetecMidManage(MidManage midManage, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\pppa\\Desktop\\" + fileName;
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        midManage.setPath(filePath);
        midManage.setCreate(LocalDateTime.now());
        midManage.setUpdate(LocalDateTime.now());
        midManageRepository.save(midManage);
        if (midManage.getState().equals("1")) {
            PostManage postManage = new PostManage();
            postManage.setState("0");
            postManage.setProject(midManage.getProject());
            postManage.setCreate(LocalDateTime.now());
            postManageRepository.save(postManage);
        }
    }

    @Override
    public void saveMidManage(MidManage midManage, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\pppa\\Desktop\\"+fileName;
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        midManage.setMidmaterial(filePath);
        midManage.setCreate(LocalDateTime.now());
        midManage.setUpdate(LocalDateTime.now());
        midManageRepository.save(midManage);
        if(midManage.getState().equals("1")){
            PostManage postManage = new PostManage();
            postManage.setState("0");
            postManage.setProject(midManage.getProject());
            postManage.setCreate(LocalDateTime.now());
            postManageRepository.save(postManage);
        }
    }
    @Override
    public  MidManage findOne(Integer id){
        Optional<MidManage> optional = midManageRepository.findById(id);
        MidManage midManage = optional.get();
        return midManage;
    }

}
