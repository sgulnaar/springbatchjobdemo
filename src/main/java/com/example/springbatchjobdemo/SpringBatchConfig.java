package com.example.springbatchjobdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.text.ParseException;

public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("file:src/main/resources/output/output.xml")
    private Resource outputXml;

    public ItemReader<Record> itemReader(Resource inputData) throws UnexpectedInputException, ParseException {
        FlatFileItemReader<Record> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"id", "name", "favouritebookandauthor"};
        tokenizer.setNames(tokens);
        reader.setResource(inputData);
        DefaultLineMapper<Record> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<Record, Record> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Record> itemWriter(Marshaller marshaller) {
        StaxEventItemWriter<Record> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Record.class);
        return marshaller;
    }

    @Bean
    protected Step step1(@Qualifier("itemProcessor") ItemProcessor<Record, Record> processor, ItemWriter<Record> writer) throws ParseException {
        return stepBuilderFactory
                .get("step1")
                .<Record, Record> chunk(10)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("firstBatchJob").start(step1).build();
    }

}
