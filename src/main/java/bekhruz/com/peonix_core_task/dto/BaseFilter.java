package bekhruz.com.peonix_core_task.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BaseFilter {
    private Integer page;
    private Integer limit;
}
