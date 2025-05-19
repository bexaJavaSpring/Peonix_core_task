package bekhruz.com.peonix_core_task.specification;

import bekhruz.com.peonix_core_task.dto.UserRequest;
import bekhruz.com.peonix_core_task.entity.auth.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@AllArgsConstructor
public class UserSpecification implements Specification<User> {
    private final UserRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!Objects.isNull(request.getFirstName())) {
            predicates.add(builder.like(builder.lower(root.get("firstName")), "%" + request.getFirstName().toLowerCase() + "%"));
        }
        if (!Objects.isNull(request.getLastName())) {
            predicates.add(builder.like(builder.lower(root.get("lastName")), "%" + request.getLastName().toLowerCase() + "%"));
        }
        if (!Objects.isNull(request.getEmail())) {
            predicates.add(builder.like(builder.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
        }
        if (!Objects.isNull(request.getPhoneNumber())) {
            predicates.add(builder.like(builder.lower(root.get("phoneNumber")), "%" + request.getPhoneNumber().toLowerCase() + "%"));
        }

        predicates.add(builder.equal(root.get("deleted"), false));
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
