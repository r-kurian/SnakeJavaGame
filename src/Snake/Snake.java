package Snake;

import java.util.ArrayList;


public class Snake {

    // direction of the snake's movement
    private SnakeDirection direction;

    // snake's state
    private boolean isAlive;

    // list of snake's pieces.
    private ArrayList<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    /**
     * The method moves the snake one step.
     * The direction of movement is specified by the direction variable.
     */
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * The method moves the snake to an adjacent cell.
     * The coordinates of the cell are set relative to the snake's head using the variables (dx, dy).
     */
    private void move(int dx, int dy) {

        // create a new snake's head
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        // checking if the snake's head remains within the game-field
        checkBorders(head);
        if (!isAlive) return;

        // checking if the snake crosses itself
        checkBody(head);
        if (!isAlive) return;

        // checking if the snake has eaten a mouse.
        Mouse mouse = MainGame.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // ...has eaten
        {
            sections.add(0, head);                 // added a new head
            MainGame.game.eatMouse();                    // create a new mouse.
        } else // just moving
        {
            sections.add(0, head);                  // added a new head
            sections.remove(sections.size() - 1);   // removed the last element from the snake's tail
        }
    }

    /**
     * Method checks if the new head is within the game-field
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0
                || head.getX() >= MainGame.game.getWidth())
                || head.getY() < 0
                || head.getY() >= MainGame.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * method checks if the head crosses with any part of the snake's body.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}