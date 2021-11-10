# Project Assignment
## Environment & Tools
*Rasmus Djupedal*
* Operating system Linux Mint 20.2 Cinnamon, 64-bit
* IDE IntelliJ IDEA Edu 2021.2.1
* Java OpenJDK 16
* GIT version 2.25.1
* Maven Apache 3.6.3

*Tobias Liljeblad*
* Operating system  Pop!_OS 21.04
* IDE   Microsoft VSC 1.62.0
* Java OpenJDK  16
* GIT version   2.30.2
* Maven Apache  3.6.3

## Sources
* Bitmap(wall) https://opengameart.org/content/wall-0
* Sound effects https://freesound.org

## Purpose
The purpose of this program is to build a clone of the old classic Pacman game while relying only on the standard Java libraries. The final game should look quite similar to the original but the aim is not a 100% resemblance in terms of score, difficulty, ghost behaviour etc.
We decided to write this program in a group. One of the strongest benefits to doing so is the possibility to discuss code. Another one is to get some more real experience of collaboration with git.

In accordance with the stated requirements for the grade A we have implemented Multiple Design patterns, Multithreading, Synchronization, Multiple unique Swing Components and Multiple Swing layouts.



## Procedures
We started by setting up a shared google document and a Trello page containing notes while having a close communication on Discord. We discussed approaches and the general structure of the program and how we were going to collaborate.
We also decided that we should not use pull requests when merging, but always talk beforehand and inspect each other's branch before merging. This was done to both inspect the integrity of the code but also to gain knowledge about the work the other one was doing.
It was clear from the beginning that we needed to extend a JFrame in which the game will be displayed and for a long time the only container in this **PacmanFrame** was an extended JPanel, our **MazePanel**.
The next step was to create a simple Pacman, which was originally just a FilledOval reacting to key input and moving around. We later decided to use drawImage() instead, to not have to make an animation ourselves. The classes **Pacman** and **Ghost** do have many things in common, and therefore we decided to write a superclass **LivingCharacter** which both of these subclasses inherit. The **LivingCharacter** holds many datafields
and methods that both its subclasses use. The superclass also contains a template method **doMove** in which it dictates that a move consists of the following two steps, first deciding the new direction of the character and then makes the actual
move if possible.  This method is final, so it can't be overridden by the subclasses.


For the ghosts, the movement is a bit special as each ghost has a specific movement behaviour depending on both the current state they are in and in which manner they chase Pacman. We implemented these behaviours as a strategy pattern and assigned them to the ghosts. We instantiate the ghosts and pacman via an abstract factory.
There was some problem in getting the ghost to move to different patterns, but in the end we opted for just checking the next possible direction which had the shortest hypotenuse to pacman. The ghosts also observes the **StateSetter** and updates its state based upon the changes to it.

From this point on we continued implementing the **Maze** class. We created the actual maze in a text file, where
different characters symbolize different **MazeBrick** objects, such as "W" for wall, "S" for Space, "C" for candy etc.
The file was then read into a byte-array, not caring about new lines. We could have also read the file line by line, but we chose this approach instead in order for future scalability in terms of game size. From here we would iterate over this array and for each character create an instance of the **MazeBrick**, with properties set in accordance to the object we were creating. Every **MazeBrick** also have an X- and Y- coordinate which would be calculated and passed together with the other arguments to the object.
This was the early implementation, later on we decided to change the implementation and make use of an Object Pool, as it felt like a good way to reuse roughly 1000 objects instead of discarding them and creating new ones at every game over or finish.

Anytime Pacman or a Ghost wants to move to a new position it must check that it is not a wall. It does so by first obtaining the **MazeBrick** object at the position that it is evaluating for the moment by calling **Maze.INSTANCE.getBrick()** and supplying coordinates as arguments.
The returned **MazeBrick** provides a method **isWall()** which returns a boolean value depending on if it is a wall or not. We ran into a small problem here with the passage to the Ghost cage, which needs to be open when ghosts are inside it, and closed when all the ghosts have left it. To accomplish the open / close door we first used a stream to collect the door bricks in an ArrayList, and iterated over that array changing the type of the MazeBricks.

