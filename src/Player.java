public class Player {

    double x = 1.5;
    double y = 1.5;

    double dirX = 1.0;
    double dirY = 0.0;

    double planeX = 0.0;
    double planeY = 0.66; //FOV in degrees

    private static final double MARGIN = 0.2;

    public void move(double speed, Map map) {
        double newX = x + dirX * speed;
        double newY = y + dirY * speed;

        if (clearAt(newX, y, map)) x = newX;
        if (clearAt(x, newY, map)) y = newY;
    }

    private boolean clearAt(double px, double py, Map map) {
        return map.getCell((int)(px + MARGIN), (int)(py + MARGIN)) == 0 &&
               map.getCell((int)(px + MARGIN), (int)(py - MARGIN)) == 0 &&
               map.getCell((int)(px - MARGIN), (int)(py + MARGIN)) == 0 &&
               map.getCell((int)(px - MARGIN), (int)(py - MARGIN)) == 0;
    }

    public void rotate(double angle) {
        double oldDirX = dirX;
        dirX = dirX * Math.cos(angle) - dirY * Math.sin(angle);
        dirY = oldDirX * Math.sin(angle) + dirY * Math.cos(angle);

        double oldPlaneX = planeX;
        planeX = planeX * Math.cos(angle) - planeY * Math.sin(angle);
        planeY = oldPlaneX * Math.sin(angle) + planeY * Math.cos(angle);
    }
}
