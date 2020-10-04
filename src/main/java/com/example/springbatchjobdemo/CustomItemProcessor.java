package com.example.springbatchjobdemo;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Record, Record> {

    public Record process(Record item) {
        System.out.println("Processing..." + item);
        return item;
    }
}
