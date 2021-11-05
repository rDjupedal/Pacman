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
around. From this point on we continued implementing the Maze and the ghosts.  



## Discussion
Througout the whole assignment we have had scalability in focus. The Maze-implementation reads the maze from a file
which makes it easy to modify / add more mazes. The format of the maze file is easy to understand and the file
can easily be modified in a text editor. A future feature however could be adding a Maze-editor.