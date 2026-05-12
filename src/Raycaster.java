import java.awt.Color;
import java.awt.Graphics;

public class Raycaster {

    public void render(Graphics g, Player player, Map map, int screenW, int screenH) {
        for (int x = 0; x < screenW; x++) {

            double cameraX = 2.0 * x / screenW - 1.0;

            double rayDirX = player.dirX + player.planeX * cameraX;
            double rayDirY = player.dirY + player.planeY * cameraX;

            int mapX = (int) player.x;
            int mapY = (int) player.y;

            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1.0 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1.0 / rayDirY);

            double sideDistX, sideDistY;
            int stepX, stepY;

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (player.x - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - player.x) * deltaDistX;
            }

            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (player.y - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - player.y) * deltaDistY;
            }

            // step through the grid until we hit a wall
            int side = 0;
            while (map.getCell(mapX, mapY) == 0) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
            }

            // perpendicular distance to avoids fisheye
            double wallDist;
            if (side == 0) {
                wallDist = sideDistX - deltaDistX;
            } else {
                wallDist = sideDistY - deltaDistY;
            }

            if (wallDist < 0.0001) wallDist = 0.0001;

            int wallHeight = (int) (screenH / wallDist);
            int top    = screenH / 2 - wallHeight / 2;
            int bottom = screenH / 2 + wallHeight / 2;

            // make n and s walls darker
            if (side == 1) {
                g.setColor(new Color(150, 0, 0));
            } else {
                g.setColor(new Color(200, 0, 0));
            }

            g.drawLine(x, top, x, bottom);
        }
    }
}
