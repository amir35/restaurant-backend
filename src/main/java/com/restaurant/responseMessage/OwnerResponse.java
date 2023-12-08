package com.restaurant.responseMessage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class OwnerResponse<T> {

    private T owner;

    private String message;


}
