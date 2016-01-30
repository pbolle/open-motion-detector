package org.omd.services;

import org.omd.filter.Filter;
import org.omd.filter.FilterContext;
import org.opencv.core.Mat;

import java.util.List;

/**
 * Created by pbolle on 16.01.16.
 */
public class FilterChain {
    private List<Filter> filters;

    public void execute(FilterContext context){
        for(Filter filter:filters){
            filter.execute(context);
        }
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }


}
