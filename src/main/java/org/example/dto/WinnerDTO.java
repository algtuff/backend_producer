package org.example.dto;

public class WinnerDTO {

    public WinnerDTO() {
    }

    public WinnerDTO(final String producer, final Integer previousWin, final Integer followingWin, final Integer interval) {
        this.producer = producer;
        this.interval = interval;
        this.followingWin = followingWin;
        this.previousWin = previousWin;
    }

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(Integer followingWin) {
        this.followingWin = followingWin;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Integer previousWin) {
        this.previousWin = previousWin;
    }
}
