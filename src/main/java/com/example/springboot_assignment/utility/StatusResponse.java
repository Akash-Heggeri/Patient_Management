package com.example.springboot_assignment.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatusResponse {
    private Object responseObject;
    private String status;
    private List<String> responseMessage;
}
