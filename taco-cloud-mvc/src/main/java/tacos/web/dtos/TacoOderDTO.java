package tacos.web.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacoOderDTO {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private Set<Long> tacoIds;
    private String username;

    public void addTaco(Long taco) {
        if (this.tacoIds == null)
            this.tacoIds = new HashSet<>();

        this.tacoIds.add(taco);
    }
}
