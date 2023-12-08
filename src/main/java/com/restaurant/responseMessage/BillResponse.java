package com.restaurant.responseMessage;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillResponse {

    private byte[] data;

    private String fileName;

    private String message;
}
