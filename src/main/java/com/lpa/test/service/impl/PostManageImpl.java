package com.lpa.test.service.impl;


import com.lpa.test.entity.PostManage;
import com.lpa.test.repository.PostManageRepository;
import com.lpa.test.service.PostManageService;
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
public class PostManageImpl implements PostManageService {

    @Autowired
    private PostManageRepository postManageRepository;


    @Override
    public List<PostManage> findAll() {
        return postManageRepository.findAllWithDetails();
    }
    @Override
    public  void savePostManage(PostManage postManage){
        postManage.setCreate(LocalDateTime.now());
        postManage.setUpdate(LocalDateTime.now());
        postManageRepository.save(postManage);
    }

    @Override
    public PostManage findOne(Integer id){
        Optional<PostManage> optional = postManageRepository.findById(id);
        PostManage postManage=optional.get();
        return postManage;
    }

    @Override
    public void savePostManage(PostManage postManage, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "/Users/apple/Desktop/"+fileName;
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        postManage.setPath(filePath);
        postManage.setCreate(LocalDateTime.now());
        postManage.setUpdate(LocalDateTime.now());
        postManageRepository.save(postManage);
    }
}
