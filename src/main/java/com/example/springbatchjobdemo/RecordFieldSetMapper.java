package com.example.springbatchjobdemo;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordFieldSetMapper implements FieldSetMapper<Record> {

    public Record mapFieldSet(FieldSet fieldSet) throws BindException {
        Record record = new Record();
        record.setId(fieldSet.readString("id"));
        record.setName(fieldSet.readString("name"));

        String favouriteBookAndAuthorsStr = fieldSet.readString(2);
        final List<FavouriteBookAndAuthor> favouriteBookAndAuthors = Stream.of(favouriteBookAndAuthorsStr.split("&"))
                .map(s -> {
                    final String[] arr = s.split(":");
                    return new FavouriteBookAndAuthor(arr[0], arr[1]);
                })
                .collect(Collectors.toList());
        record.setFavouriteBookAndAuthors(favouriteBookAndAuthors);

        return record;
    }

}
