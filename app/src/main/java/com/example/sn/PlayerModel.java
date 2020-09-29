package com.example.sn;

/* Donald Hughes
 * Persistence - Assessment 3 * */
public class PlayerModel {

    private int id;
    private String name;
    private long score;
    private boolean isActive;
    private String date;

    public PlayerModel(int id, String name, long score, String date, boolean isActive) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.isActive = isActive;
        this.date = date;

    }

    public PlayerModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return

                name + "'s" +
                " score=" + score + " " + date;
    }
}
