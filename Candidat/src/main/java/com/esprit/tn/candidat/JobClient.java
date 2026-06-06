package com.esprit.tn.candidat;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@FeignClient(name = "job", url = "http://localhost:8083")
@FeignClient(name = "job")
public interface JobClient {

    @RequestMapping("jobs/list")
    public List<Job> getAllJobs();

    @RequestMapping("jobs/{id}")
    public Job getJobById(@PathVariable int id);
}