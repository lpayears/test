package com.lpa.test.controller;


import com.lpa.test.entity.PostManage;
import com.lpa.test.entity.Project;
import com.lpa.test.service.PostManageService;
import com.lpa.test.service.ProjectService;
import com.lpa.test.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class PostManageController {

    @Autowired
    private PostManageService postManageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getPostManage(@RequestParam(value = "postManageId",required = false)Integer id, Map<String,Object> map){
            if(id != null){//被ModelAttribute标注的方法每次请求发送过来都会被调用
            PostManage postManage = postManageService.findOne(id);
            postManage.setProject(null);
            postManage.setUpdate(LocalDateTime.now());
            map.put("postManage",postManage);
        }
    }

    @GetMapping("/postManages")
    public String toListPage(Model model){
        List<PostManage> postManages= postManageService.findAll();
        model.addAttribute("postManages",postManages);
        return "postManage/list";
    }

    @GetMapping("/postManage/{id}/{userstatus}")
    public String toEditPage(@PathVariable("id")Integer id, Model model, @PathVariable("userstatus")Integer userstatus){//@PathVariable能将请求中的id取出
        PostManage postManage = postManageService.findOne(id);
        List<Project> projects = projectService.findAll();
        model.addAttribute("postManage",postManage);
        model.addAttribute("projects",projects);
        String str = null;
        if(userstatus==3) {
            str = "/postManage/edit";
        } else if (userstatus==1) {
            str = "/postManage/submit";
        }
        return str;
    }

    @PutMapping("/postManage" )
    public String updatePostManage(PostManage postManage){//更新结题日期
        postManageService.savePostManage(postManage);
        return "redirect:/postManages";
    }

    @GetMapping("/reject/{id}")
    public String toRejectPage(@PathVariable("id")Integer id, Model model){//@PathVariable能将请求中的id取出
        PostManage postManage = postManageService.findOne(id);
        List<Project> projects = projectService.findAll();
        model.addAttribute("postManage",postManage);
        model.addAttribute("projects",projects);
        return "/postManage/reject";
    }

    @PutMapping("/updatereject")
    public String updateReject(PostManage postManage){//更新驳回原因
        postManageService.savePostManage(postManage);
        return "redirect:/postManages";
    }

    @PutMapping("/submit")
    public String submit(PostManage postManage,@RequestParam("file") MultipartFile file) throws IOException {
        postManageService.savePostManage(postManage,file);
        return "redirect:/postManages";
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable("id")Integer id) throws IOException {
        PostManage postManage = postManageService.findOne(id);
        String filePath = postManage.getPath();
        String[] strings = filePath.split("/");
        String name = strings[strings.length-1];
        File file = new File(filePath);
        //处理显示中文文件名的问题
        String fileName=new String(name.getBytes("utf-8"),"ISO-8859-1");
        //设置请求头内容,告诉浏览器代开下载窗口
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",fileName );
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        return responseEntity;
    }
}
