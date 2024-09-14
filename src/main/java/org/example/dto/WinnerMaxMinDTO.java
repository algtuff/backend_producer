package org.example.dto;

public class WinnerMaxMinDTO {

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
