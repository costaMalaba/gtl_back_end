package gtl.proccessor.email_proccessor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Valid
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailReqDto(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "email is required") String email,
        @NotBlank(message = "message is required") String message
) implements Serializable {
}
