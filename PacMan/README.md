# Project Assignment
## Environment & Tools
*Rasmus Djupedal*  
* Operating system Linux Mint 20.2 Cinnamon, 64-bit  
* IDE IntelliJ IDEA Edu 2021.2.1  
* Java OpenJDK 16  
* GIT version 2.25.1  
* Maven Apache 3.6.3   

*Tobias Liljeblad*  
* Operating system  
* IDE   
* Java OpenJDK  
* GIT version   
* Maven Apache  

## Sources
* Bitmap(wall) https://opengameart.org/content/wall-0  
* Sound effects https://freesound.org  

## Purpose
The purpose of this program is to build a clone of the old Pacman game while relying only on the standard libraries
of Java. The final game should look quite similar to the original but the aim is not a 100% resemblance in terms
of score, difficulty, ghost behaviour etc. 
We decided to write this program in a group. The strongest reasons for these are that we are finally permitted to discuss
code with each other, but also using GIT in a more realistic production-like way than with ordinary laborations. 

## Procedures
We started by setting up a shared google document and a Trello page containing notes while having a close communication
on Discord. We discussed approaches and the general structure of the program and how we are going to collaborate.
It was clear from the beginning that we need to extend a JFrame in which the game will be displayed and for a long time
the only container in this **PacmanFrame** was an extended JPanel, our **MazePanel**.
The next step was to create a simple Pacman, which was originally just a FilledOval reacting to key input and moving
around. The classes **Pacman** and **Ghost** do have many things in common, and therefore we decided to write a 
superclass **LivingCharacter** which both of these subclasses inherit. The **LivingCharacter** holds many datafields
and methods that both its subclasses use. The superclass also contains a template method **doMove** in which it dictates
that a move consists of these two steps, first deciding the new direction of the character and then makes the actual
move if possible.  This method is final, so it can't be overridden. 

From this point on we continued implementing the **Maze** class. We created the actual maze in a text file, where
different characters symbolize different **MazeBrick** objects, such as "W" for wall, "S" for Space, "C" for candy etc. 
The file was than read into a byte-array. From here we would iterate over this array and for each character create an
instance of the **MazeBrick**, with properties set in accordance to the object we were creating. Every **MazeBrick** 
also have an X- and Y- coordinate which would be calculated and passed together with the other arguments to the object.
This was the early implementation, later on we decided to make use of an Object Pool here, as it felt like a good
way to reuse roughly 1000 objects instead of discarding them and creating new ones at every game over or finish.

Any time Pacman or Ghost wants to move to a new position it must check that it is not a wall. It does this by first
obtaining the **MazeBrick** object at this position by calling **Maze.INSTANCE.getBrick()** and supplying coordinates as arguments.
The returned **MazeBrick** provides a method **isWall()** and returns a boolean value depending on if it is a wall or not.


## Discussion
Througout the whole assignment we have had scalability in focus. The Maze-implementation reads the maze from a file
which makes it easy to modify / add more mazes. The format of the maze file is easy to understand and the file
can easily be modified in a text editor. A future feature however could be adding a Maze-editor.

Use of resources;
It could be discussed whether constantly iterating over an ArrayList of roughly 1000 objects each time a Ghost or Pacman is
checking what moves are legal (when calling **Maze.INSTANCE.getBrick()**). Our conclusion is that we could probably make
this less resource hungry, but with modern computers today it is unnecessary. To prove this claim, I tested temporarily 
rewriting the method to iterate over the Array 100 times before returning the **MazeBrick**, and there was still no
obvious loss of performance.
However, we could have used different ArrayLists for different kinds of **MazeBricks**.
For example one ArrayList only containing MazeBricks that are walls.
With this approach it would be possible to check "Is there a wall at position x, y ?"  instead of as we do now
"What MazeBrick is at position X, Y?", then asking the MazeBrick "are you a wall?". Another approach could have been
adding a field to each **MazeBrick** containing legal directions to go from there.

Our current code is clean and easy to read and follow. This we value higher than a not notable performance lost.

In general we are very satisfied with the result of this Pacman clone. Its definitely playable and we already have
a some players enjoying it (our kids).