In order to avoid flickering and wasting resources we have been trying to repaint as little, and as small areas as possible. In order to do so we implemented **livingCharacter::getRectangle()**, which returns a rectangle that is a few pixels wider and higher than the actual character. This rectangle is than used by the **MazePanel** class to repaint that area. Such a rectangle is returned from Pacman and each ghosts at every moment.

Another important cass is the **GameEngine** which is responsible for the actual game and status of the game. This class is a singleton, as obviously we only need one instance of the game. This gives us the possibility to access its instance from many different places, such as from the Pacman, or from the PacmanFrame.

The EDT timer in the PacmanFrame is what makes the whole game run. When that timer stops all the characters stop moving. This timer is stopped after a maze is finished or Pacman is killed, and then directly started again as soon as a key is pressed.

**PacmanFrame** is calling **GameEngine.INSTANCE.updateGame()** at every timer tick.
GameEngine lets Pacman and Ghost do their moves. It also checks if they collide, if there’s still food left etc.
GameEngine provides some boolean data fields that can be set and read by other classes in order to communicate. For example PacmanFrame reads the boolean field **isRunning** in GameEngine to ensure that the game is still running, and if not, the timer is stopped.

We also implemented an executor service in the **GameEngine** which handles the **doMove()** for ghosts and PacMan.

We have tried to keep the EDT out of unnecessary heavy tasks in order to not have visible delays in the program. The work of constantly checking the Maze and its ArrayList are done by Pacman and the ghosts, which all run in their own threads.

One requirement we could not implement in the game itself is the Producer/consumer problem, so we created the class **PostMaster** where Ghosts send random messages to pacman which he can retrieve, and in turn Pacman sometimes responds.




## Discussion

The Trello kanban board has been very helpful in getting us organized on what to do and we are in the process. When we have decided to implement a feature, we have used Trello to add that feature and then one of us has taken it and assigned it to oneself. It helped us get organized and keep control over the workflow and delegation of work.


Throughout the whole assignment we have had scalability in focus. The Maze-implementation reads the maze from a file which makes it easy to modify / add more mazes. The format of the maze file is easy to understand and the file can easily be modified in a text editor. A future feature however could be adding a Maze-editor. Also the ghosts movements can have scalability with just adding another movement behaviour or changing a behaviour without breaking the class.

Using a superclass for Ghost and Pacman was a decision to make the controls of the two classes similar. They share similarities in what way they can move and share the same datafields that define some of the classes.

We made use of the strategy pattern by writing an interface for behaviour, which all the ghosts implement. The ghost class is instantiated 4 times, for each ghost and each ghost has a different behaviour pattern. To write all behaviours in one class would make the ghost class very cluttered with code that would only be used for some instances of the ghost. Since the ghosts also toggles between 4 different behaviours, the strategy pattern is also very useful for these switches. It also enables us to easily adjust or remove patterns without breaking the ghost class.

As for the behaviours, we opted to use a hypotenuse to check the shortest route to Pacman. This logic is a bit flawed and a breadth first search might have been better and more correct, but with limited time we opted for an OK solution and this is something we might fix in the future.

For changing the state we implemented an observer pattern. This was done as in our version of Pacman the ghosts change state (behaviour) at the same time for all ghosts. This decision was mainly due to incorporating the observer pattern. The StateSetter works as it is called when the state counters in GameEngine is done and then calls on each ghost to update the state(behaviour). The states are Strings and this is something where we could have implemented an actual state object instead as that would have made it more safe to call. We also used the Statesetter for closing and opening the ghost living room door.
Because the living room door needs to be open when ghosts are in there, and closed when they are in the maze, so as not to get stuck in the lower levels of the living room.
We fixed this by using the Streams API in maze to get an array of all the bricks that were used for the door, and then we change their type when the ghosts are in the state “wakeup” , that is, when we move them from the living room to the maze.



