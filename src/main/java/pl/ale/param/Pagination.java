package pl.ale.param;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static pl.ale.constant.Constants.*;

@Data
public class Pagination {


    @Min(GITHUB_MINIMAL_REPOS_PER_PAGE)
    @Max(GITHUB_MAX_RESULTS_PER_PAGE)
    private int pageSize = GITHUB_DEFAULT_RESULTS_PER_PAGE;

    @Min(GTIHUB_DEFAULT_PAGE)
    private int pageNumber = GTIHUB_DEFAULT_PAGE;

    public static Pagination of(int pageNumber, int pageSize) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(pageNumber);
        pagination.setPageSize(pageSize);
        return pagination;
    }

}
