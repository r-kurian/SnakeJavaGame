package Snake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class MainGame {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public MainGame(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void run() {
        //Create a "Keyboard Reader" object and start it.
        KeyboardReader keyboardObserver = new KeyboardReader();
        keyboardObserver.start();

        // ...while the snake is alive
        while (snake.isAlive()) {

            // KeyboardReader contains keystroke events
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                // if equal to 'q', exit the game.
                if (event.getKeyChar() == 'q') return;

                    // if the "arrow to the left", than move the figure to the left
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    // if the "arrow to the right", than move the figure to the right
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    // if the "arrow is up", than move the figure up
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    // if the "arrow down", than move the figure down
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   // moving the snake
            print();        // displaying the current state of the game
            sleep();        // pause between moves
        }

        System.out.println("Game Over!");
    }

    /**
     * Displaying the current state of the game
     */
    public void print() {
        // Create an array where we will display the current state of the game
        int[][] matrix = new int[height][width];

        // displaying all the pieces of the snake
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        // displaying the head of the snake (4 - if the snake is dead)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        // displaying the mouse
        matrix[mouse.getY()][mouse.getX()] = 3;

        // display it all on the screen
        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * The method is called when the mouse has been eaten
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Creates a new mouse
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public static MainGame game;

    public static void main(String[] args) {
        game = new MainGame(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }

    private int initialDelay = 520;
    private int delayStep = 20;

    /**
     * The program pauses, the length of which depends on snake's length.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
