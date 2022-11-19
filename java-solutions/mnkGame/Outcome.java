package mnkGame;

public class Outcome {
    final Result result;
    final Player causer;

    public Outcome(Result result, Player causer) {
        this.result = result;
        this.causer = causer;
    }

    public Result getResult() {
        return result;
    }

    public Player getCauser() {
        return causer;
    }
}
