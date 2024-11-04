package com.example.mini_sns.postdomain.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicSearchCond {
    private String useId;
    private Integer likes;
}
