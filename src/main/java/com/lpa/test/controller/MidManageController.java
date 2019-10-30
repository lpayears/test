package com.lpa.test.controller;


import com.lpa.test.entity.MidManage;
import com.lpa.test.entity.Project;
import com.lpa.test.service.MidManageService;
import com.lpa.test.service.ProjectService;
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
public class MidManageController {

    @Autowired
    private MidManageService midManageService;
    @Autowired
    private ProjectService projectService;

    @ModelAttribute
    public void getMidManage(@RequestParam(value = "midManageId",required = false)Integer id, Map<String,Object>map){
        if(id!=null){
            MidManage midManage = midManageService.findOne(id);
            midManage.setProject(null);
            midManage.setUpdate(LocalDateTime.now());
            map.put("midManage",midManage);
        }
    }

    @GetMapping("/midManages")
    public String toListPage(Model model){
        List<MidManage> midManages= midManageService.findAll();
        model.addAttribute("midManages",midManages);
        return "midManage/list";
    }

    @GetMapping("/tecmidManages")
    public String toTeacherListPage(Model model){
        List<MidManage> midManages= midManageService.findAll();
        model.addAttribute("midManages",midManages);
        return "midManage/teacherlist";
    }

    @GetMapping("/midManage/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        MidManage midManage = midManageService.findOne(id);
        List<Project> projects = projectService.findAll();
        model.addAttribute("midManage",midManage);
        model.addAttribute("projects",projects);
        return "/midManage/edit";

    }
    @PutMapping("/midManage")
    public String updateMidManage(MidManage midManage,@RequestParam("file") MultipartFile file)throws IOException {
        midManageService.savetecMidManage(midManage,file);
        return "redirect:/midManages";
    }

    @GetMapping("/check/{id}")
    public String toCheckPage(@PathVariable("id") Integer id, Model model){
        MidManage midManage = midManageService.findOne(id);
        List<Project> projects = projectService.findAll();
        model.addAttribute("midManage",midManage);
        model.addAttribute("projects",projects);
        return "/midManage/check";
    }

    @PutMapping("/check")
    public String updateCheck(MidManage midManage){
        midManageService.saveMidManage(midManage);
        return "redirect:/midManages";
    }

    @GetMapping("/midsubmit/{id}")
    public String toMidSubmitPage(@PathVariable("id") Integer id, Model model){
        MidManage midManage = midManageService.findOne(id);
        List<Project> projects = projectService.findAll();
        model.addAttribute("midManage",midManage);
        model.addAttribute("projects",projects);
        return "/midManage/submit";
    }

    @PutMapping("/midsubmit")
    public String updateMidSubmit(MidManage midManage,@RequestParam("file") MultipartFile file)throws IOException {
        midManageService.saveMidManage(midManage,file);
        return "redirect:/tecmidManages";
    }


    @RequestMapping("/tecmiddownload/{id}")
    public ResponseEntity<byte[]> tecmiddownload(@PathVariable("id")Integer id)throws IOException{
        MidManage midManage = midManageService.findOne(id);
        String filePath = midManage.getMidmaterial();
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

    @RequestMapping("/middownload/{id}")
    public ResponseEntity<byte[]> middownload(@PathVariable("id")Integer id)throws IOException{
        MidManage midManage = midManageService.findOne(id);
        String filePath = midManage.getPath();
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
