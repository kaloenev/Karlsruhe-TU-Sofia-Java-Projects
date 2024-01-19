package com.alibou.security.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    String timeLowerBound;
    String timeUpperBound;
    double distance;
    int latitude;
    int longitude;
}
