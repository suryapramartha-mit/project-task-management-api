package com.project.management.api.util;

import com.project.management.api.dto.ErrorDTO;
import com.project.management.api.dto.RestResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private RestResponseDTO responseStatus;
    private T data;
    private List<ErrorDTO> detailErrors;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                new RestResponseDTO(HttpStatus.OK.value(), message),
                data,
                null
        );
    }

    public static <T> ApiResponse<T> error(Integer code, String message, List<ErrorDTO> errors) {
        return new ApiResponse<>(
                new RestResponseDTO(code, message),
                null,
                errors
        );
    }
}