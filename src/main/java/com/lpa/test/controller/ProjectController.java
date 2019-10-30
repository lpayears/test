package com.lpa.test.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpa.test.entity.Country;
import com.lpa.test.entity.Currency;
import com.lpa.test.entity.Project;
import com.lpa.test.service.CountryService;
import com.lpa.test.service.CurrencyService;
import com.lpa.test.service.ProjectService;
import com.lpa.test.utils.ExcelUtil;
import com.lpa.test.utils.Message;
import com.lpa.test.utils.MyPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CurrencyService currencyService;

    @ModelAttribute//被ModelAttribute标注的方法每次请求发送过来都会被调用
    public void getProject(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map){
        if(id != null){
            Project project = projectService.findOne(id);
            project.setCountry(null);
            project.setCurrency(null);
            map.put("project",project);
        }
    }


    //@GetMapping("/projects")
    @RequestMapping(value = "/projects",method = RequestMethod.GET)
    public String toListPage(HttpServletResponse response,Model model,Integer pageNum,MyPo po){//请求所有项目
        logger.info("pageNum=="+pageNum);
        if (pageNum == null){
            pageNum = 1;
        }
       // List<Project> projects = projectService.findAll();
       // model.addAttribute("projects",projects);//thymeleaf
        Page<Project> list = projectService.selectAll(pageNum,5,po);
        logger.info(""+po.getStatue());
        logger.info("pageNum=="+pageNum);
        logger.info(list.toString());
        response.addHeader("x-frame-options","SAMEORIGIN");  //
        model.addAttribute("pageInfo", list);
        return "project/list";
    }

    @GetMapping(value = "/warn")
    public String toWarnPage(Model model,Integer pageNum){
        if (pageNum == null){
            pageNum = 1;
        }
        Page<Project> list = projectService.findWarn(pageNum,5);
        model.addAttribute("pageInfo",list);
        return "dashboard";
    }

 //   @ResponseBody
    @RequestMapping(value = "/getprojects" ,method = RequestMethod.GET)
    //@RequestMapping( "/getprojects")
    public String toExportPage(Integer pageNum, MyPo po,Model model){//请求所有项目
        logger.info("pageNum=="+pageNum);
        if (pageNum == null){
            pageNum = 1;
        }
     //   PageHelper.startPage(pn,5);
   //     PageHelper.startPage(pn,5);
        Page<Project> projects = projectService.findByConditions(pageNum,5,po);
        logger.info(projects.toString());
        logger.info("pageNum=="+pageNum);
       // logger.info(po.getStatue().toString());
     //   PageInfo<?> page = new PageInfo<>(projects,5);
        model.addAttribute("pageInfo",projects);
        return "project/export";
       // return Message.success().add("pageInfo",page);
    }

    @GetMapping("/project")//请求添加页面
    public String toAddPage(Model model){
        List<Country> countries = countryService.findAll();
        List<Currency> currencies = currencyService.findAll();
        model.addAttribute("countries",countries);
        model.addAttribute("currencies",currencies);
        return "project/add";
    }

    @PostMapping("/project")
    public String addProject(Project project){
        System.out.println(project);

        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @PutMapping("/project")//更新
    public String updateProject(Project project){
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/project/{id}")
    public String toEditPage(@PathVariable("id")Integer id,Model model){
        Project project = projectService.findOne(id);
        List<Country> countries = countryService.findAll();
        List<Currency> currencies = currencyService.findAll();
        model.addAttribute("project",project);
        model.addAttribute("countries",countries);
        model.addAttribute("currencies",currencies);
        return "/project/add";
    }



    @DeleteMapping("/project/{id}")
    public String deleteProject(@PathVariable("id")Integer id){
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @PutMapping("/reset/{id}")
    public String resetPwd(@PathVariable("id") Integer id){
        return "redirect:/projects";
    }

    @GetMapping("/404")
    public String to404(){
        return  "404";
    }

    @RequestMapping("/list")
    public String list(Model model, int pageNum,int pageSize,MyPo po){

      //  Sort sort = new Sort(Sort.Direction.DESC, "recordNo");  // 这里的"recordNo"是实体类的主键，记住一定要是实体类的属性，而不能是数据库的字段
        Page<Project> list = projectService.selectAll(pageNum,pageSize,po);
        model.addAttribute("pageInfo", list);
        return "record_list";
    }

    @RequestMapping(value = "/ExcelDownload",method = RequestMethod.GET)
 //   @ResponseBody
    public void excelDownload(HttpServletResponse response, HttpServletRequest request)throws IOException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        if(null == request || null == response){
            return;
        }
        Integer statue =Integer.parseInt(request.getParameter("statue")) ;
        logger.info(statue.toString());
        DateTimeFormatter str = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (request.getParameter("startDate")!="") {
            startDate = LocalDate.parse(request.getParameter("startDate"),str);
        }
        if (request.getParameter("endDate")!="") {
            endDate = LocalDate.parse(request.getParameter("endDate"),str);
        }
        Integer export =Integer.parseInt(request.getParameter("export")) ;
        List<List<String>> excelDate = new ArrayList<>();
        List<String> head = new ArrayList<>();

        head.add("id");
        head.add("创立时间");
        head.add("地址");
        head.add("承诺时间");
        head.add("公司名称");
        head.add("销账日期");
        head.add("金额");
        head.add("业务编号");
        head.add("手机号码");
        head.add("业务人员");
        head.add("业务状态");
        head.add("汇款国家");
        head.add("汇款币种");
        head.add("核查时间");
        head.add("后置处理");

        excelDate.add(head);
        List<Project> projects = projectService.export(statue,startDate,endDate,export);

        //二、 数据转成excel


        for (int i =0;i< projects.size();i++){
            List<String> data = new ArrayList<>();
            data.add(projects.get(i).getId().toString());
            data.add(projects.get(i).getYw_time().toString());
            if (projects.get(i).getAddress()==null)
                data.add("");
            else
            data.add(projects.get(i).getAddress());
            if (projects.get(i).getBook_time()==null)
                data.add("");
            else
            data.add(projects.get(i).getBook_time().toString());
            if (projects.get(i).getCompany()==null)
                data.add("");
            else
            data.add(projects.get(i).getCompany());
            if (projects.get(i).getFinish_time()==null)
                data.add("");
            else
            data.add(projects.get(i).getFinish_time().toString());
            if (projects.get(i).getMoney()==null)
                data.add("");
            else
                data.add(projects.get(i).getMoney().toString());
            if (projects.get(i).getNum()==null)
                data.add("");
            else
            data.add(projects.get(i).getNum());
            if (projects.get(i).getPhone()==null)
                data.add("");
            else
            data.add(projects.get(i).getPhone());
            if (projects.get(i).getStaff()==null)
                data.add("");
            else
            data.add(projects.get(i).getStaff());
            if (projects.get(i).getStatue()==0)
            data.add("未核查");
            if (projects.get(i).getStatue()==1)
                data.add("已核查");
            if (projects.get(i).getStatue()==2)
                data.add("已销账");
            data.add(projects.get(i).getCountry().getD_name());
            data.add(projects.get(i).getCurrency().getC_name());
            if (projects.get(i).getCheck_time()==null)
                data.add("");
            else
            data.add(projects.get(i).getCheck_time().toString());
            if (projects.get(i).getDispose()==null)
                data.add("");
            else
                data.add(projects.get(i).getDispose());
            excelDate.add(data);
        }
        String sheetName = "sheet1";
        String fileName = "MyExcel.xls";


        ExcelUtil.exportExcel(response, excelDate, sheetName, fileName, 15);

       // return Message.success();
    }

}
