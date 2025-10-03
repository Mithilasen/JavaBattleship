# JavaBattleship

🚢 Battleship Game
A complete Java implementation of the classic Battleship game with multiplayer support, built with object-oriented principles.

🎮 Features
Full Game Logic: Ship placement, hit detection, and sinking mechanics

Two-Player Mode: Play against a friend with turn-based gameplay

Fog of War: Hidden ship locations for strategic gameplay

Multiple Ship Types: 5 different ships with varying sizes

Input Validation: Comprehensive error checking and user feedback

Screen Management: Clean screen transitions between player turns

🎯 How to Play
Ship Placement Phase
Player 1 places all 5 ships on their 10x10 grid

Player 2 places their ships (no peeking!)

Ships must be placed in straight lines without overlapping and with 1-cell gap between ships

Battle Phase
Players take turns shooting at coordinates (e.g., "A1", "J10")

View shows opponent's foggy board (top) and your own ships (bottom)

Game continues until one player sinks all opponent's ships

Ship Types
Aircraft Carrier (5 cells)

Battleship (4 cells)

Submarine (3 cells)

Cruiser (3 cells)

Destroyer (2 cells)
```bash

🏗️ Project Structure
text
battleship/
├── Board.java          # Game board management
├── Ship.java           # Individual ship representation
├── Ships.java          # Ship types enum
├── Coordinates.java    # Coordinate parsing and validation
├── Main.java           # Game flow and multiplayer logic
└── ShotResult.java     # Shot outcome enumeration
```

🎯 Game Algorithms
Ship Placement Validation
Checks for straight-line placement and exact ship length matching

Ensures no ship overlap with 1-cell gap between ships

Hit Detection & Sinking
Tracks individual ship coordinates

Detects when all cells of a ship are hit

Provides appropriate feedback ("Hit!", "Sank a ship!")

Fog of War Implementation
Dual board system: real board (truth) vs fog board (player view)

Only reveals hits ('X') and misses ('M') to opponent

Keeps ship positions ('O') hidden

🛠️ Technical Highlights
Object-Oriented Design: Clean separation of concerns

Enum Usage: Type-safe ship definitions

Input Validation: Robust error handling

Algorithm Efficiency: Optimized ship tracking and hit detection

Multiplayer Architecture: Scalable turn-based system
