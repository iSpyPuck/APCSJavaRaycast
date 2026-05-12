public class Player {

    double x = 2.0;
    double y = 2.0;

    double dirX = 1.0;
    double dirY = 0.0;

    double planeX = 0.0;
    double planeY = 0.66; //FOV in degrees

    public void move(double speed, Map map) {
        double newX = x + dirX * speed;
        double newY = y + dirY * speed;

        if (map.getCell((int) newX, (int) y) == 0) x = newX;
        if (map.getCell((int) x, (int) newY) == 0) y = newY;
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
