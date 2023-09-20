import java.util.ArrayList;
import java.util.List;

// Command interface 
interface Command {
    void execute(Rover rover);
}

// Concrete command for the rover to move forward
class MoveCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.move();
    }
}

// Concrete command for turning left
class TurnLeftCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnLeft();
    }
}

// command class for turning right
class TurnRightCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnRight();
    }
}

// Receiver class that represents the Rover
class Rover {
     int x;
     int y;
     Direction direction; // using Enum direction
    // takes the position as x , y coordinates and direction - N/ S/ E/ W
    public Rover(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    //make a move based on the direction
    public void move() {
        int newX = x;
        int newY = y;
        switch (direction) {
            case N:
                newY++;
                break;
            case S:
                newY--;
                break;
            case E:
                newX++;
                break;
            case W:
                newX--;
                break;
        }

        // Check for obstacles
        if (!Grid.hasObstacle(newX, newY)) {
            x = newX;
            y = newY;
        }
    }
    // turn left
    public void turnLeft() {
        direction = direction.left();
    }
    // turn right
    public void turnRight() {
        direction = direction.right();
    }

    public String getStatusReport() {
        return "Rover is at (" + x + ", " + y + ") facing " + direction + ". " + (Grid.hasObstacle(x, y) ? "Obstacle detected." : "No obstacles detected.");
    }
}

// Enum represents the directions here
enum Direction {
    N, S, E, W;
// The ordinal starts at 0 for the first constant and increments by 1 for each subsequent constant. In this case, N has an ordinal of 0, S has an ordinal of 1, E has an ordinal of 2, and W has an ordinal of 3.
    public Direction left() {
        return values()[(ordinal() + 3) % 4];
    }
// calculates the ordinal of the direction to the right of the current direction. It adds 1 to the current direction's ordinal and then takes the modulo 4 to ensure the result wraps around within the range of valid ordinals (0, 1, 2, 3). This effectively advances the direction by one step to the right.
    public Direction right() {
        return values()[(ordinal() + 1) % 4];
    }
}

// Composite class representing the grid and obstacles
class Grid {
    private static List<Obstacle> obstacles = new ArrayList<>();
    //add obstacles to the list whenever detected
    public static void addObstacle(int x, int y) {
        obstacles.add(new Obstacle(x, y));
    }
    //checks for obstactes at every move
    public static boolean hasObstacle(int x, int y) {
        return obstacles.stream().anyMatch(o -> o.x == x && o.y == y);
    }
}

// Class representing individual obstacles
class Obstacle {
    int x;
    int y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

//Program first calls the main method of this class
//As input we can add obstacles in the grid
public class MarsRoverSimulation {
    public static void main(String[] args) {
        // Initialize the grid and obstacles
        Grid.addObstacle(2, 2);
        Grid.addObstacle(3, 5);

        //Initialise the rover position and direction
        Rover rover = new Rover(0, 0, Direction.E);

        //Call the direction commands as per your choice and make the rover move.
        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCommand());
        commands.add(new MoveCommand());
        commands.add(new TurnRightCommand());
        commands.add(new MoveCommand());
        commands.add(new TurnLeftCommand());
        commands.add(new MoveCommand());

        for (Command command : commands) {
            command.execute(rover);
        }

        // Get the status report
        String statusReport = rover.getStatusReport();
        //Print the final position of the rover.
        System.out.println("Final Position: (" + rover.x + ", " + rover.y + ", " + rover.direction + ")");
        System.out.println("Status Report: " + statusReport);
    }
}
