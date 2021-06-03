package com.epam.esm.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "certificate")
@Audited
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Embedded
    private Price price;

    @Column
    private Duration duration;

    @Column
    private LocalDateTime dateOfCreation;

    @Column
    private LocalDateTime dateOfModification;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    @OneToMany
    @JoinColumn(name = "certificate_id")
    private List<Order> orders;

    @PrePersist
    public void prePersist() {
        this.dateOfCreation = LocalDateTime.now();
        this.dateOfModification = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateOfModification = LocalDateTime.now();
    }
}
