package com.restaurant.responseMessage;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ApiResponse<T> {

    private T data;

    private String message;


}
