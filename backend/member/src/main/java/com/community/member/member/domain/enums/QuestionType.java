package com.community.member.member.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionType {

    MOTHER_MAIDEN_NAME("어머니의 성함은 무엇입니까?"),
    FIRST_PET_NAME("처음 키운 애완동물의 이름은 무엇입니까?"),
    FAVORITE_TEACHER("가장 좋아했던 선생님의 성함은 무엇입니까?"),
    BIRTH_CITY("태어난 도시의 이름은 무엇입니까?"),
    FAVORITE_FOOD("가장 좋아하는 음식은 무엇입니까?");

    private final String value;
}
