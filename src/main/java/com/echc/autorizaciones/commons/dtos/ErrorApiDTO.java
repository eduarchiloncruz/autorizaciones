package com.echc.autorizaciones.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorApiDTO implements Serializable {

    private String message;
    private String url;
    private String method;
    private Integer code;
    private LocalDateTime timestamp;
}
