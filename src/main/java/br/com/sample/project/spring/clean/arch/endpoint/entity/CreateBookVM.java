package br.com.sample.project.spring.clean.arch.endpoint.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CreateBookVM implements Serializable {
    @NotBlank(message = "Field title is mandatory")
    private String title;
    @NotBlank(message = "Field author is mandatory")
    private String author;
    @Min(1)
    @NotNull(message = "Field pages is mandatory")
    private int pages;
}
