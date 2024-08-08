package com.community.member.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_interests")
public class MemberInterests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interests_id")
    private Long interestsId;

    //relation with industry
    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    //relation with member
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


}
