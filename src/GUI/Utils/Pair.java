package GUI.Utils;

public class Pair<TFirst, TLast> {
    private TFirst first;
    private TLast last;

    public Pair(TFirst first, TLast last) {
        this.first = first;
        this.last = last;
    }

    public TFirst getFirst() {
        return first;
    }

    public TLast getLast() {
        return last;
    }

    public void setFirst(TFirst first) {
        this.first = first;
    }

    public void setLast(TLast last) {
        this.last = last;
    }
}
