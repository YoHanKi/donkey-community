package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_interests")
public class MemberInterests {

    @Id
    @Column(name = "interests_id")
    private String interestsId;

    //relation with industry
    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    //relation with member
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


}
