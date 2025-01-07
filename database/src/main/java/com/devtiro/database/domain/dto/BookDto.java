package com.devtiro.database.domain.dto;

import com.devtiro.database.domain.entities.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String isbn;

    private String title;
    private AuthorDto authorDto;
}
