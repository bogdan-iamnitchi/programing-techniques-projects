package Strategy;

public enum StrategyPolicy {
    SHORTEST_TIME(0),
    SHORTEST_QUEUE(1);

    public final int value;

    StrategyPolicy(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
