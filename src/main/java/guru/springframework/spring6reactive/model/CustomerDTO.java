package guru.springframework.spring6reactive.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CustomerDTO {

    private Integer id;

    @NotBlank
    @Size(min = 5, max = 255)
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
