package alg;

public class Solver {
    private int botsCount;
    private Bot[] bots;
    private int[] botsMax;
    private int[] botHarvestedInCurrentIteration;

    private int rowsCount;
    private int columnsCount;
    private int[][] matrix;
    private int[][] visited;

    private int possibleMaxFromAvailableBots;
    private int max = Integer.MIN_VALUE;
    private int currentValue = 0;

    private Bot[] availableBots;
    private Bot[] usedBots;

    public Solver(int botsCount, Bot[] bots, int[][] matrix, int[] botsMax, int rowsCount, int columnsCount) {
        this.botsCount = botsCount;
        this.bots = bots;
        this.matrix = matrix;
        this.botsMax = botsMax;
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;

        availableBots = new Bot[botsCount];
        usedBots = new Bot[botsCount];
        botHarvestedInCurrentIteration = new int[botsCount];

        for (int i = 0; i < botsCount; i++) {
            availableBots[i] = bots[i];
            possibleMaxFromAvailableBots += bots[i].botMax;
        }

        visited = new int[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int y = 0; y < columnsCount; y++) {
                visited[i][y] = -1;
            }
        }
    }

    public int solve() {
        for (int botId = 0; botId < botsCount; botId++) {
            go(botId);
            recurBack(botId);
        }

        return max;
    }

    private void fixUsed(int botId) {
        Bot bot = bots[botId];

        int startRowIdx = bot.startRowIdx;
        int endRowIdx = bot.endRowIdx;
        int startColIdx = bot.startColIdx;
        int endColIdx = bot.endColIdx;

        if (startRowIdx == endRowIdx) {
            int x = startRowIdx;
            if (startColIdx > endColIdx) {
                for (int y = startColIdx; y >= endColIdx; y--) {
                    if (visited[x][y] == botId) {
                        visited[x][y] = -1;
                    } else return;
                }
            } else {
                for (int y = startColIdx; y <= endColIdx; y++) {
                    if (visited[x][y] == botId) {
                        visited[x][y] = -1;
                    } else return;
                }
            }

        } else if (startRowIdx > endRowIdx) {
            int y = startColIdx;
            for (int x = startRowIdx; x >= endRowIdx; x--) {
                if (visited[x][y] == botId) {
                    visited[x][y] = -1;
                } else return;
            }
        } else {
            int y = startColIdx;
            for (int x = startRowIdx; x <= endRowIdx; x++) {
                if (visited[x][y] == botId) {
                    visited[x][y] = -1;
                } else return;
            }
        }

    }

    private void recurBack(int botId) {
        possibleMaxFromAvailableBots += botsMax[botId];
        availableBots[botId] = usedBots[botId];
        usedBots[botId] = null;

        currentValue -= botHarvestedInCurrentIteration[botId];
        botHarvestedInCurrentIteration[botId] = 0;

        fixUsed(botId);
    }

    private void prepareForHarvest(int botId) {
        possibleMaxFromAvailableBots -= botsMax[botId];
        usedBots[botId] = availableBots[botId];
        availableBots[botId] = null;
        botHarvestedInCurrentIteration[botId] = 0;
    }

    private void go(int botId) {
        prepareForHarvest(botId);
        Bot bot = bots[botId];
        botHarvestedInCurrentIteration[botId] = harvest(bot);
        currentValue += botHarvestedInCurrentIteration[botId];
        boolean end = true;
        for (int i = 0; i < botsCount; i++) {
            if (availableBots[i] == null) continue;
            end = false;
            go(i);
            recurBack(i);
            fixUsed(i);
        }

        if (end) {
            max = currentValue > max ? currentValue : max;
        }
    }

    private int harvest(Bot bot) {
        int harvested = 0;
        if (bot.startRowIdx == bot.endRowIdx) {
            int x = bot.startRowIdx;
            if (bot.startColIdx > bot.endColIdx) {
                for (int y = bot.startColIdx; y >= bot.endColIdx; y--) {
                    if (visited[x][y] == -1) {
                        harvested += matrix[x][y];
                        visited[x][y] = bot.id;
                    } else return harvested;
                }
                return harvested;
            } else {
                for (int y = bot.startColIdx; y <= bot.endColIdx; y++) {
                    if (visited[x][y] == -1) {
                        harvested += matrix[x][y];
                        visited[x][y] = bot.id;
                    } else return harvested;
                }
                return harvested;
            }

        } else if (bot.startRowIdx > bot.endRowIdx) {
            int y = bot.startColIdx;
            for (int x = bot.startRowIdx; x >= bot.endRowIdx; x--) {
                if (visited[x][y] == -1) {
                    harvested += matrix[x][y];
                    visited[x][y] = bot.id;
                } else return harvested;
            }
            return harvested;
        } else {
            int y = bot.startColIdx;
            for (int x = bot.startRowIdx; x <= bot.endRowIdx; x++) {
                if (visited[x][y] == -1) {
                    harvested += matrix[x][y];
                    visited[x][y] = bot.id;
                } else return harvested;
            }
            return harvested;
        }
    }

}
