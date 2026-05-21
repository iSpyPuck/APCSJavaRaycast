import java.util.ArrayList;
import java.util.Collections;

public class Map {

    static final int ROOMS = 10;
    static final int WIDTH  = 2 * ROOMS + 1;
    static final int HEIGHT = 2 * ROOMS + 1;

    int[][] grid = new int[HEIGHT][WIDTH];
    boolean[][] visited = new boolean[ROOMS][ROOMS];
    int endX = 2 * (ROOMS - 1) + 1;
    int endY = 2 * (ROOMS - 1) + 1;

    public Map() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                grid[y][x] = 1;
            }
        }

        carve(0, 0);

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (grid[endY + dy][endX + dx] == 1) {
                    grid[endY + dy][endX + dx] = 3;
                }
            }
        }
    }

    private void carve(int roomX, int roomY) {
        visited[roomY][roomX] = true;
        grid[2 * roomY + 1][2 * roomX + 1] = 0;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0,  0, 1, -1};

        ArrayList<Integer> dirs = new ArrayList<>();
        for (int i = 0; i < 4; i++) dirs.add(i);
        Collections.shuffle(dirs);

        for (int dir : dirs) {
            int nextX = roomX + dx[dir];
            int nextY = roomY + dy[dir];
            if (nextX >= 0 && nextX < ROOMS && nextY >= 0 && nextY < ROOMS && !visited[nextY][nextX]) {
                grid[2 * roomY + 1 + dy[dir]][2 * roomX + 1 + dx[dir]] = 0;
                carve(nextX, nextY);
            }
        }
    }

    public int getCell(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return 1;
        return grid[y][x];
    }

    public boolean isEnd(double playerX, double playerY) {
        return (int) playerX == endX && (int) playerY == endY;
    }
}
