package com.example.springbatchjobdemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringConfig {
    private JobRepository getJobRepository(PlatformTransactionManager platformTransactionManager, DataSource dataSource) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(platformTransactionManager);
        factory.afterPropertiesSet();
        return (JobRepository) factory.getObject();
    }

    public JobLauncher getJobLauncher(PlatformTransactionManager platformTransactionManager, DataSource dataSource) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository(platformTransactionManager, dataSource));
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
