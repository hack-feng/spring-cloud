package com.maple.system.api.bean;

import com.sun.jndi.toolkit.url.Uri;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDefinition{
    private Uri uri;
    private String routeId;
    private List<FilterDefinition> filters = new ArrayList<>();
    private List<PredicateDefinition> predicates = new ArrayList<>();
    private String description;
    private Integer orders;
    private String status;
}
