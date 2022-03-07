# IdLearn - learn algorithms through an idle game!

# Game description and the actual product

<h2> The objective </h2>
<hr>

Idlearn is an idle game that is both fun and educative. 

The clicker/idle genre shines with regard to how much it can capture the player's attention and focus them on simple tasks. The tasks performed in IdLearn however differ from typical clicking and instead have real meaning. The game's tasks are algorithmic and the objective is to make learning the basics of algorithmics fun by enriching the process with the core part of idle games, the reward system. 


<h2> The language </h2>
<hr>

IdLearn offers its own Turing-complete programming language - IdLang. Its aim is to be a beginner-friendly tool with a pleasant graphical interface. The languages components are linked together as drag-and-drop blocks able to be nested. Idlang may be simple but it performs well as an entry-level tool while still maintaining the core aspects of a programming language.

<h2> Gaining points</h2>
<hr>

Following the idle/clicker model, there are two main ways to earn points:
- The first (basic one) is performing a simple, repetitive task, typically clicking. In our case it's predicting the correct results of certain algorithms. The points gained through such correct predictions are awarded once after every successful guess.
- The more sophisticated way - automatic point generation. In the case of clicker games this usually involves a "tower" or some other entity that can be acquired with the in-game currency. In IdLang, what generates points this way are tasks for which the player has provided a correct algorithm.

<h2> Scalability - give me more points! </h2>
<hr>

As the game's objective is to promote the user's growth with regard to their algorithmic experience, the available tasks grow in difficulty and with them grows the number of points one is able to generate from a single task. Both point-gaining methods are also subject to bonuses, either by upgrading how many points a passed test gives or how fast the user's algorithms are executed (therefore, how quickly they can generate points).

<h2> What's the use of points anyway? </h2>
<hr>

Points are essential in order to unlock more in-game features allowing to gain more points to use for more features, which then... yeah, you get the idea.

<h3> The store </h3>
The in-game store offers:
  - points-per-test bonusesm
  - speed increments for automatic point generation,
  - higher memory limits,
  - IdLang data structures, code snippets and mini-libraries,
  - task hints,
  - backgrounds.

<h2> Tracking progress </h2>
<hr>

The game has an achievement system, a leaderboard and a user stats section, with data such as their tries, completed tasks, etc.


# The tools used in this project
- Java 17
- JavaFX
- Maven
- JUnit

Inspirations:
- the design of SIO2 "task packages" and automatic testing
- Scratch by MIT

# Minimum Viable Product
The game's most primitive yet functional form consists of:
1. A subset of IdLang
   - types: integer
   - assignment operator
   - logical and arithmetic operators
   - control flow: if, else
   - loops
   - basic input-output functions
2. One algorithmic task
   - some easy tests
   - simple text editor (for manually passed test output)
3. An interactive graphical layout
   - greeting message
   - main menu
4. Point system