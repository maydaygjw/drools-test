package jg78779.test.template;

/**
 * Created by Junior on 4/27/14.
 */
public class Sprinkler {

    private boolean on = false;

    public Sprinkler(Room room) {
        this.room = room;
    }

    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
