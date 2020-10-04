package com.example.springbatchjobdemo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("restriction")
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "name", "favouriteBookAndAuthors"})
public class Record {

    private String id;

    private String name;

    @XmlElementWrapper(name="favouriteBookAndAuthors")
    @XmlElement(name = "favouriteBookAndAuthor")
    private List<FavouriteBookAndAuthor> favouriteBookAndAuthors = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FavouriteBookAndAuthor> getFavouriteBookAndAuthors() {
        return favouriteBookAndAuthors;
    }

    public void setFavouriteBookAndAuthors(List<FavouriteBookAndAuthor> favouriteBookAndAuthors) {
        this.favouriteBookAndAuthors = favouriteBookAndAuthors;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", favouriteBookAndAuthors=" + favouriteBookAndAuthors +
                '}';
    }
}
