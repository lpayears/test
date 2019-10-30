package com.lpa.test.repository;


import com.lpa.test.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer>,JpaSpecificationExecutor<Project>{


    //三标连接
    @Query(value = "SELECT u FROM Project u INNER JOIN FETCH u.currency d INNER JOIN FETCH u.country r ORDER BY u.id ")
    List<Project> findAllWithDetails();

//    @Query(value = "SELECT u FROM Project u INNER JOIN FETCH u.currency d INNER JOIN FETCH u.country r ORDER BY u.id ")
//    Page<Project> findALL(Specification<Project> var1, Pageable var2);
}
