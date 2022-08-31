package com.example.collections.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "nameErr")
    private String name;
    private String tag;
    @ManyToOne
    private Collection collection;
    @ManyToMany
    @JoinTable(
            name = "item_tags",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private Set<Tag> tagSet;
    @ManyToMany
    @JoinTable(
            name="item_likes",
            joinColumns = @JoinColumn(name="item_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> likes = new HashSet<>();
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    public Item(String name, String tag, Collection collection) {
        this.name = name;
        this.tag = tag;
        this.collection = collection;
        this.tagSet = new HashSet<>();

    }
    @Column(nullable = true, length = 64)
    private String photos;

    public String getPhotos() {
        return photos;
    }
    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/user-photos/" + id + "/" + photos;
    }
    public void setPhotos(String photos) {
        this.photos = photos;
    }
    public Item() {

    }
    private String customString1;
    private String customString2;
    private String customString3;

    private Integer customInteger1;
    private Integer customInteger2;
    private Integer customInteger3;

    @Column(length = 10)
    @Type(type = "true_false")
    private Boolean customBoolean1;
    @Column(length = 10)
    @Type(type = "true_false")
    private Boolean customBoolean2;
    @Column(length = 10)
    @Type(type = "true_false")
    private Boolean customBoolean3;

    private String customMultilineText1;
    private String customMultilineText2;
    private String customMultilineText3;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate customDate1;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate customDate2;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate customDate3;

    public String getCustomString1() {
        return customString1;
    }

    public void setCustomString1(String customString1) {
        this.customString1 = customString1;
    }

    public String getCustomString2() {
        return customString2;
    }

    public void setCustomString2(String customString2) {
        this.customString2 = customString2;
    }

    public String getCustomString3() {
        return customString3;
    }

    public void setCustomString3(String customString3) {
        this.customString3 = customString3;
    }

    public Integer getCustomInteger1() {
        return customInteger1;
    }

    public void setCustomInteger1(Integer customInteger1) {
        this.customInteger1 = customInteger1;
    }

    public Integer getCustomInteger2() {
        return customInteger2;
    }

    public void setCustomInteger2(Integer customInteger2) {
        this.customInteger2 = customInteger2;
    }

    public Integer getCustomInteger3() {
        return customInteger3;
    }

    public void setCustomInteger3(Integer customInteger3) {
        this.customInteger3 = customInteger3;
    }

    public Boolean getCustomBoolean1() {
        return customBoolean1;
    }

    public void setCustomBoolean1(Boolean customBoolean1) {
        this.customBoolean1 = customBoolean1;
    }

    public Boolean getCustomBoolean2() {
        return customBoolean2;
    }

    public void setCustomBoolean2(Boolean customBoolean2) {
        this.customBoolean2 = customBoolean2;
    }

    public Boolean getCustomBoolean3() {
        return customBoolean3;
    }

    public void setCustomBoolean3(Boolean customBoolean3) {
        this.customBoolean3 = customBoolean3;
    }

    public String getCustomMultilineText1() {
        return customMultilineText1;
    }

    public void setCustomMultilineText1(String customMultilineText1) {
        this.customMultilineText1 = customMultilineText1;
    }

    public String getCustomMultilineText2() {
        return customMultilineText2;
    }

    public void setCustomMultilineText2(String customMultilineText2) {
        this.customMultilineText2 = customMultilineText2;
    }

    public String getCustomMultilineText3() {
        return customMultilineText3;
    }

    public void setCustomMultilineText3(String customMultilineText3) {
        this.customMultilineText3 = customMultilineText3;
    }

    public LocalDate getCustomDate1() {
        return customDate1;
    }

    public void setCustomDate1(LocalDate customDate1) {
        this.customDate1 = customDate1;
    }

    public LocalDate getCustomDate2() {
        return customDate2;
    }

    public void setCustomDate2(LocalDate customDate2) {
        this.customDate2 = customDate2;
    }

    public LocalDate getCustomDate3() {
        return customDate3;
    }

    public void setCustomDate3(LocalDate customDate3) {
        this.customDate3 = customDate3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
