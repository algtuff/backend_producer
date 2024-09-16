package org.example.dto;

public class WinnerMaxMinDTO {

    public WinnerMaxMinDTO() {
    }

    public WinnerMaxMinDTO(WinnerDTO[] min, WinnerDTO[] max) {
        this.min = min;
        this.max = max;
    }

    private WinnerDTO[] min;
    private WinnerDTO[] max;

    public WinnerDTO[] getMax() {
        return max;
    }

    public void setMax(WinnerDTO[] max) {
        this.max = max;
    }

    public WinnerDTO[] getMin() {
        return min;
    }

    public void setMin(WinnerDTO[] min) {
        this.min = min;
    }
}
