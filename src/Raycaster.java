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

            double deltaDistX = rayDirX == 0 ? 1e30 : Math.abs(1.0 / rayDirX);
            double deltaDistY = rayDirY == 0 ? 1e30 : Math.abs(1.0 / rayDirY);

            int stepX = rayDirX < 0 ? -1 : 1;
            int stepY = rayDirY < 0 ? -1 : 1;
            double sideDistX = rayDirX < 0 ? (player.x - mapX) * deltaDistX : (mapX + 1.0 - player.x) * deltaDistX;
            double sideDistY = rayDirY < 0 ? (player.y - mapY) * deltaDistY : (mapY + 1.0 - player.y) * deltaDistY;

            int side = 0;
            while (map.getCell(mapX, mapY) == 0) {
                if (sideDistX < sideDistY) { sideDistX += deltaDistX; mapX += stepX; side = 0; }
                else                        { sideDistY += deltaDistY; mapY += stepY; side = 1; }
            }

            double wallDist = Math.max(0.0001, side == 0 ? sideDistX - deltaDistX : sideDistY - deltaDistY);

            int wallHeight = (int)(screenH / wallDist);
            int top    = Math.max(0, screenH / 2 - wallHeight / 2);
            int bottom = Math.min(screenH - 1, screenH / 2 + wallHeight / 2);

            int b   = (int)(220 * Math.max(0.0, 1.0 - wallDist / 20.0));
            int dim = (int)(b * 0.6);

            boolean green = map.getCell(mapX, mapY) == 3;
            g.setColor(side == 1
                ? new Color(green ? 0 : dim, green ? dim : 0, 0)
                : new Color(green ? 0 : b,   green ? b   : 0, 0));

            g.drawLine(x, top, x, bottom);
        }
    }
}