For creating Pacman and the ghost we used the abstract factory method. It worked out pretty well as we have two classes, PacMan and Ghosts which are related. Since we only have two different factories, we used a boolean to determine if we need a Pacman or ghost factory. The different factories run a check on which getCharacter:: to use, since Ghosts have an added parameter of String Color.

For the creation of Ghosts, we did have a conversation (and even implemented) subclasses of Ghost for each ghost type. However, we felt that there was not enough code being different and decided to split them up in the constructor, using the color as a means to this. We could have also added some automation to the creation of ghosts, but decided to keep that logic in GameEngine. As for the ghost Factory, we did split up ghosts into different kinds of ghosts (redGhost.java, etc.) to properly use the factory method, but for the simplicity of the structure, we opted to only use one ghost class instead of 4. This comes with a drawback of the proper implementation of GhostFactory, which we are aware of.

For the producer/Consumer problem we could not find a good way to incorporate it into the game directly, so we created a small message board, where the ghosts have a chance to send a message to Pacman which he reads and Pacman can also send messages to the ghosts. Here we solved some of the synchronizations that were required as well. For the synchronization we used a synchronized block on the LinkedList containing the messages, which enables us to have synchronization among multiple methods as well as the resource. The synchronized block on the resource is good because we have multiple producers (and consumers) trying to get access at the same time which can cause corruption in the resource.



Use of resources;
It could be discussed whether constantly iterating over an ArrayList of roughly 1000 objects each time a Ghost or Pacman is checking what moves are legal (when calling **Maze.INSTANCE.getBrick()**). Our conclusion is that we could probably make it less resource hungry, but with modern computers today this is unnecessary. To prove this claim, we tested temporarily rewriting the method to iterate over the Array 100 times before returning the **MazeBrick**, and there was still no obvious loss of performance.

However, we could have separated “isWall”- **MazeBricks** into their own ArrayList and kept the other MazeBricks in another. With this approach it would be possible to check "Is there a wall at position x, y ?" by only iterating over the isWall MazeBricks ArrayList instead of as we do now when iterating over all the Mazebricks; "What MazeBrick is at position X, Y?", and then asking the MazeBrick "are you a wall?". A second approach could have been adding a field to each  **MazeBrick** containing an array of legal directions to go from there.

Unexpectedly much time went to  troubleshooting problems related to the shortcut, or tunnel, which teleports characters from the left to the right side and vice versa. One problem was that sometimes when pacman is switching direction very far out on the screen, the isWall check could sometimes check for coordinates outside the Maze, and **Maze::getBrick()** wouldn’t return any **MazeBrick**. In order to not crash the program in these situations we let the **getBrick()** method return whatever “dummybrick” that is not a wall. This might not be an ideal solution but it works.



Our current code is clean and easy to read and follow. This we value higher than a non-notable performance loss.
Throughout the development of this game we have learned a lot, not only in terms of coding but also communication and collaboration. At an early stage one of us was working on the ghosts while the other one on pacman. Too late we realized that a lot of code that we both had implemented was supposed to accomplish the exact same thing. For example, checking if a nearby MazeBrick is a wall. After we realized this we started rewriting code in order to have more similar code in both classes, and then merged similarities into a superclass that both subclasses inherit. If we had been more experienced, and if we had planned better, we’d of course have done this from the beginning. This is one of things we definitely will do differently next time we do something similar again.

In general we are very satisfied with the result of this Pacman clone. It is definitely playable and we already have a fanclub of players enjoying it, our kids!
We still have tons of ideas about things we want to improve, like more levels (Maze’s), maybe online multiplayer features etc. And we believe that this project will live on after the end of this course. Actually one of the hardest tasks with this assignment was limiting the game to our timeframe.