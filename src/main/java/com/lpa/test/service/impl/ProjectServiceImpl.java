package com.lpa.test.service.impl;

import com.lpa.test.entity.Project;
import com.lpa.test.entity.Project_;
import com.lpa.test.repository.ProjectRepository;
import com.lpa.test.service.ProjectService;
import com.lpa.test.utils.MyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public List<Project> findAll(){
        List<Project> projects = projectRepository.findAllWithDetails();
        return projects;
    }

    @Override
    public void saveProject(Project project){
        if(project == null){
            project.setYw_time(LocalDate.now());
        }
        projectRepository.save(project);
    }

    @Override
    public Project findOne(Integer id){
        Optional<Project> optional = projectRepository.findById(id);
        Project project = optional.get();
        return project;
    }

    @Override
    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }

    /**
     * 查询全部列表,并做分页
     *  @param pageNum 开始页数
     * @param pageSize 每页显示的数据条数
     *                 加油努力绝不放弃，做完以后做个总结，加油加油
     */
    @Override
    public Page<Project> selectAll(Integer pageNum, Integer pageSize,MyPo po) {
        String num = po.getNum();
        Integer statue = po.getStatue();
        String company = po.getCompany();
        //将参数传给这个方法就可以实现物理分页了，非常简单。
      //  Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
       // Page<Project> projects = projectRepository.findAll(pageable);
        return  projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //业务编码精准查询
            if (num!=null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.get(Project_.num),"%"+num+"%")));
            }
            if (statue!=null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Project_.statue),statue)));
            }
            if (company!=null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.get(Project_.company),"%"+company+"%")));
            }
            return  criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        },pageable);
    }

    @Override
    public Page<Project> findByConditions(Integer pageNum, Integer pageSize,MyPo po){
        Integer statue = po.getStatue();
        LocalDate startDate = po.getStartDate();
        LocalDate endDate = po.getEndDate();
        Integer export = po.getExport();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        Page<Project> projects = projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //statue精准查询
            if (statue != null){
                if (statue==2)
                predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.statue),1)));
                if (statue ==1)
               predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Project_.statue),2)));
                if (statue == 3)
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Project_.statue),1)));
            }
            //时间范围
            if(startDate != null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.yw_time),startDate)));
            }
            if(endDate != null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.yw_time),endDate)));
            }
            if (export != null){
              //  DateTimeFormatter str = DateTimeFormatter.ofPattern("yyyyMMdd");
              //  String data = LocalDate.now().format(str);
               // String data1 = LocalDate.now().plusDays(15).format(str);
               // String data2 =LocalDate.now().minusDays(30).format(str);
                LocalDate data = LocalDate.now();
                LocalDate data1 =  LocalDate.now().plusDays(15);
                LocalDate data2 = LocalDate.now().minusDays(30);
                if(export == 1){
                    //predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.book_time).as(String.class),data)));
                    //predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.book_time).as(String.class),data1)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.get(Project_.book_time),data,data1)));
                }
                if(export == 2){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.book_time),data1)));
                }
                if(export == 3){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.get(Project_.book_time),data2,data)));
                }
                if(export == 4){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.book_time),data2)));
                }
            }
            return  criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        },pageable);
        return projects;
    }

    @Override
    public List<Project> export(Integer statue,LocalDate startDate,LocalDate endDate,Integer export){
        List<Project> projects =projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //statue精准查询
            if (statue != null){
                if (statue==2)
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.statue),1)));
                if (statue ==1)
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Project_.statue),2)));
                if (statue == 3)
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Project_.statue),1)));
            }
            //时间范围
            if(startDate != null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.yw_time),startDate)));
            }
            if(endDate != null){
                predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.yw_time),endDate)));
            }
            if (export != null){
                //  DateTimeFormatter str = DateTimeFormatter.ofPattern("yyyyMMdd");
                //  String data = LocalDate.now().format(str);
                // String data1 = LocalDate.now().plusDays(15).format(str);
                // String data2 =LocalDate.now().minusDays(30).format(str);
                LocalDate data = LocalDate.now();
                LocalDate data1 =  LocalDate.now().plusDays(15);
                LocalDate data2 = LocalDate.now().minusDays(30);
                if(export == 1){
                    //predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.book_time).as(String.class),data)));
                    //predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.book_time).as(String.class),data1)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.get(Project_.book_time),data,data1)));
                }
                if(export == 2){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Project_.book_time),data1)));
                }
                if(export == 3){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.get(Project_.book_time),data2,data)));
                }
                if(export == 4){
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.book_time),data2)));
                }
            }
            return  criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        } );
        return projects;
    }

    @Override
    public Page<Project> findWarn(Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            LocalDate data = LocalDate.now();
            LocalDate data1 =  LocalDate.now().plusDays(15);
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(Project_.statue),1)));
            predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.get(Project_.book_time),data,data1)));
            return  criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        },pageable);
    }

}
