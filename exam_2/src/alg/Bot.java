package alg;

public class Bot {
    public int id;
    public int startRowIdx;
    public int startColIdx;
    public int endRowIdx;
    public int endColIdx;
    public int botMax;

    public Bot(int id, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx, int botMax) {
        this.id = id;
        this.startRowIdx = startRowIdx;
        this.startColIdx = startColIdx;
        this.endRowIdx = endRowIdx;
        this.endColIdx = endColIdx;
        this.botMax = botMax;
    }
}
