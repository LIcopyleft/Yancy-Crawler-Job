package com.Yancy.job.Service.impl;

import com.Yancy.job.Dao.JobInfoDao;
import com.Yancy.job.Pojo.JobInfo;
import com.Yancy.job.Service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    public void save(JobInfo jobInfo) {
       /* 查询原有的数据
        判断数据库中是否有已存在的数据
                如果已存在，就执行更新
                如果不存在，就执行新增*/

        //先从数据库查询数据,根据发布日期查询和url查询
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = this.findJobInfo(param);

        if (list.size() == 0) {
            //没有查询到数据则新增或者修改数据
            this.jobInfoDao.saveAndFlush(jobInfo);
        }
    }


    public List<JobInfo> findJobInfo(JobInfo jobInfo) {

        //设置查询条件
        Example example = Example.of(jobInfo);

        //执行查询
        List list = this.jobInfoDao.findAll(example);
        return list;
    }
}
