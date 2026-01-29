package ru.mentee.app.domain.specification;


import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mentee.app.domain.entity.CourseEntity;
import ru.mentee.app.domain.entity.CourseEntity_;
import ru.mentee.app.domain.search.SearchInfo;

@UtilityClass
public class CourseSpecification {
    public Specification<CourseEntity> buildSpecification(SearchInfo searchInfo) {
        if (searchInfo == null || searchInfo.filter() == null) {
            return Specification.where(null);
        }

        return Specification.where(hasCategory(searchInfo.filter().category()))
                .and(hasLevel(searchInfo.filter().level()));
    }

    public Specification<CourseEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.equal(root.get(CourseEntity_.category), category) : null;
    }

    public Specification<CourseEntity> hasLevel(String level) {
        return (root, query, criteriaBuilder) ->
                level != null
                        ? criteriaBuilder.equal(root.get(CourseEntity_.level), level)
                        : null;
    }
}
