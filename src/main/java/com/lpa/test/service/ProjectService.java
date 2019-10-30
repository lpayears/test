package com.lpa.test.service;

import com.lpa.test.entity.Project;
import com.lpa.test.utils.MyPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    void saveProject(Project project);

    Project findOne(Integer id);

    void deleteProject(Integer id);

    Page<Project> selectAll(Integer pageNum,Integer pageSize,MyPo po);

    Page<Project> findByConditions(Integer pageNum, Integer pageSize,MyPo po);

    List<Project> export(Integer statue, LocalDate startDate, LocalDate endDate, Integer export);

    Page<Project> findWarn(Integer pageNum,Integer pageSize);
}
